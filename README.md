# Application de Gestion des Produits

Ce projet est une application web complète basée sur **Angular** pour le frontend et **Spring Boot** pour le backend. Elle propose des fonctionnalités avancées pour la gestion des produits avec une authentification basée sur les rôles, un design moderne, et des tests pour garantir la qualité du code.

---

## Fonctionnalités

### Frontend (Angular)
1. **Page d'authentification** :
   - Les utilisateurs peuvent se connecter en sélectionnant leur rôle (admin ou utilisateur).
   - Accès restreint selon le rôle :
     - Administrateurs : modification, suppression, ajout de produits.
     - Utilisateurs : consultation uniquement.

2. **Filtres avancés** :
   - Recherche par catégorie.
   - Recherche par mot-clé pour simplifier la navigation dans les produits.

3. **Design modernisé** :
   - Mise à jour des thèmes et du style général pour une meilleure expérience utilisateur.

4. **Auth Guards** :
   - Protection des routes pour empêcher l'accès non autorisé selon les rôles.

5. **Boutons de connexion et de déconnexion** :
   - Gestion intuitive de la session utilisateur.

6. **Problème connu** :
   - La configuration des tests unitaires avec **Karma** et **Jasmine** n'est pas complètement fonctionnelle.

7. **Tests effectués** :
   - Tests unitaires sur les services et les guards avec **Jest**.
   - Tests d'intégration pour les fonctionnalités critiques (authentification, gestion des produits).

---

### Backend (Java Spring Boot)
1. **Actions CRUD sur les produits** :
   - Ajout, modification, suppression et affichage des produits.

2. **Authentification et gestion des rôles** :
   - **Spring Security** intégré pour sécuriser les endpoints REST.
   - Les tokens JWT (JSON Web Tokens) sont utilisés pour gérer les sessions utilisateur.
     - Les tokens sont générés lors de la connexion.
     - Validation des tokens pour chaque requête.

3. **Base de données H2** :
   - Base de données en mémoire, réinitialisée à chaque démarrage.
   - **Mapping automatique** : Une liste `products.json` est automatiquement mappée et insérée en base de données au démarrage de l'application.

4. **Organisation modulaire** :
   - Code structuré en modules pour faciliter la maintenabilité et l’évolutivité.

5. **Tests effectués** :
   - Tests unitaires pour les services et les contrôleurs avec **JUnit**.
   - Tests d'intégration pour vérifier les endpoints REST, y compris la validation des tokens JWT.

---

## Technologies Utilisées

### Frontend
- **Angular** : Framework JavaScript pour construire des applications web dynamiques.
- **TypeScript** : Langage typé pour JavaScript.
- **SCSS** : Utilisé pour le theming.

### Backend
- **Java Spring Boot** : Framework pour des applications web robustes.
- **Spring Security** : Gestion de l'authentification et de l'autorisation.
- **JWT** : Utilisé pour la gestion des sessions utilisateur.
- **H2 Database** : Base de données en mémoire rapide et facile à configurer.
