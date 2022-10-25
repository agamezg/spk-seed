# Dockerfile

FROM adoptopenjdk/openjdk11:alpine
LABEL name=spk-seed-evolution

RUN apk add --no-cache bash
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
RUN mkdir -p /opt/spk-seed-evolution && chown javauser:javauser /opt/spk-seed-evolution

WORKDIR /opt/spk-seed-evolution
USER javauser

COPY build/libs/*.jar tools/*.jar /opt/spk-seed-evolution/

ENV JAVA_TOOL_OPTIONS "-javaagent:aws-opentelemetry-agent.jar"

# OpenTelemetry agent configuration
ENV OTEL_RESOURCE_ATTRIBUTES "service.name=spk-seed-evolution"
ENV OTEL_TRACES_EXPORTER "logging"

CMD ["java", "-jar", "api.jar"]
d
