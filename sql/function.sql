CREATE OR REPLACE FUNCTION update_reservation_price_from_classe()
RETURNS TRIGGER AS $$
DECLARE
    v_total_price NUMERIC(15,2);
BEGIN
    WITH prix_par_classe AS (
        SELECT 
            rc.id_reservation,
            rc.id_classe,
            rc.nombre AS nbr,
            vs.prix AS base_price,
            fc.facteur,
            CASE 
                WHEN EXISTS (
                    SELECT 1 
                    FROM promotion_siege ps
                    JOIN Promotion p ON ps.id_promotion = p.id_promotion
                    JOIN Reservation r ON r.id_vol = p.id_vol 
                    WHERE r.id_reservation = rc.id_reservation
                    AND ps.id_Type_siege = rc.id_Type_siege
                    AND (
                        SELECT COUNT(*) 
                        FROM Reservation_classe rc2
                        JOIN Reservation r2 ON rc2.id_reservation = r2.id_reservation
                        WHERE r2.id_vol = r.id_vol
                        AND rc2.id_Type_siege = rc.id_Type_siege
                        AND r2.date_reservation <= (
                            SELECT date_reservation 
                            FROM Reservation 
                            WHERE id_reservation = NEW.id_reservation
                        )
                    ) <= ps.nbr_siege
                )
                THEN 
                    (vs.prix + (fc.facteur/100 * vs.prix)) * 
                    (1 - (
                        SELECT ps.promotion/100 
                        FROM promotion_siege ps
                        JOIN Promotion p ON ps.id_promotion = p.id_promotion
                        JOIN Reservation r ON r.id_vol = p.id_vol 
                        WHERE r.id_reservation = rc.id_reservation
                        AND ps.id_Type_siege = rc.id_Type_siege
                        LIMIT 1
                    )) * rc.nombre
                ELSE 
                    (vs.prix + (fc.facteur/100 * vs.prix)) * rc.nombre
            END AS prix_classe
        FROM Reservation_classe rc
        JOIN Reservation r ON rc.id_reservation = r.id_reservation
        JOIN Facteur_classe fc ON fc.id_classe = rc.id_classe
        JOIN vol_siege vs ON vs.id_vol = r.id_vol AND vs.id_Type_siege = rc.id_Type_siege 
        WHERE rc.id_reservation = NEW.id_reservation
    )
    SELECT COALESCE(SUM(prix_classe), 0)
    INTO v_total_price
    FROM prix_par_classe;
    
    UPDATE Reservation
    SET prix = v_total_price
    WHERE id_reservation = NEW.id_reservation;
    
    IF v_total_price = 0 THEN
        RAISE WARNING 'Aucun prix calculé pour cette réservation (ID: %)', NEW.id_reservation;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_update_reservation_price_from_classe
AFTER INSERT OR UPDATE ON Reservation_classe
FOR EACH ROW
EXECUTE FUNCTION update_reservation_price_from_classe();






CREATE OR REPLACE FUNCTION update_reservation_price_on_delete()
RETURNS TRIGGER AS $$
DECLARE
    v_id_reservation VARCHAR(50) := OLD.id_reservation;
    v_total_price NUMERIC(15,2);
BEGIN
    SELECT COALESCE(SUM(
        CASE 
            WHEN EXISTS (
                SELECT 1 
                FROM promotion_siege ps
                JOIN Promotion p ON ps.id_promotion = p.id_promotion
                JOIN Reservation r2 ON r2.id_vol = p.id_vol
                WHERE r2.id_reservation = r.id_reservation
                AND ps.id_Type_siege = rc.id_Type_siege
                AND (
                    SELECT COUNT(*) 
                    FROM Reservation_classe rc2
                    JOIN Reservation r3 ON rc2.id_reservation = r3.id_reservation
                    WHERE r3.id_vol = r.id_vol
                    AND rc2.id_Type_siege = rc.id_Type_siege
                    AND r3.date_reservation <= r.date_reservation
                ) <= ps.nbr_siege
            )
            THEN 
                (vs.prix + (fc.facteur/100 * vs.prix)) * 
                (1 - (
                    SELECT ps.promotion/100 
                    FROM promotion_siege ps
                    JOIN Promotion p ON ps.id_promotion = p.id_promotion
                    JOIN Reservation r2 ON r2.id_vol = p.id_vol
                    WHERE r2.id_reservation = r.id_reservation
                    AND ps.id_Type_siege = rc.id_Type_siege
                    LIMIT 1
                )) * rc.nombre
            ELSE 
                (vs.prix + (fc.facteur/100 * vs.prix)) * rc.nombre
        END
    ), 0) INTO v_total_price
    FROM Reservation r
    JOIN Reservation_classe rc ON r.id_reservation = rc.id_reservation
    JOIN Facteur_classe fc ON rc.id_classe = fc.id_classe
    JOIN vol_siege vs ON vs.id_vol = r.id_vol AND vs.id_Type_siege = rc.id_Type_siege
    WHERE r.id_reservation = v_id_reservation;
    
    UPDATE Reservation
    SET prix = v_total_price
    WHERE id_reservation = v_id_reservation;
    
    IF v_total_price IS NULL THEN
        RAISE WARNING 'Incapable de calculer le prix pour la réservation (ID: %)', v_id_reservation;
        UPDATE Reservation
        SET prix = 0
        WHERE id_reservation = v_id_reservation;
    END IF;
    
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_update_reservation_price_on_delete
AFTER DELETE ON Reservation_classe
FOR EACH ROW
EXECUTE FUNCTION update_reservation_price_on_delete();