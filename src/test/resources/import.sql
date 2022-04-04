-- Insert clients
INSERT INTO client(id, name, code, contact_person) VALUES ('ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', 'acme corporation', 'ACME', 'Wile E. Coyote');
INSERT INTO client(id, name, code, contact_person) VALUES ('9f71d01e-5695-4379-a924-ca6dc316175b', 'evil corp', 'EVIL', 'Tyrell Wellick');
INSERT INTO client(id, name, code, contact_person) VALUES ('15759e2d-21ab-4ec2-bb9a-2aee55c409fe', 'freedom limited', 'FREEDOM', 'Austin Powers');
INSERT INTO client(id, name, code, contact_person) VALUES ('b0c02363-5031-4c0c-9a83-f242df4039b1', 'hey foods', 'HEY', null);
-- Insert services
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('7374fd6d-03e3-498f-869f-b0144445d096', 'kidnapping', 'KIDNAPPING', '9f71d01e-5695-4379-a924-ca6dc316175b', 'USD', 'queue_kidnapping');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('5e4be620-6be0-4526-8369-6d9ef4752580', 'blackmailing', 'BLACKMAILING', '9f71d01e-5695-4379-a924-ca6dc316175b', 'USD', 'queue_blackmailing');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('69fe1c30-57c0-44b4-a1cb-b398890174f3', 'pizza', 'PIZZA', 'b0c02363-5031-4c0c-9a83-f242df4039b1', 'BWP', 'queue_pizza');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('75a95db1-b439-4231-be7f-a112b4a943d5', 'coffee', 'COFFEE', 'b0c02363-5031-4c0c-9a83-f242df4039b1', 'BWP', 'queue_coffee');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('1ef53d19-5518-43f6-81fb-bcc152a88c4f', 'acme bomb', 'ACME_BOMB', 'ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', 'GHS', 'queue_acme_bomb');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('3f03b288-f3f3-4641-bc1a-650670dbd8a7', 'acme catapult', 'ACME_CATAPULT', 'ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', 'GHS', 'queue_acme_catapult');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('28e37fc7-eb20-42a3-a4f4-ec0da0e91d6e', 'free speech', 'FREE_SPEECH', '15759e2d-21ab-4ec2-bb9a-2aee55c409fe', 'KSH', 'queue_free_speech');
INSERT INTO service(id, name, code, client_id, currency, queue_name) VALUES ('4ef76836-0775-4eb0-83b0-2f7438c5ced8', 'free health', 'FREE_HEALTH', '15759e2d-21ab-4ec2-bb9a-2aee55c409fe', 'KSH', 'queue_free_health');

-- Insert service_setting
INSERT INTO service_setting(service_id, payer_client_id, receiver_client_id, collection_account, notify_customer, acknowledge_payer, sms_source_address, float_amount) VALUES('7374fd6d-03e3-498f-869f-b0144445d096', 'ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', '9f71d01e-5695-4379-a924-ca6dc316175b', 'A secret offshore account', true, true, 'EVIL', 0.00);
INSERT INTO service_setting(service_id, payer_client_id, receiver_client_id, collection_account, notify_customer, acknowledge_payer, sms_source_address, float_amount) VALUES('5e4be620-6be0-4526-8369-6d9ef4752580', 'ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', '9f71d01e-5695-4379-a924-ca6dc316175b', 'A secret offshore account', false, true, 'EVIL', 0.00);
INSERT INTO service_setting(service_id, payer_client_id, receiver_client_id, collection_account, notify_customer, acknowledge_payer, sms_source_address, float_amount) VALUES('69fe1c30-57c0-44b4-a1cb-b398890174f3', 'ce74d8f2-ef49-4f2a-b5cc-52ef30046d40', 'b0c02363-5031-4c0c-9a83-f242df4039b1', '', false, true, '', 1000.00);
INSERT INTO service_setting(service_id, payer_client_id, receiver_client_id, collection_account, notify_customer, acknowledge_payer, sms_source_address, float_amount) VALUES('69fe1c30-57c0-44b4-a1cb-b398890174f3', '9f71d01e-5695-4379-a924-ca6dc316175b', 'b0c02363-5031-4c0c-9a83-f242df4039b1', '', false, false, '', 9009.99);
INSERT INTO service_setting(service_id, payer_client_id, receiver_client_id, collection_account, notify_customer, acknowledge_payer, sms_source_address, float_amount) VALUES('69fe1c30-57c0-44b4-a1cb-b398890174f3', '15759e2d-21ab-4ec2-bb9a-2aee55c409fe', 'b0c02363-5031-4c0c-9a83-f242df4039b1', '', true, true, '', 1999.00);
