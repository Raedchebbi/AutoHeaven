# 🚗 AutoHeaven - Application Desktop de vente et services automobiles

## 📌 Présentation

AutoHeaven est une application desktop développée en Java avec JavaFX qui permet :
- La **vente de voitures** neuves .
- La **vente d’équipements** automobiles.
- La **réservation de mécaniciens** pour les réparations.
- La **réservation de services de remorquage**.
- La **réservation de voitures à l'essai** avant achat.

Ce projet vise à simplifier l’expérience client en centralisant plusieurs services automobiles sur une seule plateforme.

---

## 🚀 Fonctionnalités

- 👨‍🔧 Réservation de mécanicien en ligne.
- 🛠️ Vente d’équipements avec gestion de stock.
- 🚘 Consultation, recherche et réservation de véhicules.
- 🧪 Réservation d’essai de véhicule.
- 🚛 Demande de remorquage.
- 👨‍💼 Espace administrateur pour la gestion complète (voitures, équipements, réservations , clients , mécaniciens ,reclamations).
- 🔐 Authentification et rôles (admin/client).
- 📝 Ajout de réclamations par les utilisateurs.
- 🌟 Ajout d’avis et de notes pour les voitures.

---

## 🧰 Technologies utilisées

- Java 21
- JavaFX 23 (modules utilisés : `controls`, `fxml`, `web`)
- JDBC (pour connexion à MySQL)
- MySQL
- Jackson / Gson (JSON)
- WebSockets (Tyrus, javax.websocket)
- Stripe (paiements)
- Mailjet (emailing)
- Infobip (SMS / Notifications)
- PDF (iText)
- Excel (Apache POI)
- Google Gemini (IA)
- FontAwesomeFX (icônes UI)
- OkHttp (requêtes HTTP)

## ⚙️ Prérequis

- Java **JDK 21** ou supérieur
- MySQL (ou équivalent)
- Maven
- JavaFX SDK 23.0.2 (à télécharger depuis [https://openjfx.io](https://openjfx.io))

> 🔧 Assurez-vous d’avoir correctement configuré le chemin vers le JavaFX SDK dans le fichier `pom.xml` :  
> `<javafx.sdk>C:/javafx-sdk/javafx-sdk-23.0.2</javafx.sdk>`



---

## 🛠️ Installation & exécution


```bash
# 1. Cloner le projet
git clone https://github.com/tasnim3A21/AutoHeaven.git
cd AutoHeaven

# 2. Importer le projet dans un IDE Java (IntelliJ, Eclipse…)

# 3. Créer la base de données MySQL nommée 'pidev' et configurer DBUtil.java

# 4. Lancer le projet avec Maven :
mvn clean javafx:run
```


---
## 📁 Structure du projet

- `src/main/java/` : Code Java
- `src/main/resources/` : FXML, images, fichiers CSS
- `controllers/` : Contrôleurs JavaFX
- `models/` : Modèles de données
- `utils/` :     Gonfiguration de base de données
- `services/` : gestion des opérations de CRUD dans la base de données et configuration des apis

---

## 📜 Licence

Ce projet est sous licence **MIT**. 

---

# 🚗 AutoHeaven - Plateforme de vente et services automobiles  
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)


