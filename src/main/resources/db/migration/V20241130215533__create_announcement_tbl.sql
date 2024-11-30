CREATE TABLE IF NOT EXISTS "institute_announcements" (
    "id" SERIAL PRIMARY KEY,
    "institute_id" SERIAL NOT NULL,
    "title" TEXT NOT NULL,
    "announcement" TEXT NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);