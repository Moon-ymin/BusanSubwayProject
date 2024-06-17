USE pus_sub_proj_db;

CREATE TABLE line (
    line_cd INT PRIMARY KEY,
    line_name VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE station (
    scode INT PRIMARY KEY,
    sname VARCHAR(45) NOT NULL UNIQUE,
    line_cd INT,
    exchange INT NOT NULL,
    FOREIGN KEY (line_cd) REFERENCES line(line_cd)
);

CREATE TABLE exchange (
    exchange_id INT PRIMARY KEY,
    scode INT,
    line_cd INT NOT NULL,
    ex_scode INT NOT NULL,
    ex_line_cd INT,
    walking_time TIME,
    FOREIGN KEY (scode) REFERENCES station(scode)
);

CREATE TABLE station_location (
    scode INT PRIMARY KEY,
    x1 INT NOT NULL,
    y1 INT NOT NULL,
    x2 INT NOT NULL,
    y2 INT NOT NULL,
    FOREIGN KEY (scode) REFERENCES station(scode)
);

CREATE TABLE schedule (
    schedule_id INT PRIMARY KEY,
    continuity INT NOT NULL,
    scode INT,
    arrival_time TIME NOT NULL,
    direction INT,
    day INT,
    FOREIGN KEY (scode) REFERENCES station(scode)
);