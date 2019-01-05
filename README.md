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
        - enregistrer dans un fichier l'id text
        - supprimer ce fichier à la déconnexion
        - vérifier si l'utilisateur est connecté (si le fichier existe ou si il est vide)
        - récupération de l'id (lecture du fichier)


# Classe bdd : 
    Méthode : 
        - connexion à la base de données
        - récupération des données de loc
            - trier par rapport à la distance et à la date (méthode existante ?)
        - vérification de la correspodance des informations de login pour la page de connexion
        - enregistrement de la localisation avec la date


# Classe GoogleMap : 
    Methode : 
        - récupération de la localisation toutes les x secondes ou toutes les x mètres ? (classe ou page)


# Page GoogleMap : 
    - Doit afficher la localisation


# Page Login : 
    Doit : 
        - permettre la connexion si l'utilisateur n'est pas connecté
        - ouvrir la page de googleMap si l'utilisateur est connecté
