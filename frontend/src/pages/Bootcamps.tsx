// src/pages/BootcampsRefactored.tsx
/**
 * Page Bootcamps - Affiche la liste des bootcamps
 * ARCHITECTURE PROFESSIONNELLE avec séparation des responsabilités
 */

import { useMemo } from 'react';
import { Link } from 'react-router-dom';
import { Layout } from '@/components/layout/Layout';
import { Button } from '@/components/ui/button';
import { ErrorState } from '@/components/ui/error-state';
import { BootcampsLoadingState } from '@/components/ui/bootcamp-skeleton';
import { BootcampCard } from '../components/ui/bootcampcard';
import { useBootcamps } from '@/hooks/queries/useBootcamps';
import { Bootcamp } from '../types/bootcamp.types';
import {
  ArrowRight,
  Download,
  Calendar
} from 'lucide-react';

export function BootcampPage(): JSX.Element {
  const { data: bootcamps, isLoading, error, refetch } = useBootcamps();

  // Trier les bootcamps en vedette en premier
  const sortedBootcamps = useMemo((): Bootcamp[] => {
    return [...bootcamps].sort((a, b) => {
      if (a.featured === b.featured) return 0;
      return a.featured ? -1 : 1;
    });
  }, [bootcamps]);

  return (
      <Layout>
        {/* Hero Section */}
        <section className="py-20 lg:py-28 bg-secondary">
          <div className="container mx-auto px-4 lg:px-8">
            <div className="max-w-3xl mx-auto text-center">
              <h1 className="font-heading text-4xl md:text-5xl font-bold text-foreground mb-6">
                Bootcamps & Formations
              </h1>
              <p className="text-muted-foreground text-lg leading-relaxed mb-8">
                Des programmes intensifs pour une montée en compétences rapide.
                Formations pratiques, cas réels et certification.
              </p>
              <div className="flex flex-wrap justify-center gap-4">
                <Button asChild size="lg">
                  <Link to="/contact">
                    S'inscrire à un bootcamp
                  </Link>
                </Button>
                <Button asChild variant="outline" size="lg">
                  <a href="#" download>
                    <Download className="h-4 w-4" />
                    Télécharger le catalogue
                  </a>
                </Button>
              </div>
            </div>
          </div>
        </section>

        {/* Contenu principal */}
        <section className="py-20 lg:py-28">
          <div className="container mx-auto px-4 lg:px-8">
            {/* État de chargement */}
            {isLoading && <BootcampsLoadingState count={4} />}

            {/* État d'erreur */}
            {error && !isLoading && <ErrorState error={error} onRetry={refetch} />}

            {/* Liste des bootcamps */}
            {!isLoading && !error && sortedBootcamps.length > 0 && (
                <div className="space-y-8">
                  {sortedBootcamps.map((bootcamp, index) => (
                      <BootcampCard key={bootcamp.id} bootcamp={bootcamp} index={index} />
                  ))}
                </div>
            )}

            {/* Aucun bootcamp */}
            {!isLoading && !error && sortedBootcamps.length === 0 && (
                <div className="flex flex-col items-center justify-center py-12 px-4">
                  <div className="max-w-md w-full text-center">
                    <div className="flex justify-center mb-4">
                      <div className="p-3 bg-muted rounded-full">
                        <Calendar className="h-8 w-8 text-muted-foreground" />
                      </div>
                    </div>
                    <h3 className="text-lg font-semibold text-foreground mb-2">
                      Aucun bootcamp disponible
                    </h3>
                    <p className="text-sm text-muted-foreground mb-6">
                      Aucun bootcamp n'est actuellement disponible. Veuillez revenir plus tard.
                    </p>
                    <Button onClick={() => refetch()} variant="outline" size="sm">
                      Rafraîchir
                    </Button>
                  </div>
                </div>
            )}
          </div>
        </section>

        {/* CTA */}
        <section className="py-20 lg:py-28 bg-secondary">
          <div className="container mx-auto px-4 lg:px-8">
            <div className="max-w-3xl mx-auto text-center">
              <h2 className="font-heading text-3xl md:text-4xl font-bold text-foreground mb-6">
                Besoin d'une formation sur-mesure ?
              </h2>
              <p className="text-muted-foreground text-lg mb-8">
                Nous pouvons adapter nos bootcamps à vos besoins spécifiques ou créer un programme entièrement personnalisé.
              </p>
              <Button asChild size="xl">
                <Link to="/contact">
                  Discutons de votre projet
                  <ArrowRight className="h-5 w-5" />
                </Link>
              </Button>
            </div>
          </div>
        </section>
      </Layout>
  );
}

export default BootcampPage;
