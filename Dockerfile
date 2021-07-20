FROM adoptopenjdk:16-hotspot

# Set application directory
WORKDIR /app

# Set application entry
ENTRYPOINT ["java", "-cp", "/app/lib/*", "com.sandpolis.client.ascetic.Main"]

# Set environment
ENV S7S_RUNTIME_RESIDENCY     "container"
ENV S7S_PATH_LIB              "/app/lib"
ENV S7S_PATH_PLUGIN           "/app/plugin"
ENV S7S_PLUGINS_ENABLED       "true"

# Install application
COPY build/lib /app/lib
