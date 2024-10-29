CREATE TABLE events
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(70)   NOT NULL,
    description   VARCHAR(5000) NOT NULL,
    thematic      VARCHAR(20)   NOT NULL,
    event_type    VARCHAR(20)   NOT NULL,
    location_type VARCHAR(20)   NOT NULL,
    start_time    TIMESTAMP     NOT NULL,
    image_url     VARCHAR(255),
    user_id       BIGINT        NOT NULL,
    CONSTRAINT CHK_thematic CHECK (thematic IN ('ECONOMIC', 'SOCIAL', 'ENVIRONMENTAL')),
    CONSTRAINT CHK_event_type CHECK (event_type IN ('PUBLIC', 'PRIVATE')),
    CONSTRAINT CHK_location_type CHECK (location_type IN ('ONLINE', 'OFFLINE')),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);