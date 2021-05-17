-- noinspection SpellCheckingInspectionForFile

--
-- DATABASE_NAME: tv2_semesterprojekt_f3f70b5a // F3F70B5A is an Adler-32 value of tv2_semesterprojekt_17-05-2021-12:20
-- USER_NAME: tv2_dbuser_f3f70b5a // F3F70B5A is an Adler-32 value of tv2_semesterprojekt_17-05-2021-12:20
-- PASSWORD: 4A03069D // 4A03069D is an Adler-32 value of "asfwe 47igfsvw3443e2" without quotation marks
--


--
-- Remove the tv2_semesterprojekt_f3f70b5a database and tv2_dbuser_f3f70b5a user
--

-- Kick all activity connections to the tv2_semesterprojekt_f3f70b5a database if any
SELECT PG_TERMINATE_BACKEND(pid)
FROM pg_stat_activity
WHERE datname = 'tv2_semesterprojekt_f3f70b5a';

-- Drop the tv2_semesterprojekt_f3f70b5a database if the database exists
DROP DATABASE IF EXISTS tv2_semesterprojekt_f3f70b5a;

-- Revoke all rights for tv2_dbuser_f3f70b5a user if the user exists
DO
$$BEGIN
    IF EXISTS (SELECT FROM pg_roles WHERE rolname = 'tv2_dbuser_f3f70b5a') THEN
        EXECUTE 'REVOKE CONNECT ON DATABASE "postgres" FROM tv2_dbuser_f3f70b5a';
    END IF;
END$$;

-- Drop tv2_dbuser_f3f70b5a user if the user exists
DROP USER IF EXISTS tv2_dbuser_f3f70b5a;


--
-- Create tv2_dbuser_f3f70b5a database and tv2_dbuser_f3f70b5a user
--

-- Create tv2_dbuser_f3f70b5a user
CREATE USER tv2_dbuser_f3f70b5a WITH ENCRYPTED PASSWORD '4A03069D';

-- Create the tv2_semesterprojekt_f3f70b5a database
CREATE DATABASE tv2_semesterprojekt_f3f70b5a
    WITH
    OWNER tv2_dbuser_f3f70b5a
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

-- Assign all rights to tv2_dbuser_f3f70b5a user
GRANT ALL PRIVILEGES ON DATABASE tv2_semesterprojekt_f3f70b5a TO tv2_dbuser_f3f70b5a;
