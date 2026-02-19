// src/components/BootcampCard.tsx
/**
 * Composant BootcampCard
 * Affiche une carte bootcamp avec les détails
 */

import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Bootcamp } from '@/types/bootcamp.types';
import {
    Calendar,
    Clock,
    Users,
    Award,
    ArrowRight,
    CheckCircle
} from 'lucide-react';

interface BootcampCardProps {
    bootcamp: Bootcamp;
    index: number;
}

export function BootcampCard({ bootcamp, index }: Readonly<BootcampCardProps>): JSX.Element {
    return (
        <div
            className={`relative p-8 lg:p-10 rounded-2xl border transition-all duration-300 opacity-0 animate-fade-in ${
                bootcamp.featured
                    ? 'bg-gradient-to-br from-primary/5 to-accent/5 border-primary/30'
                    : 'bg-card border-border'
            }`}
            style={{ animationDelay: `${0.1 + index * 0.1}s` }}
        >
            {bootcamp.featured && (
                <div className="absolute top-4 right-4 px-3 py-1 bg-accent text-accent-foreground text-xs font-semibold rounded-full">
                    Prochaine session
                </div>
            )}

            <div className="grid lg:grid-cols-[2fr,1fr] gap-8">
                {/* Contenu gauche */}
                <div>
                    <h3 className="font-heading text-2xl lg:text-3xl font-bold text-foreground mb-4">
                        {bootcamp.title}
                    </h3>
                    <p className="text-muted-foreground mb-6 leading-relaxed">
                        {bootcamp.description}
                    </p>

                    {/* Infos bootcamp */}
                    <div className="grid sm:grid-cols-2 gap-4 mb-6">
                        <div className="flex items-center gap-3">
                            <Clock className="h-5 w-5 text-primary flex-shrink-0" />
                            <div>
                                <p className="text-xs text-muted-foreground">Durée</p>
                                <p className="text-sm font-medium text-foreground">{bootcamp.duration }</p>
                            </div>
                        </div>

                        <div className="flex items-center gap-3">
                            <Users className="h-5 w-5 text-primary flex-shrink-0" />
                            <div>
                                <p className="text-xs text-muted-foreground">Public cible</p>
                                <p className="text-sm font-medium text-foreground">{bootcamp.audience}</p>
                            </div>
                        </div>

                        <div className="flex items-center gap-3">
                            <Award className="h-5 w-5 text-primary flex-shrink-0" />
                            <div>
                                <p className="text-xs text-muted-foreground">Prérequis</p>
                                <p className="text-sm font-medium text-foreground">{bootcamp.prerequisites}</p>
                            </div>
                        </div>

                        <div className="flex items-center gap-3">
                            <Calendar className="h-5 w-5 text-accent flex-shrink-0" />
                            <div>
                                <p className="text-xs text-muted-foreground">Prochaine session</p>
                                <p className="text-sm font-medium text-accent">{bootcamp.nextSession}</p>
                            </div>
                        </div>
                    </div>

                    {/* Bénéfices */}
                    {bootcamp.benefits && bootcamp.benefits.length > 0 && (
                        <div>
                            <p className="text-sm font-medium text-foreground mb-3">Ce que vous apprendrez:</p>
                            <ul className="grid sm:grid-cols-2 gap-2">
                                {bootcamp.benefits.map((benefit) => (
                                    <li key={benefit} className="flex items-center gap-2 text-sm text-muted-foreground">
                                        <CheckCircle className="h-4 w-4 text-accent flex-shrink-0" />
                                        {benefit}
                                    </li>
                                ))}
                            </ul>
                        </div>
                    )}
                </div>

                {/* Côté droit */}
                <div className="lg:border-l lg:border-border lg:pl-8 flex flex-col justify-center">
                    <div className="text-center lg:text-left mb-6">
                        {bootcamp.priceFcfa && (
                            <>
                                <p className="text-sm text-muted-foreground mb-1">Tarif</p>
                                <p className="font-heading text-3xl font-bold text-foreground">
                                    {bootcamp.priceFcfa}
                                </p>
                            </>
                        )}
                        <p className="text-xs text-muted-foreground mt-1">Supports de formation inclus</p>
                    </div>

                    <div className="space-y-3">
                        <Button asChild className="w-full" size="lg">
                            <Link to="/contact">
                                S'inscrire
                                <ArrowRight className="h-4 w-4" />
                            </Link>
                        </Button>
                        <Button asChild variant="outline" className="w-full">
                            <Link to={`/bootcamps/${bootcamp.id}`}>
                                En savoir plus
                            </Link>
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default BootcampCard;
