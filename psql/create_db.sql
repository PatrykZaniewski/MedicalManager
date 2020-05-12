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
    ispublic BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.doctor_hospital
(
    id_doctor bigserial NOT NULL,
    id_hospital bigserial NOT NULL,
    PRIMARY KEY (id_doctor, id_hospital),
    FOREIGN KEY (id_doctor) references doctor(id),
    FOREIGN KEY (id_hospital) references hospital(id)
);

CREATE TABLE public.operation
(
    id bigserial NOT NULL,
    specialization text NOT NULL
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

INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Jan', 'Kowalski', '95111504845', '2013-06-13', 'Warszawa', '545747695');
INSERT INTO doctor (firstname, lastname, specialization, degree) values ('Adam', 'Nowak', 'dermatolog', 'inżynier xD');
INSERT INTO doctor (firstname, lastname, specialization, degree) values ('Anna', 'Szara', 'kardiolog', 'doktor');
INSERT INTO hospital (name, street, city, isPublic) values ('Publiczny nr 1', 'Długa', 'Warszawa', 't');
INSERT INTO hospital (name, street, city, isPublic) values ('Prywatny nr 1', 'Szeroka', 'Warszawa', 't');
INSERT INTO doctor_hospital (id_doctor, id_hospital) values (1, 1), (1, 2), (2, 2);

INSERT INTO operation (name) values ('konsultacja');
INSERT INTO event (id_patient, id_doctor, id_hospital, eventdate, billable, cost) values ('1','1','1', '2011-06-13', 'f', '0');