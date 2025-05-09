<html lang="zh_CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="css/login.css" type="text/css">
</head>
<body>
<div id="app" class="container">
    <h4>账号密码登陆</h4>
    <form action="login/do" method="post">
        <span>用户名称</span>
        <label><input type="text" name="username" value="admin"/></label>
        <br>
        <span>用户密码</span>
        <label><input type="password" name="password" value="123456"/></label>
        <br>
        <span>图形验证码</span>
        <label><input class="graph" type="text" name="graphCode" value="1"/></label>
        <img id="imgCode" src="imgCode" @click="loginDo" alt="验证码"/>
        <br>
        <label><input hidden id="uuid" name="uuid" value="todo"></label>
        <input type="button" @click="loginDo" value="登陆">
        <label><input type="checkbox" name="rememberMe" value="true">记住我</label>
    </form>
    <hr>
    <h4>手机号登陆</h4>
    <#--noinspection HtmlUnknownTarget -->
    <form action="login/phone" method="post">
        <span>手机号</span>
        <label><input type="text" name="phone" value="19999999999"/></label>
        <br>
        <span>短信验证码</span>
        <label><input class="sms" type="text" name="smsCode" value="1234"/></label>
        <a href="basic/sms/code?phone=19999999999">发送验证码</a>
        <br>
        <input type="submit" value="登陆">
        <label><input type="checkbox" name="rememberMe" value="true">记住我</label>
    </form>
    {{imgCode}}
</div>
</body>
<script src="js/vue.js"></script>
<script src="js/axios.js"></script>
<script>
    const app = new Vue({
        el: '#app',
        data() {
            return {
                imgCode: "basic/graph/code?uuid=" + Math.random()
            }
        },
        methods: {
            loginDo() {
                this.imgCode = "basic/graph/code?uuid=" + Math.random();
            },
        },
        mounted() {
            this.imgCode = "basic/graph/code?uuid=" + Math.random();
            /*axios.get('data.json').then(res => {
                this.info = res.data.info;
            });*/
        }
    });
</script>
</html>