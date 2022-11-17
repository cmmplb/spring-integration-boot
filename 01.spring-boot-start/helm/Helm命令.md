
#### 语法校验
Helm lint project_name

#### 在result目录模板渲染之后的配置文件
Helm template project_name --output-dir ./result

#### 预安装校验
预安装会模拟正式的安装流程，但不会在环境上真正部署安装包。
Helm install --dry-run --debug [release-name] project_name

#### 部署
Helm install [release-name] project_name

#### 覆盖参数值
但有个遗留问题，values.yaml 中包含了默认的安装参数，但是诸如数据库的ip、username、password，若我们不想去修改安装包，如何在安装的时候进行覆盖呢？我们只要在 install 时使用 set
选项，设置想要覆盖的参数值即可。

Helm install [release-name] project_name--set config.mysql.server=100.71.32.11 用户也可以在安装时指定自己的values.yaml配置。
例如用户在升级的时候用 upgrade 命令，指定新的参数配置文件，即可实现在原有部署好的应用的基础上变更配置。命令如下：

Helm install [release-name] project_name -f my-values.yaml
Helm upgrade [release-name] project_name -f my-new-values.yaml

````
kubectl create secret docker-registry aliyun-registry --docker-server=registry.cn-hangzhou.aliyuncs.com --docker-username=xxx --docker-password=xxx -n cmmplb
kubectl create secret docker-registry aliyun-registry（别名） --docker-server=Harbor 地址 --docker-username = 账户 --docker-password=Harbor 密码 --namespace = 项目所在命名空间
````

#### 官方常用函数

https://helm.sh/zh/docs/chart_template_guide/builtin_objects/

#### helm-Charts常用函数
````
quote: 从.Values中读取的值变成字符串的时候就可以通过调用quote模板函数来实现,处理字符串的函数,作用是强制转换为字符串
upper 转大写
repeat COUNT STRING:返回给定参数特定的次数,以下结果：drink: "coffeecoffeecoffeecoffeecoffee"
define: 定义一个模板,可以在整个chart中使用
include: 引用一个定义的模板
default: 允许我们在模板内部指定默认值，以防止该值被忽略掉了
trunc: 截断字符串
trimSuffix: 修剪字符串中的后缀
contains: 测试以查看一个字符串是否包含在另一个字符串中
nindent: 缩进指定空格数
template: 使用define定义的模板
with: 修改范围
range: 遍历列表
printf: 打印
````