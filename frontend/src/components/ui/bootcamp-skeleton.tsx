// src/components/ui/bootcamp-skeleton.tsx
/**
 * Composant Skeleton pour le chargement des bootcamps
 * Affiche un placeholder animé pendant que les données chargent
 */

interface BootcampSkeletonProps {
    readonly index?: number;
}

interface BootcampsLoadingStateProps {
    readonly count?: number;
}

/**
 * Skeleton individuel pour un bootcamp
 */
export function BootcampSkeleton({ index = 0 }: BootcampSkeletonProps): JSX.Element {
    return (
        <div className="p-8 lg:p-10 rounded-2xl border bg-card border-border animate-pulse">
            <div className="grid lg:grid-cols-[2fr,1fr] gap-8">
                <div>
                    {/* Titre */}
                    <div className="h-8 bg-muted rounded-lg mb-4 w-3/4"></div>

                    {/* Description */}
                    <div className="space-y-2 mb-6">
                        <div className="h-4 bg-muted rounded-lg w-full"></div>
                        <div className="h-4 bg-muted rounded-lg w-5/6"></div>
                    </div>

                    {/* Infos (4 colonnes) */}
                    <div className="grid sm:grid-cols-2 gap-4 mb-6">
                        {[...Array(4)].map((_, i) => (
                            <div key={`info-${index}-${i}`} className="flex items-center gap-3">
                                <div className="h-5 w-5 bg-muted rounded-full flex-shrink-0"></div>
                                <div className="flex-1 space-y-1">
                                    <div className="h-3 bg-muted rounded-lg w-16"></div>
                                    <div className="h-4 bg-muted rounded-lg w-32"></div>
                                </div>
                            </div>
                        ))}
                    </div>

                    {/* Bénéfices */}
                    <div>
                        <div className="h-4 bg-muted rounded-lg mb-3 w-32"></div>
                        <div className="grid sm:grid-cols-2 gap-2">
                            {[...Array(6)].map((_, i) => (
                                <div key={`benefit-${index}-${i}`} className="flex items-center gap-2">
                                    <div className="h-4 w-4 bg-muted rounded-full flex-shrink-0"></div>
                                    <div className="h-4 bg-muted rounded-lg w-40"></div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>

                {/* Côté droit */}
                <div className="lg:border-l lg:border-border lg:pl-8 flex flex-col justify-center">
                    <div className="text-center lg:text-left mb-6">
                        <div className="h-8 bg-muted rounded-lg w-32 mx-auto lg:mx-0 mb-2"></div>
                        <div className="h-3 bg-muted rounded-lg w-40 mx-auto lg:mx-0"></div>
                    </div>

                    {/* Boutons */}
                    <div className="space-y-3">
                        <div className="h-10 bg-muted rounded-lg w-full"></div>
                        <div className="h-10 bg-muted rounded-lg w-full"></div>
                    </div>
                </div>
            </div>
        </div>
    );
}

/**
 * Conteneur avec plusieurs skeletons
 */
export function BootcampsLoadingState({ count = 4 }: BootcampsLoadingStateProps): JSX.Element {
    return (
        <div className="space-y-8">
            {[...Array(count)].map((_, i) => (
                <BootcampSkeleton key={`skeleton-${i}`} index={i} />
            ))}
        </div>
    );
}

export default BootcampSkeleton;
