CREATE TABLE IF NOT EXISTS "institutes" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    "is_blacklisted" BOOLEAN DEFAULT false,
    "created_at" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);