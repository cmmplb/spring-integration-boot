FROM registry.steam.crcloud.com/rancher/openjdk:8u275v2
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai

ADD logback-spring.xml logback-spring.xml
ADD elastic-apm-agent-1.19.0.jar /elastic-apm-agent-1.19.0.jar
ADD libsapjco3.so /usr/lib/libsapjco3.so
ADD crbr-basic-bp-center.jar crbr-basic-bp-center.jar
ADD start.sh start.sh
ADD fonts.conf fonts.conf
ADD run-jco.sh /run-jco.sh

RUN mkdir /usr/share/fonts/chinese
ADD simsun.ttc /usr/share/fonts/chinese/
RUN chmod -R 755 /usr/share/fonts/chinese
RUN cp fonts.conf /etc/fonts/fonts.conf
RUN fc-cache

RUN chmod +x start.sh
RUN chmod +x /run-jco.sh
RUN sh /run-jco.sh

EXPOSE 40001
ENTRYPOINT [ "./start.sh"]
