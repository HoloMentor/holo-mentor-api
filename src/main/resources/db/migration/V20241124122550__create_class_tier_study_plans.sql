CREATE TABLE IF NOT EXISTS "class_tier_study_plans" (
    "id" SERIAL PRIMARY KEY,
    "class_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "tier" INTEGER NOT NULL,
    "name" VARCHAR(100) NOT NULL,
    "description" TEXT NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_institute_class FOREIGN KEY (class_id) REFERENCES institute_classes(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);