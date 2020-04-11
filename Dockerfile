FROM java:8
COPY target/shortUrl-0.0.1-exec.jar shortUrl.jar
ENTRYPOINT exec java $JAVA_OPTS -jar shortUrl.jar