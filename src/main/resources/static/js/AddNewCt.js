document.getElementById('addContactForm').addEventListener('submit', function(event){
    event.preventDefault(); // 阻止默认提交行为

    const contact = {
        ctName: document.getElementById('ctName').value.trim(),
        ctPhone: document.getElementById('ctPhone').value.trim(),
        ctMf: document.getElementById('ctGender').value,
        ctAd: document.getElementById('ctAddress').value.trim(),
        ctYb: document.getElementById('ctZipcode').value.trim(),
        ctQq: document.getElementById('ctQQ').value.trim(),
        ctWx: document.getElementById('ctWeChat').value.trim(),
        ctEm: document.getElementById('ctEmail').value.trim(),
        ctBirth: document.getElementById('ctBirth').value,
    };

    window.api.post('/contact/add', contact)
        .then(response => {
            // 检查是否是 success 状态
            if (response.status === 'success') {
               MyAlertByStr(response.message, true);
                document.getElementById('addContactForm').reset();
                // 跳转到联系人列表页
                window.location.href = 'ContactList.html';
            } else {
                MyAlertByStr(response.message, false);
            }
        })
        .catch(error => {
            // 如果请求失败或服务器异常，进入 catch
            console.error('联系人添加失败:', error);

            // 尝试获取错误信息
            const errorMessage = error.response?.data?.message
                || error.message
                || '网络错误，请稍后再试';

            MyAlertByStr(errorMessage, false);
        });
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

    // 文件大小限制判断（1MB）
    const MAX_SIZE = 1024 * 1024; // 1MB
    if (file.size > MAX_SIZE) {
        MyAlertByStr("头像大小不能超过 1MB，请重新选择", false);
        return;
    }

    // 提交到后端
    const formData = new FormData();
    formData.append('picName', file); // 假设后端接收字段名为 picFile

    // 使用 axios 发送请求（需要支持 multipart/form-data）
    window.api.up('/picture/add', formData)
        .then(response => {
            MyAlertByStr('头像更新成功！', true);
        })
        .catch(error => {
            console.error('上传失败:', error);
            MyAlertByStr('头像上传失败，请重试。', false);
        });
});


document.addEventListener("DOMContentLoaded", async () => {
    initUserInfo();
});