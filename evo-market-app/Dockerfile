# 基础镜像
FROM openjdk:17-jre-slim

# 作者
MAINTAINER bread

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/evo-market-app.jar /evo-market-app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /evo-market-app.jar $PARAMS"]