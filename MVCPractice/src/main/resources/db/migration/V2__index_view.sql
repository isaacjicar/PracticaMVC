
CREATE INDEX ix_person_name_last_name ON person (name, last_name);
CREATE INDEX ix_pr_person ON person_role (person_id);
CREATE INDEX ix_pr_role   ON person_role (role_id);


CREATE OR REPLACE VIEW vw_person_and_role AS
SELECT
    p.id,
    p.name,
    p.last_name,
    p.email,
    p.number,
    COALESCE(GROUP_CONCAT(r.name ORDER BY r.name SEPARATOR ', '), '') AS roles
FROM person p
         LEFT JOIN person_role pr ON pr.person_id = p.id
         LEFT JOIN role r         ON r.id = pr.role_id
GROUP BY p.id, p.name, p.last_name, p.email, p.number;
