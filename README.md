# 📚 Model Technologie - Backend & Infrastructure

Transformation numérique de Model Technologie avec une architecture moderne **monorepo** Frontend + Backend.
Ce projet permettra a Model tech de se positionner comme une référence en Afrique Francophone pour les solutions technologiques innovantes.

---

## 🎯 Stack Technologique

### **Frontend**
- **React 18** avec TypeScript
- **Tailwind CSS** pour le styling
- **React Router** pour la navigation
- **TanStack Query** pour la gestion d'état
- **Docker** pour la conteneurisation

### **Backend**
- **Spring Boot 3.x** (Java 17+)
- **Spring Data JPA** pour la persistance
- **PostgreSQL 16** comme base de données
- **Spring Security** pour l'authentification
- **Springdoc OpenAPI** pour la documentation API (Swagger)

### **Infrastructure & DevOps**
- **Docker & Docker Compose** pour la conteneurisation
- **GitHub Actions** pour la CI/CD
- **PostgreSQL** avec backups automatiques
- **Nginx** comme reverse proxy
- **Let's Encrypt** pour les certificats SSL
- **Dozzle** pour la gestion des logs
- **Adminer** pour la gestion de base de données

---

## 📁 Structure du Projet

```
model-technologie/
│
├── 📂 frontend/                    # Application React
│   ├── src/
│   ├── public/
│   ├── Dockerfile
│   └── package.json
│
├── 📂 backend/                     # Application Spring Boot
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── application.yml
│
├── 📂 devops/                      # Infrastructure & deployment
│   ├── docker-compose.yml          # Développement
│   ├── docker-compose.prod.yml     # Production
│   ├── nginx/                      # Configuration Nginx
│   ├── postgres/                   # Scripts DB
│   ├── monitoring/                 # Dozzle & Adminer
│   └── scripts/                    # Scripts de maintenance
│
├── 📂 .github/
│   └── workflows/                  # CI/CD pipelines
│       ├── frontend-ci.yml
│       ├── backend-ci.yml
│       └── deploy.yml
│
└── 📂 docs/                        # Documentation

```

---

## 🚀 Quick Start - Développement Local

### **Prérequis**
- Docker & Docker Compose installés
- Node.js 18+ (pour dev frontend)
- Java 17+ (pour dev backend)
- Git

### **Installation**

```bash
# 1. Cloner le repo
git clone https://github.com/lionnelpat/model-technologie.git
cd model-technologie

# 2. Copier les variables d'environnement
cp .env.example .env
# Éditer .env si besoin pour dev local

# 3. Démarrer les services Docker
docker-compose up -d

# Services disponibles :
# - Frontend      : http://localhost:3000
# - Backend API   : http://localhost:8080/api
# - Swagger API   : http://localhost:8080/api/swagger-ui.html
# - Dozzle Logs   : http://localhost:8888
# - Adminer DB    : http://localhost:8080/adminer

# 4. Vérifier que tout fonctionne
curl http://localhost:8080/api/actuator/health
```

---

## 🔄 Workflows CI/CD

### **Comment les workflows marchent**

Notre setup utilise des **path filters** : les workflows ne se déclenchent QUE si leurs fichiers respectifs changent.

```
┌─────────────────────────────────────────────────┐
│             Push vers GitHub                    │
└─────────────┬──────────────────┬────────────────┘
              │                  │
        Fichier dans         Fichier dans
        frontend/** ?         backend/** ?
              │                  │
              ▼                  ▼
      ┌───────────────┐  ┌───────────────┐
      │ FRONTEND-CI   │  │ BACKEND-CI    │
      └───────┬───────┘  └───────┬───────┘
              │                  │
              └──────────┬───────┘
                         │
                    ✅ Tests OK
                         │
                         ▼
              ┌──────────────────────┐
              │  DEPLOY to VPS       │
              │  (si branch main)    │
              └──────────────────────┘
```

### **Path Filters**

- **Frontend-CI** se déclenche si : `frontend/` ou `.github/workflows/frontend-ci.yml` changent
- **Backend-CI** se déclenche si : `backend/` ou `.github/workflows/backend-ci.yml` changent
- **Deploy** se déclenche si : tous les workflows CI sont passés ET push sur `main` ou `staging`

**Avantage** : Une modif du frontend ne rebuildé pas le backend. Zéro perte de temps ! ⚡

---

## 📝 Commande Courantes

### **Frontend**

```bash
# Développement
cd frontend
npm install
npm run dev

# Build pour production
npm run build

# Tests
npm test

# Linter
npm run lint
```

### **Backend**

```bash
# Développement
cd backend
mvn spring-boot:run

# Build
mvn clean package

# Tests
mvn test

# Générer javadoc
mvn javadoc:javadoc
```

### **Docker**

```bash
# Démarrer tous les services (dev)
docker-compose up -d

# Logs en temps réel
docker-compose logs -f

# Arrêter les services
docker-compose down

# Prod
docker-compose -f devops/docker-compose.prod.yml up -d
```

---

## 🔐 Secrets GitHub à Configurer

Voir le guide complet : [docs/GITHUB_SECRETS.md](docs/GITHUB_SAFE)

En résumé, ajouter ces secrets dans Settings > Secrets > Actions :

```
VPS_SSH_KEY           → Clé SSH privée pour déploiement
VPS_HOST_PROD         → IP/domaine du VPS production
VPS_HOST_STAGING      → IP/domaine du VPS staging
SLACK_WEBHOOK_URL     → (Optionnel) Pour notifications Slack
DB_PASSWORD_PROD      → Mot de passe PostgreSQL prod
JWT_SECRET_PROD       → Secret JWT pour prod
```

---

## 🌍 Déploiement

### **Staging**

```bash
# N'importe quel push sur la branche 'staging'
git checkout staging
# ... modifications ...
git push origin staging

# Le workflow deploy.yml se déclenche automatiquement
# Application accessible sur VPS staging
```

### **Production**

```bash
# Créer une release depuis main
git checkout main
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0

# Ou pusher directement sur main
git push origin main

# Le workflow deploy.yml pousse vers prod
# Application accessible sur https://model-technologie.com
```

---

## 📊 Monitoring & Logs

### **Dozzle** (Dashboard des logs)
- **URL** : http://localhost:8888 (dev) ou https://model-technologie.com:8888 (prod)
- **Fonction** : Voir les logs en temps réel de tous les containers

### **Adminer** (Gestion DB)
- **URL** : http://localhost:8080/adminer
- **Serveur** : postgres
- **User** : postgres
- **Pass** : (depuis .env)

### **Health Check**

```bash
# Frontend
curl http://localhost:3000

# Backend
curl http://localhost:8080/api/actuator/health

# DB
curl http://localhost:8080/api/actuator/db
```

---

## 🆘 Troubleshooting

### **Workflow ne se déclenche pas**

```bash
# Vérifier les logs
cd .github/workflows/
cat frontend-ci.yml | grep "paths:"

# La modification correspond-elle au path filter ?
# ✅ frontend/src/App.tsx → Déclenche FRONTEND-CI
# ✅ backend/src/... → Déclenche BACKEND-CI
# ❌ README.md → Aucun workflow
```

### **Docker compose error**

```bash
# Vérifier que Docker est lancé
docker ps

# Vérifier les ports
sudo lsof -i :3000
sudo lsof -i :8080

# Vérifier les images
docker images | grep model-tech
```

### **Database error**

```bash
# Vérifier que PostgreSQL fonctionne
docker-compose logs postgres

# Se connecter à la DB
docker-compose exec postgres psql -U postgres -d model_tech_db
```

---

## 📚 Documentation Complète

- [ARCHITECTURE.md](docs/ARCHITECTURE.md) - Architecture détaillée
- [SETUP.md](docs/SETUP.md) - Setup initial VPS
- [DEPLOYMENT.md](docs/DEPLOYMENT.md) - Guide déploiement
- [SECURITY.md](docs/SECURITY.md) - Sécurité & best practices
- [GITHUB_SECRETS.md](docs/GITHUB_SAFE) - Configuration secrets

---

## 🤝 Contributing

1. Créer une branche : `git checkout -b feature/ma-feature`
2. Faire les modifications
3. Pousser : `git push origin feature/ma-feature`
4. Créer une Pull Request
5. Attendre l'approbation et le merge

---

## 📄 License

Propriétaire © Model Technologie 2026

---

## 👤 Contact

- **Email** : dplionnel@gmail.com
- **Website** : https://model-technologie.com
- **Repository** : https://github.com/lionnelpat/model-technologie

---

**Dernière mise à jour** : Février 2026