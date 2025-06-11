// 页面加载完成后获取联系人列表
window.onload = async function () {
    try {
        const res = await window.api.get('/contact/names');
        const contacts = res.data || [];

        const select = document.getElementById('contactSelect');

        contacts.forEach(contact => {
            const option = document.createElement('option');
            option.value = contact.ctId; // ctId 存储为 value
            option.textContent = contact.ctName; // 显示 ctName
            //option.dataset.ctId = contact.ctId; // 可选：存储额外属性
            select.appendChild(option);
        });
    } catch (err) {
        console.error('获取联系人失败:', err);
        alert('无法加载联系人列表，请刷新页面重试。');
    }
};

// 表单提交逻辑
document.getElementById('addMatterForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const contactSelect = document.getElementById('contactSelect');
    const matterTime = document.getElementById('matterTime').value;
    const matter = document.getElementById('matter').value;

    const selectedOption = contactSelect.options[contactSelect.selectedIndex];
    const ctId = selectedOption ? selectedOption.value : null;

    if (!ctId || !matterTime || !matter) {
        alert('请填写完整信息');
        return;
    }

    try {
        await window.api.post('/matter/add', {
            ctId: ctId,
            matterTime: matterTime,
            matter: matter,
        });

        alert('事项添加成功');
        window.location.href = 'Matter.html'; // 跳转回事项列表页
    } catch (err) {
        console.error('事项添加失败:', err);
        alert('事项添加失败');
    }
});

document.getElementById('matter').addEventListener('input', function () {
    const count = this.value.length;
    document.getElementById('wordCount').textContent = count;
});

// 页面初始化
window.onload = () => {
    initUserInfo();
};