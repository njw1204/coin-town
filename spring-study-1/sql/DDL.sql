DROP TABLE IF EXISTS member;
CREATE TABLE member (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    email VARCHAR(300) NOT NULL DEFAULT '',
    password VARCHAR(300) NOT NULL DEFAULT '',
    name VARCHAR(300) NOT NULL DEFAULT '',
    PRIMARY KEY (id)
);
