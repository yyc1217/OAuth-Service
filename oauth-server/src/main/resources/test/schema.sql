CREATE TABLE IF NOT EXISTS access_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  date_expired DATETIME,
  encrypted_token VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS refresh_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  date_expired DATETIME,
  encrypted_token VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL,
  access_token_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS authorization_code
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  date_expired DATETIME,
  encrypted_code VARCHAR(255) ,
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS client
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  callback VARCHAR(255),
  description VARCHAR(255),
  name VARCHAR(255),
  encrypted_secret VARCHAR(255),
  url VARCHAR(255),
  deleted BOOLEAN DEFAULT FALSE ,
  owner_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS api_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  date_expired DATETIME,
  encrypted_token VARCHAR(255),
  client_id INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS user
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS role
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_roles
(
  user_id INT NOT NULL,
  role_id INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS permission
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS access_token_scope
(
  permission_id INT NOT NULL,
  access_token_id INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS authorization_code_scope
(
  permission_id INT NOT NULL,
  authorization_code_id INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS refresh_token_scope
(
  permission_id INT NOT NULL,
  refresh_token_id INT NOT NULL,
);

CREATE TABLE LOGS
(
  USER_ID VARCHAR(20) NOT NULL,
  DATED   DATETIME NOT NULL,
  LOGGER  VARCHAR(50) NOT NULL,
  LEVEL   VARCHAR(10) NOT NULL,
  MESSAGE VARCHAR(1000) NOT NULL
);