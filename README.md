CONTENTS OF THIS FILE
---------------------

 * Introduction
 * Necessary modules
 * Installation
 * Guide Pas a Pas


INTRODUCTION
------------

PROJET TP3 REST



NECESSARY MODULES
--------------------

IL FAUT ABSOLUMENT POSSEDER LA VERSION 17 DU JDK TROUVABLE ICI https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.exe
TESTE UNIQUEMENT SOUS WINDOWS, COMPATIBILITE LINUX NON GARANTIE


INSTALLATION
-------------------

Il suffit d'éxecuter le script TP3.bat qui lancera les 3 modules du TP à savoir 
les WS qui gèrent les hotels et les agences ainsi que le comparateur d'offres.

GUIDE D'UTILISATION PAS A PAS
-----------------------

1. Lancez le script bat TP3.bat
2. Ouvrez le navigateur internet et aller à l'url http://localhost:8080/h2-console/ pour avoir une vue sur la BDD liée au module HotelREST.
User Name: sa  Password: password
3. Dans la fenêtre qui vous demande de selectionner les identifiants de connexion de l'agence à laquelle vous souhaitez vous connecter, vous interagissez avec le module AgencyREST.
4. Selectionnez le numero de l'agence à laquelle vous voulez vous connecter.
5. On vous demande un mot de passe: les mdp sont de la forme agence1 pour l'Agence 1, agence2 pour Agence 2 ect..
6. Selectionnez 1 pour consulter les offres.
7. Les hotels, les chambres et les agences partenaires sont générés de manière aléatoire à chaque execution. Il est donc possible qu'aucune offre ne corresponde à vos critères de selection.
Pour s'assurer d'avoir des résultats, il suffit d'aller consulter la liste des hotels dans la BDD que vous avez ouverte à l'étape 2.
Dans le cadre blanc de la console h2, écrivez la requette SELECT * FROM HOTEL.
8.Sur la fenêtre du module maintenant, renseignez les critères de préférence qui correspondent à une des lignes de l'hotel.
Exemple:
On vous demande la ville, écrivez Lyon.
On  vous demande une date de début de séjour, écrivez 2022-01-01.
On vous demande une date de fin de séjour, écrivez 2022-01-02.
On vous demande un nombre de lits, écrivez 2.
On vous demande un nombre d'étoiles, écrivez 2.
On vous demande un budget minimum, écrivez 30.
On vous demande un budget maximum, écrivez 400.
9. Si des offres ont été trouvées, on vous demande si vous voulez consulter une offre, écrivez 1 pour consulter.
10. On vous demande ensuite de renseigner le numéro de l'offre à consulter.
11. La consultation résulte en le téléchargement de l'image de la chambre dans le fichier TP3/IMG/Agency. Pour consulter l'image, entrez l'uri qui vous est donnée dans votre explorateur de fichiers.
12. On vous demande si vous voulez réserver la chambre, écrivez 1 pour réserver.
13. Vos informations vous seront demandées
14. La réservation est confirmée des lors que vous avez entré les informations. Vous pouvez voir que vos informations ont été entrées dans la table CUSTOMER 
et que votre réservation est dans la table BOOKING.
15. Dans la fenêtre qui affiche "Bienvenue dans le comparateur d'Offres." vous interagissez avec le module Trivago.
16. Pour commencer à comparer des offres, il faut d'abord ajoutez les url des agences à comparer.
Il y a en tout 10 agences dont l'url est de type http://localhost:8081/agency/i avec i le numéro de l'agence de 1 à 10.
17. Entrez 2 pour ajouter une agence, entrez l'url http://localhost:8081/agency/1 pour ajouter l'Agence 1 par exemple.
18. Vous pouvez répéter l'étape 17 autant de fois que vous le voulez pour ajouter les autres agences.
19. Entrez 1 pour voir les agences que vous avez ajouté
20. Entrez 3 pour supprimer une agence.
21. Entrez 4 pour accéder à l'interface de consultation des offres qui est la même que celle avec laquelle vous avez interagi dans le module AgencyREST.
22. Vous pouvez entrer les mêmes informations de consultation à des dates différentes pour voir si il était possible d'obtenir la chambre pour moins cher a travers une autre agence.
23. L'application se clos des lors que vous réservez une offre.


