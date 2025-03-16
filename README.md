
# Gatcha APIs

## Auteurs

Ce projet a été développé par :
  • Benjamin Zawoda
  • Walid Sakoukni
  • Salsabil Amri
  • Matys Lepretre 


Ce projet implémente un système de jeu de type Gatcha réparti en plusieurs microservices (authentification, gestion des joueurs, gestion des monstres et les invocations). Il est conçu pour être déployé facilement grâce à Docker Compose.

## Prérequis
	•	Git – pour cloner le projet.
	•	Docker et Docker Compose – pour construire et lancer les microservices.

## Installation
	1.	Cloner le projet
Ouvrez un terminal et clonez le projet depuis GitHub :

git clone https://github.com/benzwd/gatcha-apis.git


	2.	Se placer à la racine du projet
Accédez au dossier cloné :

cd gatcha-apis



## Lancement du projet

Le projet étant composé de plusieurs microservices, vous pouvez les lancer tous en une seule commande grâce à Docker Compose.
	1.	Construire et lancer les conteneurs
Dans la racine du projet, lancez :

docker-compose up --build

Cette commande va :
	•	Construire les images Docker de chaque microservice.
	•	Démarrer tous les conteneurs définis dans le fichier docker-compose.yml.

	2.	Vérifier le lancement
Une fois les conteneurs démarrés, consultez les logs dans le terminal. Chaque microservice sera accessible via les ports définis dans le fichier Docker Compose.

## Endpoints Principaux
	•	API d’authentification : pour obtenir un token d’accès.
	•	API des joueurs : pour gérer les profils (récupération du profil, ajout d’expérience, gestion des monstres du joueur, etc.).
	•	API des monstres : pour la création et la gestion des monstres (récupération des BaseMonsters, sauvegarde d’un monstre, ajout d’expérience, etc.).
	•	API d’invocation : pour invoquer un monstre aléatoire à partir d’un BaseMonster.


## Arrêt du conteneur

Pour arrêter et supprimer le conteneur, exécutez :

docker-compose down
