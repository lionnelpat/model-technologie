// src/services/errors/ApiError.ts
/**
 * Classe de base pour les erreurs API
 */

import { ApiErrorType, IApiError } from '@/types/error.types.ts';

export class ApiError extends Error implements IApiError {
    public readonly type: ApiErrorType;
    public readonly statusCode?: number;
    public readonly originalError?: Error;
    public readonly validationErrors?: Record<string, string[]>;

    constructor(
        message: string,
        type: ApiErrorType,
        statusCode?: number,
        originalError?: Error,
        validationErrors?: Record<string, string[]>
    ) {
        super(message);
        this.name = 'ApiError';
        this.type = type;
        this.statusCode = statusCode;
        this.originalError = originalError;
        this.validationErrors = validationErrors;

        // Maintenance de la chaîne de prototype pour instanceof
        Object.setPrototypeOf(this, ApiError.prototype);
    }

    public isRetryable(): boolean {
        return this.type === ApiErrorType.TIMEOUT_ERROR ||
            this.type === ApiErrorType.NETWORK_ERROR ||
            this.type === ApiErrorType.SERVICE_UNAVAILABLE;
    }

    public toJSON() {
        return {
            name: this.name,
            message: this.message,
            type: this.type,
            statusCode: this.statusCode,
            validationErrors: this.validationErrors
        };
    }
}

// ============================================================================
// Erreurs spécifiques
// ============================================================================

export class NetworkError extends ApiError {
    constructor(message: string = 'Erreur de connexion réseau') {
        super(message, ApiErrorType.NETWORK_ERROR);
        this.name = 'NetworkError';
        Object.setPrototypeOf(this, NetworkError.prototype);
    }
}

export class TimeoutError extends ApiError {
    constructor(message: string = 'La requête a expiré') {
        super(message, ApiErrorType.TIMEOUT_ERROR);
        this.name = 'TimeoutError';
        Object.setPrototypeOf(this, TimeoutError.prototype);
    }
}

export class OfflineError extends ApiError {
    constructor(message: string = 'Vous êtes hors ligne') {
        super(message, ApiErrorType.OFFLINE_ERROR);
        this.name = 'OfflineError';
        Object.setPrototypeOf(this, OfflineError.prototype);
    }
}

export class BadRequestError extends ApiError {
    constructor(
        message: string = 'Requête invalide',
        validationErrors?: Record<string, string[]>
    ) {
        super(message, ApiErrorType.BAD_REQUEST, 400, undefined, validationErrors);
        this.name = 'BadRequestError';
        Object.setPrototypeOf(this, BadRequestError.prototype);
    }
}

export class UnauthorizedError extends ApiError {
    constructor(message: string = 'Authentification requise') {
        super(message, ApiErrorType.UNAUTHORIZED, 401);
        this.name = 'UnauthorizedError';
        Object.setPrototypeOf(this, UnauthorizedError.prototype);
    }
}

export class ForbiddenError extends ApiError {
    constructor(message: string = 'Accès refusé') {
        super(message, ApiErrorType.FORBIDDEN, 403);
        this.name = 'ForbiddenError';
        Object.setPrototypeOf(this, ForbiddenError.prototype);
    }
}

export class NotFoundError extends ApiError {
    constructor(message: string = 'Ressource non trouvée') {
        super(message, ApiErrorType.NOT_FOUND, 404);
        this.name = 'NotFoundError';
        Object.setPrototypeOf(this, NotFoundError.prototype);
    }
}

export class ConflictError extends ApiError {
    constructor(message: string = 'Conflit avec les données existantes') {
        super(message, ApiErrorType.CONFLICT, 409);
        this.name = 'ConflictError';
        Object.setPrototypeOf(this, ConflictError.prototype);
    }
}

export class ValidationError extends ApiError {
    constructor(
        message: string = 'Erreur de validation',
        validationErrors?: Record<string, string[]>
    ) {
        super(message, ApiErrorType.VALIDATION_ERROR, 422, undefined, validationErrors);
        this.name = 'ValidationError';
        Object.setPrototypeOf(this, ValidationError.prototype);
    }
}

export class ServerError extends ApiError {
    constructor(message: string = 'Erreur serveur') {
        super(message, ApiErrorType.SERVER_ERROR, 500);
        this.name = 'ServerError';
        Object.setPrototypeOf(this, ServerError.prototype);
    }
}

export class ServiceUnavailableError extends ApiError {
    constructor(message: string = 'Service temporairement indisponible') {
        super(message, ApiErrorType.SERVICE_UNAVAILABLE, 503);
        this.name = 'ServiceUnavailableError';
        Object.setPrototypeOf(this, ServiceUnavailableError.prototype);
    }
}

export class CorsError extends ApiError {
    constructor(message: string = 'Erreur CORS - Vérifiez la configuration') {
        super(message, ApiErrorType.CORS_ERROR);
        this.name = 'CorsError';
        Object.setPrototypeOf(this, CorsError.prototype);
    }
}

export class UnknownError extends ApiError {
    constructor(message: string = 'Une erreur inconnue s\'est produite', originalError?: Error) {
        super(message, ApiErrorType.UNKNOWN_ERROR, undefined, originalError);
        this.name = 'UnknownError';
        Object.setPrototypeOf(this, UnknownError.prototype);
    }
}
