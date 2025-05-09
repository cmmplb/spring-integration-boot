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
    <a href="resource/info">查询资源</a>
    <a href="user/info">查询用户</a>
    <a href="annotation/info">查询注解控制</a>
    <#--noinspection HtmlUnknownTarget -->
    <a id="logout" href="logout">退出登陆</a>
</div>
</body>
<script type="application/javascript">
    $.get('personal/center/principal', {}, function (res) {
        $('#sp').html('欢迎您' + res.data.name)
    });
</script>
</html>