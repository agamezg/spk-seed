# SPK-SEED

Repositorio base, para ser usado a modo de semilla al construir microservicios, utilizando como lenguaje Kotlin, y Spring-Boot como framework.

## Architecture

Tomando en cuenta la complejidad y la incertidumbre en cuánto a las tecnologías y del negocio en si, vemos pertinente el uso de una arquitectura limpia, que nos abstraiga de dichas complejidades y a la vez nos brinde ese desacoplamiento entre las clases de negocio, con las propias de infraestructura. En este caso usaremos una interpretación de arquitectura hexagonal, basándonos [esta semilla](https://github.com/redbeestudios/sp-spring-kotlin-seed).

## Kafka

Crear un tópico

```
docker exec -i {kafka-container-name} kafka-topics.sh --bootstrap-server localhost:9092 --create --topic {topic-name} --partitions 3 --replication-factor 1
```

Listar tópicos

```
docker exec -i {kafka-container-name} kafka-topics.sh --bootstrap-server localhost:9092 --list
```

Consumir un mensaje

```
docker exec -i {kafka-container-name} kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic {topic-name} --from-beginning
```

## Acuerdos para el desarrollo

[Introduzca acuerdos acá]
