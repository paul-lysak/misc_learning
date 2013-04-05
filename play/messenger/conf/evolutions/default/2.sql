# Messages table
 
# --- !Ups
 
CREATE TABLE MESSAGES (
    id SERIAL PRIMARY KEY,
    from_user_id INTEGER NOT NULL,
    to_user_id INTEGER NOT NULL,
    body varchar(255) NOT NULL
);
 
# --- !Downs
 
DROP TABLE MESSAGES
