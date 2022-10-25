MS_NAME='spk-seed-evolution'

./gradlew clean ktlintCheck assemble

docker build --rm -t $MS_NAME .

docker-compose -f assets/local/docker-compose/docker-compose.yaml down
docker-compose -f assets/local/docker-compose/docker-compose.yaml up
