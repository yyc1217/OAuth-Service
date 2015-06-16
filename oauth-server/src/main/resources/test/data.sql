INSERT INTO user ( id, name ) VALUES
  ( 1, 'ADMIN1' ),
  ( 2, 'ADMIN2' ),
  ( 3, 'ADMIN3' );

INSERT INTO role ( id, name ) VALUES
  ( 1, 'admin' );

INSERT INTO user_roles ( user_id, role_id ) VALUES
  ( 1, 1 );

INSERT INTO client ( id, name, encrypted_secret, url, callback, description, owner_id ) VALUES
  ( 1, 'APP1', 'SECRET1', 'http://example.com', 'http://example.com', '1111', 1 ),
  ( 2, 'APP2', 'SECRET2', 'http://example.com', 'http://example.com', '2222', 2 ),
  ( 3, 'APP3', '0bd2173ec356a389a96fe1527af2ca0441c4f22ebea08bb545cda0abbf1e8a58', 'http://example.com', 'http://example.com', '3333', 2 );
-- SECRET

INSERT INTO api_token ( id, encrypted_token, date_created, last_updated, date_expired,  client_id ) VALUES
  ( 1, 'TOKEN1', '2050-12-25', '2050-12-25', '2100-12-25', 1 ),
  ( 2, 'TOKEN2', '2050-12-25', '2050-12-25', '2000-12-25', 2 ),
  ( 3, '1c202d72a9202db6d14be298abb6e317770c3484fa1e5a176e2da4d7e4ea94c1', '2050-12-25', '2050-12-25', '2100-12-25', 3 );
-- Mzo6OlRPS0VO

INSERT INTO refresh_token ( id, encrypted_token, access_token_id, client_id, user_id, date_created, last_updated, date_expired ) VALUES
  ( 1, 'TOKEN1', 1, 1, 1, '2050-12-25', '2050-12-25', '2100-12-25' ),
  ( 2, 'TOKEN2', 2, 2, 2, '2050-12-25', '2050-12-25', '2000-12-25' ),
  ( 3, '1c202d72a9202db6d14be298abb6e317770c3484fa1e5a176e2da4d7e4ea94c1', 3, 3, 3, '2050-12-25', '2050-12-25', '2100-12-25' );
-- Mzo6OlRPS0VO

INSERT INTO refresh_token_scope ( permission_id, refresh_token_id ) VALUES
  ( 1, 1 ),
  ( 2, 1 ),
  ( 1, 3 ),
  ( 2, 3 );

INSERT INTO access_token ( id, encrypted_token, client_id, user_id, date_created, last_updated, date_expired ) VALUES
  ( 1, 'TOKEN1', 1,  1, '2050-12-25', '2050-12-25', '2100-12-25' ),
  ( 2, 'TOKEN2', 2,  2, '2050-12-25', '2050-12-25', '2000-12-25' ),
  ( 3, '1c202d72a9202db6d14be298abb6e317770c3484fa1e5a176e2da4d7e4ea94c1', 3, 3, '2050-12-25', '2050-12-25', '2100-12-25' );
-- Mzo6OlRPS0VO

INSERT INTO access_token_scope ( permission_id, access_token_id ) VALUES
  ( 1, 1 ),
  ( 2, 1 ),
  ( 1, 3 ),
  ( 2, 3 );

INSERT INTO authorization_code ( id, encrypted_code, client_id, user_id, date_created, last_updated, date_expired ) VALUES
  ( 1, 'CODE1', 1,  1, '2050-12-25', '2050-12-25', '2100-12-25' ),
  ( 2, 'CODE2', 2,  2, '2050-12-25', '2050-12-25', '2000-12-25' ),
  ( 3, '0a8e074040d26600eb09531fcc90de0e619243f24966dd91a8b3791cf4b3e83d', 3, 3, '2050-12-25', '2050-12-25', '2100-12-25' );
-- Mzo6OkNPREU=

INSERT INTO authorization_code_scope ( permission_id, authorization_code_id ) VALUES
  ( 1, 1 ),
  ( 2, 1 ),
  ( 1, 3 ),
  ( 2, 3 );

INSERT INTO permission ( id, name ) VALUES
  ( 1, 'ADMIN' ),
  ( 2, 'READ' ),
  ( 3, 'WRITE' );