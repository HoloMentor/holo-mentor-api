CREATE TABLE IF NOT EXISTS "institute_subjects" (
    "id" SERIAL PRIMARY KEY,
    "institute_id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);