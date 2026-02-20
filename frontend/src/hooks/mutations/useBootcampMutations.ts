// src/hooks/mutations/useBootcampMutations.ts
/**
 * Hooks de mutation pour les bootcamps — basés sur TanStack Query
 */

import { useMutation, useQueryClient } from '@tanstack/react-query';
import { bootcampService } from '@/services/bootcamp/BootcampService';
import { CreateBootcampRequest, UpdateBootcampRequest } from '@/types/bootcamp.types';
import { bootcampKeys } from '@/hooks/queries/useBootcamps';

// ============================================================================
// Hook pour créer un bootcamp
// ============================================================================

export function useCreateBootcamp() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: (request: CreateBootcampRequest) =>
            bootcampService.createBootcamp(request),
        onSuccess: () => {
            // Invalider la liste pour forcer un rechargement
            queryClient.invalidateQueries({ queryKey: bootcampKeys.all });
        },
    });
}

// ============================================================================
// Hook pour mettre à jour un bootcamp
// ============================================================================

interface UpdateVariables {
    id: number;
    request: UpdateBootcampRequest;
}

export function useUpdateBootcamp() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ id, request }: UpdateVariables) =>
            bootcampService.updateBootcamp(id, request),
        onSuccess: (_data, variables) => {
            // Invalider le détail et toutes les listes
            queryClient.invalidateQueries({ queryKey: bootcampKeys.byId(variables.id) });
            queryClient.invalidateQueries({ queryKey: bootcampKeys.all });
        },
    });
}

// ============================================================================
// Hook pour supprimer un bootcamp
// ============================================================================

export function useDeleteBootcamp() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: (id: number) => bootcampService.deleteBootcamp(id),
        onSuccess: (_data, id) => {
            // Retirer l'entrée du cache et invalider les listes
            queryClient.removeQueries({ queryKey: bootcampKeys.byId(id) });
            queryClient.invalidateQueries({ queryKey: bootcampKeys.all });
        },
    });
}
