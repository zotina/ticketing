CREATE OR REPLACE FUNCTION update_reservation_price()
RETURNS TRIGGER AS $$
DECLARE
    promo_applied BOOLEAN := FALSE;
    promo_percent NUMERIC(15,2);
    promo_seat_limit INTEGER;
    base_price NUMERIC(15,2);
    reservation_count INTEGER;
BEGIN
    SELECT vs.prix INTO base_price
    FROM vol_siege vs
    WHERE vs.id_Type_siege = NEW.id_Type_siege AND vs.id_vol = NEW.id_vol;

    SELECT ps.promotion, ps.nbr_siege
    INTO promo_percent, promo_seat_limit
    FROM promotion_siege ps
    JOIN Promotion p ON ps.id_promotion = p.id_promotion
    WHERE p.id_vol = NEW.id_vol AND ps.id_Type_siege = NEW.id_Type_siege
    LIMIT 1;

    IF FOUND THEN
        SELECT COUNT(*) INTO reservation_count
        FROM (
            SELECT r.id_reservation,
                   ROW_NUMBER() OVER (PARTITION BY r.id_Type_siege, r.id_vol ORDER BY r.date_reservation ASC) AS row_num
            FROM Reservation r
            JOIN Promotion p ON r.id_vol = p.id_vol
            WHERE r.id_Type_siege = NEW.id_Type_siege AND r.id_vol = NEW.id_vol
        ) subquery
        WHERE row_num <= promo_seat_limit;

        IF reservation_count < promo_seat_limit THEN
            promo_applied := TRUE;
        END IF;
    END IF;

    IF promo_applied THEN
        NEW.prix := base_price - (promo_percent / 100 * base_price);
    ELSE
        NEW.prix := base_price;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_reservation_price
BEFORE INSERT ON Reservation
FOR EACH ROW
EXECUTE FUNCTION update_reservation_price();


CREATE OR REPLACE FUNCTION get_promotion_vol_siege(
    id_vol_param VARCHAR, 
    id_Type_siege_param VARCHAR
)
RETURNS INTEGER AS $$
DECLARE
    reservation_count INTEGER DEFAULT 0;
    promo_seat_limit INTEGER;
BEGIN
    SELECT COALESCE(MAX(ps.nbr_siege), 0) INTO promo_seat_limit
    FROM promotion_siege ps
    JOIN Promotion p ON ps.id_promotion = p.id_promotion
    WHERE p.id_vol = id_vol_param AND ps.id_Type_siege = id_Type_siege_param;

    SELECT COUNT(*) INTO reservation_count
    FROM (
        SELECT r.id_reservation,
               ROW_NUMBER() OVER (PARTITION BY r.id_Type_siege, r.id_vol ORDER BY r.date_reservation ASC) AS row_num
        FROM Reservation r
        WHERE r.id_Type_siege = id_Type_siege_param AND r.id_vol = id_vol_param
    ) subquery
    WHERE row_num <= promo_seat_limit;

    RETURN reservation_count;
END;
$$ LANGUAGE plpgsql;

SELECT get_promotion_vol_siege('1','1');