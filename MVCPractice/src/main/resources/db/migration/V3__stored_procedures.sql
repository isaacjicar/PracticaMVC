
DROP PROCEDURE IF EXISTS sp_upsert_person;
CREATE PROCEDURE sp_upsert_person(
    IN p_id INT,
    IN p_name VARCHAR(80),
    IN p_last_name VARCHAR(120),
    IN p_email VARCHAR(220),
    IN p_number VARCHAR(30)
)
BEGIN
    DECLARE v_exists_id INT;

START TRANSACTION;

IF p_id IS NULL THEN
        INSERT INTO person (name, last_name, email, number)
        VALUES (p_name, p_last_name, p_email, p_number);
ELSE
SELECT id INTO v_exists_id FROM person WHERE id = p_id FOR UPDATE;
IF v_exists_id IS NULL THEN
            ROLLBACK;
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La persona no existe';
END IF;

UPDATE person
SET name = p_name,
    last_name = p_last_name,
    email = p_email,
    number = p_number
WHERE id = p_id;
END IF;

COMMIT;
END;

DROP PROCEDURE IF EXISTS sp_assign_role;
CREATE PROCEDURE sp_assign_role(
    IN p_person_id INT,
    IN p_role_id INT
)
BEGIN
    DECLARE v_p INT;
    DECLARE v_r INT;
    DECLARE v_dup INT;

START TRANSACTION;

SELECT id INTO v_p FROM person WHERE id = p_person_id FOR UPDATE;
IF v_p IS NULL THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La persona no existe';
END IF;

SELECT id INTO v_r FROM role WHERE id = p_role_id FOR UPDATE;
IF v_r IS NULL THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El rol no existe';
END IF;

SELECT id INTO v_dup
FROM person_role
WHERE person_id = p_person_id AND role_id = p_role_id
    FOR UPDATE;

IF v_dup IS NOT NULL THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La persona ya tiene ese rol';
END IF;

INSERT INTO person_role (person_id, role_id)
VALUES (p_person_id, p_role_id);

COMMIT;
END;

DROP PROCEDURE IF EXISTS sp_remove_association;
CREATE PROCEDURE sp_remove_association(
    IN p_person_role_id INT
)
BEGIN
    DECLARE v_exists INT;

START TRANSACTION;

SELECT id INTO v_exists FROM person_role WHERE id = p_person_role_id FOR UPDATE;
IF v_exists IS NULL THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Asociación no existente';
END IF;

DELETE FROM person_role WHERE id = p_person_role_id;

COMMIT;
END;

DROP PROCEDURE IF EXISTS sp_search_person;
CREATE PROCEDURE sp_search_person(IN p_q VARCHAR(120))
BEGIN
SELECT p.*
FROM person p
WHERE p.name      LIKE CONCAT('%', p_q, '%')
   OR p.last_name LIKE CONCAT('%', p_q, '%')
   OR p.email     LIKE CONCAT('%', p_q, '%')
ORDER BY p.last_name, p.name;
END;