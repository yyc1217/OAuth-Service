INSERT INTO user ( id, name ) VALUES
  ( 1, 'ADMIN1' ),
  ( 2, 'ADMIN2' ),
  ( 3, 'ADMIN3' );

INSERT INTO client ( id, name, encrypted_secret, url, callback, description, owner_id ) VALUES
  ( 1, 'APP1', 'SECRET1', 'http://example.com', 'http://example.com', '1111', 1 ),
  ( 2, 'APP2', 'SECRET2', 'http://example.com', 'http://example.com', '2222', 2 ),
  ( 3, 'APP3', '$2a$10$Sm07H4pIys2Ae45rdO5rJuiEe/swFKoJneZLg.bU9HjFsBBZktwua', 'http://example.com', 'http://example.com', '3333', 2 );
-- SECRET

INSERT INTO api_token ( id, encrypted_token, date_created, last_updated, date_expired,  client_id ) VALUES
  ( 1, 'TOKEN1', '2050-12-25', '2050-12-25', '2100-12-25', 1 ),
  ( 2, 'TOKEN2', '2050-12-25', '2050-12-25', '2000-12-25', 2 ),
  ( 3, '$2a$10$QIWTQ5g9A5EiQOOGHlRDm.C4GBjlStg/Fzgj9h3xfYBqtyxSxKWDq', '2050-12-25', '2050-12-25', '2100-12-25', 3 );
-- Mzo6OlRPS0VO

INSERT INTO refresh_token ( id, encrypted_token, access_token_id, client_id, user_id, date_created, last_updated, date_expired ) VALUES
  ( 1, 'TOKEN1', 1, 1, 1, '2050-12-25', '2050-12-25', '2100-12-25' ),
  ( 2, 'TOKEN2', 2, 2, 2, '2050-12-25', '2050-12-25', '2000-12-25' ),
  ( 3, '$2a$10$dEFh.Tw.s05K66oj0gOopObZePAyYRGuTCkBLN5dvrdfS4x2RxtHO', 3, 3, 3, '2050-12-25', '2050-12-25', '2100-12-25' );
-- Mzo6OlRPS0VO

INSERT INTO refresh_token_scope ( permission_id, refresh_token_id ) VALUES
  ( 1, 1 ),
  ( 2, 1 ),
  ( 1, 3 ),
  ( 2, 3 );

INSERT INTO access_token ( id, encrypted_token, client_id, user_id, date_created, last_updated, date_expired ) VALUES
  ( 1, 'TOKEN1', 1,  1, '2050-12-25', '2050-12-25', '2100-12-25' ),
  ( 2, 'TOKEN2', 2,  2, '2050-12-25', '2050-12-25', '2000-12-25' ),
  ( 3, '$2a$10$dEFh.Tw.s05K66oj0gOopObZePAyYRGuTCkBLN5dvrdfS4x2RxtHO', 3, 3, '2050-12-25', '2050-12-25', '2100-12-25' );
-- Mzo6OlRPS0VO

INSERT INTO access_token_scope ( permission_id, access_token_id ) VALUES
  ( 1, 1 ),
  ( 2, 1 ),
  ( 1, 3 ),
  ( 2, 3 );

INSERT INTO authorization_code ( id, encrypted_code, client_id, user_id, date_created, last_updated, date_expired ) VALUES
  ( 1, 'CODE1', 1,  1, '2050-12-25', '2050-12-25', '2100-12-25' ),
  ( 2, 'CODE2', 2,  2, '2050-12-25', '2050-12-25', '2000-12-25' ),
  ( 3, '$2a$10$BCU4w83RytKXMUswUcflw.nhZdAAq.lr4Mf873a0C6zXX/IclxTIC', 3, 3, '2050-12-25', '2050-12-25', '2100-12-25' );
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