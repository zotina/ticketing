-- Insérer des rôles
INSERT INTO Role (id_role, libelle) VALUES ('1', 'admin');
INSERT INTO Role (id_role, libelle) VALUES ('2', 'user');

-- Insérer des types de sièges
INSERT INTO Type_siege (id_Type_siege, libelle) VALUES ('1', 'Commercial');
INSERT INTO Type_siege (id_Type_siege, libelle) VALUES ('2', 'Economique');

-- Insérer des villes
INSERT INTO Ville (id_ville, nom) VALUES ('1', 'Paris');
INSERT INTO Ville (id_ville, nom) VALUES ('2', 'Londres');
INSERT INTO Ville (id_ville, nom) VALUES ('3', 'New York');
INSERT INTO Ville (id_ville, nom) VALUES ('4', 'Tokyo');
INSERT INTO Ville (id_ville, nom) VALUES ('5', 'Sydney');

-- Insérer des avions
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('1', '2015-06-10', 'Boeing 747');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('2', '2018-04-20', 'Airbus A380');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('3', '2020-09-15', 'Embraer E190');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('4', '2012-03-22', 'Boeing 737');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('5', '2016-11-30', 'Airbus A320');

-- Insérer un utilisateur admin
INSERT INTO Utilisateur (id_utilisateur, email, mdp, nom, id_role) VALUES ('1', 'admin@gmail.com', 'admin', 'Administrateur', 'ROLE-1');

-- Insérer un critère de réservation
INSERT INTO Critere_reservation (id_reservation, heur, date_changement) VALUES ('1', 48.00, '2025-02-15');

-- Insérer une annulation de réservation
INSERT INTO annulation_reservation (id_annulation_reservation, heur, date_changement) VALUES ('1', 24.00, '2025-02-10');
