-- Create the users table
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      user_id VARCHAR(255),
    customer_id BIGINT,
    product_id BIGINT,
    quantity INT,
    user_date TIMESTAMP,
    user_status VARCHAR(255)
    );

-- Insert sample data
INSERT INTO users (user_id, customer_id, product_id, quantity, user_date, user_status)
VALUES ('ORD001', 1, 101, 2, '2022-01-01 10:00:00', 'open'),
       ('ORD002', 2, 102, 3, '2022-02-02 12:00:00', 'open'),
       ('ORD002', 2, 104, 3, '2022-02-02 12:00:00', 'open'),
       ('ORD003', 3, 103, 1, '2022-03-03 14:00:00', 'open');
