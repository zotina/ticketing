DELETE FROM  promotion_siege;
DELETE FROM  vol_siege;
DELETE FROM  Avionsiege;

DELETE FROM  Reservation;
DELETE FROM  Promotion;
DELETE FROM  Utilisateur;
DELETE FROM  Vol;

DELETE FROM  Critere_reservation;
DELETE FROM  annulation_reservation;
DELETE FROM  Type_siege;
DELETE FROM  Ville;
DELETE FROM  Avion;
DELETE FROM  Role;


DROP TRIGGER IF EXISTS set_id_reservation_classe ON reservation_classe;

DROP FUNCTION IF EXISTS generate_id_reservation_classe();

DROP SEQUENCE IF EXISTS seq_reservation_classe;
