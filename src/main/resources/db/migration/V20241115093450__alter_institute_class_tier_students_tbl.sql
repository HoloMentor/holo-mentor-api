ALTER TABLE institute_class_tier_students DROP CONSTRAINT IF EXISTS fk_institute_tier_id;
ALTER TABLE institute_class_tier_students DROP COLUMN IF EXISTS tier_id;

ALTER TABLE institute_class_tier_students ADD COLUMN IF NOT EXISTS marks_out_of FLOAT DEFAULT 0 NOT NULL;
ALTER TABLE institute_class_tier_students ADD COLUMN IF NOT EXISTS upload_date DATE NOT NULL;

DROP TABLE IF EXISTS institute_class_tiers;