ALTER TABLE institute_class_tiers ADD COLUMN min_mark FLOAT;
ALTER TABLE institute_class_tiers ADD COLUMN max_mark FLOAT;
ALTER TABLE institute_class_tiers ADD COLUMN week VARCHAR(50);
ALTER TABLE institute_class_tiers ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE institute_class_tiers DROP COLUMN name;
ALTER TABLE institute_class_tiers DROP COLUMN description;
