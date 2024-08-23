CREATE TABLE IF NOT EXISTS "forum_questions"(
    "id" SERIAL PRIMARY KEY,
    "vote_count" INT NOT NULL,
    "user_id" SERIAL NOT NULL,
    "question" VARCHAR(1024) NOT NULL,
    "answer" VARCHAR(255) NOT NULL,
    "class_id" SERIAL NOT NULL,
    FOREIGN KEY (class_id) REFERENCES institute_classes(id)

);

CREATE TABLE IF NOT EXISTS "question_votes"(
    "id" SERIAL PRIMARY KEY,
    "question_id" SERIAL NOT NULL,
    "user_id" SERIAL NOT NULL,
    "vote_type" BOOLEAN NOT NULL,
    FOREIGN KEY (question_id) REFERENCES forum_questions(id)

);