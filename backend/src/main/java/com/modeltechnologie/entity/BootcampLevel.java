package com.modeltechnologie.entity;

/**
 * Niveau de certification d'un bootcamp.
 * Stocké en base en VARCHAR via @Enumerated(EnumType.STRING).
 * Les données existantes ont été normalisées en majuscules par la migration V4.
 */
public enum BootcampLevel {
    FOUNDATION,
    PRACTITIONER,
    PROFESSIONAL
}
