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
    specialization text,
    name text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.event
(
    id bigserial NOT NULL,
    id_patient bigserial NOT NULL,
    id_doctor bigserial NOT NULL,
    id_hospital bigserial NOT NULL,
    id_operation bigserial NOT NULL,
    eventdate date NOT NULL,
    billable BOOLEAN NOT NULL,
    cost real NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_patient) REFERENCES public.patient (id),
    FOREIGN KEY (id_doctor) REFERENCES public.doctor (id),
    FOREIGN KEY (id_hospital) REFERENCES public.hospital (id),
    FOREIGN KEY (id_operation) REFERENCES public.operation (id)
);

INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Jan', 'Adamiak', '95111504845', '2013-06-13', 'Warszawa', '545747695');
INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Adam', 'Kowalski', '97110504855', '1999-06-01', 'Lodz', '554668779');
INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Anna', 'Cepik', '98111504845', '1954-03-13', 'Bialystok', '4459987564');
INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Bartosz', 'Cebula', '87111504895', '1945-06-18', 'Warszawa', '125478556');
INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Patryk', 'Nowak', '55111504111', '1965-03-27', 'Warszawa', '668447552');
INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Mateusz', 'Mauer', '95011504845', '1970-06-06', 'Lodz', '994858778');
INSERT INTO patient (firstname, lastname, pesel, birthdate, residence, phone) values ('Maciej', 'Keller', '84011504456', '2008-09-11', 'Gdansk', '664858998');


INSERT INTO doctor (firstname, lastname, specialization, degree) values ('Adam', 'Nowak', 'dentysta', 'doktor');
INSERT INTO doctor (firstname, lastname, specialization, degree) values ('Anna', 'Szara', 'kardiolog', 'profesor');
INSERT INTO doctor (firstname, lastname, specialization, degree) values ('Jerry', 'Wolf', 'psychiatra', 'doktor');
INSERT INTO doctor (firstname, lastname, specialization, degree) values ('Anna', 'Szara', 'chirurg', 'doktor habilitowany');



INSERT INTO hospital (name, street, city, isPublic) values ('Publiczny nr 1', 'Długa', 'Warszawa', 't');
INSERT INTO hospital (name, street, city, isPublic) values ('Publiczny nr 2', 'Szeroka', 'Warszawa', 't');
INSERT INTO hospital (name, street, city, isPublic) values ('Prywatny nr 1', 'Ladna', 'Lodz', 'f');
INSERT INTO hospital (name, street, city, isPublic) values ('Prywatny nr 2', 'Sloneczna', 'Gdansk', 'f');

INSERT INTO doctor_hospital (id_doctor, id_hospital) values (1, 1), (1, 2), (2, 2), (4, 2), (3, 3), (4, 3), (4, 1), (2, 1), (2, 3), (3, 4);

INSERT INTO operation (name) values ('konsultacja');
INSERT INTO operation (name) values ('wypisanie recepty');
INSERT INTO operation (name, specialization) values ('rekonstrukcja kosci udowej', 'chirurg');
INSERT INTO operation (name, specialization) values ('wymiana plomby', 'dentysta');
INSERT INTO operation (name, specialization) values ('wstawienie stentow', 'kardiolog');
INSERT INTO operation (name, specialization) values ('leczenie depresji', 'psychiatra');


INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('1','1','1','1', '2011-03-01', 'f', '0');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('2','2','2','1', '2010-08-22', 'f', '0');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('3','3','3','1', '2019-04-21', 't', '1000');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('4','4','3','1', '2018-03-05', 't', '2000');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('5','1','1','2', '2013-08-30', 'f', '0');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('6','2','3','2', '2012-06-27', 't', '2500');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('7','3','3','6', '2011-02-07', 't', '3000');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('3','4','4','3', '2010-03-05', 't', '300');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('2','1','1','4', '2008-01-15', 'f', '0');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('2','2','1','5', '2011-08-16', 'f', '0');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('6','3','3','2', '2002-05-13', 't', '150');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('5','4','2','3', '2011-03-11', 'f', '0');
INSERT INTO event (id_patient, id_doctor, id_hospital, id_operation, eventdate, billable, cost) values ('6','1','2','1', '2011-05-12', 'f', '0');