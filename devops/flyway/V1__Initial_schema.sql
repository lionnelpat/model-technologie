-- ============================================
-- V1__Initial_schema.sql
-- Initial database schema for Model Technologie
-- ============================================

-- Create bootcamps table
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
                                         is_active BOOLEAN DEFAULT true
);

-- Create bootcamp_sessions table
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
                                                 is_active BOOLEAN DEFAULT true
);

-- Create alumni table
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
                                      is_active BOOLEAN DEFAULT true
);

-- Create alumni_bootcamp_enrollments table
CREATE TABLE IF NOT EXISTS alumni_bootcamp_enrollments (
                                                           id BIGSERIAL PRIMARY KEY,
                                                           alumni_id BIGINT NOT NULL REFERENCES alumni(id),
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
                                                           UNIQUE(alumni_id, bootcamp_session_id)
);

-- Create project_gallery table
CREATE TABLE IF NOT EXISTS project_galleries (
                                               id BIGSERIAL PRIMARY KEY,
                                               alumni_id BIGINT NOT NULL REFERENCES alumni(id),
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
                                               is_active BOOLEAN DEFAULT true
);