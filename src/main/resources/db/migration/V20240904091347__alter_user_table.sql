DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='users' AND column_name='image') THEN
ALTER TABLE users ADD COLUMN image TEXT;
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='users' AND column_name='country') THEN
ALTER TABLE users ADD COLUMN country VARCHAR(255);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='users' AND column_name='country_code') THEN
ALTER TABLE users ADD COLUMN country_code VARCHAR(255);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='users' AND column_name='contact_number') THEN
ALTER TABLE users ADD COLUMN contact_number VARCHAR(255);
END IF;
END $$;
