// src/components/ui/error-state.tsx
import { AlertCircle, RefreshCw, Home } from 'lucide-react';
import { Link } from 'react-router-dom';
import { Button } from './button';
import { ApiError, ApiErrorType } from '../../types/error.types';

interface ErrorStateProps {
    error: ApiError | null;
    onRetry?: () => void;
    isBackendAvailable?: boolean;
}

/**
 * Composant pour afficher les erreurs de manière user-friendly
 */
export const ErrorState = ({
                               error,
                               onRetry,
                           }: ErrorStateProps) => {
    if (!error) return null;

    // Déterminer le type d'erreur et le message à afficher
    const getErrorMessage = (): {
        title: string;
        description: string;
        suggestion: string;
    } => {
        const errorType = 'type' in error && typeof error.type === 'string'
            ? (error.type as ApiErrorType)
            : undefined;

        switch (errorType) {
            case ApiErrorType.NETWORK_ERROR:
                return {
                    title: 'Impossible de connecter au serveur',
                    description: 'Le serveur backend n\'est pas disponible ou ne répond pas.',
                    suggestion: 'Vérifiez que le serveur est démarré et que vous êtes connecté à Internet.'
                };

            case ApiErrorType.TIMEOUT_ERROR:
                return {
                    title: 'La requête a expiré',
                    description: 'Le serveur a mis trop de temps à répondre.',
                    suggestion: 'Vérifiez votre connexion Internet et essayez de nouveau.'
                };

            case ApiErrorType.CORS_ERROR:
                return {
                    title: 'Erreur de configuration CORS',
                    description: 'La communication avec le serveur est bloquée.',
                    suggestion: 'Cela peut être un problème de configuration serveur. Contactez le support.'
                };

            case ApiErrorType.NOT_FOUND:
                return {
                    title: 'Ressource non trouvée',
                    description: 'Les données demandées n\'existent pas sur le serveur.',
                    suggestion: 'Cela peut être un problème temporaire. Essayez de rafraîchir la page.'
                };

            case ApiErrorType.UNAUTHORIZED:
                return {
                    title: 'Authentification requise',
                    description: 'Vous devez vous connecter pour accéder à cette ressource.',
                    suggestion: 'Veuillez vous connecter à votre compte.'
                };

            case ApiErrorType.FORBIDDEN:
                return {
                    title: 'Accès refusé',
                    description: 'Vous n\'avez pas la permission d\'accéder à cette ressource.',
                    suggestion: 'Contactez l\'administrateur si vous pensez que c\'est une erreur.'
                };

            case ApiErrorType.SERVER_ERROR:
                return {
                    title: 'Erreur serveur',
                    description: 'Le serveur a rencontré une erreur interne.',
                    suggestion: 'L\'équipe technique a été notifiée. Essayez de nouveau dans quelques instants.'
                };

            default:
                return {
                    title: 'Une erreur s\'est produite',
                    description: error.message || 'Une erreur inattendue s\'est produite.',
                    suggestion: 'Vérifiez votre connexion et essayez de nouveau.'
                };
        }
    };

    const { title, description, suggestion } = getErrorMessage();

    return (
        <div className="flex flex-col items-center justify-center py-12 px-4">
            <div className="max-w-md w-full text-center">
                {/* Icône d'erreur */}
                <div className="flex justify-center mb-4">
                    <div className="p-3 bg-red-100 rounded-full">
                        <AlertCircle className="h-8 w-8 text-red-600" />
                    </div>
                </div>

                {/* Titre */}
                <h3 className="text-lg font-semibold text-foreground mb-2">
                    {title}
                </h3>

                {/* Description */}
                <p className="text-sm text-muted-foreground mb-2">
                    {description}
                </p>

                {/* Suggestion */}
                <p className="text-xs text-muted-foreground mb-6">
                    {suggestion}
                </p>

                {/* Détails techniques (en dev seulement) */}
                {process.env.NODE_ENV === 'development' && (
                    <details className="text-left mb-6 p-3 bg-secondary rounded text-xs">
                        <summary className="cursor-pointer font-medium mb-2">
                            Détails techniques
                        </summary>
                        <p className="break-words text-muted-foreground">
                            {error.message}
                        </p>
                        {error.statusCode && (
                            <p className="text-muted-foreground">
                                Code: {error.statusCode}
                            </p>
                        )}
                    </details>
                )}

                {/* Boutons d'action */}
                <div className="flex flex-col gap-3">
                    {onRetry && (
                        <Button
                            onClick={onRetry}
                            variant="default"
                            size="sm"
                            className="w-full"
                        >
                            <RefreshCw className="h-4 w-4 mr-2" />
                            Réessayer
                        </Button>
                    )}

                    <Button
                        asChild
                        variant="outline"
                        size="sm"
                        className="w-full"
                    >
                        <Link to="/">
                            <Home className="h-4 w-4 mr-2" />
                            Retour à l'accueil
                        </Link>
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default ErrorState;
