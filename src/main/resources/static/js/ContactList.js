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
            ctDelete: 0
        });

        const totalPage = await window.api.post('/contact/page', {
            pageSize,
            ctDelete: 0
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
        <button class="btn btn-sm btn-info me-1">详情</button>
        <button class="btn btn-sm btn-danger">屏蔽</button>
      </td>
    `;
        tbody.appendChild(tr);
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


loadContacts(currentPage);