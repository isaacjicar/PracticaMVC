insert into role (name,description)
    values('Admin','Acceso total')
on duplicate key update description = values(description);

insert into role(name,description)
    values('Operator', 'Operador')
on duplicate key update description = values(description);