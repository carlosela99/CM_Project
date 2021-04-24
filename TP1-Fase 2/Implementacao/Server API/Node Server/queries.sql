
CREATE DATABASE [IF NOT EXISTS] reclaimportugal_db;

use reclaimportugal_db;

CREATE TABLE IF NOT EXISTS players (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    confirmation_code INT NOT NULL,
	confirmed TINYINT DEFAULT 0 NOT NULL
);

CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creator_id INT NOT NULL,
    question VARCHAR(255) NOT NULL,
    right_answer VARCHAR(255) NOT NULL,
    wrong_answer VARCHAR(255) NOT NULL,
    region_a VARCHAR(255) NOT NULL,
    region_b VARCHAR(255),
    approved TINYINT DEFAULT 0 NOT NULL
);
