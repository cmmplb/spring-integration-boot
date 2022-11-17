````shell
安装指南
````
https://helm.sh/zh/docs/intro/install/


````shell
教程
````
https://zhuanlan.zhihu.com/p/511413922



问题
二、INSTALLATION FAILED: cannot re-use a name that is still in use
#执行helm安装时报错
Error: INSTALLATION FAILED: cannot re-use a name that is still in use

解决：

helm ls --all-namespaces
kubectl delete namespace qsh-test
kubectl create namespace qsh-test
三、Namespace无法删除
unable to create new content in namespace posthog because it is being terminated

现象：

##命名空间一直处于Terminating状态
[ec2-user@eks posthog]$ kubectl get ns -owide
NAME                       STATUS        AGE
default                    Active        3d
kube-node-lease            Active        3d
kube-public                Active        3d
kube-system                Active        3d
posthog                    Terminating   3h23m

##执行强制删除命令会一直卡住
[ec2-user@eks posthog]$ kubectl delete ns posthog --grace-period=0 --force
warning: Immediate deletion does not wait for confirmation that the running resource has been terminated. The resource may continue to run on the cluster indefinitely.
\namespace "posthog" force deleted
解决：

##查看posthog的命名空间描述
kubectl get ns posthog -o json > ns-posthog.json

##删除spec
###删除前内容如下：
    "spec": {
        "finalizers": [
            "kubernetes"
        ]
    },

###删除后内容如下：
    "spec": {
    },

##打开一个新窗口运行kubectl proxy跑一个API代理在本地的8081端口
kubectl proxy --port=8081

##curl删除
curl -k -H "Content-Type:application/json" -X PUT --data-binary @ns-posthog.json http://127.0.0.1:8081/api/v1/namespaces/posthog/finalize

##重新检查，发现已删除
kubectl get ns