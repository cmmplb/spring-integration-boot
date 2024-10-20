md文档格式

颜色

<font color="red">中间写上想说的话</font>

# 2.字体

````
**这是加粗的文字**
*这是倾斜的文字*`
***这是斜体加粗的文字***
~~这是加删除线的文字~~
````

效果是这样的：

**这是加粗的文字**

*这是倾斜的文字*`

***这是斜体加粗的文字***

~~这是加删除线的文字~~

````
/ :可以用来转义 '*,/,-,>'等符号
````
# 3. 引用

````
> 这是引用的内容
>> 这是引用的内容
>>> 这是引用的内容
````

> 这是引用的内容
>> 这是引用的内容
>>> 这是引用的内容

# 4. 分割线

````
---
***
````

效果:

---
***

# 5.图片（直接拖就行）

````
![图片alt](图片地址 ''图片title'')
````

图片alt就是显示在图片下面的文字, 相当于对图片内容的解释。 图片title是图片的标题, 当鼠标移到图片上时显示的内容。title可加可不加

# 6. 超链接

````
！[超链接名](超链接地址 "超链接title")
   title可加可不加 
````

# 7.列表

## 无序列表

````
- 列表内容
+ 列表内容
* 列表内容

注意：- + * 跟内容之间都要有一个空格
````

- 列表内容

+ 列表内容

* 列表内容

## 有序列表

````
1. 列表内容 
2. 列表内容 
3. 列表内容

注意：序号跟内容之间要有空格
````

1. 列表内容
2. 列表内容
3. 列表内容

# 8.表格

````
| 表头  | 表头  |  表头 |
|-----|:---:|----:|
| 内容  | 内容  |  内容 |
| 内容  | 内容  |  内容 |

第二行分割表头和内容。
- 有一个就行, 为了对齐, 多加了几个 
文字默认居左 
    -两边加：表示文字居中 
    -右边加：表示文字居右 
注：原生的语法两边都要用 | 包起来。此处省略
````

````
例子一
|123|234|345|
|:-|:-:|-:|
|abc|bcd|cde|
|abc|bcd|cde|
|abc|bcd|cde|

//例子二
|123|234|345|
|:---|:---:|---:|
|abc|bcd|cde|
|abc|bcd|cde|
|abc|bcd|cde|

//例子三
123|234|345
:-|:-:|-:
abc|bcd|cde
abc|bcd|cde
abc|bcd|cde

上述实例效果相同, 得出亮点结论：

表格的格式不一定要对的非常起, 但是为了良好的变成风格, 尽量对齐是最好的
分割线后面的冒号表示对齐方式, 写在左边表示左对齐, 右边为右对齐, 两边都写表示居中
````

| 表头  | 表头  |  表头 |
|-----|:---:|----:|
| 内容  | 内容  |  内容 |
| 内容  | 内容  |  内容 |

# 9. 代码

单行代码：1个反单引号

`ssss`

多行代码：3个`

```
代码内容
```