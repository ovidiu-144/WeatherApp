# WeatherApp - Laborator Sisteme Distribuite

Această aplicație este un serviciu de monitorizare meteo dezvoltat folosind **Spring Framework**, realizat în cadrul disciplinei **Sisteme Distribuite (SD)**. Proiectul demonstrează integrarea unui client HTTP modern cu un serviciu extern de date meteorologice.

## Funcționalități
* **Interogare Date Live:** Preluarea temperaturii, vitezei vântului și a codurilor meteo în timp real.
* **Integrare Open-Meteo:** Utilizarea API-ului [Open-Meteo](https://open-meteo.com/) pentru date de înaltă precizie, fără necesitatea unei chei de autentificare.
* **Arhitectură Spring:** Folosirea conceptelor de `RestTemplate` sau `WebClient` pentru apeluri externe și `Service Layer` pentru procesarea logicii.
* **Mapping JSON:** Conversia automată a răspunsurilor API în obiecte Java (POJO) folosind Jackson.

## Arhitectura Tehnică
Aplicația urmează un flux de date distribuit:
1. **Client Spring Boot:** Trimite cereri GET către endpoint-urile Open-Meteo (ex: `api.open-meteo.com/v1/forecast`).
2. **External API (Open-Meteo):** Calculează prognoza și returnează un payload JSON.
3. **Procesare locală:** Spring deserializează datele și le afișează utilizatorului.

## Tehnologii Utilizate
* **Framework:** Spring Boot
* **Limbaj:** Kotlin
* **Build Tool:** Maven
* **Sursă Date:** Open-Meteo API
