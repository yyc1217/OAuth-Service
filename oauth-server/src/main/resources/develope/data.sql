
CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256)
);

INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri) VALUES
  ('I001', 'P001', 'http://example.com'),
  ('I002', 'P002', 'http://example.com'),
  ('I003', 'P003', 'http://example.com'),
  ('I004', 'P004', 'http://example.com'),
  ('I005', 'P005', 'http://example.com'),
  ('I006', 'P006', 'http://example.com');
