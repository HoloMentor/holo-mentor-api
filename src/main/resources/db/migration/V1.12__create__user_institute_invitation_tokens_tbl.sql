CREATE TABLE IF NOT EXISTS "user_invitation_tokens" (
    "id" SERIAL PRIMARY KEY,
    "user_id" SERIAL NOT NULL,
    "institute_id" SERIAL NOT NULL,
    "user_institute_id" SERIAL NOT NULL,
    "is_valid" BOOLEAN NOT NULL DEFAULT FALSE,
    "token" TEXT,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_institute FOREIGN KEY (user_institute_id) REFERENCES user_institutes(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);