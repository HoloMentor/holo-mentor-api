CREATE TYPE days_of_week AS ENUM('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN');

CREATE TABLE IF NOT EXISTS "institute_classes" (
    "id" SERIAL PRIMARY KEY,
    "teacher_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "institute_teacher_id" SERIAL NOT NULL,
    "subject_id" SERIAL NOT NULL,
    "class_name" VARCHAR(255) NOT NULL,
    "start_time" VARCHAR(255) NOT NULL,
    "end_time" VARCHAR(255) NOT NULL,
    "day_of_week" days_of_week NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (teacher_id) REFERENCES users(id),
    CONSTRAINT fk_institute_user FOREIGN KEY (institute_teacher_id) REFERENCES user_institutes(id),
    CONSTRAINT fk_institute_subject FOREIGN KEY (subject_id) REFERENCES institute_subjects(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);