CREATE TABLE IF NOT EXISTS "forum_comments"(
    "id" SERIAL PRIMARY KEY,
    "question_id" BIGINT NOT NULL REFERENCES forum_questions(id) ON DELETE CASCADE,
    "user_id" BIGINT NOT NULL REFERENCES users(id),
    "comment_text" JSON NOT NULL,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);