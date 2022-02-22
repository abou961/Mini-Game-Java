			******************ScientifiquesVSCOVID*****************

NOMS_AUTEURS: Bellagnech Assmae, Abou Moussa Jean, Ben Mouh Alyae, Karkache Sofia.
CONTACT: jean.abou-moussa@insa-lyon.fr
         alyae.benmouh@insa-lyon.fr
         assmae.bellagnech@insa-lyon.fr
         sofia.karkache@insa-lyon.fr
DESCRIPTIONS: 3 mini-jeux mettant en scène des scientifiques connus afin de lutter contre le COVID.
		-Niveau 1: Jeu type Mario Sokoban.
		-Niveau 2: Jeu type jetPack.
		-Niveau 3: création d'un vaccin.

USAGE: Extraire tous les fichiers dans le même dossier.Bien compiler tous les fichiers *.java. Exécuter la classe Simulation.java. 
Dans le premier niveau, utiliser le clavier pour les déplacements de Einstein, et la souris pour ceux de Hawking.Les mouvements de Tesla dans le niveau 2 sont assurés par les touches du clavier. Dans le dernier niveau, le jeu se joue avec la souris.
CONSEILS DE LECTURE DU CODE: Voici un découpage du code suivant les niveaux afin de faciliter votre lecture:
	*Main:
		-Simulation.java
	*Classes communes à au moins 2 niveaux:
		-Personnage.java
		-Item.java
	*Classes spécifiques au Niveau 1:
		-FenetreJeu.java
		-CentreControle.java
		-Einstein.java
		-Hawking.java	
		-Map.java		    
	*Classes spécifiques au Niveau 2:
		-Niveau2.java
		-Tesla.java
		-Masque.java
		-Virus.java
	*Classes spécifiques au Niveau 3:
		-Niveau3.java
		-Antigene.java
		-FenetreRegle.java
	*Fin du Jeu:
		-Fin.java

Total: 16 classes 

AMELIORATIONS: Difficultés du niveau 3 trop facile.








