// 初始化用户信息（异步）
async function initUserInfo() {
    const token = window.api.getToken();

    if (!token) {
        MyAlertByStr('请先登录', false);
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
    MyAlertByStr("您已登出", false);
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
