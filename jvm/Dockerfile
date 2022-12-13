ARG BASE_IMG=openjdk:8-jre-alpine
FROM ${BASE_IMG}
# Other args must be specified after FROM directive to work


# Double check to ensure BIN_NAME is set
RUN test -n "build/libs/jvm-0.0.1-SNAPSHOT.war" || (echo "Build-args BIN_NAME is required but missing." && false)

# Setup timezone & create /app/config dir which will be bound to host 
# dir /opt/dockerContainer/<host>/<conainer_name>/, where you could
# store the APP_OPTS and other files in need for app running.
RUN apk add tzdata \
  && cp /usr/share/zoneinfo/America/Toronto /etc/localtime \
  && mkdir -p /app/config 

ENV BASE_JAVA_OPTS -D"java.security.egd=file:/dev/./urandom" \
  -D"user.timezone=America/Toronto"

COPY build/libs/jvm-0.0.1-SNAPSHOT.war /app/app.jar

# Entry point only takes ENV var, ARG not work in it. https://vsupalov.com/docker-arg-vs-env/
#ENV ENV_CONTEXT=${WEB_CONTEXT}

# use grep -s in case the file doesn't exist. Also NOTE -jar and -cp are exclusive to each other !!!
RUN echo '#!/bin/sh' > /entrypoint.sh \
	&& echo 'set -x' >> /entrypoint.sh \
	&& echo 'export APP_OPTS=`grep -sv "^#" /app/config/APP_OPTS|tr "\n" " "`' >> /entrypoint.sh \
	&& echo 'sh -c "cd /app; exec java $BASE_JAVA_OPTS $JAVA_OPTS $APP_OPTS -jar app.jar"'  >> /entrypoint.sh \
	&& chmod a+x /entrypoint.sh
	 
ENTRYPOINT /entrypoint.sh
