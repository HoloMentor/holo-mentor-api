CREATE TYPE material_type AS ENUM('PDF', 'VIDEO', 'URL', 'FILE', 'IMAGE', 'OTHER');

CREATE TABLE IF NOT EXISTS "institute_class_materials" (
    "id" SERIAL PRIMARY KEY,
    "institute_id" SERIAL NOT NULL,
    "class_id" SERIAL NOT NULL,
    "topic_id" SERIAL NOT NULL,
    "sub_topic_id" SERIAL NOT NULL,
    "description" TEXT,
    "type" material_type NOT NULL DEFAULT 'OTHER',
    "url" TEXT,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_institute_class_topic FOREIGN KEY (topic_id) REFERENCES institute_class_topics(id),
    CONSTRAINT fk_institute_class_sub_topic FOREIGN KEY (sub_topic_id) REFERENCES institute_class_sub_topics(id),
    CONSTRAINT fk_institute_class FOREIGN KEY (class_id) REFERENCES institute_classes(id),
    CONSTRAINT fk_institute FOREIGN KEY (institute_id) REFERENCES institutes(id)
);