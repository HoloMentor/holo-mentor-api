CREATE TYPE institute_roles AS ENUM('STUDENT', 'TEACHER', 'STAFF', 'INSTITUTE');

CREATE TABLE IF NOT EXISTS "user_institutes" (
    "id" SERIAL PRIMARY KEY,
    "user_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "role" institute_roles NOT NULL DEFAULT 'STUDENT',
    "is_blacklisted" BOOLEAN DEFAULT false,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);