// src/hooks/queries/useBootcamps.ts
/**
 * Hook personnalisé pour récupérer les bootcamps
 * Gère les états de chargement, erreur et données
 */

import { useState, useEffect, useCallback } from 'react';
import { bootcampService } from '@/services/bootcamp/BootcampService';
import { Bootcamp, BootcampLevel, TargetSector } from '@/types/bootcamp.types';
import { ApiError } from '@/services/errors/ApiError';

// ============================================================================
// Types du hook
// ============================================================================

interface UseBootcampsState {
    data: Bootcamp[];
    isLoading: boolean;
    error: ApiError | null;
    isSuccess: boolean;
}

interface UseBootcampsReturn extends UseBootcampsState {
    refetch: () => Promise<void>;
}

interface UseBootcampByIdReturn {
    data: Bootcamp | null;
    isLoading: boolean;
    error: ApiError | null;
    isSuccess: boolean;
    refetch: () => Promise<void>;
}

// ============================================================================
// Hook pour récupérer tous les bootcamps
// ============================================================================

export function useBootcamps(): UseBootcampsReturn {
    const [state, setState] = useState<UseBootcampsState>({
        data: [],
        isLoading: true,
        error: null,
        isSuccess: false
    });

    const refetch = useCallback(async () => {
        setState(prev => ({ ...prev, isLoading: true, error: null }));

        try {
            const data = await bootcampService.getAllActiveBootcamps();
            setState({
                data,
                isLoading: false,
                error: null,
                isSuccess: true
            });
        } catch (error) {
            const apiError = error instanceof ApiError ? error : new Error(String(error));
            setState({
                data: [],
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
        }
    }, []);

    useEffect(() => {
        refetch();
    }, [refetch]);

    return { ...state, refetch };
}

// ============================================================================
// Hook pour récupérer un bootcamp par ID
// ============================================================================

export function useBootcampById(id: number | null): UseBootcampByIdReturn {
    const [state, setState] = useState<Omit<UseBootcampByIdReturn, 'refetch'>>({
        data: null,
        isLoading: true,
        error: null,
        isSuccess: false
    });

    const refetch = useCallback(async () => {
        if (id === null) return;

        setState(prev => ({ ...prev, isLoading: true, error: null }));

        try {
            const data = await bootcampService.getBootcampById(id);
            setState({
                data,
                isLoading: false,
                error: null,
                isSuccess: true
            });
        } catch (error) {
            const apiError = error instanceof ApiError ? error : new Error(String(error));
            setState({
                data: null,
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
        }
    }, [id]);

    useEffect(() => {
        refetch();
    }, [refetch]);

    return { ...state, refetch };
}

// ============================================================================
// Hook pour récupérer les bootcamps en vedette
// ============================================================================

export function useFeaturedBootcamps(): UseBootcampsReturn {
    const [state, setState] = useState<UseBootcampsState>({
        data: [],
        isLoading: true,
        error: null,
        isSuccess: false
    });

    const refetch = useCallback(async () => {
        setState(prev => ({ ...prev, isLoading: true, error: null }));

        try {
            const data = await bootcampService.getFeaturedBootcamps();
            setState({
                data,
                isLoading: false,
                error: null,
                isSuccess: true
            });
        } catch (error) {
            const apiError = error instanceof ApiError ? error : new Error(String(error));
            setState({
                data: [],
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
        }
    }, []);

    useEffect(() => {
        refetch();
    }, [refetch]);

    return { ...state, refetch };
}

// ============================================================================
// Hook pour récupérer les bootcamps par niveau
// ============================================================================

export function useBootcampsByLevel(level: BootcampLevel | null): UseBootcampsReturn {
    const [state, setState] = useState<UseBootcampsState>({
        data: [],
        isLoading: true,
        error: null,
        isSuccess: false
    });

    const refetch = useCallback(async () => {
        if (level === null) return;

        setState(prev => ({ ...prev, isLoading: true, error: null }));

        try {
            const data = await bootcampService.getBootcampsByLevel(level);
            setState({
                data,
                isLoading: false,
                error: null,
                isSuccess: true
            });
        } catch (error) {
            const apiError = error instanceof ApiError ? error : new Error(String(error));
            setState({
                data: [],
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
        }
    }, [level]);

    useEffect(() => {
        refetch();
    }, [refetch]);

    return { ...state, refetch };
}

// ============================================================================
// Hook pour récupérer les bootcamps par secteur
// ============================================================================

export function useBootcampsByTargetSector(sector: TargetSector | null): UseBootcampsReturn {
    const [state, setState] = useState<UseBootcampsState>({
        data: [],
        isLoading: true,
        error: null,
        isSuccess: false
    });

    const refetch = useCallback(async () => {
        if (sector === null) return;

        setState(prev => ({ ...prev, isLoading: true, error: null }));

        try {
            const data = await bootcampService.getBootcampsByTargetSector(sector);
            setState({
                data,
                isLoading: false,
                error: null,
                isSuccess: true
            });
        } catch (error) {
            const apiError = error instanceof ApiError ? error : new Error(String(error));
            setState({
                data: [],
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
        }
    }, [sector]);

    useEffect(() => {
        refetch();
    }, [refetch]);

    return { ...state, refetch };
}
