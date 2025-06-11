// 全局变量

let currentPage = 1;
let totalPages = 1;
let contacts = [];
const pageSize = 5;



// 加载联系人数据
async function loadContacts(page = 1) {
    const ctName = document.getElementById('searchInput').value || null;
    const ctMf = document.getElementById('genderFilter').value === 'all' ? null : document.getElementById('genderFilter').value;

    try {
        const res = await window.api.post('/contact/list', {
            pageNo: page,
            pageSize,
            ctName,
            ctMf,
            ctDelete: 1
        });

        const totalPage = await window.api.post('/contact/page', {
            pageSize,
            ctDelete: 1
        });

        contacts = res.data || [];
        totalPages = totalPage.data || 1;
        currentPage = page;

        renderContacts();
        updatePagination();

    } catch (err) {
        console.error('联系人加载失败:', err);
        document.getElementById('contactTableBody').innerHTML = '<tr><td colspan="4" class="text-danger text-center">加载失败</td></tr>';
    }
}

// 渲染联系人
function renderContacts() {
    const tbody = document.getElementById('contactTableBody');
    tbody.innerHTML = '';

    if (!contacts.length) {
        tbody.innerHTML = '<tr><td colspan="4" class="text-center">暂无数据</td></tr>';
        return;
    }

    contacts.forEach(contact => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
      <td>${contact.ctName}</td>
      <td>${contact.ctMf}</td>
      <td>${contact.ctPhone}</td>
      <td>
        <button class="btn btn-sm btn-info me-1 detail-btn" data-id="${contact.ctId}">详情</button>
        <button class="btn btn-sm btn-danger delete-btn" data-id="${contact.ctId}">还原</button>
      </td>
    `;
        tbody.appendChild(tr);
    });

    // 绑定按钮点击事件
    bindContactActions();
}

function bindContactActions() {
    // 详情按钮点击
    document.querySelectorAll('.detail-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const ctId = e.target.dataset.id;
            window.location.href = `DetailCt.html?ctId=${ctId}&ctDelete=1`;
        });
    });

    // 屏蔽（删除）按钮点击
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const ctId = e.target.dataset.id;

            const result = await Swal.fire({
                title: '确认操作',
                text: '确定要解除屏蔽此联系人？',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                reverseButtons: true,
                customClass: {
                    confirmButton: 'btn btn-primary me-2',
                    cancelButton: 'btn btn-secondary'
                },
                buttonsStyling: false
            });

            if (!result.isConfirmed) return;

            try {
                await window.api.get('/contact/cancel', { ctId });
                MyAlertByStr("解除屏蔽成功!", true);
                // 删除成功后重新加载当前页
                loadContacts(currentPage);
            } catch (err) {
                console.error('解除屏蔽失败:', err);
                MyAlertByStr('解除屏蔽联系人失败', false);
            }
        });
    });
}

// 更新分页信息
function updatePagination() {
    const pageInfo = document.getElementById('pageInfo');
    pageInfo.textContent = `第 ${currentPage} 页 / 共 ${totalPages} 页`;
}

// 搜索按钮点击事件
document.getElementById('searchBtn').addEventListener('click', () => {
    currentPage = 1;
    loadContacts(currentPage);
});

// 翻页按钮
document.getElementById('nextPage').addEventListener('click', () => {
    if (currentPage < totalPages) {
        loadContacts(currentPage + 1);
    }
});
document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 1) {
        loadContacts(currentPage - 1);
    }
});


document.addEventListener("DOMContentLoaded", async () => {
    initUserInfo();
    loadContacts(currentPage); // 再加载联系人
});