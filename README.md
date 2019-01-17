# Projet Android TesOu

# Code pour ce document :
    * "- : à faire", 
    * "x : fait", 
    * "/ : en cours"


# Pour la Modularité :
    Tout passer par des variables (ne rien coder en dure)
    Passer par les classes


# Classe VerifConnexion : 
    Méthode : 
        x enregistrer dans un fichier l'id text
        - supprimer ce fichier à la déconnexion
        x vérifier si l'utilisateur est connecté (si le fichier existe ou si il est vide)
        x récupération de l'id (lecture du fichier)


# Classe bdd : 
    Méthode : 
        x connexion à la base de données
	x enregistrer les données de localisation dans fireBase avec un identifiant associé
        - récupération des données de loc
            - trier par rapport à la distance et à la date (méthode existante ?)
        - vérification de la correspodance des informations de login pour la page de connexion
        x enregistrement de la localisation avec la date


# Classe GoogleMap : 
    Methode : 
        x récupération de la localisation toutes les x secondes ou toutes les x mètres ? (classe ou page)


# Page GoogleMap : 
    x - La carte Google s'affiche lorsque l'on clique sur le bouton "Connecter"
    x - Un marqueur googleMap affiche la localisation


# Page Login : 
    Doit : 
        x permettre la connexion si l'utilisateur n'est pas connecté
        x ouvrir la page de googleMap si l'utilisateur est connecté
