CREATE TABLE IF NOT EXISTS mcq_questions (
    id serial4 NOT NULL PRIMARY KEY,
    user_id serial4 NOT NULL,
    question jsonb NOT NULL,
    answer int4 NOT NULL,
    class_id serial4 NOT NULL,
    topic varchar NOT NULL,
    sub_topic varchar NOT NULL,
    mcq_answers json NULL,
    activation int4 DEFAULT 0 NOT NULL,
    CONSTRAINT fk_institute_class FOREIGN KEY (class_id) REFERENCES institute_classes(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);