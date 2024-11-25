CREATE TABLE IF NOT EXISTS "class_tier_study_plan_tasks" (
    "id" SERIAL PRIMARY KEY,
    "class_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "study_plan_id" SERIAL NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "description" JSON NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id),
    CONSTRAINT fk_institute_class FOREIGN KEY (class_id) REFERENCES institute_classes(id),
    CONSTRAINT fk_class_tier_study_plan FOREIGN KEY (study_plan_id) REFERENCES class_tier_study_plans(id)
);