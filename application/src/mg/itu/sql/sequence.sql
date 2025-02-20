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


CREATE OR REPLACE FUNCTION generate_id_avion()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_avion IS NULL THEN
    NEW.id_avion := 'AV-' || nextval('seq_avion');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_avion
BEFORE INSERT ON Avion
FOR EACH ROW EXECUTE FUNCTION generate_id_avion();

CREATE OR REPLACE FUNCTION generate_id_type_siege()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_Type_siege IS NULL THEN
    NEW.id_Type_siege := 'TS-' || nextval('seq_type_siege');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_type_siege
BEFORE INSERT ON Type_siege
FOR EACH ROW EXECUTE FUNCTION generate_id_type_siege();

CREATE OR REPLACE FUNCTION generate_id_ville()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_ville IS NULL THEN
    NEW.id_ville := 'V-' || nextval('seq_ville');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_ville
BEFORE INSERT ON Ville
FOR EACH ROW EXECUTE FUNCTION generate_id_ville();

CREATE OR REPLACE FUNCTION generate_id_critere_reservation()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_reservation IS NULL THEN
    NEW.id_reservation := 'CR-' || nextval('seq_critere_reservation');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_critere_reservation
BEFORE INSERT ON Critere_reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_critere_reservation();

CREATE OR REPLACE FUNCTION generate_id_vol()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_vol IS NULL THEN
    NEW.id_vol := 'VOL-' || nextval('seq_vol');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_vol
BEFORE INSERT ON Vol
FOR EACH ROW EXECUTE FUNCTION generate_id_vol();

CREATE OR REPLACE FUNCTION generate_id_promotion()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_promotion IS NULL THEN
    NEW.id_promotion := 'PROMO-' || nextval('seq_promotion');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_promotion
BEFORE INSERT ON Promotion
FOR EACH ROW EXECUTE FUNCTION generate_id_promotion();

CREATE OR REPLACE FUNCTION generate_id_annulation_reservation()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_annulation_reservation IS NULL THEN
    NEW.id_annulation_reservation := 'AR-' || nextval('seq_annulation_reservation');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_annulation_reservation
BEFORE INSERT ON annulation_reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_annulation_reservation();

CREATE OR REPLACE FUNCTION generate_id_role()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_role IS NULL THEN
    NEW.id_role := 'ROLE-' || nextval('seq_role');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_role
BEFORE INSERT ON Role
FOR EACH ROW EXECUTE FUNCTION generate_id_role();

CREATE OR REPLACE FUNCTION generate_id_utilisateur()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_utilisateur IS NULL THEN
    NEW.id_utilisateur := 'USER-' || nextval('seq_utilisateur');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_utilisateur
BEFORE INSERT ON Utilisateur
FOR EACH ROW EXECUTE FUNCTION generate_id_utilisateur();

CREATE OR REPLACE FUNCTION generate_id_reservation()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_reservation IS NULL THEN
    NEW.id_reservation := 'RES-' || nextval('seq_reservation');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_reservation
BEFORE INSERT ON Reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_reservation();