CREATE TABLE USER_ACCOUNT (
                              id SERIAL NOT NULL,
                              email VARCHAR(255) UNIQUE NOT NULL,
                              firstname VARCHAR(255) NOT NULL,
                              lastname VARCHAR(255) NOT NULL,
                              password VARCHAR(255) NOT NULL,
                              role VARCHAR(255) NOT NULL,
                              PRIMARY KEY (id)
);