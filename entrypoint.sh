#!/bin/bash
set -e

# PostgreSQL'i başlat
service postgresql start

# PostgreSQL'in hazır olmasını bekle
until pg_isready -U "$POSTGRES_USER"; do
  echo "PostgreSQL başlatılıyor..."
  sleep 2
done

# Kullanıcı şifresini ayarla
su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD '${POSTGRES_PASSWORD}';\""

# Eğer veritabanı yoksa oluştur
DB_EXIST=$(su - postgres -c "psql -tAc \"SELECT 1 FROM pg_database WHERE datname='${POSTGRES_DB}'\"")
if [ "$DB_EXIST" != "1" ]; then
  echo "Veritabanı '${POSTGRES_DB}' bulunamadı, oluşturuluyor..."
  su - postgres -c "createdb ${POSTGRES_DB}"
else
  echo "Veritabanı '${POSTGRES_DB}' zaten mevcut, oluşturulmadı."
fi

# MinIO'yu başlat
minio server /data/minio --console-address ":9001" &

echo "MinIO başlatıldı"

# Spring Boot uygulamasını başlat
exec java -jar /app/app.jar
