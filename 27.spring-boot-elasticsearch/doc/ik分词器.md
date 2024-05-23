# 下载ik分词器：
    https://github.com/medcl/elasticsearch-analysis-ik/releases?page=4

在elasticsearch/plugins路径下创建ik文件夹，将压缩包放置在该文件夹下并解压

cd /data/elk-ayers/elasticsearch-7.10.1-node01/plugins/

mkdir ik
cd ik

unzip elasticsearch-analysis-ik-7.10.1.zip

# 重启ES

在elasticsearch.yml配置文件中加入一句index.analysis.analyzer.ik.type : “ik”，否则有可能报错

# 自定义词语配置
## 创建custom文件夹
在plugins/ik/config/目录下创建一个custom文件夹

cd /data/elk-ayers/elasticsearch-7.10.1-node01/plugins/ik/config
mkdir custom
## 2.创建my_word.dic文件
在custom文件夹下创建一个my_word.dic文件，直接在其中加入我们想要识别的词组

cd custom
vim my_word.dic

## 配置IKAnalyser.cfg.xml文件
在plugins/ik/config/目录下编辑IKAnalyser.cfg.xml，修改对应内容

vim /data/elk-ayers/elasticsearch-7.10.1-node01/plugins/ik/config/IKAnalyzer.cfg.xml