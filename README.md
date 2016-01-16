# Strabic

Librairies externes :
- jsoup-1.8.3.jar
- blueprints-core-2.2.0.jar
- pdfbox-1.8.8.jar
- commons-logging-1.2.jar
- fontbox-1.8.2.jar
- org.ow2.sat4j.core-2.3.5.jar
- sqlite-jdbc-3.8.7.jar
- commons-lang-2.6.jar


BDD : data/strabic_fr_20150203.sqlite
Fichiers graphml : data/tmp/
Fichiers txt articles : data/articles/

## Génération des graphs
(package 'graphGeneration')

- Récupération des articles via la base de données
- Analyse conceptuelle des articles
- Création des graphs
- Génération des fichiers graphml
- Génération des fichiers txt contenant le "corps" des articles

## Génération des maps
(package 'mapGeneration')

- Application de l'algorithme de placement
- Génération de la vue HTML représentant la map [TODO]



