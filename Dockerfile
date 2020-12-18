FROM adoptopenjdk:15-hotspot AS base

# Set application directory
WORKDIR /app

# Sandpolis configuration
ENV SANDPOLIS_NET_CONNECTION_TLS    "true"
ENV SANDPOLIS_NET_LOGGING_DECODED   "false"
ENV SANDPOLIS_NET_LOGGING_RAW       "false"
ENV SANDPOLIS_PATH_LIB              "/app/lib"
ENV SANDPOLIS_PATH_PLUGIN           "/app/plugin"
ENV SANDPOLIS_PLUGINS_ENABLED       "true"

FROM gradle:jdk15 AS build
COPY --chown=gradle:gradle . .
RUN gradle --no-daemon :com.sandpolis.client.ascetic:imageSyncBuildContext
RUN cp -r com.sandpolis.client.ascetic/build/docker/lib /app

FROM base AS debug
COPY --from=build /app /app/lib

# Enable JVM debugging
#ENV JAVA_TOOL_OPTIONS "-agentlib:jdwp=transport=dt_socket,address=0.0.0.0:7000,server=y,suspend=y"

ENTRYPOINT ["java", "-cp", "/app/lib/*", "com.sandpolis.client.ascetic.Main"]

FROM base AS production
COPY --from=build /app /app/lib

ENTRYPOINT ["java", "-cp", "/app/lib/*", "com.sandpolis.client.ascetic.Main"]
