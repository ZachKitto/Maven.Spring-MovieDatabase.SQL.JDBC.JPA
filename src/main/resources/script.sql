INSERT INTO movies (title, runtime, genre, imdb_score, rating)
VALUES ('Howard the Duck', 110, 'Sci-Fi', 4.6, 'PG'),
       ('Lavalantula', 83, 'Horror', 4.7, 'TV-14'),
       ('Starship Troopers', 129, 'Sci-Fi', 7.2, 'PG-13'),
       ('Waltz With Bashir', 90, 'Documentary', 8.0, 'R'),
       ('Spaceballs', 96, 'Comedy', 7.1, 'PG'),
       ('Monsters Inc.', 92, 'Animation', 8.1, 'G');


INSERT INTO movies (title, runtime, genre, imdb_score, rating)
VALUES ('Honey, I Shrunk the Kids', 93, 'Sci-Fi', 6.4, 'PG'),
       ('Casper', 100, 'Comedy', 6.1, 'PG'),
       ('Night at the Museum', 108, 'Comedy', 6.4, 'PG');


SELECT *
FROM movies
WHERE genre LIKE 'Sci-Fi';


SELECT *
FROM movies
WHERE imdb_score >= 6.5;


SELECT *
FROM movies
WHERE rating LIKE 'G' OR rating LIKE 'PG'
    AND runtime < 100;


SELECT genre, AVG(runtime) AS Average_Runtimes
FROM movies
WHERE imdb_score < 7.5
GROUP BY genre;


UPDATE movies
    SET rating = 'R'
WHERE title LIKE 'Starship Troopers';


SELECT id, rating
FROM movies
WHERE genre LIKE 'Horror'
    OR genre LIKE 'Documentary'


SELECT rating, AVG(imdb_score), MAX(imdb_score), MIN(imdb_score)
FROM movies
GROUP BY rating;


SELECT rating, AVG(imdb_score), MAX(imdb_score), MIN(imdb_score)
FROM movies
GROUP BY rating
    HAVING COUNT(*) > 1;


DELETE FROM movies
WHERE rating LIKE 'R';


SELECT h.address, h.homenumber
FROM home AS h
JOIN person AS p
    ON p.home_id = h.id
GROUP BY h.address
    HAVING COUNT(*) > 1;


SELECT p.first_name, p.last_name, h.address
FROM person AS p
JOIN home AS h
    ON p.home_id = h.id
WHERE h.address LIKE '11 Essex Dr.Farmingdale, NY 11735';


UPDATE person
    SET home_id = 2
WHERE last_name LIKE 'Kitto';


UPDATE home
    SET homenumber = '111-1111'
WHERE address LIKE '11 Essex Dr.Farmingdale, NY 11735';


SELECT p.first_name, p.last_name, h.homenumber
FROM person AS p
JOIN home AS h
    ON p.home_id = h.id
WHERE p.first_name LIKE 'John'
    AND p.last_name LIKE 'Smith';