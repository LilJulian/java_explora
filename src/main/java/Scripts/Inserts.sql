select * from usuarios;
select * from roles;
select * from permisos;
select * from permisos_roles;

select * from estados;
select * from tipo_transportes;
select * from transportes;

select * from ciudades;
select * from ruta_ciudad;

SELECT c.id, c.nombre, c.id_pais, p.nombre AS paisNombre 
                 FROM ciudades c 
                 INNER JOIN paises p ON c.id_pais = p.id order by c.id asc;
select * from paises;


ALTER TABLE estados AUTO_INCREMENT = 1;
insert into permisos_roles (id_rol, id_permiso) values (1,1), (1,2), (1,3), (1,4), (1,5), (1,6), (1,7);

INSERT INTO estados (estado) VALUES (TRUE);   -- Activo
INSERT INTO estados (estado) VALUES (FALSE);  -- Inactivo

delete from estados;

INSERT INTO tipo_transportes (nombre) values ('terrestre'), ('acuatico'), ('aereo');



set foreign_key_checks = 0;
set foreign_key_checks = 1;

SELECT p.nombre FROM permisos p 
                     JOIN permisos_roles pr ON p.id = pr.id_permiso 
                     JOIN roles r ON pr.id_rol = r.id 
                     JOIN usuarios u ON u.id_rol = r.id 
                     WHERE u.id = 12;
                     
                     -- Insertar un estado activo (true / 1)
INSERT INTO estados (estado) VALUES (true);

-- Insertar un estado inactivo (false / 0)
INSERT INTO estados (estado) VALUES (false);


#Superadmin
#julian@example.com
#123456

#Cliente
#cliente@correo.com
#COntra#seg1


