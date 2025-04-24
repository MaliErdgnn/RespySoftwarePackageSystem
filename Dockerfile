FROM openjdk:17-jdk-slim

# PostgreSQL için gerekli ortam değişkenlerini ayarlayın
ENV POSTGRES_USER=postgres \
    POSTGRES_PASSWORD=postgres \
    POSTGRES_DB=SoftwarePackageSystem

# Spring Boot uygulaması için ortam değişkenlerini ayarlayın
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/SoftwarePackageSystem \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=postgres \
    MINIO_ENDPOINT=http://localhost:9000 \
    MINIO_ACCESS_KEY=minioadmin \
    MINIO_SECRET_KEY=minioadmin123 \
    MINIO_BUCKET=packages \
    STORAGE_STRATEGY=file-system


# Gerekli paketleri yükle
RUN apt-get update && \
    apt-get install -y gnupg2 curl wget lsb-release && \
    echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list && \
    curl https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - && \
    apt-get update && \
    apt-get install -y postgresql postgresql-contrib && \
    apt-get clean && rm -rf /var/lib/apt/lists/*


# MinIO binary indir
RUN curl -O https://dl.min.io/server/minio/release/linux-amd64/minio && \
    chmod +x minio && \
    mv minio /usr/local/bin/

# MinIO için klasör oluştur
RUN mkdir -p /data/minio


# Çalışma dizinini ayarlayın
WORKDIR /app

# Spring Boot JAR dosyasını kopyalayın
COPY target/RepsySoftwarePackageSystem-0.0.1-SNAPSHOT.jar /app/app.jar

# PostgreSQL verilerini saklamak için dizin oluşturun
RUN mkdir -p /var/lib/postgresql/data

# Lokaldeki storage klasörünü volume olarak işaretle
VOLUME ["/var/lib/postgresql/data", "/data/minio", "/app/local-storage"]


# Giriş noktası betiğini kopyalayın
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Giriş noktası betiğini çalıştırın
ENTRYPOINT ["/app/entrypoint.sh"]
