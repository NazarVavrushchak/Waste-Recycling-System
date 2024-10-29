CREATE TABLE habits
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(70) NOT NULL,
    description TEXT        NOT NULL,
    difficulty  INT         NOT NULL,
    image_url   VARCHAR(255),
    created_at  TIMESTAMP   NOT NULL,
    user_id     BIGINT      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    INDEX (user_id)
);

CREATE TABLE habit_tags
(
    habit_id BIGINT NOT NULL,
    tag      VARCHAR(255),
    FOREIGN KEY (habit_id) REFERENCES habits (id)
);
