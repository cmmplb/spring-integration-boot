<html lang="zh_CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="css/login.css" type="text/css">
</head>
<body>
<div style="text-align:center">
    <h4>账号密码登陆</h4>
    <#--noinspection HtmlUnknownTarget -->
    <form action="login" method="post">
        <span>用户名称</span>
        <label><input type="text" name="username" value="admin"/></label>
        <br>
        <span>用户密码</span>
        <label><input type="password" name="password" value="123456"/></label>
        <br>
        <span>图形验证码</span>
        <label><input class="graph" type="text" name="graphCode"/></label>
        <img id="imgCode" src="basic/graph/code?uuid=todo" onclick="this.src='basic/graph/code?uuid='+Math.random()" alt="验证码"/>
        <br>
        <label><input hidden name="uuid" value="todo"></label>
        <input type="submit" value="登陆">
        <label><input type="checkbox" name="isRemeberMe" value="true">记住我</label>
    </form>
    <hr>
    <h4>手机号登陆</h4>
    <#--noinspection HtmlUnknownTarget -->
    <form action="login/mobile" method="post">
        <span>手机号</span>
        <label><input type="text" name="mobile" value="19999999999"/></label>
        <br>
        <span>短信验证码</span>
        <label><input class="sms" type="text" name="smsCode" value="1234"/></label>
        <a href="basic/sms/code?mobile=19999999999">发送验证码</a>
        <br>
        <input type="submit" value="登陆">
        <label><input type="checkbox" name="isRemeberMe" value="true">记住我</label>
    </form>
</div>
</body>
</html>