// src/types/bootcamp.types.ts
/**
 * Types et interfaces pour les bootcamps
 * Séparation claire entre les types, DTOs et entités
 */

// ============================================================================
// Énumérations
// ============================================================================

export enum BootcampLevel {
    FOUNDATION = 'Foundation',
    PRACTITIONER = 'Practitioner',
    PROFESSIONAL = 'Professional'
}

export enum BootcampStatus {
    PLANNED = 'PLANNED',
    ACTIVE = 'ACTIVE',
    COMPLETED = 'COMPLETED',
    ARCHIVED = 'ARCHIVED'
}

export enum BootcampSessionStatus {
    SCHEDULED = 'SCHEDULED',
    ONGOING = 'ONGOING',
    COMPLETED = 'COMPLETED',
    CANCELLED = 'CANCELLED'
}

export enum TargetSector {
    BANKING = 'Banking',
    TELECOM = 'Telecom',
    RETAIL = 'Retail',
    HEALTH = 'Health',
    AGRITECH = 'Agritech',
    GOVERNMENT = 'Government'
}

// ============================================================================
// Entités (représentent les données du backend)
// ============================================================================

export interface BootcampBenefit {
    id: string;
    benefitText: string;
    displayOrder: number;
}

export interface BootcampSession {
    id: string;
    sessionName: string;
    startDate: string;
    endDate: string;
    country: string;
    location: string;
    language: string;
    status: BootcampSessionStatus;
}

export interface Bootcamp {
    id: number;
    title: string;
    description: string;
    audience: string;
    prerequisites: string;
    level: BootcampLevel;
    duration: number;
    nextSession: string;
    durationWeeks: number;
    durationDays: number;
    durationHours: number;
    maxStudents: number | null;
    priceEuros: number | null;
    priceFcfa: string | null;
    currency: string;
    targetSector: TargetSector;
    featured: boolean;
    status: BootcampStatus;
    benefits: string[];
    sessions: BootcampSession[];
    createdAt: string;
    updatedAt: string;
}

// ============================================================================
// DTOs (Data Transfer Objects) - Requêtes/Réponses API
// ============================================================================

export interface CreateBootcampRequest {
    title: string;
    description: string;
    audience: string;
    prerequisites: string;
    level: BootcampLevel;
    durationWeeks: number;
    durationDays: number;
    durationHours: number;
    priceEuros?: number;
    priceFcfa?: string;
    targetSector: TargetSector;
    featured?: boolean;
    benefits?: string[];
}

export type UpdateBootcampRequest = Partial<CreateBootcampRequest>

export type BootcampResponse = Bootcamp

// ============================================================================
// Réponses paginées (pour les appels listés)
// ============================================================================

export interface PaginatedResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    currentPage: number;
    pageSize: number;
    hasNext: boolean;
    hasPrevious: boolean;
}

// ============================================================================
// Réponses génériques
// ============================================================================

export interface ApiSuccessResponse<T> {
    data: T;
    message?: string;
    timestamp?: string;
}

export interface ApiListResponse<T> {
    data: T[];
    total: number;
    page?: number;
    pageSize?: number;
}
