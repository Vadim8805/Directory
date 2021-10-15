CREATE table IF NOT EXISTS Directory (
               id INT PRIMARY KEY,
               name VARCHAR(255),
               region VARCHAR(255),
               district VARCHAR(255),
               population INT,
               foundation INT
                       );

SELECT * FROM Directory;