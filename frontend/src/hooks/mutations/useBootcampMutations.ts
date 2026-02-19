// src/hooks/mutations/useBootcampMutations.ts
/**
 * Hook personnalisé pour les mutations bootcamp (POST, PUT, DELETE)
 */

import { useState, useCallback } from 'react';
import { bootcampService } from '@/services/bootcamp/BootcampService';
import { CreateBootcampRequest, UpdateBootcampRequest, BootcampResponse } from '@/types/bootcamp.types';
import { ApiError } from '@/types/error.types';

// ============================================================================
// Types du hook
// ============================================================================

interface MutationState<T> {
    data: T | null;
    isLoading: boolean;
    error: ApiError | null;
    isSuccess: boolean;
}

interface MutationReturn<T, V> extends MutationState<T> {
    mutate: (variables: V) => Promise<T>;
    reset: () => void;
}

// ============================================================================
// Hook pour créer un bootcamp
// ============================================================================

export function useCreateBootcamp(): MutationReturn<BootcampResponse, CreateBootcampRequest> {
    const [state, setState] = useState<MutationState<BootcampResponse>>({
        data: null,
        isLoading: false,
        error: null,
        isSuccess: false
    });

    const mutate = useCallback(async (request: CreateBootcampRequest): Promise<BootcampResponse> => {
        setState({
            data: null,
            isLoading: true,
            error: null,
            isSuccess: false
        });

        try {
            const data = await bootcampService.createBootcamp(request);
            setState({
                data,
                isLoading: false,
                error: null,
                isSuccess: true
            });
            return data;
        } catch (error) {
            const apiError = new Error(String(error));
            setState({
                data: null,
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
            throw apiError;
        }
    }, []);

    const reset = useCallback(() => {
        setState({
            data: null,
            isLoading: false,
            error: null,
            isSuccess: false
        });
    }, []);

    return { ...state, mutate, reset };
}

// ============================================================================
// Hook pour mettre à jour un bootcamp
// ============================================================================

interface UpdateVariables {
    id: number;
    request: UpdateBootcampRequest;
}

export function useUpdateBootcamp(): MutationReturn<BootcampResponse, UpdateVariables> {
    const [state, setState] = useState<MutationState<BootcampResponse>>({
        data: null,
        isLoading: false,
        error: null,
        isSuccess: false
    });

    const mutate = useCallback(async ({ id, request }: UpdateVariables): Promise<BootcampResponse> => {
        setState({
            data: null,
            isLoading: true,
            error: null,
            isSuccess: false
        });

        try {
            const data = await bootcampService.updateBootcamp(id, request);
            setState({
                data,
                isLoading: false,
                error: null,
                isSuccess: true
            });
            return data;
        } catch (error) {
            const apiError =  new Error(String(error));
            setState({
                data: null,
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
            throw apiError;
        }
    }, []);

    const reset = useCallback(() => {
        setState({
            data: null,
            isLoading: false,
            error: null,
            isSuccess: false
        });
    }, []);

    return { ...state, mutate, reset };
}

// ============================================================================
// Hook pour supprimer un bootcamp
// ============================================================================

export function useDeleteBootcamp(): MutationReturn<void, number> {
    const [state, setState] = useState<MutationState<void>>({
        data: undefined,
        isLoading: false,
        error: null,
        isSuccess: false
    });

    const mutate = useCallback(async (id: number): Promise<void> => {
        setState({
            data: undefined,
            isLoading: true,
            error: null,
            isSuccess: false
        });

        try {
            await bootcampService.deleteBootcamp(id);
            setState({
                data: undefined,
                isLoading: false,
                error: null,
                isSuccess: true
            });
        } catch (error) {
            const apiError =  new Error(String(error));
            setState({
                data: undefined,
                isLoading: false,
                error: apiError as ApiError,
                isSuccess: false
            });
            throw apiError;
        }
    }, []);

    const reset = useCallback(() => {
        setState({
            data: undefined,
            isLoading: false,
            error: null,
            isSuccess: false
        });
    }, []);

    return { ...state, mutate, reset };
}
