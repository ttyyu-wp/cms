// js/api.js

const API_BASE_URL = 'http://localhost:8080'; // 后端地址

// 创建 axios 实例
const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true
});

function getToken() {
    return localStorage.getItem('token');
}

function setToken(token) {
    localStorage.setItem('token', token);
}

function removeToken() {
    localStorage.removeItem('token');
}

apiClient.interceptors.request.use(config => {
    const token = getToken();
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

async function get(url, params = {}) {
    try {
        const response = await apiClient.get(url, { params });
        return response.data;
    } catch (error) {
        handleError(error);
    }
}

async function post(url, body) {
    try {
        const response = await apiClient.post(url, body);
        return response.data;
    } catch (error) {
        handleError(error);
    }
}

async function del(url, params = {}) {
    try {
        const response = await apiClient.delete(url, { params });
        return response.data;
    } catch (error) {
        handleError(error);
    }
}

async function upload(url, formData) {
    try {
        const config = {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            withCredentials: true
        };

        // 添加 token 到 header（如果需要）
        const token = getToken();
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }

        const response = await axios.post(url, formData, config);
        return response.data;
    } catch (error) {
        handleError(error);
    }
}

function handleError(error) {
    console.error('API 请求出错:', error);
    const message = error.response?.data?.message
        || error.message
        || '请求失败';
    MyAlert({ status: "error", message });
    throw error;
}

// 挂载为全局函数
window.api = {
    get,
    post,
    del: del,
    getToken,
    setToken,
    removeToken,
    up: upload
};