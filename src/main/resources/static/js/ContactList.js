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
        <button class="btn btn-sm btn-info me-1 detail-btn" data-id="${contact.ctId}">详情</button>
        <button class="btn btn-sm btn-danger delete-btn" data-id="${contact.ctId}">屏蔽</button>
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
            window.location.href = `DetailCt.html?ctId=${ctId}`;
        });
    });

    // 屏蔽（删除）按钮点击
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            if (!confirm('确定要屏蔽此联系人？')) return;

            const ctId = e.target.dataset.id;

            try {
                await window.api.del('/contact/delete', { ctId });

                // 删除成功后重新加载当前页
                loadContacts(currentPage);
            } catch (err) {
                console.error('屏蔽失败:', err);
                alert('屏蔽联系人失败');
            }
        });
    });
}


// 初始化用户信息（异步）
async function initUserInfo() {
    const token = window.api.getToken();

    if (!token) {
        alert('请先登录');
        window.location.href = 'Login.html';
        return;
    }

    try {
        const user = await window.api.get('/user/me');

        const avatarElement = document.getElementById("userAvatar");
        const nameElement = document.getElementById("userName");

        // 设置头像（优先用真实头像，否则用默认）
        const avatarUrl = user.data.picName
            ? user.data.picName
            : './images/default-avatar.jpg';

        avatarElement.src = avatarUrl;
        nameElement.textContent = user.data.userId;

    } catch (err) {
        console.error('获取用户信息失败:', err);
    }
}

// 绑定登出事件
document.getElementById("logoutBtn").addEventListener("click", () => {
    window.api.removeToken();
    localStorage.removeItem("user");
    alert("您已登出");
    window.location.href = "Login.html";
});

// 点击头像触发文件选择
document.getElementById('userAvatar').addEventListener('click', () => {
    document.getElementById('avatarInput').click();
});

// 文件选择后处理并上传
document.getElementById('avatarInput').addEventListener('change', async function (e) {
    const file = e.target.files[0];
    if (!file) return;

    // 检查是否为图片类型
    if (!file.type.startsWith('image/')) {
        alert('请选择一张图片');
        return;
    }

    // 实时预览
    const reader = new FileReader();
    reader.onload = function (event) {
        document.getElementById('userAvatar').src = event.target.result;
    };
    reader.readAsDataURL(file);

    // 构造表单数据
    const formData = new FormData();
    formData.append("picName", file); // 假设后端接收字段名为 "file"

    try {
        // 调用接口 /picture/update
        await window.api.up('/user/pic', formData)
            .then(response => {
                alert('头像更新成功！');
                initUserInfo();
            })
            .catch(error => {
                console.error('上传失败:', error);
                alert('头像上传失败，请重试。');
            });

    } catch (error) {
        console.error('上传错误:', error);
        alert('上传过程中发生错误');
    }
});

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