-- ============================================
-- Model Technologie - Database Initialization
-- ============================================

-- Create extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- Create schemas
CREATE SCHEMA IF NOT EXISTS public;

-- Set search path
SET search_path TO public;

-- ============================================
-- Create Tables
-- ============================================

-- Bootcamps table
CREATE TABLE IF NOT EXISTS bootcamps (
                                         id BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL UNIQUE,
                                         description TEXT,
                                         level VARCHAR(50) NOT NULL,
                                         duration_weeks INTEGER NOT NULL,
                                         max_students INTEGER,
                                         price_euros NUMERIC(10, 2),
                                         currency VARCHAR(3) DEFAULT 'EUR',
                                         target_sector VARCHAR(100),
                                         status VARCHAR(50) DEFAULT 'PLANNED',
                                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         is_active BOOLEAN DEFAULT true,
                                         CONSTRAINT valid_level CHECK (level IN ('Foundation', 'Practitioner', 'Professional')),
                                         CONSTRAINT valid_status CHECK (status IN ('PLANNED', 'ACTIVE', 'COMPLETED', 'ARCHIVED'))
);

-- Bootcamp Sessions table
CREATE TABLE IF NOT EXISTS bootcamp_sessions (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 bootcamp_id BIGINT NOT NULL REFERENCES bootcamps(id),
                                                 session_name VARCHAR(255) NOT NULL,
                                                 start_date DATE NOT NULL,
                                                 end_date DATE NOT NULL,
                                                 country VARCHAR(100) NOT NULL,
                                                 location VARCHAR(255) NOT NULL,
                                                 language VARCHAR(10) DEFAULT 'FR',
                                                 status VARCHAR(50) DEFAULT 'SCHEDULED',
                                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                 is_active BOOLEAN DEFAULT true,
                                                 CONSTRAINT valid_session_status CHECK (status IN ('SCHEDULED', 'ONGOING', 'COMPLETED', 'CANCELLED')),
                                                 CONSTRAINT valid_dates CHECK (end_date > start_date)
);

-- Alumni table
CREATE TABLE IF NOT EXISTS alumnis (
                                      id BIGSERIAL PRIMARY KEY,
                                      first_name VARCHAR(100) NOT NULL,
                                      last_name VARCHAR(100) NOT NULL,
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      phone_number VARCHAR(20),
                                      country VARCHAR(100),
                                      city VARCHAR(100),
                                      linkedin_profile VARCHAR(500),
                                      github_profile VARCHAR(500),
                                      bio TEXT,
                                      current_job_title VARCHAR(255),
                                      current_company VARCHAR(255),
                                      skills TEXT,
                                      portfolio_url VARCHAR(500),
                                      status VARCHAR(50) DEFAULT 'ACTIVE',
                                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      is_active BOOLEAN DEFAULT true,
                                      CONSTRAINT valid_alumni_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'ARCHIVED'))
);

-- Alumni Bootcamp Enrollments table
CREATE TABLE IF NOT EXISTS alumni_bootcamp_enrollments (
                                                           id BIGSERIAL PRIMARY KEY,
                                                           alumni_id BIGINT NOT NULL REFERENCES alumnis(id),
                                                           bootcamp_session_id BIGINT NOT NULL REFERENCES bootcamp_sessions(id),
                                                           enrollment_date DATE NOT NULL,
                                                           completion_date DATE,
                                                           final_score NUMERIC(5, 2),
                                                           status VARCHAR(50) DEFAULT 'ENROLLED',
                                                           certificate_issued BOOLEAN DEFAULT false,
                                                           certificate_url VARCHAR(500),
                                                           immersion_company VARCHAR(255),
                                                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                           is_active BOOLEAN DEFAULT true,
                                                           CONSTRAINT valid_enrollment_status CHECK (status IN ('ENROLLED', 'IN_PROGRESS', 'COMPLETED', 'DROPPED')),
                                                           CONSTRAINT valid_score CHECK (final_score IS NULL OR (final_score >= 0 AND final_score <= 100)),
                                                           CONSTRAINT valid_enrollment_dates CHECK (completion_date IS NULL OR completion_date >= enrollment_date),
                                                           UNIQUE(alumni_id, bootcamp_session_id)
);

-- Project Gallery table
CREATE TABLE IF NOT EXISTS project_galleries (
                                               id BIGSERIAL PRIMARY KEY,
                                               alumni_id BIGINT NOT NULL REFERENCES alumnis(id),
                                               bootcamp_session_id BIGINT NOT NULL REFERENCES bootcamp_sessions(id),
                                               project_title VARCHAR(255) NOT NULL,
                                               project_description TEXT,
                                               technologies_used TEXT,
                                               github_url VARCHAR(500),
                                               project_url VARCHAR(500),
                                               demo_video_url VARCHAR(500),
                                               project_image_url VARCHAR(500),
                                               company_partner VARCHAR(255),
                                               status VARCHAR(50) DEFAULT 'COMPLETED',
                                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                               is_active BOOLEAN DEFAULT true,
                                               CONSTRAINT valid_project_status CHECK (status IN ('ONGOING', 'COMPLETED', 'APPROVED'))
);

-- ============================================
-- Create Indexes
-- ============================================

-- Bootcamps indexes
CREATE INDEX idx_bootcamps_status ON bootcamps(status) WHERE is_active = true;
CREATE INDEX idx_bootcamps_level ON bootcamps(level) WHERE is_active = true;
CREATE INDEX idx_bootcamps_sector ON bootcamps(target_sector) WHERE is_active = true;

-- Bootcamp Sessions indexes
CREATE INDEX idx_sessions_bootcamp_id ON bootcamp_sessions(bootcamp_id) WHERE is_active = true;
CREATE INDEX idx_sessions_status ON bootcamp_sessions(status) WHERE is_active = true;
CREATE INDEX idx_sessions_dates ON bootcamp_sessions(start_date, end_date) WHERE is_active = true;
CREATE INDEX idx_sessions_country ON bootcamp_sessions(country) WHERE is_active = true;

-- Alumni indexes
CREATE INDEX idx_alumni_email ON alumnis(email) WHERE is_active = true;
CREATE INDEX idx_alumni_country ON alumnis(country) WHERE is_active = true;
CREATE INDEX idx_alumni_company ON alumnis(current_company) WHERE is_active = true;
CREATE INDEX idx_alumni_skills ON alumnis USING GIN(to_tsvector('english', skills)) WHERE is_active = true;
CREATE INDEX idx_alumni_status ON alumnis(status) WHERE is_active = true;

-- Alumni Enrollments indexes
CREATE INDEX idx_enrollments_alumni_id ON alumni_bootcamp_enrollments(alumni_id) WHERE is_active = true;
CREATE INDEX idx_enrollments_session_id ON alumni_bootcamp_enrollments(bootcamp_session_id) WHERE is_active = true;
CREATE INDEX idx_enrollments_status ON alumni_bootcamp_enrollments(status) WHERE is_active = true;
CREATE INDEX idx_enrollments_certified ON alumni_bootcamp_enrollments(certificate_issued) WHERE is_active = true;

-- Project Gallery indexes
CREATE INDEX idx_projects_alumni_id ON project_galleries(alumni_id) WHERE is_active = true;
CREATE INDEX idx_projects_session_id ON project_galleries(bootcamp_session_id) WHERE is_active = true;
CREATE INDEX idx_projects_status ON project_galleries(status) WHERE is_active = true;
CREATE INDEX idx_projects_company ON project_galleries(company_partner) WHERE is_active = true;

-- ============================================
-- Create Triggers for updated_at
-- ============================================

CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER bootcamps_update_timestamp
    BEFORE UPDATE ON bootcamps
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER bootcamp_sessions_update_timestamp
    BEFORE UPDATE ON bootcamp_sessions
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER alumni_update_timestamp
    BEFORE UPDATE ON alumnis
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER enrollments_update_timestamp
    BEFORE UPDATE ON alumni_bootcamp_enrollments
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER projects_update_timestamp
    BEFORE UPDATE ON project_galleries
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- ============================================
-- Create Views
-- ============================================

-- Alumni with enrollment count
CREATE OR REPLACE VIEW alumni_summary AS
SELECT
    a.id,
    a.email,
    a.first_name,
    a.last_name,
    COUNT(abe.id) as total_enrollments,
    COUNT(CASE WHEN abe.status = 'COMPLETED' THEN 1 END) as completed_bootcamps,
    COUNT(CASE WHEN abe.certificate_issued = true THEN 1 END) as certificates_earned,
    MAX(abe.completion_date) as last_completion_date
FROM alumnis a
         LEFT JOIN alumni_bootcamp_enrollments abe ON a.id = abe.alumni_id AND abe.is_active = true
WHERE a.is_active = true
GROUP BY a.id, a.email, a.first_name, a.last_name;

-- Bootcamp session statistics
CREATE OR REPLACE VIEW bootcamp_session_stats AS
SELECT
    bs.id as session_id,
    bs.session_name,
    b.name as bootcamp_name,
    COUNT(abe.id) as total_students,
    COUNT(CASE WHEN abe.status = 'COMPLETED' THEN 1 END) as completed_students,
    ROUND(COUNT(CASE WHEN abe.status = 'COMPLETED' THEN 1 END)::numeric / NULLIF(COUNT(abe.id), 0) * 100, 2) as completion_rate,
    COUNT(CASE WHEN abe.certificate_issued = true THEN 1 END) as certificates_issued
FROM bootcamp_sessions bs
         JOIN bootcamps b ON bs.bootcamp_id = b.id
         LEFT JOIN alumni_bootcamp_enrollments abe ON bs.id = abe.bootcamp_session_id AND abe.is_active = true
WHERE bs.is_active = true AND b.is_active = true
GROUP BY bs.id, bs.session_name, b.name;

-- ============================================
-- Grants (for application user)
-- ============================================

-- Note: Uncomment and configure for your deployment user
-- CREATE USER app_user WITH PASSWORD 'secure_password_here';
-- GRANT CONNECT ON DATABASE model_tech_db TO app_user;
-- GRANT USAGE ON SCHEMA public TO app_user;
-- GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO app_user;
-- GRANT USAGE ON ALL SEQUENCES IN SCHEMA public TO app_user;
-- ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO app_user;
-- ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT USAGE ON SEQUENCES TO app_user;

-- ============================================
-- Final Configuration
-- ============================================

-- Enable query logging for dev (comment in production)
-- SET log_statement = 'all';

-- Database created successfully
-- Run Flyway migrations after this initialization