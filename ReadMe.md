# SpringBoot 3 Project Readme

## Introduction

## Project Overview

## Getting Started (Docker)

Follow these steps to get the project up and running (Windows):

0. **Clone repo:**
    ```
    git clone https://github.com/Fuego-Uni/ProgettoTAAS2023
    ```

1. **Build the services:**\
    per ogni cartella `service` eseguire:
    ```
    ./mvnw package
    docker rmi <nome-servizio> .
    docker build -t <nome-servizio>:fuego .
    ```
    altrimenti si può eseguire il python `buildall.py` per compilarli tutti automaticamente

2. **Run the Dockerfile:**
    ```
    docker-compose build && docker-compose up
    ```

## Development

Access to resources:

- localhost:8081/demo

- con uso di gateway: localhost:8080/demo

TODO:
gestire se hai gia recensito il film
mostrare solo i film consigliati
rework soket
css film
chat service
