CREATE SCHEMA IF NOT EXISTS mng;

--
-- Management / User
--

CREATE TABLE IF NOT EXISTS mng.user (

  id         BIGSERIAL    NOT NULL,
  username   VARCHAR(64)  NOT NULL,
  full_name  VARCHAR(128) NOT NULL,
  password   VARCHAR(128),

  PRIMARY KEY (id)
);

COMMENT ON TABLE mng.user IS 'Stores users';
COMMENT ON COLUMN mng.user.username IS 'Unique username/code of the user (e.g. joe_doe)';
COMMENT ON COLUMN mng.user.full_name IS 'Full name of the user (e.g. Joe Henry Doe)';
COMMENT ON COLUMN mng.user.password IS 'Encoded user password';
