CREATE SEQUENCE seq_critere_reservation START 1;

CREATE OR REPLACE FUNCTION generate_id_critere_reservation()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_reservation IS NULL THEN
    NEW.id_reservation := 'CRI-' || nextval('seq_critere_reservation');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_critere_reservation
BEFORE INSERT ON critere_reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_critere_reservation();

CREATE SEQUENCE seq_ville START 1;

CREATE OR REPLACE FUNCTION generate_id_ville()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_ville IS NULL THEN
    NEW.id_ville := 'VIL-' || nextval('seq_ville');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_ville
BEFORE INSERT ON ville
FOR EACH ROW EXECUTE FUNCTION generate_id_ville();

CREATE SEQUENCE seq_vol START 1;

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
BEFORE INSERT ON vol
FOR EACH ROW EXECUTE FUNCTION generate_id_vol();

CREATE SEQUENCE seq_avion START 1;

CREATE OR REPLACE FUNCTION generate_id_avion()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_avion IS NULL THEN
    NEW.id_avion := 'AVI-' || nextval('seq_avion');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_avion
BEFORE INSERT ON avion
FOR EACH ROW EXECUTE FUNCTION generate_id_avion();

CREATE SEQUENCE seq_promotion START 1;

CREATE OR REPLACE FUNCTION generate_id_promotion()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_promotion IS NULL THEN
    NEW.id_promotion := 'PRO-' || nextval('seq_promotion');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_promotion
BEFORE INSERT ON promotion
FOR EACH ROW EXECUTE FUNCTION generate_id_promotion();

CREATE SEQUENCE seq_annulation_reservation START 1;

CREATE OR REPLACE FUNCTION generate_id_annulation_reservation()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_annulation_reservation IS NULL THEN
    NEW.id_annulation_reservation := 'ANN-' || nextval('seq_annulation_reservation');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_annulation_reservation
BEFORE INSERT ON annulation_reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_annulation_reservation();

CREATE SEQUENCE seq_role START 1;

CREATE OR REPLACE FUNCTION generate_id_role()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_role IS NULL THEN
    NEW.id_role := 'ROL-' || nextval('seq_role');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_role
BEFORE INSERT ON role
FOR EACH ROW EXECUTE FUNCTION generate_id_role();

CREATE SEQUENCE seq_utilisateur START 1;

CREATE OR REPLACE FUNCTION generate_id_utilisateur()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_utilisateur IS NULL THEN
    NEW.id_utilisateur := 'UTI-' || nextval('seq_utilisateur');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_utilisateur
BEFORE INSERT ON utilisateur
FOR EACH ROW EXECUTE FUNCTION generate_id_utilisateur();

CREATE SEQUENCE seq_reservation START 1;

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
BEFORE INSERT ON reservation
FOR EACH ROW EXECUTE FUNCTION generate_id_reservation();

CREATE SEQUENCE seq_detailsreservation START 1;

CREATE OR REPLACE FUNCTION generate_id_detailsreservation()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_detailsreservation IS NULL THEN
    NEW.id_detailsreservation := 'DET-' || nextval('seq_detailsreservation');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_detailsreservation
BEFORE INSERT ON detailsreservation
FOR EACH ROW EXECUTE FUNCTION generate_id_detailsreservation();

CREATE SEQUENCE seq_type_siege START 1;

CREATE OR REPLACE FUNCTION generate_id_type_siege()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_type_siege IS NULL THEN
    NEW.id_type_siege := 'TYP-' || nextval('seq_type_siege');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_type_siege
BEFORE INSERT ON type_siege
FOR EACH ROW EXECUTE FUNCTION generate_id_type_siege();

CREATE SEQUENCE seq_classe START 1;

CREATE OR REPLACE FUNCTION generate_id_classe()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.id_classe IS NULL THEN
    NEW.id_classe := 'CLA-' || nextval('seq_classe');
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_id_classe
BEFORE INSERT ON classe
FOR EACH ROW EXECUTE FUNCTION generate_id_classe();