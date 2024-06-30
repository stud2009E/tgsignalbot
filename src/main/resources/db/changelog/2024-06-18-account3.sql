--liquibase formatted sql

--changeset zhernovoy:03 labels: release1.0

ALTER  TABLE  COMMAND_TEXT  DROP  COLUMN  LANG;