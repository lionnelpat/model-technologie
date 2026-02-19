.PHONY: help build start stop restart logs lint format clean test dev prod docker-up docker-down \
        backend-build backend-run backend-lint backend-test backend-clean \
        frontend-build frontend-run frontend-lint frontend-test frontend-clean \
        db-init db-clean db-migrate \
        install update status ps

# Variables
DOCKER_COMPOSE = devops/docker-compose.dev
BACKEND_DIR = ./backend
FRONTEND_DIR = ./frontend
PROJECT_NAME = model-technologie

# Couleurs pour l'affichage
RED = \033[0;31m
GREEN = \033[0;32m
YELLOW = \033[1;33m
BLUE = \033[0;34m
NC = \033[0m # No Color

# Logo
LOGO = echo "$(BLUE)╔════════════════════════════════════════════════════════╗$(NC)"; \
       echo "$(BLUE)║  Model Technologie - Development Environment           ║$(NC)"; \
       echo "$(BLUE)╚════════════════════════════════════════════════════════╝$(NC)"

help: ## Affiche cette aide
	@$(LOGO)
	@echo ""
	@echo "$(GREEN)📋 COMMANDES DISPONIBLES$(NC)"
	@echo ""
	@echo "$(YELLOW)🐳 DOCKER & INFRASTRUCTURE$(NC)"
	@echo "  make docker-up          → Démarrer tous les services (PostgreSQL, Redis, etc.)"
	@echo "  make docker-down        → Arrêter tous les services"
	@echo "  make docker-restart     → Redémarrer tous les services"
	@echo "  make docker-logs        → Afficher les logs de tous les services"
	@echo "  make docker-ps          → Voir l'état des services"
	@echo "  make docker-clean       → Arrêter et supprimer les volumes"
	@echo ""
	@echo "$(YELLOW)🔧 BACKEND (Spring Boot)$(NC)"
	@echo "  make backend-build      → Compiler le backend (Maven)"
	@echo "  make backend-run        → Démarrer le backend seul"
	@echo "  make backend-lint       → Lancer SonarQube lint (CheckStyle)"
	@echo "  make backend-format     → Formater le code Java (Spotless)"
	@echo "  make backend-test       → Exécuter les tests unitaires"
	@echo "  make backend-test-all   → Tests unitaires + intégration"
	@echo "  make backend-clean      → Nettoyer la compilation"
	@echo "  make backend-logs       → Afficher les logs du backend"
	@echo ""
	@echo "$(YELLOW)⚛️  FRONTEND (React)$(NC)"
	@echo "  make frontend-build     → Compiler le frontend"
	@echo "  make frontend-run       → Démarrer le serveur de développement"
	@echo "  make frontend-lint      → Lancer ESLint et Prettier"
	@echo "  make frontend-format    → Formater le code avec Prettier"
	@echo "  make frontend-test      → Exécuter les tests Jest"
	@echo "  make frontend-clean     → Nettoyer node_modules et build"
	@echo ""
	@echo "$(YELLOW)🗄️  DATABASE$(NC)"
	@echo "  make db-init            → Initialiser la base de données"
	@echo "  make db-migrate         → Exécuter les migrations (Flyway)"
	@echo "  make db-reset           → Réinitialiser la base de données"
	@echo "  make db-shell           → Accéder au shell PostgreSQL"
	@echo ""
	@echo "$(YELLOW)🚀 GLOBAL$(NC)"
	@echo "  make install            → Installer les dépendances (backend + frontend)"
	@echo "  make dev                → Lancer tous les services en développement"
	@echo "  make prod               → Lancer tous les services en production"
	@echo "  make status             → Afficher le statut global"
	@echo "  make clean              → Nettoyer tous les artifacts"
	@echo "  make start              → Alias pour dev"
	@echo "  make stop               → Arrêter tous les services"
	@echo "  make restart            → Redémarrer tous les services"
	@echo ""

# ============================================================================
# 🐳 DOCKER & INFRASTRUCTURE
# ============================================================================

docker-up: ## Démarrer tous les services Docker
	@echo "$(GREEN)▶️  Démarrage des services Docker...$(NC)"
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) up -d
	@echo "$(GREEN)✅ Services démarrés!$(NC)"
	@make docker-ps

docker-down: ## Arrêter tous les services Docker
	@echo "$(YELLOW)⏹️  Arrêt des services Docker...$(NC)"
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) down
	@echo "$(GREEN)✅ Services arrêtés!$(NC)"

docker-restart: docker-down docker-up ## Redémarrer tous les services

docker-logs: ## Afficher les logs de tous les services
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) logs -f

docker-logs-backend: ## Afficher les logs du backend uniquement
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) logs -f backend

docker-logs-frontend: ## Afficher les logs du frontend uniquement
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) logs -f frontend

docker-logs-db: ## Afficher les logs de la base de données
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) logs -f postgres

docker-ps: ## Afficher l'état des services
	@echo "$(BLUE)📊 État des services:$(NC)"
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) ps

docker-clean: docker-down ## Arrêter et supprimer les volumes
	@echo "$(YELLOW)🧹 Suppression des volumes Docker...$(NC)"
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) down -v
	@echo "$(GREEN)✅ Volumes supprimés!$(NC)"

# ============================================================================
# 🔧 BACKEND (Spring Boot)
# ============================================================================

backend-build: ## Compiler le backend avec Maven
	@echo "$(GREEN)▶️  Compilation du backend...$(NC)"
	@cd $(BACKEND_DIR) && mvn clean package -DskipTests
	@echo "$(GREEN)✅ Backend compilé!$(NC)"

backend-build-docker: ## Compiler le backend avec Docker
	@echo "$(GREEN)▶️  Compilation du backend avec Docker...$(NC)"
	@cd $(BACKEND_DIR) && mvn clean package -DskipTests -f pom.xml
	@echo "$(GREEN)✅ Backend compilé!$(NC)"

backend-run: docker-up ## Démarrer le backend seul
	@echo "$(GREEN)▶️  Démarrage du backend...$(NC)"
	@cd $(BACKEND_DIR) && mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

backend-run-prod: ## Démarrer le backend en production
	@echo "$(GREEN)▶️  Démarrage du backend (PROD)...$(NC)"
	@cd $(BACKEND_DIR) && mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"

backend-lint: ## Lancer SonarQube lint avec CheckStyle
	@echo "$(GREEN)▶️  Lancement du lint (CheckStyle + SpotBugs)...$(NC)"
	@cd $(BACKEND_DIR) && mvn checkstyle:check spotbugs:check
	@echo "$(GREEN)✅ Lint terminé!$(NC)"

backend-format: ## Formater le code Java avec Spotless
	@echo "$(GREEN)▶️  Formatage du code Java...$(NC)"
	@cd $(BACKEND_DIR) && mvn spotless:apply
	@echo "$(GREEN)✅ Code formaté!$(NC)"

backend-test: ## Exécuter les tests unitaires
	@echo "$(GREEN)▶️  Exécution des tests unitaires...$(NC)"
	@cd $(BACKEND_DIR) && mvn test
	@echo "$(GREEN)✅ Tests terminés!$(NC)"

backend-test-all: ## Exécuter tous les tests (unitaires + intégration)
	@echo "$(GREEN)▶️  Exécution de tous les tests...$(NC)"
	@cd $(BACKEND_DIR) && mvn verify
	@echo "$(GREEN)✅ Tous les tests terminés!$(NC)"

backend-clean: ## Nettoyer la compilation du backend
	@echo "$(YELLOW)🧹 Nettoyage du backend...$(NC)"
	@cd $(BACKEND_DIR) && mvn clean
	@echo "$(GREEN)✅ Backend nettoyé!$(NC)"

backend-logs: ## Afficher les logs du backend
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) logs -f backend

backend-debug: ## Démarrer le backend en mode debug
	@echo "$(GREEN)▶️  Démarrage du backend (DEBUG)...$(NC)"
	@cd $(BACKEND_DIR) && mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"

# ============================================================================
# ⚛️  FRONTEND (React)
# ============================================================================

frontend-install: ## Installer les dépendances frontend
	@echo "$(GREEN)▶️  Installation des dépendances (npm)...$(NC)"
	@cd $(FRONTEND_DIR) && npm install
	@echo "$(GREEN)✅ Dépendances installées!$(NC)"

frontend-build: ## Compiler le frontend pour la production
	@echo "$(GREEN)▶️  Compilation du frontend...$(NC)"
	@cd $(FRONTEND_DIR) && npm run build
	@echo "$(GREEN)✅ Frontend compilé!$(NC)"

frontend-run: ## Démarrer le serveur de développement React
	@echo "$(GREEN)▶️  Démarrage du serveur React (dev)...$(NC)"
	@cd $(FRONTEND_DIR) && npm start

frontend-lint: ## Lancer ESLint
	@echo "$(GREEN)▶️  Lancement d'ESLint...$(NC)"
	@cd $(FRONTEND_DIR) && npm run lint
	@echo "$(GREEN)✅ Lint terminé!$(NC)"

frontend-lint-fix: ## Corriger les erreurs ESLint automatiquement
	@echo "$(GREEN)▶️  Correction automatique d'ESLint...$(NC)"
	@cd $(FRONTEND_DIR) && npm run lint:fix
	@echo "$(GREEN)✅ Erreurs corrigées!$(NC)"

frontend-format: ## Formater le code avec Prettier
	@echo "$(GREEN)▶️  Formatage du code (Prettier)...$(NC)"
	@cd $(FRONTEND_DIR) && npm run format
	@echo "$(GREEN)✅ Code formaté!$(NC)"

frontend-test: ## Exécuter les tests Jest
	@echo "$(GREEN)▶️  Exécution des tests Jest...$(NC)"
	@cd $(FRONTEND_DIR) && npm test
	@echo "$(GREEN)✅ Tests terminés!$(NC)"

frontend-test-coverage: ## Générer un rapport de couverture de tests
	@echo "$(GREEN)▶️  Génération du rapport de couverture...$(NC)"
	@cd $(FRONTEND_DIR) && npm test -- --coverage
	@echo "$(GREEN)✅ Rapport généré!$(NC)"

frontend-clean: ## Nettoyer les dépendances et build
	@echo "$(YELLOW)🧹 Nettoyage du frontend...$(NC)"
	@cd $(FRONTEND_DIR) && rm -rf node_modules build dist
	@echo "$(GREEN)✅ Frontend nettoyé!$(NC)"

frontend-logs: ## Afficher les logs du frontend
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) logs -f frontend

# ============================================================================
# 🗄️  DATABASE
# ============================================================================

db-init: docker-up ## Initialiser la base de données
	@echo "$(GREEN)▶️  Initialisation de la base de données...$(NC)"
	@sleep 5  # Attendre que PostgreSQL soit prêt
	@cd $(BACKEND_DIR) && mvn flyway:migrate -Dspring.profiles.active=dev
	@echo "$(GREEN)✅ Base de données initialisée!$(NC)"

db-migrate: ## Exécuter les migrations Flyway
	@echo "$(GREEN)▶️  Exécution des migrations...$(NC)"
	@cd $(BACKEND_DIR) && mvn flyway:migrate
	@echo "$(GREEN)✅ Migrations terminées!$(NC)"

db-reset: ## Réinitialiser la base de données (ATTENTION: supprime les données)
	@echo "$(RED)⚠️  ATTENTION: Cette opération va réinitialiser la base de données!$(NC)"
	@read -p "Êtes-vous sûr? [y/n] " -n 1 -r; \
	echo; \
	if [[ $$REPLY =~ ^[Yy]$$ ]]; then \
		echo "$(YELLOW)Suppression de la base...$(NC)"; \
		cd $(BACKEND_DIR) && mvn flyway:clean && mvn flyway:migrate; \
		echo "$(GREEN)✅ Base de données réinitialisée!$(NC)"; \
	else \
		echo "$(YELLOW)Opération annulée.$(NC)"; \
	fi

db-shell: ## Accéder au shell PostgreSQL
	@echo "$(BLUE)🔌 Connexion à PostgreSQL...$(NC)"
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) exec postgres psql -U modeltechnologie -d modeltechnologie

db-backup: ## Sauvegarder la base de données
	@echo "$(GREEN)▶️  Création d'une sauvegarde...$(NC)"
	@mkdir -p ./backups
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) exec postgres pg_dump -U modeltechnologie modeltechnologie > ./backups/backup_$$(date +%Y%m%d_%H%M%S).sql
	@echo "$(GREEN)✅ Sauvegarde créée!$(NC)"

db-logs: ## Afficher les logs de la base de données
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) logs -f postgres

# ============================================================================
# 🚀 GLOBAL
# ============================================================================

install: ## Installer toutes les dépendances (backend + frontend)
	@echo "$(GREEN)▶️  Installation de toutes les dépendances...$(NC)"
	@cd $(BACKEND_DIR) && mvn install -DskipTests
	@cd $(FRONTEND_DIR) && npm install
	@echo "$(GREEN)✅ Dépendances installées!$(NC)"

dev: docker-up ## Lancer tous les services en mode développement
	@echo "$(GREEN)▶️  Mode DÉVELOPPEMENT activé$(NC)"
	@echo "$(BLUE)Backend:  http://localhost:8080/api$(NC)"
	@echo "$(BLUE)Frontend: http://localhost:3000$(NC)"
	@echo "$(BLUE)Swagger:  http://localhost:8080/api/swagger-ui.html$(NC)"
	@echo "$(BLUE)PgAdmin:  http://localhost:5050$(NC)"

prod: docker-up ## Lancer tous les services en production
	@echo "$(GREEN)▶️  Mode PRODUCTION activé$(NC)"
	@echo "$(RED)Attention: Mode production!$(NC)"

start: dev ## Alias pour dev

stop: docker-down ## Arrêter tous les services

restart: docker-restart ## Redémarrer tous les services

status: ## Afficher le statut complet du projet
	@echo "$(BLUE)╔════════════════════════════════════════════════════════╗$(NC)"
	@echo "$(BLUE)║             STATUT DU PROJET                          ║$(NC)"
	@echo "$(BLUE)╚════════════════════════════════════════════════════════╝$(NC)"
	@echo ""
	@echo "$(YELLOW)🐳 Services Docker:$(NC)"
	@$(DOCKER_COMPOSE) -p $(PROJECT_NAME) ps
	@echo ""
	@echo "$(YELLOW)📊 Versions:$(NC)"
	@echo "  Java:   $$(java -version 2>&1 | grep version | cut -d' ' -f3)"
	@echo "  Node:   $$(node --version)"
	@echo "  npm:    $$(npm --version)"
	@echo "  Maven:  $$(mvn --version | head -1)"
	@echo "  Docker: $$(docker --version)"
	@echo ""

ps: ## Afficher les services en cours d'exécution (alias docker-ps)
	@make docker-ps

# ============================================================================
# 🧹 NETTOYAGE GLOBAL
# ============================================================================

clean: ## Nettoyer tous les artifacts et caches
	@echo "$(YELLOW)🧹 Nettoyage global...$(NC)"
	@make backend-clean
	@make frontend-clean
	@echo "$(YELLOW)Suppression des caches...$(NC)"
	@find . -type d -name ".mvn" -exec rm -rf {} + 2>/dev/null || true
	@find . -type d -name "node_modules" -exec rm -rf {} + 2>/dev/null || true
	@find . -type d -name "dist" -exec rm -rf {} + 2>/dev/null || true
	@find . -type d -name "build" -exec rm -rf {} + 2>/dev/null || true
	@echo "$(GREEN)✅ Nettoyage terminé!$(NC)"

clean-all: docker-clean clean ## Nettoyage complet (services + artifacts)
	@echo "$(GREEN)✅ Nettoyage complet terminé!$(NC)"

# ============================================================================
# 🔄 WORKFLOWS COMPLETS
# ============================================================================

setup: ## Configuration initiale complète du projet
	@echo "$(BLUE)╔════════════════════════════════════════════════════════╗$(NC)"
	@echo "$(BLUE)║        SETUP INITIAL DU PROJET                        ║$(NC)"
	@echo "$(BLUE)╚════════════════════════════════════════════════════════╝$(NC)"
	@make install
	@make docker-up
	@sleep 5
	@make db-migrate
	@echo "$(GREEN)✅ Setup initial terminé!$(NC)"
	@make dev

full-build: ## Build complet (backend + frontend + tests)
	@echo "$(BLUE)BUILD COMPLET$(NC)"
	@make backend-build
	@make backend-test
	@make frontend-build
	@echo "$(GREEN)✅ Build complet terminé!$(NC)"

full-lint: ## Lint complet (backend + frontend)
	@echo "$(BLUE)LINT COMPLET$(NC)"
	@make backend-lint
	@make frontend-lint
	@echo "$(GREEN)✅ Lint complet terminé!$(NC)"

full-format: ## Formater tout le code (backend + frontend)
	@echo "$(BLUE)FORMATAGE COMPLET$(NC)"
	@make backend-format
	@make frontend-format
	@echo "$(GREEN)✅ Formatage complet terminé!$(NC)"

# ============================================================================
# 📝 UTILITAIRES
# ============================================================================

version: ## Afficher les versions des outils
	@echo "$(BLUE)📦 VERSIONS:$(NC)"
	@echo "  Java:      $$(java -version 2>&1 | grep version | cut -d' ' -f3 || echo 'Non installé')"
	@echo "  Maven:     $$(mvn --version 2>/dev/null | head -1 || echo 'Non installé')"
	@echo "  Node:      $$(node --version || echo 'Non installé')"
	@echo "  npm:       $$(npm --version || echo 'Non installé')"
	@echo "  Docker:    $$(docker --version || echo 'Non installé')"
	@echo "  Docker Compose: $$(docker-compose --version || echo 'Non installé')"

# ============================================================================
# 📚 OPENAPI / SWAGGER
# ============================================================================

swagger-open: ## Ouvrir la documentation Swagger
	@echo "$(GREEN)🔍 Ouverture de Swagger...$(NC)"
	@command -v xdg-open >/dev/null 2>&1 && xdg-open http://localhost:8080/api/swagger-ui.html || \
	command -v open >/dev/null 2>&1 && open http://localhost:8080/api/swagger-ui.html || \
	echo "$(YELLOW)Accédez à: http://localhost:8080/api/swagger-ui.html$(NC)"

# ============================================================================
# Par défaut
# ============================================================================

.DEFAULT_GOAL := help