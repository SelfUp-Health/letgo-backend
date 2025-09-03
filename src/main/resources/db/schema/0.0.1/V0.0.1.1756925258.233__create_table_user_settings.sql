-- Create the settings table to store user-specific preferences
CREATE TABLE mng.settings (
  id                    BIGSERIAL
    PRIMARY KEY,
  user_id               BIGINT NOT NULL,
  notifications_enabled BOOLEAN     DEFAULT FALSE,
  dark_mode_enabled     BOOLEAN     DEFAULT FALSE,
  language              VARCHAR(10) DEFAULT 'PL'
);

-- Add comments to the table and columns for better documentation
COMMENT ON TABLE mng.settings IS 'Stores user-specific application settings and preferences.';
COMMENT ON COLUMN mng.settings.id IS 'Unique identifier for the settings record.';
COMMENT ON COLUMN mng.settings.user_id IS 'Foreign key referencing the user this setting belongs to.';
COMMENT ON COLUMN mng.settings.notifications_enabled IS 'Flag to enable or disable push/email notifications for the user.';
COMMENT ON COLUMN mng.settings.dark_mode_enabled IS 'Flag to enable or disable dark mode in the UI.';
COMMENT ON COLUMN mng.settings.language IS 'The preferred language for the user interface (e.g., EN, PL).';

-- Add a unique constraint to ensure one-to-one relationship with the user table
ALTER TABLE mng.settings
  ADD CONSTRAINT uq_settings_user_id
    UNIQUE (user_id);

-- Add a foreign key constraint to link to the user table
ALTER TABLE mng.settings
  ADD CONSTRAINT fk_settings_user_id
    FOREIGN KEY (user_id)
      REFERENCES mng.user(id)
      ON DELETE CASCADE;

-- Create an index on the user_id for fast lookups
CREATE INDEX idx_settings_user_id ON mng.settings(user_id);
