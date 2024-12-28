CREATE TABLE images
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(255),
    original_file_name VARCHAR(255),
    size               BIGINT,
    content_type       VARCHAR(255),
    bytes              LONGBLOB,
    habit_id           BIGINT,
    event_id           BIGINT,
    FOREIGN KEY (habit_id) REFERENCES habits (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);