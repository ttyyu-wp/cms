document.addEventListener("DOMContentLoaded", function () {
    const btnLogin = document.getElementById("btn_login");
    const btnRegister = document.getElementById("btn_register");

    if (btnLogin) {
        btnLogin.addEventListener("click", async function () {
            const userId = document.querySelector("input[name='userId']").value;
            const userPassword = document.querySelector("input[name='userPassword']").value;

            try {
                // 登录获取 token
                const response = await window.api.post("/user/login", {
                    userId,
                    userPassword
                });

                if (response.status === "success") {
                    // 保存 token
                    window.api.setToken(response.data);

                    // 获取用户信息
                    const userResponse = await window.api.get("/user/me");
                    localStorage.setItem("user", JSON.stringify(userResponse.data));

                    MyAlert({ status: "success", message: "登录成功" }, "ContactList.html");
                } else {
                    MyAlert({ status: "error", message: response.message });
                }
            } catch (error) {
                // 错误已在 handleError 处理
            }
        });
    }

    if (btnRegister) {
        btnRegister.addEventListener("click", async function () {
            const userId = document.querySelector("input[name='userId']").value;
            const userPassword = document.querySelector("input[name='userPassword']").value;

            try {
                const response = await window.api.post("/user/register", {
                    userId,
                    userPassword
                });

                if (response.status === "success") {
                    MyAlert({ status: "success", message: "注册成功，请登录" }, "Login.html");
                } else {
                    MyAlert({ status: "error", message: response.message });
                }
            } catch (error) {
                // 错误已在 handleError 处理
            }
        });
    }
});