--liquibase formatted sql

--changeset zhernovoy:05 labels: release1.0
ALTER TABLE BOT_USER ADD mode VARCHAR(10) NULL;