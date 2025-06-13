# =========================================================================
# ESTÁGIO 1: Build da Aplicação com Maven e JDK
# Usamos uma imagem que já contém o Maven e um JDK (Java 17 é uma ótima escolha para Spring Boot 3)
# =========================================================================
FROM maven:3.9-eclipse-temurin-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache do Docker.
# As dependências só serão baixadas novamente se o pom.xml mudar.
COPY pom.xml .

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Copia todo o código-fonte da aplicação para o container
COPY src ./src

# Executa o build do Maven para gerar o arquivo .jar.
# -DskipTests pula a execução dos testes durante o build da imagem.
RUN mvn package -DskipTests


# =========================================================================
# ESTÁGIO 2: Execução da Aplicação com JRE (Java Runtime Environment)
# Usamos uma imagem muito menor, que contém apenas o necessário para rodar Java.
# =========================================================================
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio 'build' para o estágio final
# O nome do JAR deve corresponder ao que está definido no seu pom.xml
# Verifique o nome exato na sua pasta /target após um build
COPY --from=build /app/target/armario-inteligente-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação usa (definida no application.properties)
EXPOSE 8080

# Comando para iniciar a aplicação quando o container for executado
ENTRYPOINT ["java", "-jar", "app.jar"]