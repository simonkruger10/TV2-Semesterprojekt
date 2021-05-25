-- noinspection SpellCheckingInspectionForFile

DROP TABLE IF EXISTS account CASCADE;
CREATE TABLE account
(
    id           SERIAL PRIMARY KEY,
    f_name       VARCHAR(65)  NOT NULL,
    l_name       VARCHAR(65),
    email        VARCHAR(253) NOT NULL,
    access_level INTEGER DEFAULT -1,
    password     VARCHAR(253) NOT NULL,
    UNIQUE (email)
);

DROP TABLE IF EXISTS producer CASCADE;
CREATE TABLE producer
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(65) NOT NULL,
    logo       VARCHAR(254),
    account_id INTEGER REFERENCES account (id)
);

DROP TABLE IF EXISTS production CASCADE;
CREATE TABLE production
(
    id            SERIAL,
    name          VARCHAR(65) NOT NULL,
    release_day   INTEGER     NOT NULL,
    release_month INTEGER     NOT NULL,
    release_year  INTEGER     NOT NULL,
    description   TEXT,
    image         VARCHAR(254),
    producer_id   INTEGER REFERENCES producer (id),
    PRIMARY KEY (name, release_day, release_month, release_year),
    UNIQUE (id)
);

DROP TABLE IF EXISTS credit_unit CASCADE;
CREATE TABLE credit_unit
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(65) NOT NULL,
    UNIQUE (name)
);

DROP TABLE IF EXISTS credit_person CASCADE;
CREATE TABLE credit_person
(
    l_name VARCHAR(65),
    image  VARCHAR(254),
    email  VARCHAR(253) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
) INHERITS (credit_unit);

DROP TABLE IF EXISTS credit_group CASCADE;
CREATE TABLE credit_group
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(65) NOT NULL,
    description TEXT
);

DROP TABLE IF EXISTS production_credit_unit_relation CASCADE;
CREATE TABLE production_credit_unit_relation
(
    production_id   INTEGER NOT NULL REFERENCES production (id),
    credit_id       INTEGER NOT NULL REFERENCES credit_unit (id),
    credit_group_id INTEGER NOT NULL REFERENCES credit_group (id),
    PRIMARY KEY (production_id, credit_id, credit_group_id)
);

DROP TABLE IF EXISTS production_credit_person_relation CASCADE;
CREATE TABLE production_credit_person_relation
(
    credit_id INTEGER NOT NULL REFERENCES credit_person (id),
    PRIMARY KEY (production_id, credit_id, credit_group_id)
) INHERITS (production_credit_unit_relation);
