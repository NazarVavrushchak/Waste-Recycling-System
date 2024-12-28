-- Add the column
ALTER TABLE habits ADD COLUMN duration_in_days INTEGER;

-- Update existing records with calculated duration
UPDATE habits SET duration_in_days = EXTRACT(DAY FROM (end_date - start_date));

-- Enforce NOT NULL constraint
ALTER TABLE habits ALTER COLUMN duration_in_days SET NOT NULL;