/**
 * 为axios实例添加请求拦截器
 * 请求拦截器可以在发送请求之前对请求配置进行处理
 * @param {Object} axios - axios实例，用于添加请求拦截器
 */
function request(axios) {
  // 使用axios的请求拦截器方法，处理请求
  axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    console.log('request.config:', config);
    return config;
  }, function (error) {
    // 对请求错误做些什么
    console.log('request.error:', error);
    return Promise.reject(error);
  });
}

/**
 * 为 axios 实例添加响应拦截器
 * 该函数添加的拦截器会在每个请求返回的数据被处理之前执行
 * @param {Object} axios - axios 实例对象，用于拦截响应
 */
function response(axios) {
  // 使用 axios 实例的 interceptors.response 方法添加响应拦截器
  axios.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    console.log('response.response:', response);
    if (response.data.code === 403) {
      // 使用 ElementPlus 框架显示错误消息
      ElementPlus.ElMessage.error(response.data.msg);
      return;
    }
    return response;
  }, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    console.log('response.error:', error);
    // 判断错误状态码为 401 时的处理逻辑
    if (error.status === 401) {
      // 使用 ElementPlus 框架显示错误消息
      ElementPlus.ElMessage.error(error.response.data.msg);
      // 2秒后跳转到登录页
      setTimeout(() => {
        window.location.href = '/login';
      }, 2000);
    }
    // 判断错误状态码为 403 时的处理逻辑
    if (error.status === 403) {
      // 使用 ElementPlus 框架显示错误消息
      ElementPlus.ElMessage.error(error.response.data.msg);
    }
    // 将错误以 Promise 的 reject 形式返回，以便调用者可以进一步处理错误
    return Promise.reject(error);
  });
}