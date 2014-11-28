CREATE TABLE IF NOT EXISTS access_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  scope VARCHAR(255) NOT NULL,
  token VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS auth_code
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
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
  user_id INT NOT NULL
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

INSERT INTO user ( id, name ) VALUES
  ( 1, 'ADMIN1' ),
  ( 2, 'ADMIN2' );

INSERT INTO client ( id, name, secret, url, callback, description, user_id ) VALUES
  ( 1, 'APP1', 'SECRET1', 'http://example.com', 'http://example.com', '1111', 1 ),
  ( 2, 'APP2', 'SECRET2', 'http://example.com', 'http://example.com', '2222', 2 );

INSERT INTO access_token ( id, token, scope, client_id, user_id ) VALUES
  ( 1, 'TOKEN1', '11', 1,  1 ),
  ( 2, 'TOKEN2', '00', 2,  2 );

INSERT INTO auth_code ( id, code, scope, client_id, user_id ) VALUES
  ( 1, 'CODE1', '11', 1,  1 ),
  ( 2, 'CODE2', '00', 2,  2 );

INSERT INTO permission ( id, name ) VALUES
  ( 1, 'READ' ),
  ( 2, 'WRITE' );