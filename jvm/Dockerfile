ARG BASE_IMG=openjdk:8-jre-alpine
FROM ${BASE_IMG}
ARG ENV_CONTEXT=jvm
RUN test -n "build/libs/jvm-0.0.1-SNAPSHOT.war" || (echo "Build-args BIN_NAME is required but missing." && false)

RUN apk add tzdata \
  && cp /usr/share/zoneinfo/America/Toronto /etc/localtime \
  && mkdir -p /app/config 

ENV BASE_JAVA_OPTS -D"java.security.egd=file:/dev/./urandom" \
  -D"user.timezone=America/Toronto"

COPY build/libs/jvm-0.0.1-SNAPSHOT.war /app/app.jar

RUN echo '#!/bin/sh' > /entrypoint.sh \
	&& echo 'set -x' >> /entrypoint.sh \
	&& echo 'sh -c "cd /app; exec java $BASE_JAVA_OPTS -DpublishAuthPwd=$JAVA_OPTS -jar app.jar --server.servlet.context-path=/$ENV_CONTEXT"'  >> /entrypoint.sh \
	&& chmod a+x /entrypoint.sh
	 
ENTRYPOINT /entrypoint.sh
