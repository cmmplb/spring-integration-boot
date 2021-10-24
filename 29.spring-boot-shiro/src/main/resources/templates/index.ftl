<html lang="zh_CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>首页</title>
</head>
<script type="application/javascript" src="js/jquery-1.10.2.min.js"></script>
<body>
<div style="text-align:center">
    <h1>首页</h1>
    <div><span id="sp"></span></div>
    <a href="annotation/none">无校验</a>
    <a href="annotation/has/role">角色校验</a>
    <a href="annotation/has/permission">权限校验</a>
    <a href="annotation/has/all">多个满足</a>
    <#--noinspection HtmlUnknownTarget -->
    <a id="logout" href="logout">退出登陆</a>
</div>
</body>
<script type="application/javascript">
    $.get('personal/center/shiro/principal', {}, function (res) {
        $('#sp').html('欢迎您' + res.data.username)
    });
</script>
</html>