ALTER TABLE mng.user ADD COLUMN provider VARCHAR(64) NULL;
ALTER TABLE mng.user ADD COLUMN provider_id VARCHAR(255) NULL;
ALTER TABLE mng.user ADD COLUMN email VARCHAR(320) NULL;

ALTER TABLE mng.user ADD CONSTRAINT uq_user_username UNIQUE (username);
ALTER TABLE mng.user ADD CONSTRAINT uq_user_provider_provider_id UNIQUE (provider, provider_id);
