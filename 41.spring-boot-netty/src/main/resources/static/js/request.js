import axios from '/static/js/axios.min-0.21.1.js';

// 创建axios实例
const service = axios.create({
    baseURL: "", // url = base url + request url
    // withCredentials: true, // 跨域请求时发送cookies
    timeout: 1000000, // request timeout
    headers: {"Content-Type": "application/json"}
});

// request interceptor
// 请求拦截
service.interceptors.request.use(
    config => {
        let token = localStorage.getItem("token");
        if (token) {
            config.headers["token"] = token;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// response interceptor
// 响应拦截
service.interceptors.response.use(
    response => {
        const res = response.data;
        if (res.code === 2000) {
            // 未登录 删除本地存储的token
            if (localStorage.getItem("token")) {
                localStorage.removeItem("token");
            }
            window.location.href = "/";
            return;
        }
        if (res.code !== 200) {
            return res;
        }
        return res.data;
    },
    error =>{
        console.log(error); // for debug
        return Promise.reject(error);
    }
);

export default service;