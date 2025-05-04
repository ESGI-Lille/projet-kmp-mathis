# Gestionnaire de Films KMP

# Dumage Mathis ESGI 3 AL

**Gestionnaire de Films** est une application basé sur l'api TDMB, le but c'est de pouvoir chercher des films, les ajouter en watchlist ou en favoris.
Il est également possible de générer des films aléatoires.
https://developer.themoviedb.org/reference/intro/getting-started

---

## 🚀 Fonctionnalités

* **Découvrir** des films aléatoires via l’API TMDb.
* **Rechercher** un film par titre.
* **Afficher** les détails d’un film (affiche, synopsis, date de sortie, score, genres, production).
* **Ajouter / Supprimer** des films à une **Watchlist** locale (JSON Server).
* **Ajouter / Supprimer** des films aux **Favoris** locaux (JSON Server).
* Navigation fluide entre les écrans : Accueil, Liste de films, Résultats de recherche, Détail, Watchlist, Favoris.

---


## ⚙️ Installation & Lancement

### 1. Configurer JSON Server

J'ai utilisé JSON-SERVER pour pouvoir en plus de l'api conserver des données plus facilement en local pour m'éviter de devoir faire 10.000 requêtes.

https://www.npmjs.com/package/json-server

Dans le dossier `backend`, on retrouve normalement le fichier `db.json` :

```json
{
  "favorites": [],
  "watchlist": []
}
```

Pour lancer le serveur : Se mettre sur le chemin 

```
"\composeApp\backend>"
``` 
et faire la commande suivante: 

```bash
npx json-server db.json
```

Il ne devrait pas y avoir besoin de faire de ```npm i``` étant donné que j'ai push le node_modules avec (oops ^^)

### 2. Ouvrir le projet

#### Version Desktop:

> ```bash
> ./gradlew :composeApp:run
> ```

#### Version Mobile:
```
Pour la version android utiliser l'interface de Android Studio
J'ai utilisé un "pixel 3a" pour le développement
```

---

## 🛠️ Configuration réseau

Le projet utilise une URL locale différente selon la plateforme :

```kotlin
val localBaseUrl = when(getPlatform().name) {
  "Desktop"  -> "http://localhost:3000"
  else -> "http://10.0.2.2:3000"
}
```
Il ne devrait donc pas y avoir de modification a faire pour l'url de Json-server

La clé API est écrite en brute dans ```MovieApiSource.kt``` 

Au cas ou la voici à nouveau ici : ```d4e2d76de72776487370ac5a45dc8d35```

Si vraiment il y a un problème avec la clé c'est possible d'en regénérer facilement une ici : https://developer.themoviedb.org/docs/getting-started

Mais j'espère pas pour vous c'est super chiant faut créer un compte.