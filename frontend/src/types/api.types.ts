// src/types/api.types.ts
/**
 * Types génériques pour les appels HTTP et la gestion d'API
 */

// ============================================================================
// Types de réponse HTTP
// ============================================================================

export interface HttpResponse<T = unknown> {
    readonly status: number;
    readonly statusText: string;
    readonly data: T;
    readonly headers: Record<string, string>;
}

export interface HttpErrorResponse {
    readonly status: number;
    readonly statusText: string;
    readonly message: string;
    readonly errors?: Record<string, readonly string[]>;
    readonly timestamp?: string;
    readonly path?: string;
}

// ============================================================================
// Configuration HTTP
// ============================================================================

export interface HttpClientConfig {
    baseURL: string;
    timeout?: number;
    headers?: Record<string, string>;
    withCredentials?: boolean;
}

export interface HttpRequestConfig {
    readonly headers?: Record<string, string>;
    readonly params?: Record<string, string | number | boolean>;
    readonly timeout?: number;
}

// ============================================================================
// Types génériques pour les requêtes
// ============================================================================

/**
 * Paramètres de requête sans undefined
 * Utilisé pour les appels HTTP stricts
 */
export interface RequestParams {
    readonly [key: string]: string | number | boolean;
}

/**
 * Paramètres de requête avec undefined optionnel
 * Utilisé en interne pour permettre les undefined temporairement
 */
export interface RequestParamsWithOptional {
    readonly [key: string]: string | number | boolean | undefined;
}

export interface PaginationParams {
    page?: number;
    pageSize?: number;
    sort?: string;
    order?: 'asc' | 'desc';
}

// ============================================================================
// Types pour le streaming et progress
// ============================================================================

export interface ProgressEvent {
    loaded: number;
    total: number;
    percentage: number;
}

// ============================================================================
// Cache
// ============================================================================

export interface CacheEntry<T> {
    data: T;
    timestamp: number;
    ttl?: number; // Time to live en millisecondes
}

export interface CacheConfig {
    enabled: boolean;
    ttl?: number; // Time to live par défaut
}
