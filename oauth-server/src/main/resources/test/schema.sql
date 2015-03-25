CREATE TABLE IF NOT EXISTS access_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  date_expired DATETIME,
  token VARCHAR(255),
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
  token VARCHAR(255),
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
  code VARCHAR(255) ,
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
  api_token VARCHAR(255),
  api_use_times INT DEFAULT 0,
  name VARCHAR(255),
  secret VARCHAR(255),
  url VARCHAR(255),
  owner_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS user
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  date_created DATETIME,
  last_updated DATETIME,
  name VARCHAR(255)
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
