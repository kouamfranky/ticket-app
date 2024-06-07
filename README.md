# Système de Gestion des Tickets

Ce projet est un système de gestion de tickets simple développé en Java avec le framework Spring Boot. Le système permet aux utilisateurs de créer, lire, mettre à jour, affecter et supprimer des tickets. Chaque ticket possède un titre, une description, un utilisateur assigné et un statut (en cours, terminé, annulé). Les utilisateurs ont un nom d'utilisateur (username) et un email. Un utilisateur peut avoir plusieurs tickets et un ticket ne peut être affecté qu'à un utilisateur.

## Fonctionnalités

1. **Gestion des Tickets**
    - Créer, lire, mettre à jour, affecter et supprimer des tickets.
    - Un ticket possède un titre, une description, un utilisateur assigné et un statut.

2. **Gestion des Utilisateurs**
    - Créer, lire, mettre à jour les utilisateurs.
    - Chaque utilisateur a un nom, username, password, telephone, adresse et un email.
    - Pour les besoins de test un utilisateur par defaut est créé au démarrage de l'application. 
      * username : franko
      * password : Franko#10
   
3. **Contrôle d'Accès**
    - Restreindre l'accès aux tickets en fonction de l'utilisateur connecté.
    - APIs securisées
    - API de création d'utilisateur et de login accessibles sans token.

4. **Documentation**
    - APIs REST documentées avec OpenAPI.
    - Gestion des erreurs appropriées avec réponses HTTP correspondantes.

5. **Tests**
    - Tests unitaires pour les services et contrôleurs avec JUnit et Mockito.



