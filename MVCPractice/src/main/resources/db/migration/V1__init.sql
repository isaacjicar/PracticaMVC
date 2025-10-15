
CREATE TABLE person (
        id         int PRIMARY KEY AUTO_INCREMENT,
        name       VARCHAR(80)  NOT NULL,
        last_name  VARCHAR(120) NOT NULL,
        email      VARCHAR(220) NOT NULL,
        number     VARCHAR(30)  NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        CONSTRAINT uq_person_email UNIQUE (email)
) ;

CREATE TABLE role (
          id          int PRIMARY KEY AUTO_INCREMENT,
          name        VARCHAR(100) NOT NULL,
          description VARCHAR(200) NOT NULL,
          created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
          updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          CONSTRAINT uq_role_name UNIQUE (name)
) ;

CREATE TABLE person_role (
         id         int PRIMARY KEY AUTO_INCREMENT,
         person_id  int NOT NULL,
         role_id    int NOT NULL,
         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
         CONSTRAINT fk_pr_person  FOREIGN KEY (person_id) REFERENCES person(id)
             ON UPDATE CASCADE ON DELETE CASCADE,
         CONSTRAINT fk_pr_role    FOREIGN KEY (role_id)   REFERENCES role(id)
             ON UPDATE CASCADE ON DELETE RESTRICT,
         CONSTRAINT uq_person_role UNIQUE (person_id, role_id)
) ;

