INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('demo', 'default', 'master', 'app.greet.name', 'Demo');

-- gateway
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('gateway', 'default', 'master', 'server.port', '${SERVER_PORT:8000}');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('gateway', 'default', 'master', 'eureka.instance.preferIpAddress', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('gateway', 'default', 'master', 'eureka.instance.lease-renewal-interval-in-seconds', '10');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('gateway', 'default', 'master', 'eureka.instance.lease-expiration-duration-in-seconds', '30');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('gateway', 'default', 'master', 'eureka.client.registerWithEureka', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('gateway', 'default', 'master', 'eureka.client.fetchRegistry', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('gateway', 'default', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://${EUREKA_SERVER_1_SERVICE_HOST}:${EUREKA_SERVER_1_SERVICE_PORT}/eureka/,http://${EUREKA_SERVER_2_SERVICE_HOST}:${EUREKA_SERVER_2_SERVICE_PORT}/eureka/');

-- turbine
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'turbine.appConfig', 'gateway,biz-service');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'server.port', '${SERVER_PORT:8001}');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'eureka.instance.preferIpAddress', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'eureka.instance.lease-renewal-interval-in-seconds', '10');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'eureka.instance.lease-expiration-duration-in-seconds', '30');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'eureka.client.registerWithEureka', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'eureka.client.fetchRegistry', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('turbine', 'default', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://${EUREKA_SERVER_1_SERVICE_HOST}:${EUREKA_SERVER_1_SERVICE_PORT}/eureka/,http://${EUREKA_SERVER_2_SERVICE_HOST}:${EUREKA_SERVER_2_SERVICE_PORT}/eureka/');

-- spring-admin
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('spring-admin', 'default', 'master', 'server.port', '${SERVER_PORT:8002}');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('spring-admin', 'default', 'master', 'eureka.instance.preferIpAddress', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('spring-admin', 'default', 'master', 'eureka.instance.lease-renewal-interval-in-seconds', '10');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('spring-admin', 'default', 'master', 'eureka.instance.lease-expiration-duration-in-seconds', '30');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('spring-admin', 'default', 'master', 'eureka.client.registerWithEureka', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('spring-admin', 'default', 'master', 'eureka.client.fetchRegistry', 'true');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, KEY, VALUE)
VALUES ('spring-admin', 'default', 'master', 'eureka.client.serviceUrl.defaultZone', 'http://eureka-server:${EUREKA_SERVER_PORT:8080}/eureka/');
