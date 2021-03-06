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

INSERT INTO user ( id, name ) VALUES
  ( 1, 'ADMIN1' ),
  ( 2, 'ADMIN2' ),
  ( 3, 'ADMIN3' );

INSERT INTO client ( id, name, secret, url, callback, description, owner_id ) VALUES
  ( 1, 'APP1', 'SECRET1', 'http://example.com', 'http://example.com', '1111', 1 ),
  ( 2, 'APP2', 'SECRET2', 'http://example.com', 'http://example.com', '2222', 2 ),
  ( 3, 'APP3', '$2a$10$Sm07H4pIys2Ae45rdO5rJuiEe/swFKoJneZLg.bU9HjFsBBZktwua', 'http://example.com', 'http://example.com', '3333', 2 );

INSERT INTO access_token ( id, token, scope, client_id, user_id, date_created, date_updated ) VALUES
  ( 1, 'TOKEN1', '011', 1,  1, '2050-12-25', '2050-12-25' ),
  ( 2, 'TOKEN2', '00', 2,  2, '2050-12-25', '2050-12-25' ),
  ( 3, '$2a$10$dEFh.Tw.s05K66oj0gOopObZePAyYRGuTCkBLN5dvrdfS4x2RxtHO', '011', 2, 2, '2050-12-25', '2050-12-25' );
-- Mzo6OlRPS0VO

INSERT INTO auth_code ( id, code, scope, client_id, user_id, date_created, date_updated ) VALUES
  ( 1, 'CODE1', '011', 1,  1, '2050-12-25', '2050-12-25' ),
  ( 2, 'CODE2', '00', 2,  2, '2050-12-25', '2050-12-25' ),
  ( 3, '$2a$10$BCU4w83RytKXMUswUcflw.nhZdAAq.lr4Mf873a0C6zXX/IclxTIC', '011', 3, 3, '2050-12-25', '2050-12-25' );
-- Mzo6OkNPREU=

INSERT INTO permission ( id, name ) VALUES
  ( 1, 'ADMIN' ),
  ( 2, 'READ' ),
  ( 3, 'WRITE' );
