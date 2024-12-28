-- Create table for recycling points
CREATE TABLE recycling_points
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255)     NOT NULL,
    address    VARCHAR(255)     NOT NULL,
    latitude   DOUBLE PRECISION NOT NULL CHECK (latitude BETWEEN -90.0 AND 90.0),
    longitude  DOUBLE PRECISION NOT NULL CHECK (longitude BETWEEN -180.0 AND 180.0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id    BIGINT           NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Create table for waste types
CREATE TABLE recycling_point_waste_types
(
    id                 BIGSERIAL PRIMARY KEY,
    recycling_point_id BIGINT      NOT NULL,
    waste_type         VARCHAR(50) NOT NULL,
    CONSTRAINT fk_recycling_point FOREIGN KEY (recycling_point_id) REFERENCES recycling_points (id) ON DELETE CASCADE
);

-- Create table for working hours
CREATE TABLE working_hours
(
    id                 BIGSERIAL PRIMARY KEY,
    recycling_point_id BIGINT NOT NULL,
    opening_time       TIME   NOT NULL,
    closing_time       TIME   NOT NULL,
    CONSTRAINT fk_working_hours_recycling_point FOREIGN KEY (recycling_point_id) REFERENCES recycling_points (id) ON DELETE CASCADE
);

-- Add unique constraints for clarity
CREATE UNIQUE INDEX idx_unique_recycling_point_name ON recycling_points (name);
CREATE UNIQUE INDEX idx_unique_recycling_point_address ON recycling_points (address);
