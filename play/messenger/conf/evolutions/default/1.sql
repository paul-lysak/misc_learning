# Users schema
 
# --- !Ups
 
CREATE TABLE APP_USERS (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
	CONSTRAINT name_unique UNIQUE(name)
);
 
# --- !Downs
 
DROP TABLE APP_USERS;