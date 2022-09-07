FROM openjdk:11
EXPOSE 8081
LABEL author="SomeshArewar"
ADD target/SparePart-0.0.1-SNAPSHOT.jar SparePart-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "SparePart-0.0.1-SNAPSHOT.jar" ]