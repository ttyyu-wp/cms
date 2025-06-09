function initUserInfo() {
    const token = window.api.getToken();

    const avatarElement = document.getElementById("userAvatar");
    const nameElement = document.getElementById("userName");

    const user = window.api.get('user/me').data;
    // 设置头像（优先用真实头像，否则用默认）
    const avatarUrl = user.picName
        ? user.picName
        : `./images/default-avatar.jpg`;

    avatarElement.src = avatarUrl;
    nameElement.textContent = user.userId;

    // 绑定登出事件
    document.getElementById("logoutBtn").addEventListener("click", () => {
        window.api.removeToken();
        localStorage.removeItem("user");
        alert("您已登出");
        window.location.href = "Login.html";
    });
}

// 页面加载完成后执行
document.addEventListener("DOMContentLoaded", () => {
    initUserInfo();
});