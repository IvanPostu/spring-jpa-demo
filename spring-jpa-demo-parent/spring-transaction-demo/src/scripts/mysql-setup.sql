-- transaction_demo database
DROP DATABASE IF EXISTS transaction_demo;
DROP USER IF EXISTS `transaction_demoadmin`@`%`;
DROP USER IF EXISTS `transaction_demouser`@`%`;
CREATE DATABASE IF NOT EXISTS transaction_demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `transaction_demoadmin`@`%` IDENTIFIED WITH caching_sha2_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `transaction_demo`.* TO `transaction_demoadmin`@`%`;
CREATE USER IF NOT EXISTS `transaction_demouser`@`%` IDENTIFIED WITH caching_sha2_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `transaction_demo`.* TO `transaction_demouser`@`%`;
FLUSH PRIVILEGES;
