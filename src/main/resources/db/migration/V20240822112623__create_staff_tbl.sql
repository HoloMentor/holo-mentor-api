CREATE TABLE IF NOT EXISTS "teacher_staff" (
   "id" SERIAL PRIMARY KEY,
    "user_staff_id" SERIAL NOT NULL,
    "user_teacher_id" SERIAL NOT NULL,
    "user_institute_staff_id" SERIAL NOT NULL,
    "user_institute_teacher_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "is_delete" BOOLEAN NOT NULL DEFAULT FALSE,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_staff FOREIGN KEY (user_staff_id) REFERENCES users(id),
    CONSTRAINT fk_user_teacher FOREIGN KEY (user_teacher_id) REFERENCES users(id),
    CONSTRAINT fk_user_institute_staff FOREIGN KEY (user_institute_staff_id) REFERENCES user_institutes(id),
    CONSTRAINT fk_user_institute_teacher FOREIGN KEY (user_institute_teacher_id) REFERENCES user_institutes(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
    );
