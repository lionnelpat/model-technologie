package com.modeltechnologie.entity;

/**
 * Statut d'un bootcamp.
 * Stocké en base en VARCHAR via @Enumerated(EnumType.STRING).
 * Les données existantes ont été normalisées en majuscules par la migration V4.
 */
public enum BootcampStatus {
    ACTIVE,
    PLANNED,
    ARCHIVED
}
