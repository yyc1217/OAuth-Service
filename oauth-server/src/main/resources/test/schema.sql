CREATE TABLE IF NOT EXISTS access_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  date_expired DATETIME,
  scope VARCHAR(255) NOT NULL,
  token VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS refresh_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  date_expired DATETIME,
  scope VARCHAR(255) NOT NULL,
  token VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL,
  access_token_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS auth_code
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  date_expired DATETIME,
  scope VARCHAR(255) NOT NULL,
  code VARCHAR(255) ,
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS client
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  callback VARCHAR(255),
  description VARCHAR(255),
  name VARCHAR(255),
  secret VARCHAR(255),
  url VARCHAR(255),
  owner_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS user
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS permission
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  name VARCHAR(255)
);
