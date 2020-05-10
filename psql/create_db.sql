CREATE TABLE public.patient
(
    id bigserial NOT NULL,
    firstname text NOT NULL,
    lastname text NOT NULL,
    pesel text NOT NULL,
    birthdate date NOT NULL,
    residence text NOT NULL,
    phone text NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (pesel)
);

CREATE TABLE public.doctor
(
    id bigserial NOT NULL,
    firstname text NOT NULL,
    lastname text NOT NULL,
    specialization text NOT NULL,
    degree text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.hospital
(
    id bigserial NOT NULL,
    name text NOT NULL,
    street text NOT NULL,
    city text NOT NULL,
    isPublic BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.operation
(
    id bigserial NOT NULL,
    name text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.event
(
    id bigserial NOT NULL,
    id_patient bigserial NOT NULL,
    id_doctor bigserial NOT NULL,
    id_hospital bigserial NOT NULL,
    eventdate date NOT NULL,
    billable BOOLEAN NOT NULL,
    cost real NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_patient) REFERENCES public.patient (id),
    FOREIGN KEY (id_doctor) REFERENCES public.doctor (id),
    FOREIGN KEY (id_hospital) REFERENCES public.hospital (id)
);

INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Jan', 'Kowalski', '95111504845', '2013-06-13', 'Warszawa', '545747695')