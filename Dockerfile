FROM ubuntu:latest AS build

# Atualizar a lista de pacotes e instalar dependências
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Copiar o código fonte para o contêiner
COPY . .

# Construir o projeto com Maven
RUN mvn clean install

# Criar uma imagem mais leve para execução
FROM openjdk:17-jdk-slim

# Expor a porta 8080
EXPOSE 8080

# Copiar o arquivo JAR gerado da etapa de construção
COPY --from=build target/demo-0.0.1-SNAPSHOT.jar app.jar

# Definir o ponto de entrada para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]