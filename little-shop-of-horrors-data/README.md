
# La Petite Boutique des horreurs
![enter image description here](./imgs/Little-Shop-of-Horrors.png)
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

Remanier votre implémentation du catalogue pour utiliser le DAO nouvellement créé, remanier également tests pour ne garder dans les tests catalogues que ceux qui ont du sens (les autres tests étant devenus des tests du DAO)

#### astuces
- Vous aurez sûrement besoin du design pattern DAO
- Mettez vous dans le cas ou la source des données peut varier (entre le test et la production par exemple), donc provenir d'un fichier, d'une base de données, d'un mock...
- Votre catalogue doit déléguer tout ce qui touche à la gestion des données, au DAO (Data Access Object)
- Faite une couverture de test pour vos DAO, du coup, les tests de votre catalogue vont être déporté dans le DAO et les tests du catalogue devraient changer pour devenir un peu plus fonctionnels
- Utilisez Mockito pour vos nouveaux tests de catalogue

#### Step 2: DAO + DI
Dans peu de temps, les données proviendront d'une base de données (et plus d'un fichier)
comment faire en sorte que les impacts de ce chagement soient minimisés?

Dépendez d'abstraction ! cela vous permettra de switcher d'implémentation sans impacts.

Utilisez l'injection de dépendance pour injecter votre DAO dans votre catalogue et ainsi faire diminuer le couplage entre les objets, ainsi vous pourrez changer d'implémentation sans aucuns impacts

#### astuces
- l'injection de dépendances peut se faire par constructeur (pas besoin de framework)
- Vous pouvez également Utiliser Spring pour faire l'injection de dépendances (@Autowired !)

#### Step 3: DAO + DI + Tests
Vous avez maintenant plusieurs implémentations de votre DAO (file, database, autre..)

Réaliser/remanier vos tests pour ne tester que votre DAO d'une part, et que votre catalogue d'autre part.
Pour pouvoir isoler le catalogue, vous devriez utiliser un DAO "mock", utilisez Mockito pour mocker votre DAO.

A vous de jouer!


#### astuces
- Mockito permet de mocker et de configurer dynamiquement le comportement du mock

#### bonus


#### ressources
- DAO: https://cyrille-herby.developpez.com/tutoriels/java/mapper-sa-base-donnees-avec-pattern-dao/
- Spring IoC: http://www.baeldung.com/inversion-control-and-dependency-injection-in-spring
- Mockito: http://site.mockito.org/


### Part 3 : Un catalogue en mode REST

#### Step 1: rendre le catalogue accessible over Internet (little shop of horrors online !)
Pour écrire un service REST avec une API publique il faut commencer par définit les fonctionnalités que l'ont veut exposer, dans le cas de notre catalogue nous avons besoin de services CRUD
- ajouter une plante
 - retirer une plante
 - chercher une plante
 - etc...

Dans un premier temps, définissez votre API en écrivant une classe qui la définie.
Faite attention aux nommages de vos méthodes, il faut que ce soit très facilement compréhensible par un développeur et un utilisateur du domaine, ces méthodes seront exposées au publique.
Une fois les méthodes définies, transformer cette classe en **Controller** au sens Spring (en utilisant Spring Boot / Spring MVC)

Essayez de définir les chemins d'accès aux fonctions REST de manière intuitive et logique 
voici un exemple de logique de nommage : http://www.restapitutorial.com/lessons/restfulresourcenaming.html

Completez votre controlleur avec le fonctionnement du catalogue (en s'appuyant sur le DAO)

Vous avez maintenant
- Un controller qui est responsable de traiter les requpetes HTTP
- Un catalogue qui contient un peu de logique métier
- Un DAO qui est responsable de l'accès aux données
(Rappel: **SRP** et **Separation of Concerns**

#### Step 2: Validation API
En utilisant la validation API (javax.validation + Hiberante validator) ajouter les contraintes nécessaires à votre back end REST catalogue pour vérifier les paramètres que vous recevez en entrée.

#### Step 3: Testing !
N'oubliez pas de faire vos tests !
Pour tester le controller (tout seul) vous devez mocker votre Catalogue (utilisez Mockito)
Les tests du controller vont permettrent de tester la partie validation
(les tests du catalogue et du DAO existant déjà, vous aurez une couverture de tests complète)

Vous pouvez également tenter de faire des tests d'intégration, en lancant un véritable serveur 

#### astuces
- Le DAO doit être injecté dans le controlleur (via Spring et @Autowired)

#### bonus

#### ressources
- Tests d'intégration avec Spring:
	- http://www.baeldung.com/spring-boot-testing
	- https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
		

### Part 4 : The little shop of horrors v1

#### Step 1: Shop
Il est temps de modéliser la boutique ! 

En tant que Seymour
Je veux disposer d'une boutique
Afin de pouvoir vendre des plantes
Afin de pouvoir recevoir de nouvelles plantes à ajouter au catalogue
Afin de pouvoir gérer la recette de la boutique

La boutique devrait utiliser **un service catalogue** qui se connecter aux backend REST pour pouvoir l'interoger et récupérer les données nécessaires.
Le service catalogue devrait être une abstraction, avec une implémentation **ServiceCatalogREST**.

Au niveaux des tests, pour tester votre service (si ca à du sens) vous devez mocker les appels REST vers le RESTCatalog
Pour tester votre Shop vous devrez mocker votre service catalogue

>(ps: il s'agit du DIP, Dependency Inversion Principle, l'objet Shop dépend d'une abstraction qui est le service catalogue et l'implémentation ServiceCatalogREST s'appuie aussi sur une abstraction, pour plus d'infos:
https://code.tutsplus.com/tutorials/solid-part-4-the-dependency-inversion-principle--net-36872)


#### astuces
- Pour l'utilisation du backend REST, vous devriez utiliser le client Spring (https://spring.io/guides/gs/consuming-rest/)
- Mocker bien les dépendances pour pouvoir tester qu'un périmètre restreint
- Injecter votre client REST avec Spring
- Injecter votre service dans votre shop avec Spring également.
- le Shop doit ajouter la fonctionnalité de gestion des recettes.

#### Step 2: Ajout de la gestion du stock
Votre Little Shop doit maintenant gérer le stock des plantes, faisons un petit rappel
- le Catalogue connait les plantes (leur nom, nom long, famille et prix)
- le DAO permet d'accéder aux données
- le Controller gère les requêtes HTTP
- le catalogue connait les règles métiers et s'appuie sur le DAO pour les accès aux données

Quels objets doivent gérer le stock ? qui doit les incréments/décréments du stock en cas d'achats ou de ventes de plantes ?

Réfléchissez au cas métier que cela implique, que se passe t'il si on essaie de vendre une plantea avec un stock = 0 par exemple
Implémentez la nouvelle logique métier (dans les bons objets) et réaliser les tests nécessaires (en TDD si possible)


### Part 5 : Décorons les plantes
Considérons un objet Client (une classe Customer) qui contient une méthode *interact* (pour l'instant il ne s'agit que d'un placeholder pour la suite)

	public class Customer {  
	  
	    public void interact(String action) {  
	        System.out.println("Call action: " + action);  
	  }  
	}

Améliorons notre modélisation des plantes, pour l'instant nous n'avons qu'une classe Plante (avec ses attributs)
Les plantes ont des actions sur les être Humain, et sur les clients (c'est pour ça qu'il viennent en acheter d'ailleurs).
Ajouter une méthode (comportementale) dans vos plante

		public void interactWith(Customer customer) {  
		    customer.interact("Make Happy");  
		}
Cette méthode applique un effet sur un client (par défaut, la classe plante, rend un client heureux)

Seymour aimerait améliorer et diversifier les plantes du magasin, revoyez votre modélisation pour permettre d'avoir des plantes qui ont divers effets sur les clients:

 - Des plantes carnivores (mord les clients)
 - Des plantes illégales (détend les clients)
 - Des plantes qui chantent (amuse les clients)
(ps: chacune des plantes à sa propre implémentation de la méthode *interactWith*)

Avec votre modélisation est-il possible d'avoir une nouvelle plante : **une plante extraterrestre** qui est à la fois une plante qui chante, une plante carnivore, une plante illégale et une plante mangeuse d'hommes (mange les clients) ? Sans avoir à recopier le comportement de la méthode *interactWith* depuis les autres classes.

Pour arriver à cette composition facilement vous devriez regarder le **design pattern Decorator**
L'idée générale est de pouvoir écrire:

		PlanteExtraTerestre plant = new PlanteExtraTerestre(new PlanteCarnivore(new PlanteQuiChante())))


		


 
