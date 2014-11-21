CREATE TABLE access_token
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  date_created DATETIME,
  date_updated DATETIME,
  permission VARCHAR(255),
  token VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE auth_code
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  date_created DATETIME,
  date_updated DATETIME,
  permission VARCHAR(255),
  code VARCHAR(255),
  client_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE client
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  date_created DATETIME,
  date_updated DATETIME,
  callback VARCHAR(255),
  description VARCHAR(255),
  name VARCHAR(255),
  secret VARCHAR(255),
  url VARCHAR(255),
  user_id INT NOT NULL
);

CREATE TABLE user
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  date_created DATETIME,
  date_updated DATETIME,
  name VARCHAR(255)
);

ALTER TABLE access_token ADD FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE access_token ADD FOREIGN KEY (client_id) REFERENCES client (id);
CREATE UNIQUE INDEX UK_1djybee0iap4odfl91gkxoxem ON access_token (token);
CREATE INDEX FK_kqodiiamededdp9947dtk9ua5 ON access_token (user_id);
CREATE INDEX FK_lrorbiqd6jsbl85pf1srlhtvr ON access_token (client_id);
ALTER TABLE auth_code ADD FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE auth_code ADD FOREIGN KEY (client_id) REFERENCES client (id);
CREATE INDEX FK_jxea67rp4k544rk7o0n86jqss ON auth_code (client_id);
CREATE INDEX FK_rhdpq7v84gecrpi0it14qa5qp ON auth_code (user_id);
ALTER TABLE client ADD FOREIGN KEY (user_id) REFERENCES user (id);
CREATE INDEX FK_1ixfyfepst9sjbo9op1v65fg0 ON client (user_id);

INSERT INTO user ( id, name ) VALUES
  ( 1, 'admin');

INSERT INTO client ( name, secret, url, callback, description, user_id ) VALUES
  ('A001', 'CLUB1', 'http://example.com', 'http://example.com', '1111', 1 );