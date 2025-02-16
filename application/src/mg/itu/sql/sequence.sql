-- Création des séquences
CREATE SEQUENCE seq_avion START 1;
CREATE SEQUENCE seq_type_siege START 1;
CREATE SEQUENCE seq_ville START 1;
CREATE SEQUENCE seq_critere_reservation START 1;
CREATE SEQUENCE seq_vol START 1;
CREATE SEQUENCE seq_promotion START 1;
CREATE SEQUENCE seq_annulation_reservation START 1;
CREATE SEQUENCE seq_role START 1;
CREATE SEQUENCE seq_utilisateur START 1;
CREATE SEQUENCE seq_reservation START 1;


-- Fonction pour générer l'ID d'un avion
CREATE OR REPLACE FUNCTION generate_id_avion()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_avion := 'AV-' || nextval('seq_avion');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_avion
BEFORE INSERT ON Avion
FOR EACH ROW EXECUTE FUNCTION generate_id_avion();

-- Fonction pour générer l'ID d'un type de siège
CREATE OR REPLACE FUNCTION generate_id_type_siege()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_Type_siege := 'TS-' || nextval('seq_type_siege');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_type_siege
BEFORE INSERT ON Type_siege
FOR EACH ROW EXECUTE FUNCTION generate_id_type_siege();

-- Fonction pour générer l'ID d'une ville
CREATE OR REPLACE FUNCTION generate_id_ville()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_ville := 'V-' || nextval('seq_ville');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_ville
BEFORE INSERT ON Ville
FOR EACH ROW EXECUTE FUNCTION generate_id_ville();

-- Fonction pour générer l'ID d'un critère de réservation
CREATE OR REPLACE FUNCTION generate_id_critere_reservation()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_reservation := 'CR-' || nextval('seq_critere_reservation');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_critere_reservation
BEFORE INSERT ON Critere_reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_critere_reservation();

-- Fonction pour générer l'ID d'un vol
CREATE OR REPLACE FUNCTION generate_id_vol()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_vol := 'VOL-' || nextval('seq_vol');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_vol
BEFORE INSERT ON Vol
FOR EACH ROW EXECUTE FUNCTION generate_id_vol();

-- Fonction pour générer l'ID d'une promotion
CREATE OR REPLACE FUNCTION generate_id_promotion()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_promotion := 'PROMO-' || nextval('seq_promotion');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_promotion
BEFORE INSERT ON Promotion
FOR EACH ROW EXECUTE FUNCTION generate_id_promotion();

-- Fonction pour générer l'ID d'une annulation de réservation
CREATE OR REPLACE FUNCTION generate_id_annulation_reservation()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_annulation_reservation := 'AR-' || nextval('seq_annulation_reservation');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_annulation_reservation
BEFORE INSERT ON annulation_reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_annulation_reservation();

-- Fonction pour générer l'ID d'un rôle
CREATE OR REPLACE FUNCTION generate_id_role()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_role := 'ROLE-' || nextval('seq_role');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_role
BEFORE INSERT ON Role
FOR EACH ROW EXECUTE FUNCTION generate_id_role();

-- Fonction pour générer l'ID d'un utilisateur
CREATE OR REPLACE FUNCTION generate_id_utilisateur()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_utilisateur := 'USER-' || nextval('seq_utilisateur');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_utilisateur
BEFORE INSERT ON Utilisateur
FOR EACH ROW EXECUTE FUNCTION generate_id_utilisateur();

-- Fonction pour générer l'ID d'une réservation
CREATE OR REPLACE FUNCTION generate_id_reservation()
RETURNS TRIGGER AS $$
BEGIN
  NEW.id_reservation := 'RES-' || nextval('seq_reservation');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_reservation
BEFORE INSERT ON Reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_reservation();