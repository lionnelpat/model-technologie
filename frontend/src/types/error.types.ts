// src/types/error.types.ts
/**
 * Types pour la gestion des erreurs
 */

// ============================================================================
// Énumération des types d'erreurs
// ============================================================================

export enum ApiErrorType {
    // Erreurs réseau
    NETWORK_ERROR = 'NETWORK_ERROR',
    TIMEOUT_ERROR = 'TIMEOUT_ERROR',
    OFFLINE_ERROR = 'OFFLINE_ERROR',

    // Erreurs HTTP 4xx
    BAD_REQUEST = 'BAD_REQUEST',
    UNAUTHORIZED = 'UNAUTHORIZED',
    FORBIDDEN = 'FORBIDDEN',
    NOT_FOUND = 'NOT_FOUND',
    CONFLICT = 'CONFLICT',
    VALIDATION_ERROR = 'VALIDATION_ERROR',

    // Erreurs HTTP 5xx
    SERVER_ERROR = 'SERVER_ERROR',
    SERVICE_UNAVAILABLE = 'SERVICE_UNAVAILABLE',

    // Erreurs CORS
    CORS_ERROR = 'CORS_ERROR',

    // Erreurs inconnues
    UNKNOWN_ERROR = 'UNKNOWN_ERROR'
}

// ============================================================================
// Interface d'erreur
// ============================================================================

export interface ApiError extends Error {
    type: ApiErrorType;
    statusCode?: number;
    originalError?: Error;
    validationErrors?: Record<string, string[]>;
}

// ============================================================================
// Types pour les états d'erreur
// ============================================================================

export interface ErrorState {
    type: ApiErrorType;
    message: string;
    statusCode?: number;
    timestamp: number;
    retryable: boolean;
}

export interface ValidationError {
    field: string;
    message: string;
    code?: string;
}

// ============================================================================
// Types pour le contexte d'erreur
// ============================================================================

export interface ErrorContext {
    code: string;
    message: string;
    suggestion: string;
    icon: 'alert' | 'network' | 'server' | 'unauthorized' | 'forbidden';
    retryable: boolean;
}
