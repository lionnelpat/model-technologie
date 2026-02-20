-- ============================================================
-- V4__Normalize_enum_values.sql
-- Normaliser les valeurs des champs enum en majuscules pour
-- correspondre aux enums Java annotés @Enumerated(EnumType.STRING).
--
-- Avant cette migration, les valeurs étaient en casse mixte :
--   level       : 'Foundation', 'Practitioner', 'Professional'
--   target_sector: 'Banking', 'Telecom'
--   status      : 'ACTIVE' (déjà correct)
--
-- Aucun changement de schéma n'est nécessaire (VARCHAR reste VARCHAR).
-- ============================================================

-- Normaliser level : 'Foundation' -> 'FOUNDATION', etc.
UPDATE bootcamps SET level = UPPER(level);

-- Normaliser target_sector : 'Banking' -> 'BANKING', etc.
UPDATE bootcamps SET target_sector = UPPER(target_sector);

-- Normaliser status par cohérence (déjà 'ACTIVE' mais sécurité par défaut)
UPDATE bootcamps SET status = UPPER(status);
