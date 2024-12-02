
CREATE TYPE vote_types_enum AS ENUM ('NONE', 'VOTE_UP', 'VOTE_DOWN');


ALTER TABLE question_votes ALTER COLUMN vote_type TYPE vote_types_enum USING 'NONE';


