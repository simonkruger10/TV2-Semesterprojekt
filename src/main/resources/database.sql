/*
drop database IF EXISTS tv2_semesterprojekt;
CREATE DATABASE tv2_semesterprojekt
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
 */

CREATE TABLE account
(
    id           SERIAL PRIMARY KEY,
    f_name       VARCHAR(65)  NOT NULL,
    m_name       VARCHAR(65),
    l_name       TIMESTAMP,
    email        VARCHAR(253) NOT NULL,
    access_level INTEGER DEFAULT -1,
    password     VARCHAR(253) NOT NULL,
    UNIQUE (email)
);

CREATE TABLE producer
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(65)  NOT NULL,
    logo       VARCHAR(254) NOT NULL,
    account_id INTEGER REFERENCES account (id)
);

CREATE TABLE production
(
    id            SERIAL,
    name          VARCHAR(65) NOT NULL,
    release_day   INTEGER     NOT NULL,
    release_month INTEGER     NOT NULL,
    release_year  INTEGER     NOT NULL,
    description   TEXT,
    image         VARCHAR(254),
    PRIMARY KEY (name, release_day, release_month, release_year),
    UNIQUE (id)
);

CREATE TABLE credit_unit
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(65) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE credit_person
(
    m_name VARCHAR(65),
    l_name VARCHAR(65),
    image  VARCHAR(254),
    email  VARCHAR(253) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
) INHERITS (credit_unit);

CREATE TABLE credit_group
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(65) NOT NULL,
    description TEXT
);

CREATE TABLE producer_production_relation
(
    producer_id   INTEGER NOT NULL REFERENCES producer (id),
    production_id INTEGER NOT NULL REFERENCES production (id),
    PRIMARY KEY (producer_id, production_id)
);

CREATE TABLE production_credit_unit_relation
(
    production_id   INTEGER NOT NULL REFERENCES production (id),
    credit_id       INTEGER NOT NULL REFERENCES credit_unit (id),
    credit_group_id INTEGER NOT NULL REFERENCES credit_group (id),
    PRIMARY KEY (production_id, credit_id, credit_group_id)
);

CREATE TABLE production_credit_person_relation
(
    credit_id INTEGER NOT NULL REFERENCES credit_person (id),
    PRIMARY KEY (production_id, credit_id, credit_group_id)
) INHERITS (production_credit_unit_relation);

CREATE TABLE account_producer_relation
(
    account_id  INTEGER NOT NULL REFERENCES account (id),
    producer_id INTEGER NOT NULL REFERENCES producer (id),
    PRIMARY KEY (account_id, producer_id)
);

INSERT INTO account (f_name, email, access_level, password)
VALUES ('Admin', 'admin@system.tld', 8,
        '83b1c2b4acd1c91f65dd9148d10b990b9ffbf1fce15831250d81e48cf87aa70fac9ebc651757b8f6e8d5c93d6d5310060e8a14a9020e583bf968f8b4b320dae0'); -- System admin
