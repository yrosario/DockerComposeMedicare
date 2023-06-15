FROM openjdk:11
WORKDIR /usr/src/app
COPY .//MedicareBackend//target//medicarebackend-0.0.1-SNAPSHOT.jar /usr/src/app
ENTRYPOINT [ "java", "-jar", "medicarebackend-0.0.1-SNAPSHOT.jar" ]