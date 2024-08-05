CREATE TABLE IF NOT EXISTS "institute_class_students" (
    "id" SERIAL PRIMARY KEY,
    "student_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "institute_student_id" SERIAL NOT NULL,
    "class_id" SERIAL NOT NULL,
    "registration_number" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (student_id) REFERENCES users(id),
    CONSTRAINT fk_institute_user FOREIGN KEY (institute_student_id) REFERENCES user_institutes(id),
    CONSTRAINT fk_institute_class FOREIGN KEY (class_id) REFERENCES institute_classes(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);