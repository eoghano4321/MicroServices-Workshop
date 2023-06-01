


DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    quantity INT,
    user_email VARCHAR(255),
    user_status VARCHAR(255)
    );

-- Insert sample data
INSERT INTO users (user_id, quantity, user_email, user_status)
VALUES (1, 2, 'test1', 'open'),
       (2, 3, 'test2', 'open'),
       (2, 0, 'test3', 'open'),
       (3, 1, 'test4', 'open');
