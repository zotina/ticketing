CREATE TABLE Avion(
   id_avion VARCHAR(50) ,
   date_fabrication DATE NOT NULL,
   model VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_avion)
);

CREATE TABLE Type_siege(
   id_Type_siege VARCHAR(50) ,
   libelle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_Type_siege)
);

CREATE TABLE Ville(
   id_ville VARCHAR(50) ,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_ville)
);

CREATE TABLE Critere_reservation(
   id_reservation VARCHAR(50) ,
   heur NUMERIC(15,2)   NOT NULL,
   date_changement DATE,
   PRIMARY KEY(id_reservation)
);

CREATE TABLE Vol(
   id_vol VARCHAR(50) ,
   date_vol TIMESTAMP NOT NULL,
   enPromotion BOOLEAN,
   id_ville VARCHAR(50)  NOT NULL,
   id_ville_1 VARCHAR(50)  NOT NULL,
   id_avion VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_vol),
   FOREIGN KEY(id_ville) REFERENCES Ville(id_ville),
   FOREIGN KEY(id_ville_1) REFERENCES Ville(id_ville),
   FOREIGN KEY(id_avion) REFERENCES Avion(id_avion)
);

CREATE TABLE Promotion(
   id_promotion VARCHAR(50) ,
   date_promotion DATE,
   id_vol VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_promotion),
   FOREIGN KEY(id_vol) REFERENCES Vol(id_vol)
);

CREATE TABLE annulation_reservation(
   id_annulation_reservation VARCHAR(50) ,
   heur NUMERIC(15,2)   NOT NULL,
   date_changement DATE,
   PRIMARY KEY(id_annulation_reservation)
);

CREATE TABLE Role(
   id_role VARCHAR(50) ,
   libelle VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_role)
);

CREATE TABLE Classe(
   id_classe VARCHAR(50) ,
   min_age INTEGER,
   max_age INTEGER,
   PRIMARY KEY(id_classe)
);

CREATE TABLE Utilisateur(
   id_utilisateur VARCHAR(50) ,
   email VARCHAR(50) ,
   mdp VARCHAR(255) ,
   nom VARCHAR(50) ,
   passport TEXT,
   id_role VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_utilisateur),
   FOREIGN KEY(id_role) REFERENCES Role(id_role)
);

CREATE TABLE Reservation(
   id_reservation VARCHAR(50) ,
   date_reservation TIMESTAMP NOT NULL,
   prix NUMERIC(15,2)  ,
   id_utilisateur VARCHAR(50)  NOT NULL,
   id_vol VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_reservation), 
   FOREIGN KEY(id_vol) REFERENCES Vol(id_vol),
   FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);

CREATE TABLE DetailsReservation(
   id_detailsReservation VARCHAR(50) ,
   nom TEXT,
   age INTEGER,
   passport VARCHAR(50) ,
   id_reservation VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_detailsReservation),
   FOREIGN KEY(id_reservation) REFERENCES Reservation(id_reservation)
);

CREATE TABLE Avion_siege(
   id_avion VARCHAR(50) ,
   id_Type_siege VARCHAR(50) ,
   nbr_siege INTEGER NOT NULL,
   PRIMARY KEY(id_avion, id_Type_siege),
   FOREIGN KEY(id_avion) REFERENCES Avion(id_avion),
   FOREIGN KEY(id_Type_siege) REFERENCES Type_siege(id_Type_siege)
);

CREATE TABLE promotion_siege(
   id_Type_siege VARCHAR(50) ,
   id_promotion VARCHAR(50) ,
   nbr_siege INTEGER NOT NULL,
   promotion NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_Type_siege, id_promotion),
   FOREIGN KEY(id_Type_siege) REFERENCES Type_siege(id_Type_siege),
   FOREIGN KEY(id_promotion) REFERENCES Promotion(id_promotion)
);

CREATE TABLE vol_siege(
   id_Type_siege VARCHAR(50) ,
   id_vol VARCHAR(50) ,
   prix NUMERIC(15,2)   NOT NULL,
   PRIMARY KEY(id_Type_siege, id_vol),
   FOREIGN KEY(id_Type_siege) REFERENCES Type_siege(id_Type_siege),
   FOREIGN KEY(id_vol) REFERENCES Vol(id_vol)
);

CREATE TABLE Reservation_classe(
   id_Type_siege VARCHAR(50) ,
   id_reservation VARCHAR(50) ,
   id_classe VARCHAR(50) ,
   nombre INTEGER ,
   PRIMARY KEY(id_Type_siege, id_reservation, id_classe),
   FOREIGN KEY(id_Type_siege) REFERENCES Type_siege(id_Type_siege),
   FOREIGN KEY(id_reservation) REFERENCES Reservation(id_reservation),
   FOREIGN KEY(id_classe) REFERENCES Classe(id_classe)
);

CREATE TABLE Facteur_classe(
   id_facteur_classe VARCHAR(50), 
   facteur NUMERIC(15,2)  , 
   id_classe VARCHAR(50),
   PRIMARY KEY(id_facteur_classe),
   FOREIGN KEY(id_classe) REFERENCES Classe(id_classe)
);

CREATE INDEX idx_reservation_date ON Reservation(date_reservation);
CREATE INDEX idx_promotion_siege ON promotion_siege(id_promotion, id_Type_siege);
CREATE INDEX idx_rc_id_reservation ON Reservation_classe(id_reservation);