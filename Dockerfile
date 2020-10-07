FROM oysteinjakobsen/armv7-oracle-java8
VOLUME /tmp
ADD web_dev-0.0.1-SNAPSHOT.jar myblog.jar
ENTRYPOINT ["java","-jar","/myblog.jar"]
