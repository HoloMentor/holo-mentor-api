ALTER TABLE question_votes
DROP CONSTRAINT question_votes_question_id_fkey;

ALTER TABLE question_votes
ADD CONSTRAINT question_votes_question_id_fkey
FOREIGN KEY (question_id)
REFERENCES forum_questions(id)
ON DELETE CASCADE;
