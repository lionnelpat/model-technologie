// src/services/bootcamp/BootcampService.ts
/**
 * Service métier pour les bootcamps
 * Utilise httpClient pour les requêtes HTTP
 * Utilise des types TypeScript strictement typés
 */

import { httpClient } from '@/services/http/';
import {
    Bootcamp,
    BootcampLevel,
    BootcampStatus,
    TargetSector,
    CreateBootcampRequest,
    UpdateBootcampRequest,
    BootcampResponse,
    PaginatedResponse
} from '@/types/bootcamp.types';

// ============================================================================
// Service Bootcamp
// ============================================================================

export class BootcampService {
    private readonly basePath = '/v1/bootcamps';

    /**
     * Récupère tous les bootcamps actifs
     */
    async getAllActiveBootcamps(): Promise<Bootcamp[]> {
        return httpClient.get<Bootcamp[]>(this.basePath);
    }

    /**
     * Récupère un bootcamp par ID
     */
    async getBootcampById(id: number): Promise<Bootcamp> {
        if (!Number.isInteger(id) || id <= 0) {
            throw new Error('ID invalide');
        }
        return httpClient.get<Bootcamp>(`${this.basePath}/${id}`);
    }

    /**
     * Récupère un bootcamp par nom
     */
    async getBootcampByName(name: string): Promise<Bootcamp> {
        if (!name || name.trim().length === 0) {
            throw new Error('Le nom du bootcamp est requis');
        }
        return httpClient.get<Bootcamp>(`${this.basePath}/name/${encodeURIComponent(name)}`);
    }

    /**
     * Récupère les bootcamps en vedette
     */
    async getFeaturedBootcamps(): Promise<Bootcamp[]> {
        return httpClient.get<Bootcamp[]>(`${this.basePath}/featured`);
    }

    /**
     * Récupère les bootcamps par niveau
     */
    async getBootcampsByLevel(level: BootcampLevel): Promise<Bootcamp[]> {
        if (!Object.values(BootcampLevel).includes(level)) {
            throw new Error(`Niveau invalide: ${level}`);
        }
        return httpClient.get<Bootcamp[]>(`${this.basePath}/level/${level}`);
    }

    /**
     * Récupère les bootcamps par secteur cible
     */
    async getBootcampsByTargetSector(sector: TargetSector): Promise<Bootcamp[]> {
        if (!Object.values(TargetSector).includes(sector)) {
            throw new Error(`Secteur invalide: ${sector}`);
        }
        return httpClient.get<Bootcamp[]>(`${this.basePath}/sector/${sector}`);
    }

    /**
     * Récupère les bootcamps par statut
     */
    async getBootcampsByStatus(status: BootcampStatus): Promise<Bootcamp[]> {
        if (!Object.values(BootcampStatus).includes(status)) {
            throw new Error(`Statut invalide: ${status}`);
        }
        return httpClient.get<Bootcamp[]>(`${this.basePath}/status/${status}`);
    }

    /**
     * Récupère les bootcamps avec pagination
     */
    async getBootcampsPaginated(
        page: number = 1,
        pageSize: number = 10
    ): Promise<PaginatedResponse<Bootcamp>> {
        if (page < 1 || pageSize < 1) {
            throw new Error('Page et pageSize doivent être >= 1');
        }
        return httpClient.get<PaginatedResponse<Bootcamp>>(
            this.basePath,
            {
                params: {
                    page: page - 1, // API utilise base 0
                    pageSize
                }
            }
        );
    }

    /**
     * Crée un nouveau bootcamp (admin)
     */
    async createBootcamp(request: CreateBootcampRequest): Promise<BootcampResponse> {
        this.validateCreateRequest(request);
        return httpClient.post<BootcampResponse>(this.basePath, request);
    }

    /**
     * Met à jour un bootcamp (admin)
     */
    async updateBootcamp(
        id: number,
        request: UpdateBootcampRequest
    ): Promise<BootcampResponse> {
        if (!Number.isInteger(id) || id <= 0) {
            throw new Error('ID invalide');
        }
        return httpClient.put<BootcampResponse>(`${this.basePath}/${id}`, request);
    }

    /**
     * Supprime un bootcamp (admin) - soft delete
     */
    async deleteBootcamp(id: number): Promise<void> {
        if (!Number.isInteger(id) || id <= 0) {
            throw new Error('ID invalide');
        }
        await httpClient.delete(`${this.basePath}/${id}`);
    }

    /**
     * Valide les données de création
     */
    private validateCreateRequest(request: CreateBootcampRequest): void {
        const errors: string[] = [];

        if (!request.title || request.title.trim().length === 0) {
            errors.push('Le titre est requis');
        }

        if (!request.description || request.description.trim().length === 0) {
            errors.push('La description est requise');
        }

        if (request.durationDays < 1) {
            errors.push('La durée en jours doit être >= 1');
        }

        if (request.durationHours < 1) {
            errors.push('La durée en heures doit être >= 1');
        }

        if (!Object.values(BootcampLevel).includes(request.level)) {
            errors.push(`Niveau invalide: ${request.level}`);
        }

        if (!Object.values(TargetSector).includes(request.targetSector)) {
            errors.push(`Secteur invalide: ${request.targetSector}`);
        }

        if (errors.length > 0) {
            throw new Error(`Erreurs de validation: ${errors.join(', ')}`);
        }
    }
}

// ============================================================================
// Instance singleton
// ============================================================================

export const bootcampService = new BootcampService();
