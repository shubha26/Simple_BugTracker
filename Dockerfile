FROM openjdk:11-jre-slim
ADD target/bug_tracker.jar bug_tracker.jar
ENTRYPOINT ["sh","-c","java -jar /bug_tracker.jar"]
