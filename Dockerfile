FROM oysteinjakobsen/armv7-oracle-java8
VOLUME /tmp
ADD keystore.p12 /etc/letsencrypt/live/www.yanxia.eu/keystore.p12
ADD web_dev-0.0.1-SNAPSHOT.jar blog.jar
ENTRYPOINT ["java","-jar","/blog.jar"]
