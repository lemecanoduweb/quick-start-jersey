# Quick start jersey

![Java CI with Maven](https://github.com/lemecanoduweb/quick-start-jersey/workflows/Java%20CI%20with%20Maven/badge.svg)

Réalisation d'une api rest avec le framework Jersey.

L'api est un CRUD capable de :
- récupérer un utilisateur par son nom d'utilisateur (username)
- créer un nouvel utilisateur
- modifier un utilisateur
- supprimer un utilisateur

Pour plus d'informations, accéder à la [documentation de l'api](https://github.com/lemecanoduweb/quick-start-jersey/wiki/User-api)

## Cycle  de vie

Effectuer les tests :
```shell
mvn clean test
```

Compiler les sources :
```shell
mvn clean package
```

Lancer le serveur `jetty` :
```shell
java -jar target/quick-start-jersey-1.0-SNAPSHOT.jar
```

## Stack technique

- `java 11`
- `maven`
- `jetty 11.0.0`
- `jersey 3.0.1`
- `jakartaEE`
- `junit 4`
- `github Actions`
