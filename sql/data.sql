INSERT INTO Role (id_role, libelle) VALUES ('1', 'admin');
INSERT INTO Role (id_role, libelle) VALUES ('2', 'user');

INSERT INTO Type_siege (id_Type_siege, libelle) VALUES ('1', 'Commercial');
INSERT INTO Type_siege (id_Type_siege, libelle) VALUES ('2', 'Economique');

INSERT INTO Classe (id_classe, min_age, max_age) VALUES('1', 0, 17);
INSERT INTO Classe (id_classe, min_age, max_age) VALUES('2', 18, 200);

INSERT INTO Ville (id_ville, nom) VALUES ('1', 'Paris');
INSERT INTO Ville (id_ville, nom) VALUES ('2', 'Londres');
INSERT INTO Ville (id_ville, nom) VALUES ('3', 'New York');
INSERT INTO Ville (id_ville, nom) VALUES ('4', 'Tokyo');
INSERT INTO Ville (id_ville, nom) VALUES ('5', 'Sydney');

INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('1', '2015-06-10', 'Boeing 747');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('2', '2018-04-20', 'Airbus A380');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('3', '2020-09-15', 'Embraer E190');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('4', '2012-03-22', 'Boeing 737');
INSERT INTO Avion (id_avion, date_fabrication, model) VALUES ('5', '2016-11-30', 'Airbus A320');

INSERT INTO Utilisateur (id_utilisateur, email, mdp, nom, id_role) VALUES ('1', 'admin@gmail.com', '$2a$10$CzZurW1yocE9sFZZyUWfs.1lOKazldfPaMatFmoZc/5wMj.dIz21W', 'Administrateur', '1');

INSERT INTO Critere_reservation (id_reservation, heur, date_changement) VALUES ('1', 48.00, '2025-02-15');

INSERT INTO annulation_reservation (id_annulation_reservation, heur, date_changement) VALUES ('1', 24.00, '2025-02-10');

INSERT INTO Facteur_classe (id_facteur_classe, facteur, id_classe) VALUES ('1', 0.00, '1');  
INSERT INTO Facteur_classe (id_facteur_classe, facteur, id_classe) VALUES ('2', 10.00, '2'); 



--/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

--/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

--/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


INSERT INTO Vol (id_vol, date_vol, id_ville,id_ville_1, id_avion) VALUES ('1', '2025-03-10 14:00:00', '1','2', '1');
INSERT INTO Vol (id_vol, date_vol, id_ville,id_ville_1, id_avion) VALUES ('2', '2025-03-15 10:30:00', '2', '3','2');

INSERT INTO Promotion (id_promotion, date_promotion, id_vol) VALUES ('1', '2025-02-20', '1'); 

INSERT INTO promotion_siege (id_Type_siege, id_promotion, nbr_siege, promotion) VALUES ('1', '1', 1, 20.00);
INSERT INTO promotion_siege (id_Type_siege, id_promotion, nbr_siege, promotion) VALUES ('2', '1', 1, 15.00);


INSERT INTO vol_siege (id_Type_siege, id_vol, prix) VALUES ('1', '1', 200.00);
INSERT INTO vol_siege (id_Type_siege, id_vol, prix) VALUES ('2', '1', 100.00);
INSERT INTO vol_siege (id_Type_siege, id_vol, prix) VALUES ('1', '2', 450.00);
INSERT INTO vol_siege (id_Type_siege, id_vol, prix) VALUES ('2', '2', 280.00);

INSERT INTO Reservation (id_reservation, date_reservation, prix, id_Type_siege, id_vol, id_utilisateur) 
VALUES ('1', '2025-02-15 08:00:00', NULL, '1', '1', '1');

INSERT INTO Reservation (id_reservation, date_reservation, prix, id_Type_siege, id_vol, id_utilisateur) 
VALUES ('2', '2025-02-15 09:00:00', NULL, '2', '1', '1');

INSERT INTO Reservation (id_reservation, date_reservation, prix, id_Type_siege, id_vol, id_utilisateur) 
VALUES ('3', '2025-02-15 10:00:00', NULL, '1', '2', '1');

INSERT INTO Reservation (id_reservation, date_reservation, prix, id_Type_siege, id_vol, id_utilisateur) 
VALUES ('4', '2025-02-15 11:00:00', NULL, '1', '1', '1');

INSERT INTO Reservation_classe (id_reservation, id_classe, nombre) VALUES ('1', '1', '2'); 
INSERT INTO Reservation_classe (id_reservation, id_classe, nombre) VALUES ('1', '2', '1'); 

INSERT INTO Reservation_classe (id_reservation, id_classe, nombre) VALUES ('2', '1', '1'); 
INSERT INTO Reservation_classe (id_reservation, id_classe, nombre) VALUES ('2', '2', '2'); 

INSERT INTO Reservation_classe (id_reservation, id_classe, nombre) VALUES ('3', '2', '3'); 


INSERT INTO Reservation_classe (id_reservation, id_classe, nombre) VALUES ('4', '1', '1'); 
INSERT INTO Reservation_classe (id_reservation, id_classe, nombre) VALUES ('4', '2', '1'); 