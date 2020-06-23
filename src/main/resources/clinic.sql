CREATE TABLE IF NOT EXISTS patients
(
    id                  BIGINT NOT NULL,
    first_name          VARCHAR(20),
    last_name           VARCHAR(40),
    patronymic          VARCHAR(40),
    phone_number        VARCHAR(15),

    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS doctors
(
    id                  BIGINT NOT NULL,
    first_name          VARCHAR(20),
    last_name           VARCHAR(40),
    patronymic          VARCHAR(40),
    specialization      VARCHAR(50),

    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS recipes
(
    id                  BIGINT NOT NULL,
    description         LONGVARCHAR,
    patient_id          BIGINT NOT NULL,
    doctor_id           BIGINT NOT NULL,
    creation_date       TIMESTAMP,
    validity            BIGINT,
    priority            VARCHAR(6),

    PRIMARY KEY (id),
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);