/*// 注册按钮点击事件
$("#btn_register").click(function () {
    const userId = $("input[name='userId']").val();
    const userPassword = $("input[name='userPassword']").val();

    $.ajax({
        url: "/user/register",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            userId: userId,
            userPassword: userPassword
        }),
        success: function (data) {
            MyAlert(data, "Login.html");
        },
        error: function () {
            MyAlert({ status: "error", message: "注册请求失败，请检查网络连接或稍后再试" });
        }
    });
});

// 登录按钮点击事件
$("#btn_login").click(function () {
    const userId = $("input[name='userId']").val();
    const userPassword = $("input[name='userPassword']").val();

    $.ajax({
        url: "/user/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            userId: userId,
            userPassword: userPassword
        }),
        success: function (data) {
            MyAlert(data, "ContactList.html");
        },
        error: function () {
            MyAlert({ status: "error", message: "登录请求失败，请检查网络连接或稍后再试" });
        }
    });
});*/

// 存储 JWT 到 localStorage
function saveToken(data) {
    localStorage.setItem("jwtToken", data.data);
}

// 从 localStorage 获取 Token
function getToken() {
    return localStorage.getItem("jwtToken");
}

// 设置 Axios 默认请求头（自动添加 Token）
const apiClient = axios.create({
    baseURL: 'http://localhost:8080' // 根据你的后端地址修改
});

apiClient.interceptors.request.use(config => {
    const token = getToken();
    if (token) {
        config.headers['Authorization'] = token;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

// 注册按钮点击事件
document.getElementById("btn_register").addEventListener("click", function () {
    const userId = document.querySelector("input[name='userId']").value;
    const userPassword = document.querySelector("input[name='userPassword']").value;

    axios.post("/user/register", {
        userId: userId,
        userPassword: userPassword
    })
        .then(response => {
            MyAlert(response.data, "Login.html");
        })
        .catch(error => {
            MyAlert({ status: "error", message: "注册请求失败，请检查网络连接或稍后再试" });
        });
});

// 登录按钮点击事件
document.getElementById("btn_login").addEventListener("click", function () {
    const userId = document.querySelector("input[name='userId']").value;
    const userPassword = document.querySelector("input[name='userPassword']").value;

    axios.post("/user/login", {
        userId: userId,
        userPassword: userPassword
    })
        .then(response => {
            if (response.data.status === "success" && response.data.token) {
                saveToken(response.data.token); // 保存 JWT Token
            }
            MyAlert(response.data, "ContactList.html");
        })
        .catch(error => {
            MyAlert({ status: "error", message: "登录请求失败，请检查用户名密码或稍后再试" });
        });
});

// 自定义提示函数（替代 alert）
function MyAlert(data, to) {
    // 过滤掉 localhost 地址等调试信息
    let message = data.message.replace(/http?:\/\/[^ ]*localhost[^ \n]*/gi, '').trim();

    const alertBox = $('#custom-alert');

    // 设置提示消息文本
    alertBox.text(message);

    // 根据状态设置不同的背景颜色
    if (data.status === "success") {
        alertBox.css({
            'background-color': '#d4edda', // 成功时的背景颜色（浅绿色）
            'color': '#155724' // 文字颜色
        });
    } else {
        alertBox.css({
            'background-color': '#f8d7da', // 错误时的背景颜色（浅红色）
            'color': '#721c24' // 文字颜色
        });
    }

    // 显示提示框
    alertBox.fadeIn(300);

    // 2秒后自动隐藏提示框
    setTimeout(() => {
        alertBox.fadeOut(300);
    }, 2000);

    // 如果是成功状态并且有跳转目标，则延迟跳转
    if (data.status === "success" && to) {
        setTimeout(() => {
            window.location.href = to;
        }, 2500); // 稍微延迟跳转，确保提示可见
    }
}