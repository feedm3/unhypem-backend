# unhypem-boot

[![Build Status](https://img.shields.io/travis/feedm3/unhypem-boot.svg?style=flat-square)](https://travis-ci.org/feedm3/unhypem-boot)
[![Dependency Status](https://dependencyci.com/github/feedm3/unhypem-boot/badge?style=flat-square)](https://dependencyci.com/github/feedm3/unhypem-boot)

Backend for [Unhypem](https://github.com/feedm3/unhypem) implemented with Java.

## Setup

- Postgres Database (9.5): `localhost:5433/unhypemboot`

## Run

To start the project simply hit
```
gradlew bootRun
```

## Test

To start all test use
```
gradlew test
```

#### Outdated dependencies

To check for outdated dependencies
```
gradlew dependencyUpdates -Drevision=release
```

#### Vulnerabilities

To check for vulnerabilities in the dependencies run
```
gradlew dependencyCheck --info
```
The reports will be generated automatically under `build/reports` folder
