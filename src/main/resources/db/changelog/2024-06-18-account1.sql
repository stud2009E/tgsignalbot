--liquibase formatted sql

--changeset zhernovoy:02 labels: release1.0
ALTER TABLE BOT_USER ADD email VARCHAR(255) NULL;