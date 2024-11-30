CREATE TABLE IF NOT EXISTS "institute_class_notifications" (
    "id" SERIAL PRIMARY KEY,
    "institute_id" SERIAL NOT NULL,
    "institute_class_id" SERIAL NOT NULL,
    "title" TEXT NOT NULL,
    "message" TEXT NOT NULL,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id),
    CONSTRAINT fk_institute_classg FOREIGN KEY (institute_class_id) REFERENCES institute_classes(id)
);