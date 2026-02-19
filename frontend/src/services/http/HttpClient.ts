// src/services/http/HttpClient.ts
/**
 * Client HTTP réutilisable basé sur Axios
 * Gère les requêtes, réponses et erreurs de manière cohérente
 */

import axios, { AxiosInstance, AxiosError, AxiosRequestConfig } from 'axios';
import { HttpClientConfig, HttpRequestConfig, RequestParams } from '@/types/api.types';
import {
    ApiError,
    NetworkError,
    TimeoutError,
    BadRequestError,
    UnauthorizedError,
    ForbiddenError,
    NotFoundError,
    ConflictError,
    ValidationError,
    ServerError,
    ServiceUnavailableError,
    CorsError,
    UnknownError
} from '@/services/errors/ApiError';
import {ApiErrorType} from "@/types/error.types";

// ============================================================================
// Client HTTP
// ============================================================================

export class HttpClient {
    private client: AxiosInstance;
    private config: HttpClientConfig;

    constructor(config: HttpClientConfig) {
        this.config = config;
        this.client = axios.create({
            baseURL: config.baseURL,
            timeout: config.timeout ?? 10000,
            headers: config.headers ?? {},
            withCredentials: config.withCredentials ?? true
        });

        this.setupInterceptors();
    }

    /**
     * Configure les intercepteurs pour gérer les erreurs
     */
    private setupInterceptors(): void {
        this.client.interceptors.response.use(
            (response) => response,
            (error: AxiosError) => {
                throw this.handleError(error);
            }
        );
    }

    /**
     * Gère les erreurs et les transforme en erreurs personnalisées
     */
    private handleError(error: AxiosError): ApiError {
        // Erreur de réponse du serveur
        if (error.response) {
            const { status, data } = error.response;
            const errorData = data as Record<string, unknown>;

            switch (status) {
                case 400:
                    return new BadRequestError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Requête invalide',
                        (errorData?.errors as Record<string, string[]>) ?? undefined
                    );

                case 401:
                    return new UnauthorizedError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Authentification requise'
                    );

                case 403:
                    return new ForbiddenError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Accès refusé'
                    );

                case 404:
                    return new NotFoundError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Ressource non trouvée'
                    );

                case 409:
                    return new ConflictError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Conflit avec les données existantes'
                    );

                case 422:
                    return new ValidationError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Erreur de validation',
                        (errorData?.errors as Record<string, string[]>) ?? undefined
                    );

                case 500:
                    return new ServerError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Erreur serveur'
                    );

                case 503:
                    return new ServiceUnavailableError(
                        typeof errorData?.message === 'string' ? errorData.message : 'Service temporairement indisponible'
                    );

                default:
                    return new ApiError(
                        typeof errorData?.message === 'string' ? errorData.message : `Erreur HTTP ${status}`,
                        ApiErrorType.UNKNOWN_ERROR,
                        status,
                        error as Error
                    );
            }
        }

        // Erreurs réseau
        if (error.code === 'ECONNABORTED') {
            return new TimeoutError('La requête a expiré');
        }

        if (error.code === 'ENOTFOUND' || error.code === 'ECONNREFUSED') {
            return new NetworkError('Impossible de se connecter au serveur');
        }

        if (error.message === 'Network Error') {
            // Vérifier si c'est une erreur CORS
            if (error.config && !navigator.onLine) {
                return new NetworkError('Vous êtes hors ligne');
            }
            return new CorsError('Erreur CORS ou problème de connexion');
        }

        // Erreur inconnue
        return new UnknownError(error.message ?? 'Une erreur inconnue s\'est produite', error);
    }

    /**
     * Effectue une requête GET
     */
    async get<T = unknown>(
        url: string,
        config?: HttpRequestConfig
    ): Promise<T> {
        const response = await this.client.get<T>(url, this.buildConfig(config));
        return response.data;
    }

    /**
     * Effectue une requête POST
     */
    async post<T = unknown>(
        url: string,
        data?: unknown,
        config?: HttpRequestConfig
    ): Promise<T> {
        const response = await this.client.post<T>(url, data, this.buildConfig(config));
        return response.data;
    }

    /**
     * Effectue une requête PUT
     */
    async put<T = unknown>(
        url: string,
        data?: unknown,
        config?: HttpRequestConfig
    ): Promise<T> {
        const response = await this.client.put<T>(url, data, this.buildConfig(config));
        return response.data;
    }

    /**
     * Effectue une requête PATCH
     */
    async patch<T = unknown>(
        url: string,
        data?: unknown,
        config?: HttpRequestConfig
    ): Promise<T> {
        const response = await this.client.patch<T>(url, data, this.buildConfig(config));
        return response.data;
    }

    /**
     * Effectue une requête DELETE
     */
    async delete<T = unknown>(
        url: string,
        config?: HttpRequestConfig
    ): Promise<T> {
        const response = await this.client.delete<T>(url, this.buildConfig(config));
        return response.data;
    }

    /**
     * Effectue une requête GET avec paramètres de query
     */
    async getWithParams<T = unknown>(
        url: string,
        params: RequestParams,
        config?: HttpRequestConfig
    ): Promise<T> {
        // Filtrer les valeurs undefined pour être strict avec les types
        const cleanParams = Object.entries(params).reduce(
            (acc, [key, value]) => {
                if (value !== undefined) {
                    acc[key] = value;
                }
                return acc;
            },
            {} as Record<string, string | number | boolean>
        );

        return this.get<T>(url, {
            ...config,
            params: cleanParams
        });
    }

    /**
     * Ajoute un intercepteur pour les requêtes
     */
    // addRequestInterceptor(
    //     onFulfilled: (config: AxiosRequestConfig) => AxiosRequestConfig | Promise<AxiosRequestConfig>,
    //     onRejected?: (error: AxiosError) => Promise<AxiosError | unknown>
    // ): number {
    //     return this.client.interceptors.request.use(
    //         (config) => onFulfilled(config),
    //         onRejected
    //     );
    // }

    /**
     * Ajoute un header par défaut
     */
    setHeader(key: string, value: string): void {
        this.client.defaults.headers.common[key] = value;
    }

    /**
     * Supprime un header par défaut
     */
    removeHeader(key: string): void {
        delete this.client.defaults.headers.common[key as never];
    }

    /**
     * Construit la configuration Axios
     */
    private buildConfig(config?: HttpRequestConfig): AxiosRequestConfig {
        return {
            headers: config?.headers,
            params: config?.params,
            timeout: config?.timeout ?? this.config.timeout
        };
    }
}
