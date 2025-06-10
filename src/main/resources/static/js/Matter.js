let currentPage = 1;
let totalPages = 1; // 新增变量用于保存总页数
const pageSize = 5;
async function loadMatterList(page, matter = '', matterDelete = '', isAsc = '') {
    try {
        const matterRe = await window.api.post('/matter/all', {
            pageNo: page,
            pageSize: pageSize,
            matter: matter,
            matterDelete: matterDelete,
            isAsc: isAsc
        });

        const res = matterRe.data.records;

        const matters = res || [];
        const total = matterRe.data.pages || 1;

        // 更新总页数
        totalPages = total;
        const tbody = document.getElementById('matterTableBody');
        tbody.innerHTML = '';

        if (matters.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" class="text-center">暂无事项记录</td></tr>';
            return;
        }

        matters.forEach(matter => {
            const row = document.createElement('tr');

            let buttons = '<td>';

            // 显示的按钮根据当前状态决定
            if (matter.matterDelete === 0) {
                // 待完成状态：显示 取消 和 完成
                buttons += `
                        <button class="btn btn-sm btn-danger me-2" onclick="updateStatus('${matter.matterId}', 1)">取消</button>
                        <button class="btn btn-sm btn-success" onclick="updateStatus('${matter.matterId}', 2)">完成</button>
                    `;
            } else if (matter.matterDelete === 1) {
                // 取消状态：显示 待完成 和 完成
                buttons += `
                        <button class="btn btn-sm btn-secondary me-2" onclick="updateStatus('${matter.matterId}', 0)">待完成</button>
                        <button class="btn btn-sm btn-success" onclick="updateStatus('${matter.matterId}', 2)">完成</button>
                    `;
            } else if (matter.matterDelete === 2) {
                // 已完成状态：显示 待完成 和 取消
                buttons += `
                        <button class="btn btn-sm btn-secondary me-2" onclick="updateStatus('${matter.matterId}', 0)">待完成</button>
                        <button class="btn btn-sm btn-danger" onclick="updateStatus('${matter.matterId}', 1)">取消</button>
                    `;
            }

            buttons += '</td>';

            row.innerHTML = `
                    <td>${matter.ctName}</td>
                    <td>${matter.matterTime}</td>
                    <td>${matter.matter}</td>
                    ${buttons}
                `;

            tbody.appendChild(row);
        });

        // 更新分页信息
        document.getElementById('pageInfo').innerText = `第 ${page} 页 / 共 ${total} 页`;
        currentPage = page;

    } catch (err) {
        console.error('加载事项失败:', err);
        alert('加载事项失败');
    }
}

// 修改事项状态
async function updateStatus(matterId, matterDelete) {
    if (!confirm(matterDelete === 1 ? '确认取消该事项？' : '确认标记为已完成？')) return;

    try {
        await window.api.post('/matter/deleteByDel', {
            matterId: matterId,
            matterDelete: matterDelete
        });
        alert('状态更新成功');
        loadMatterList(currentPage); // 刷新当前页
    } catch (err) {
        console.error('状态更新失败:', err);
        alert('状态更新失败');
    }
}

document.getElementById('sortOrder').addEventListener('change', () => {
    const { keyword, status, order } = getSearchParams();
    loadMatterList(1, keyword, status, order);
});

// 绑定按钮事件
document.getElementById('searchBtn').addEventListener('click', () => {
    const { keyword, status, order } = getSearchParams();
    loadMatterList(1, keyword, status, order);
});

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 1) {
        const { keyword, status, order } = getSearchParams();
        loadMatterList(currentPage - 1, keyword, status, order);
    }
});

document.getElementById('nextPage').addEventListener('click', () => {
    if (currentPage < totalPages) { // 只能翻到最大页码
        const { keyword, status, order } = getSearchParams();
        loadMatterList(currentPage + 1, keyword, status, order);
    }
});

// 当状态筛选下拉框改变时，重新加载数据
document.getElementById('statusFilter').addEventListener('change', () => {
    const { keyword, status, order } = getSearchParams();
    loadMatterList(1, keyword, status, order);
});

function getSearchParams() {
    return {
        keyword: document.getElementById('searchInput').value,
        status: document.getElementById('statusFilter').value,
        order: document.getElementById('sortOrder').value
    };
}

// 页面初始化
window.onload = () => {
    initUserInfo();
    loadMatterList(1, '', '', '');
};