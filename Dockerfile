FROM java:8
COPY . /
WORKDIR /
RUN javac DockerConnectMySQL.java
CMD ["java", "-classpath","-jar" "mysql-connector-java-8.0.13.jar:.","DockerConnectMySQL"]
