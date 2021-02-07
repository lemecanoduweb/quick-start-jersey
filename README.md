# Quick start jersey
`jetty` `jersey` `hk2` `jakartaEE`

Réalisation d'une api rest avec le framework Jersey.

L'api est un CRUD capable de :
- récupérer un utilisateur par son nom d'utilisateur (username)
- créer un nouvel utilisateur
- modifier un utilisateur
- supprimer un utilisateur

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