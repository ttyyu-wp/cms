// 从 localStorage 获取 Token
function getToken() {
    return localStorage.getItem("jwtToken");
}

// 设置 Axios 默认请求头（自动添加 Token）
const apiClient = axios.create({
    baseURL: 'http://localhost:8080' // 根据你的后端地址修改
});


apiClient.interceptors.request.use(config => {
    const token= getToken();
    if (token) {
        config.headers['Authorization'] = token;
    }
    return config;
}, error => {
    return Promise.reject(error);
});