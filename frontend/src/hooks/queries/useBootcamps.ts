// src/hooks/queries/useBootcamps.ts
/**
 * Hooks de requête pour les bootcamps — basés sur TanStack Query
 */

import { useQuery } from '@tanstack/react-query';
import { bootcampService } from '@/services/bootcamp/BootcampService';
import { Bootcamp, BootcampLevel, TargetSector } from '@/types/bootcamp.types';
import { ApiError } from '@/services/errors/ApiError';

// ============================================================================
// Clés de cache centralisées
// ============================================================================

export const bootcampKeys = {
    all: ['bootcamps'] as const,
    active: () => [...bootcampKeys.all, 'active'] as const,
    featured: () => [...bootcampKeys.all, 'featured'] as const,
    byId: (id: number) => [...bootcampKeys.all, 'id', id] as const,
    byLevel: (level: BootcampLevel) => [...bootcampKeys.all, 'level', level] as const,
    bySector: (sector: TargetSector) => [...bootcampKeys.all, 'sector', sector] as const,
};

// ============================================================================
// Hook pour récupérer tous les bootcamps actifs
// ============================================================================

export function useBootcamps() {
    return useQuery<Bootcamp[], ApiError>({
        queryKey: bootcampKeys.active(),
        queryFn: () => bootcampService.getAllActiveBootcamps(),
    });
}

// ============================================================================
// Hook pour récupérer un bootcamp par ID
// ============================================================================

export function useBootcampById(id: number | null) {
    return useQuery<Bootcamp, ApiError>({
        queryKey: bootcampKeys.byId(id!),
        queryFn: () => bootcampService.getBootcampById(id!),
        enabled: id !== null && id > 0,
    });
}

// ============================================================================
// Hook pour récupérer les bootcamps en vedette
// ============================================================================

export function useFeaturedBootcamps() {
    return useQuery<Bootcamp[], ApiError>({
        queryKey: bootcampKeys.featured(),
        queryFn: () => bootcampService.getFeaturedBootcamps(),
    });
}

// ============================================================================
// Hook pour récupérer les bootcamps par niveau
// ============================================================================

export function useBootcampsByLevel(level: BootcampLevel | null) {
    return useQuery<Bootcamp[], ApiError>({
        queryKey: bootcampKeys.byLevel(level!),
        queryFn: () => bootcampService.getBootcampsByLevel(level!),
        enabled: level !== null,
    });
}

// ============================================================================
// Hook pour récupérer les bootcamps par secteur
// ============================================================================

export function useBootcampsByTargetSector(sector: TargetSector | null) {
    return useQuery<Bootcamp[], ApiError>({
        queryKey: bootcampKeys.bySector(sector!),
        queryFn: () => bootcampService.getBootcampsByTargetSector(sector!),
        enabled: sector !== null,
    });
}
