FROM eclipse-temurin:21-jdk
ADD target/e-commerce.jar e-commerce.jar 
ENTRYPOINT ["java", "-jar", "/e-commerce.jar"]
