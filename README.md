# Projet Android TesOu

# Code pour ce document :
    * "- : à faire", 
    * "x : fait", 
    * "/ : en cours"


# Pour la Modularité :
    Tout passer par des variables (ne rien coder en dur)
    Passer par les classes


# Classe VerifConnexion : 
    Méthode : 
        x enregistrer dans un fichier l'id text
        x supprimer ce fichier à la déconnexion
        x vérifier si l'utilisateur est connecté (si le fichier existe ou si il est vide)
        x récupération de l'id (lecture du fichier)


# Classe bdd : 
    Méthode : 
        x connexion à la base de données
	x enregistrer les données de localisation dans fireBase avec un identifiant associé
        x récupération des données de loc
            x trier par rapport à la date
        x vérification de la correspodance des informations de login pour la page de connexion
        x enregistrement de la localisation avec la date


# Classe GoogleMap : 
    Methode : 
        x récupération de la localisation toutes les x secondes ou toutes les x mètres ?
	x afficher les utilisateurs par rapport à la distance
		x distance paramétrable


# Page GoogleMap : 
       x La carte Google s'affiche lorsque l'on clique sur le bouton "Connecter"
       x Un marqueur googleMap affiche la localisation des utilisateurs


# Page Login : 
    Doit : 
        x permettre la connexion si l'utilisateur n'est pas connecté
        x ouvrir la page de googleMap si l'utilisateur est connecté


# Page SignUp :
    Doit :
	x permettre l'inscription
	

# Page de Notification 1 :
    Doit :
	x envoier des notifications quand un autre utilisateur est proche
	x tourner en tâche de fond
	
# Page de Notification Option 2 :
    Doit :
	/ modifier fréquence notification en fonction de la batterie
	
