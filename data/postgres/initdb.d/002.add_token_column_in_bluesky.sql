-- Add token-related columns to bluesky_service table
ALTER TABLE bluesky_service 
ADD COLUMN IF NOT EXISTS access_token text,
ADD COLUMN IF NOT EXISTS refresh_token text,
ADD COLUMN IF NOT EXISTS token_expires_at timestamp;