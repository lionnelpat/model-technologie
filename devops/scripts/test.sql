-- Script d'insertion des alumni_bootcamp_enrollments
-- Étape 3: Création des inscriptions aux bootcamps
-- Date: 2026-02-19

BEGIN;

-- Vérifions d'abord que nous avons les données nécessaires
DO $$
    DECLARE
        v_alumni_count INTEGER;
        v_session_count INTEGER;
    BEGIN
        SELECT COUNT(*) INTO v_alumni_count FROM alumnis WHERE is_active = true;
        SELECT COUNT(*) INTO v_session_count FROM bootcamp_sessions WHERE is_active = true;

        RAISE NOTICE 'Alumni disponibles: %, Sessions disponibles: %', v_alumni_count, v_session_count;

        IF v_alumni_count = 0 OR v_session_count = 0 THEN
            RAISE EXCEPTION 'Pas assez de données pour créer les inscriptions';
        END IF;
    END $$;

-- Insertion des inscriptions
WITH enrollment_data AS (
    SELECT
        a.id AS alumni_id,
        bs.id AS session_id,
        bs.session_name,
        b.name AS bootcamp_name,
        a.first_name,
        a.last_name,
        -- Détermination des données selon le profil
        CASE
            -- Cohorte 2024 - Power BI Finance (Amadou, Fatou)
            WHEN a.email IN ('amadou.diallo@email.com', 'fatou.sow@email.com')
                AND bs.session_name = 'Cohorte 2024-Q1'
                AND b.name = 'Power BI pour la Finance' THEN 'MATCH'

            -- Cohorte 2024 - Data Analytics (Moussa, Aïssatou, Omar)
            WHEN a.email IN ('moussa.ndiaye@email.com', 'aissatou.ba@email.com', 'omar.sy@email.com')
                AND bs.session_name = 'Cohorte 2024-Q2'
                AND b.name = 'Data Analytics pour Managers' THEN 'MATCH'

            -- Cohorte 2023 - Power BI Finance (Ibrahima, Mariama)
            WHEN a.email IN ('ibrahima.fall@email.com', 'mariama.diop@email.com')
                AND bs.session_name = 'Cohorte 2023-Q4'
                AND b.name = 'Power BI pour la Finance' THEN 'MATCH'

            -- Reporting & Data Visualization (Khady)
            WHEN a.email = 'khady.gueye@email.com'
                AND bs.session_name = 'Cohorte 2024-Q3'
                AND b.name = 'Reporting & Data Visualization' THEN 'MATCH'

            -- Certification PL-300 (Souleymane)
            WHEN a.email = 'souleymane.faye@email.com'
                AND bs.session_name = 'Cohorte 2024-Q4'
                AND b.name = 'Préparation Certification PL-300' THEN 'MATCH'

            -- Data Analytics supplémentaire (Aminata)
            WHEN a.email = 'aminata.kane@email.com'
                AND bs.session_name = 'Cohorte 2024-Q2'
                AND b.name = 'Data Analytics pour Managers' THEN 'MATCH'

            ELSE 'NO_MATCH'
            END AS match_status
    FROM alumnis a
             CROSS JOIN bootcamp_sessions bs
             JOIN bootcamps b ON b.id = bs.bootcamp_id
    WHERE a.is_active = true AND bs.is_active = true
)
INSERT INTO alumni_bootcamp_enrollments (
    alumni_id,
    bootcamp_session_id,
    enrollment_date,
    completion_date,
    final_score,
    status,
    certificate_issued,
    certificate_url,
    immersion_company,
    is_active,
    created_at,
    updated_at
)
SELECT
    ed.alumni_id,
    ed.session_id,
    -- Dates d'enrollment selon la session
    CASE
        WHEN ed.session_name = 'Cohorte 2024-Q1' THEN '2024-01-15'
        WHEN ed.session_name = 'Cohorte 2024-Q2' THEN '2024-04-15'
        WHEN ed.session_name = 'Cohorte 2023-Q4' THEN '2023-09-15'
        WHEN ed.session_name = 'Cohorte 2024-Q3' THEN '2024-08-15'
        WHEN ed.session_name = 'Cohorte 2024-Q4' THEN '2024-10-15'
        END::date AS enrollment_date,
    -- Dates de complétion
    CASE
        WHEN ed.session_name = 'Cohorte 2024-Q1' THEN '2024-02-05'
        WHEN ed.session_name = 'Cohorte 2024-Q2' THEN '2024-05-03'
        WHEN ed.session_name = 'Cohorte 2023-Q4' THEN '2023-10-05'
        WHEN ed.session_name = 'Cohorte 2024-Q3' THEN '2024-09-04'
        WHEN ed.session_name = 'Cohorte 2024-Q4' THEN NULL -- Session future
        END::date AS completion_date,
    -- Scores finaux (uniquement pour les sessions terminées)
    CASE
        WHEN a.email = 'amadou.diallo@email.com' THEN 92.5
        WHEN a.email = 'fatou.sow@email.com' THEN 88.0
        WHEN a.email = 'moussa.ndiaye@email.com' THEN 95.0
        WHEN a.email = 'aissatou.ba@email.com' THEN 89.5
        WHEN a.email = 'omar.sy@email.com' THEN 91.0
        WHEN a.email = 'ibrahima.fall@email.com' THEN 94.0
        WHEN a.email = 'mariama.diop@email.com' THEN 96.5
        WHEN a.email = 'khady.gueye@email.com' THEN 93.0
        WHEN a.email = 'souleymane.faye@email.com' THEN NULL -- Session future
        WHEN a.email = 'aminata.kane@email.com' THEN 90.5
        END AS final_score,
    -- Statut
    CASE
        WHEN ed.session_name = 'Cohorte 2024-Q4' THEN 'ENROLLED'
        ELSE 'COMPLETED'
        END AS status,
    -- Certificat émis
    CASE
        WHEN ed.session_name = 'Cohorte 2024-Q4' THEN false
        ELSE true
        END AS certificate_issued,
    -- URL du certificat
    CASE
        WHEN ed.session_name != 'Cohorte 2024-Q4'
            THEN 'https://modeltechnologie.com/certificates/' || a.id || '-' || LOWER(REPLACE(a.first_name, ' ', ''))
        ELSE NULL
        END AS certificate_url,
    -- Entreprise d'immersion
    CASE
        WHEN a.email = 'amadou.diallo@email.com' THEN 'Banque Atlantique'
        WHEN a.email = 'fatou.sow@email.com' THEN 'Orange Sénégal'
        WHEN a.email = 'moussa.ndiaye@email.com' THEN 'CBAO'
        WHEN a.email = 'aissatou.ba@email.com' THEN 'Société Générale'
        WHEN a.email = 'omar.sy@email.com' THEN 'Sonatel'
        WHEN a.email = 'ibrahima.fall@email.com' THEN 'Ecobank'
        WHEN a.email = 'mariama.diop@email.com' THEN 'Total Energies'
        WHEN a.email = 'khady.gueye@email.com' THEN 'Ecobank'
        WHEN a.email = 'souleymane.faye@email.com' THEN 'Deloitte'
        WHEN a.email = 'aminata.kane@email.com' THEN 'Wave'
        END AS immersion_company,
    true AS is_active,
    NOW() AS created_at,
    NOW() AS updated_at
FROM enrollment_data ed
         JOIN alumnis a ON a.id = ed.alumni_id
WHERE ed.match_status = 'MATCH';

-- Résumé des insertions
DO $$
    DECLARE
        v_count INTEGER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM alumni_bootcamp_enrollments;
        RAISE NOTICE '✅ % inscriptions ont été créées avec succès', v_count;
    END $$;

-- Vérification détaillée
SELECT
    CONCAT(a.first_name, ' ', a.last_name) AS alumni_name,
    a.current_job_title,
    b.name AS bootcamp_name,
    bs.session_name,
    e.status,
    e.final_score,
    e.immersion_company,
    e.certificate_issued
FROM alumni_bootcamp_enrollments e
         JOIN alumnis a ON a.id = e.alumni_id
         JOIN bootcamp_sessions bs ON bs.id = e.bootcamp_session_id
         JOIN bootcamps b ON b.id = bs.bootcamp_id
ORDER BY bs.start_date DESC, a.last_name;

-- Statistiques par bootcamp
SELECT
    b.name AS bootcamp,
    COUNT(e.id) AS nombre_inscriptions,
    ROUND(AVG(e.final_score), 2) AS score_moyen,
    COUNT(CASE WHEN e.status = 'COMPLETED' THEN 1 END) AS completes,
    COUNT(CASE WHEN e.status = 'ENROLLED' THEN 1 END) AS en_attente
FROM bootcamps b
         LEFT JOIN bootcamp_sessions bs ON bs.bootcamp_id = b.id
         LEFT JOIN alumni_bootcamp_enrollments e ON e.bootcamp_session_id = bs.id
GROUP BY b.name
ORDER BY b.name;

COMMIT;