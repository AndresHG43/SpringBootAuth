-- Trigger for complete date_created or date_updated
CREATE OR REPLACE FUNCTION trigger_save()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' AND (NEW.date_created IS NULL OR NEW.date_updated IS NULL) THEN
        NEW.date_created = CURRENT_TIMESTAMP AT TIME ZONE 'America/El_Salvador';
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        NEW.date_updated = CURRENT_TIMESTAMP AT TIME ZONE 'America/El_Salvador';
        RETURN NEW;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trigger_save BEFORE INSERT OR UPDATE ON users FOR EACH ROW EXECUTE PROCEDURE trigger_save();
CREATE OR REPLACE TRIGGER trigger_save BEFORE INSERT OR UPDATE ON roles FOR EACH ROW EXECUTE PROCEDURE trigger_save();
CREATE OR REPLACE TRIGGER trigger_save BEFORE INSERT OR UPDATE ON users_roles FOR EACH ROW EXECUTE PROCEDURE trigger_save();
