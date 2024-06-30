--liquibase formatted sql

--changeset zhernovoy:01 labels: release1.0

CREATE TABLE BOT_USER(
    id VARCHAR(30) PRIMARY KEY
)