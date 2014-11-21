CREATE TABLE IF NOT EXISTS access_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  permission VARCHAR(255) NOT NULL,
  token VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS auth_code
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  date_created DATETIME,
  date_updated DATETIME,
  permission VARCHAR(255) NOT NULL,
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

INSERT INTO user ( name ) VALUES
  ( 'admin1' ),
  ( 'admin2' );

INSERT INTO client ( name, secret, url, callback, description, user_id ) VALUES
  ( 'APP1', 'SECRET1', 'http://example.com', 'http://example.com', '1111', 1 ),
  ( 'APP2', 'SECRET2', 'http://example.com', 'http://example.com', '2222', 2 );

INSERT INTO access_token ( token, permission, client_id, user_id ) VALUES
  ( 'TOKEN1', '110', 1,  1 ),
  ( 'TOKEN2', '000', 2,  2 );

INSERT INTO auth_code ( code, permission, client_id, user_id ) VALUES
  ( 'CODE1', '110', 1,  1 ),
  ( 'CODE2', '000', 2,  2 );