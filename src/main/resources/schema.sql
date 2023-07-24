CREATE TABLE matches (
    match_id INT PRIMARY KEY AUTO_INCREMENT,
    home_team VARCHAR(100) NOT NULL,
    away_team VARCHAR(100) NOT NULL,
    home_score INT NOT NULL,
    away_score INT NOT NULL
);