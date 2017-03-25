CREATE TABLE avion
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    nb_place INT(11),
    pilote_id INT(11),
    CONSTRAINT avion_ibfk_1 FOREIGN KEY (pilote_id) REFERENCES pilote (id)
);
CREATE INDEX pilote_id ON avion (pilote_id);
CREATE TABLE client
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    num_passport VARCHAR(255) NOT NULL,
    cin VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL
);
CREATE TABLE employe
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    active SMALLINT,
    role_id INT(11),
    CONSTRAINT employe_ibfk_1 FOREIGN KEY (role_id) REFERENCES role (id)
);
CREATE INDEX role_id ON employe (role_id);
CREATE TABLE ligne
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    airoport_aller VARCHAR(255) NOT NULL,
    airoport_arriver VARCHAR(255) NOT NULL,
    prix_class DOUBLE NOT NULL,
    prix_normale DOUBLE NOT NULL
);
CREATE TABLE pilote
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    matricule VARCHAR(255) NOT NULL
);
CREATE TABLE reservation
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    client_id INT(11),
    type VARCHAR(255) NOT NULL,
    ligne_id INT(11),
    class VARCHAR(255) NOT NULL,
    date DATETIME,
    CONSTRAINT reservation_ibfk_1 FOREIGN KEY (client_id) REFERENCES client (id),
    CONSTRAINT reservation_ibfk_2 FOREIGN KEY (ligne_id) REFERENCES ligne (id)
);
CREATE INDEX client_id ON reservation (client_id);
CREATE INDEX ligne_id ON reservation (ligne_id);
CREATE TABLE role
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL
);