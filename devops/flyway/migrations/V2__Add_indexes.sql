-- ============================================
-- V2__Add_indexes.sql
-- Add indexes for performance optimization
-- ============================================

-- Bootcamps indexes
CREATE INDEX IF NOT EXISTS idx_bootcamps_status ON bootcamps(status) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_bootcamps_level ON bootcamps(level) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_bootcamps_sector ON bootcamps(target_sector) WHERE is_active = true;

-- Bootcamp Sessions indexes
CREATE INDEX IF NOT EXISTS idx_sessions_bootcamp_id ON bootcamp_sessions(bootcamp_id) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_sessions_status ON bootcamp_sessions(status) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_sessions_dates ON bootcamp_sessions(start_date, end_date) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_sessions_country ON bootcamp_sessions(country) WHERE is_active = true;

-- Alumni indexes
CREATE INDEX IF NOT EXISTS idx_alumni_email ON alumnis(email) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_alumni_country ON alumnis(country) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_alumni_company ON alumnis(current_company) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_alumni_status ON alumnis(status) WHERE is_active = true;

-- Alumni Bootcamp Enrollments indexes
CREATE INDEX IF NOT EXISTS idx_enrollments_alumni_id ON alumni_bootcamp_enrollments(alumni_id) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_enrollments_session_id ON alumni_bootcamp_enrollments(bootcamp_session_id) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_enrollments_status ON alumni_bootcamp_enrollments(status) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_enrollments_certified ON alumni_bootcamp_enrollments(certificate_issued) WHERE is_active = true;

-- Project Gallery indexes
CREATE INDEX IF NOT EXISTS idx_projects_alumni_id ON project_galleries(alumni_id) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_projects_session_id ON project_galleries(bootcamp_session_id) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_projects_status ON project_galleries(status) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_projects_company ON project_galleries(company_partner) WHERE is_active = true;

-- Create update timestamp function
CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create triggers for updated_at
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