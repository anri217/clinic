CREATE TABLE IF NOT EXISTS patients
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,
    first_name          VARCHAR(20),
    last_name           VARCHAR(40),
    patronymic          VARCHAR(40),
    phone_number        VARCHAR(15)
);
CREATE TABLE IF NOT EXISTS doctors
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,
    first_name          VARCHAR(20),
    last_name           VARCHAR(40),
    patronymic          VARCHAR(40),
    specialization      VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS recipes
(
    id                  BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,
    description         LONGVARCHAR,
    patient_id          BIGINT NOT NULL,
    doctor_id           BIGINT NOT NULL,
    creation_date       TIMESTAMP,
    validity            SMALLINT,
    priority            VARCHAR(6),

    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
)