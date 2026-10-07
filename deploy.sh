#!/usr/bin/env bash
set -e  # Exit immediately if any command fails

# Load variables from .env ignoring comments
export $(grep -v '^#' .env | xargs)

IMAGE_URI="${REGION}-docker.pkg.dev/${PROJECT_ID}/${REPO}/${IMAGE}:${TAG}"

mvn clean package -DskipTests
docker build -t "${IMAGE_URI}" .
docker push "${IMAGE_URI}"
kubectl apply -f k8s-deployment.yaml