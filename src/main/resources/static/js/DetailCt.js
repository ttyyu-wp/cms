

function getUrlParam(name) {
    const search = window.location.search || window.location.hash.split('?')[1] || '';
    const params = new URLSearchParams(search);
    return params.get(name);
}

const ctId = getUrlParam('ctId');

if (!ctId) {
    alert("无效的联系人 ID");
    window.location.href = "ContactList.html";
}

let picture = null;

if (ctId) {
    window.api.post('/picture/ctPic', { ctId: ctId })
        .then(response => {
            picture = response.data
            const picUrl = picture.picName;
            if (picUrl) {
                document.getElementById('contact-pic').src = picUrl;
            }
        })
        .catch(error => {
            console.error('加载图片失败:', error);
        });
}

let currentContact = null;

async function loadContactDetail() {
    try {
        const res = await window.api.post('/contact/one', {
            ctId: ctId,
            ctDelete: 0
        });

        currentContact = res.data;

        document.getElementById('detail-name').textContent = currentContact.ctName || '无';
        document.getElementById('detail-gender').textContent = currentContact.ctMf || '无';
        document.getElementById('detail-phone').textContent = currentContact.ctPhone || '无';
        document.getElementById('detail-address').textContent = currentContact.ctAd || '无';
        document.getElementById('detail-zipcode').textContent = currentContact.ctYb || '无';
        document.getElementById('detail-qq').textContent = currentContact.ctQq || '无';
        document.getElementById('detail-wechat').textContent = currentContact.ctWx || '无';
        document.getElementById('detail-email').textContent = currentContact.ctEm || '无';
        document.getElementById('detail-birth').textContent = currentContact.ctBirth || '无';

    } catch (err) {
        console.error('加载联系人详情失败:', err);
        alert('加载联系人详情失败');
        window.location.href = "ContactList.html";
    }
}

// 绑定编辑按钮点击事件
document.getElementById('editBtn').addEventListener('click', () => {
    if (!currentContact) {
        alert("数据未加载完成");
        return;
    }

    // 填充表单字段
    document.getElementById('edit-ctId').value = currentContact.ctId;
    document.getElementById('edit-ctName').value = currentContact.ctName;
    document.getElementById('edit-ctMf').value = currentContact.ctMf;
    document.getElementById('edit-ctPhone').value = currentContact.ctPhone;
    document.getElementById('edit-ctAd').value = currentContact.ctAd;
    document.getElementById('edit-ctYb').value = currentContact.ctYb;
    document.getElementById('edit-ctQq').value = currentContact.ctQq;
    document.getElementById('edit-ctWx').value = currentContact.ctWx;
    document.getElementById('edit-ctEm').value = currentContact.ctEm;
    document.getElementById('edit-ctBirth').value = currentContact.ctBirth;

    // 显示模态框
    const modal = new bootstrap.Modal(document.getElementById('editContactModal'));
    modal.show();
});

// 表单提交处理
document.getElementById('editContactForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const updatedContact = {
        ctId: document.getElementById('edit-ctId').value,
        ctName: document.getElementById('edit-ctName').value,
        ctMf: document.getElementById('edit-ctMf').value,
        ctPhone: document.getElementById('edit-ctPhone').value,
        ctAd: document.getElementById('edit-ctAd').value,
        ctYb: document.getElementById('edit-ctYb').value,
        ctQq: document.getElementById('edit-ctQq').value,
        ctWx: document.getElementById('edit-ctWx').value,
        ctEm: document.getElementById('edit-ctEm').value,
        ctBirth: document.getElementById('edit-ctBirth').value,
        ctDelete: currentContact.ctDelete // 保留原状态
    };

    try {
        await window.api.post('/contact/update', updatedContact);
        alert('更新成功');
        loadContactDetail(); // 刷新详情页
        bootstrap.Modal.getInstance(document.getElementById('editContactModal')).hide();
    } catch (err) {
        console.error('更新失败:', err);
        alert('更新联系人失败，请重试');
    }
});

const picInput = document.getElementById('picInput');
const contactPic = document.getElementById('contact-pic');

// 点击图片触发文件选择
contactPic.addEventListener('click', () => {
    picInput.click();
});

// 选择文件后预览并上传
picInput.addEventListener('change', () => {
    const file = picInput.files[0];
    if (!file) return;

    // 使用 FileReader 显示本地预览图
    const reader = new FileReader();
    reader.onload = function (e) {
        contactPic.src = e.target.result;
    };
    reader.readAsDataURL(file);

    // 提交到后端
    const formData = new FormData();
    formData.append('ctId', ctId);
    formData.append('picId', picture.picId);
    formData.append('picName', file); // 假设后端接收字段名为 picFile

    // 使用 axios 发送请求（需要支持 multipart/form-data）
    window.api.up('/picture/update', formData)
        .then(response => {
            alert('头像更新成功！');
        })
        .catch(error => {
            console.error('上传失败:', error);
            alert('头像上传失败，请重试。');
        });
});

// 加载详情
loadContactDetail();