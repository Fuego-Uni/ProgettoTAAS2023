# SpringBoot 3 Project Readme

## Introduction

## Project Overview

## Run on Docker (Windows)

### Setup (1 time)

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

### Run
2. **Run con docker compose:**
    ```
    docker-compose build
    docker-compose up -d
    ```
    oppure con powershell:
    ```
    (docker-compose build) -and (docker-compose up -d)
    ```

## Run on Kubernetes (Windows, Minikube)
### Setup (1 time)
1. **Start minikube:**
    ```
    minikube start
    ```

2. **Impostare powershell a usare minikube e non docker:**
    ```
    minikube -p minikube docker-env --shell powershell | Invoke-Expression
    ```

3. **buildare i servizi:**
    ```
    python .\buildall.py
    ```

4. **inizializza tutti i pod:**
    ```
    Get-ChildItem *.yaml | ForEach-Object { kubectl apply -f $_.FullName }
    ```

### Run
5. **run the services:**
    ```
    minikube start
    ```

6. **port forwarding:**
    ```
    minikube tunnel
    ```
