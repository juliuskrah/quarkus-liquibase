-- Insert clients
INSERT INTO client(id, name, code, contact_person) VALUES ('ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', 'acme corporation', 'ACME', 'Wile E. Coyote');
-- Insert services
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('1ef53d19-5518-43f6-81fb-bcc152a88c4f', 'acme bomb', 'ACME_BOMB', 'ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', 'GHS', 'queue_acme_bomb');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('3f03b288-f3f3-4641-bc1a-650670dbd8a7', 'acme catapult', 'ACME_CATAPULT', 'ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', 'GHS', 'queue_acme_catapult');