# Use the OpenJDK 17 JDK Alpine image
FROM openjdk:17-jdk-alpine

# Copy the WAR file into the container
COPY target/KPI-0.0.1-SNAPSHOT.war /app/KPI-0.0.1-SNAPSHOT.war

# Set the working directory
WORKDIR /app

# Expose the port your app runs on
EXPOSE 8081

# Run the WAR file when the container launches
ENTRYPOINT ["java", "-jar", "KPI-0.0.1-SNAPSHOT.war"]
