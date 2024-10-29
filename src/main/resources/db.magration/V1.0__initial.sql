CREATE TABLE users(
    id SERIAL PRIMARY KEY,
        username VARCHAR(25) NOT NULL,
        email VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        user_status INTEGER,
        role VARCHAR(50) NOT NULL,
        email_notification INTEGER,
        date_of_registration TIMESTAMP NOT NULL,
        last_activity_time TIMESTAMP NOT NULL,
        refresh_token_key VARCHAR(255),
        profile_picture_path VARCHAR(255),
        city VARCHAR(50),
        uuid VARCHAR(60)
);

CREATE TABLE verify_emails (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(255),
    expiry_date TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_registration (
    id SERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_registration FOREIGN KEY (user_id) REFERENCES users (id)
);