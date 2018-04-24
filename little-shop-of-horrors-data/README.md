
# La Petite Boutique des horreurs

## Synopsis

Seymour Krelborn travaille chez un petit fleuriste de quartier, et est secrètement amoureux de sa collègue Audrey. Son patron, désespérant de l'absence de clients dans le magasin, annonce qu'il va devoir mettre la clef sous la porte. Seymour propose alors de mettre en vitrine une plante inconnue qu'il avait trouvée le jour d'une éclipse. Intrigués par l'aspect étrange de la plante, les clients affluent. Il s'avère cependant que la plante a besoin de sang humain, et Seymour éprouve chaque jour - au fur et à mesure qu'elle grandit - plus de mal à la nourrir.

## Règles du jeu
Ce kata (à tiroir) doit permettre de manipuler de nombreux concepts : conception, modélisation, design pattern, tests etc..
Quelques règles à respecter

 - Ne tricher pas en allant voir les sources du kata
 - Tous les développements doivent être faits en TDD
 - L'application doit être *production ready* (logs etc...)
 - Le code doit être en anglais
 - N'oubliez pas d'avancer petit à petit (Baby steps !)
 - Certaines dépendances sont imposées et déjà présentes dans le pom.xml (logback, junit 4, assertj, mockito...)
 - N'hésitez pas à utiliser votre IDE pour un maximum d'actions (avec les raccourcis clavier :) )
 - 

## Implémentation de la gestion du stock

Dans cette première partie nous allons modéliser le domaine de la boutique ainsi que les objets correspondant.
Dans un second temps il s'agira de manipuler les données (design pattern DAO)

**En tant que Seymour Krelborn** je serais votre product owner, vous pouvez me poser toutes les questions que vous voulez pour pouvoir répondre à mon besoin.

### Part 1 : Modélisation
Nous disposons d'un **catalogue** de plantes, qui nous permet de **chercher** les plantes par leur nom ou par leur famille. Nous pouvons également ajouter ou supprimer des plantes du catalogue. De plus il est possible de trouver une plante dans le catalogue avec sa description complète (nom, prix...).
Le catalogue permet aussi de voir rapidement les plantes les moins chères (les best value inférieures à un prix donné)
Dans la boutique nous vendons des **plantes** chacune d'elles à un nom, un prix, un nom long (le nom latin en général) et une famille (la famille à laquelle la plante appartient)

#### astuces
vous aurez peut-être besoin de redéfinir hashcode & equals

#### bonus
Essayez d'utiliser les Optional de java 8 lorsqu'une méthode "devrait" renvoyer null (pour la fonction de recherche par exemple)

#### ressources
- Assertj: http://joel-costigliola.github.io/assertj/
- Optional: https://www.nurkiewicz.com/2013/08/optional-in-java-8-cheat-sheet.html


Durée: environ 2h


### Part 2 : Premier design pattern, DAO

#### Step 1: DAO
Notre première implémentation utilise une structure de données, le **catalogue**, dorénavant le stock de la boutique des horreurs provient d'un **fichier** qui contient toutes les plantes disponibles.
Le fichier est au format

	#name/longName/family/price
	Acer palmatum/Acer palmatum/Aceraceae/3
	Berzelia ecklonii/Acer palmatum/Bruniacées/30.4
	giant man-eating plant//UFO/666

En tant que développeur sur l'application little shop of horrors
je veux disposer d'un objet qui gère l'accès aux données
afin d'avoir un couplage faible entre le catalog et la source des données

En tant que Seymour
je veux pouvoir changer facilement la source des données du catalogue
afin de pouvoir diversifier la provenance des plantes mangeuses d'hommes

#### astuces
- Mettez vous dans le cas ou la source des données peut varier (entre le test et la production par exemple), donc provenir d'un fichier, d'une base de données, d'un mock...
- Votre catalogue doit déléguer tout ce qui touche à la gestion des données, au DAO (Data Access Object)
- Faite une couverture de test pour vos DAO, du coup, les tests de votre catalogue vont être déporté dans le DAO et les tests du catalogue devraient changer pour devenir un peu plus fonctionnels
- Utilisez Mockito pour vos nouveaux tests de catalogue

#### Step 2: DAO + DI
Dans peu de temps, les données proviendront d'une base de données (et plus d'un fichier)
comment faire en sorte que les impacts de ce chagement soient minimisés?

Dépendez d'abstraction et utilisez le conteneur Spring pour faire de l'injection de dépendances pour utiliser le DAO qui convient.

#### Step 3: DAO + DI + Tests

A vous de jouer!


#### astuces
Vous aurez sûrement besoin du design pattern DAO

#### bonus


#### ressources
- DAO: https://cyrille-herby.developpez.com/tutoriels/java/mapper-sa-base-donnees-avec-pattern-dao/
- Spring IoC: http://www.baeldung.com/inversion-control-and-dependency-injection-in-spring


