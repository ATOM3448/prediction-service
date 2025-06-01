create user prediction_service with login password 'prediction_service';

create database prediction_service owner prediction_service encoding 'utf-8';

create schema prediction_service;

alter schema prediction_service owner to prediction_service;