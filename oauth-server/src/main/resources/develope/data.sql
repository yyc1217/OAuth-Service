
CREATE TABLE IF NOT EXISTS club (
  clubID      VARCHAR(4)   NOT NULL,
  club_name   VARCHAR(50)  NOT NULL DEFAULT '',
  address     VARCHAR(50)  NOT NULL DEFAULT '',
  web_site    VARCHAR(100) NOT NULL DEFAULT '',
  description VARCHAR(500) NOT NULL DEFAULT '',
  PRIMARY KEY (clubID)
);

INSERT INTO club (clubID, club_name, address, web_site, description) VALUES
  ('A001', 'CLUB1', 'place1', 'site1', '1111'),
  ('A002', 'CLUB2', 'place2', 'site2', '2222'),
  ('A003', 'CLUB3', 'place3', 'site3', '3333'),
  ('A004', 'CLUB4', 'place4', 'site4', '4444'),
  ('A005', 'CLUB5', 'place5', 'site5', '5555'),
  ('A006', 'CLUB6', 'place6', 'site6', '6666');