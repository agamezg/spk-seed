MS_NAME='spk-seed-evolution'

./gradlew clean ktlintCheck assemble

docker build --rm -t $MS_NAME .
docker tag $MS_NAME:latest localhost:5500/$MS_NAME:latest
docker push localhost:5500/$MS_NAME:latest

helm upgrade --debug --install spk-seed-evolution assets/local/k8s --values assets/local/k8s/values.local.yaml
