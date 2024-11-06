CREATE TABLE IF NOT EXISTS "institute_class_tier_students" (
    "id" SERIAL PRIMARY KEY,
    "class_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "tier_id" SERIAL NOT NULL,
    "student_class_id" SERIAL NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_institute_class FOREIGN KEY (class_id) REFERENCES institute_classes(id),
    CONSTRAINT fk_institute_tier_id FOREIGN KEY (class_id) REFERENCES institute_class_tiers(id),
    CONSTRAINT fk_institute_student_class FOREIGN KEY (student_class_id) REFERENCES institute_class_students(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);