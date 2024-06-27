USE pus_sub_proj_db;

CREATE TABLE IF NOT EXISTS line (
    line_cd INT PRIMARY KEY,
    line_name VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS station (
    scode INT PRIMARY KEY,
    sname VARCHAR(45) NOT NULL,
    line_cd INT,
    exchange INT NOT NULL,
    FOREIGN KEY (line_cd) REFERENCES line(line_cd) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exchange (
    exchange_id INT PRIMARY KEY,
    scode INT,
    line_cd INT NOT NULL,
    ex_scode INT NOT NULL,
    ex_line_cd INT,
    walking_time TIME,
    FOREIGN KEY (scode) REFERENCES station(scode) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS station_location (
    scode INT PRIMARY KEY,
    x1 INT NOT NULL,
    y1 INT NOT NULL,
    x2 INT NOT NULL,
    y2 INT NOT NULL,
    FOREIGN KEY (scode) REFERENCES station(scode) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS schedule (
    schedule_id INT PRIMARY KEY,
    continuity INT NOT NULL,
    scode INT,
    arrival_time TIME NOT NULL,
    direction INT,
    day INT,
    FOREIGN KEY (scode) REFERENCES station(scode) ON DELETE CASCADE
);