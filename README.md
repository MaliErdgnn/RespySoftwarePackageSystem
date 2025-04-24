Repsy Software Package System

This document will guide you through running the RepsySoftwarePackageSystem which is deployed to the repsy.io.
὎6 1. Pull the Docker Image

Pull the latest Docker image from Repsy:

docker pull repo.repsy.io/mehmeterdogan/repsysoftwrpackagesystem/sftwrpckgsystm:latest

⚙️ 2. Run the Container

Run the container with your desired configuration:

docker run -d  --name MehmetAliErdogan-homework -p 8080:8080 -p 5432:5432 -p 9000:9000 -p 9001:9001 -e STORAGE_STRATEGY=file-system -v repsy-pgdata:/var/lib/postgresql/data -v repsy-minio:/data/minio -v repsy-storage:/app/local-storage repo.repsy.io/mehmeterdogan/repsysoftwrpackagesystem/sftwrpckgsystm:latest

🔐 Notes:

You can change STORAGE_STRATEGY to object-storage if desired when starting the container.

Volumes are used to persist PostgreSQL and MinIO data across restarts.

You cannot change environment variables after a container is created. To apply new environment variables, stop and remove the old container and create a new one.

🚪 3. Access the Application

App API: http://localhost:8080

MinIO Web UI: http://localhost:9001

🔄 Container usage

To stop:

docker stop MehmetAliErdogan-homework

To start:

docker start MehmetAliErdogan-homework

To remove and re-run with changes:

docker rm MehmetAliErdogan-homework
# Then rerun with updated "docker run" command

ℹ️ API Usage

Deploy a package:

POST /{name}/{version}
Form fields:
- package.rep (only .rep extension allowed)
- meta.json  (only .json extension allowed, must include name, version, author. Also name and version should match with the url parts)

Download a file:

GET /{name}/{version}/{filename}
- filename can only be package.rep or meta.json.

📁 Storage Structure

When using file-system, packages are stored under /app/local-storage/{name}/{version}.



