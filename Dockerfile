# =========================
# 🧱 Etapa 1: Build (compilación)
# =========================
FROM eclipse-temurin:17-jdk AS builder

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias (para aprovechar el cache de Docker)
COPY . .

# Compilamos la aplicación (generará el .jar dentro de /app/target)
RUN ./mvnw clean package -DskipTests

# =========================
# 🚀 Etapa 2: Runtime (ejecución)
# =========================
FROM eclipse-temurin:17-jdk AS runtime

# Establece el directorio de trabajo
WORKDIR /app

COPY --from=builder /app/target/unijobs-0.0.1-SNAPSHOT.jar ./app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Usamos el ENTRYPOINT para ejecutar el JAR que se montará como volumen
ENTRYPOINT ["java", "-jar", "app.jar"]
