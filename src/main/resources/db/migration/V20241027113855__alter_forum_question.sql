ALTER TABLE forum_questions
ALTER COLUMN question TYPE JSON
USING question::JSON;

