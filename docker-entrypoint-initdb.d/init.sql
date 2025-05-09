CREATE DATABASE IF NOT EXISTS task CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

-- Set default character set and collation for the database
USE task;
ALTER DATABASE task CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Set SQL mode
SET GLOBAL sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
