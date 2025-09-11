select * from usuarios;
select * from roles;
select * from permisos;
select * from permisos_roles;

select * from estados;
select * from tipo_transportes;
select * from transportes;

select * from ciudades;
select * from ruta_ciudad;

select * from viaje;
select * from tipo_reservas;
select * from reservas;
select * from estado_reserva;
select * from ticket;


insert into tipo_reservas(nombre) values ('ida'), ('ida y vuelta');
insert into estado_reserva(nombre) values ('pendiente'), ('confirmada'),('cancelada');
insert into roles(nombre) values ('superadministrador'), ('administrador'),('cliente');

describe viaje;

INSERT INTO estados (estado) VALUES (TRUE);   -- Activo
INSERT INTO estados (estado) VALUES (FALSE);  -- Inactivo

delete from estados;

INSERT INTO tipo_transportes (nombre) values ('terrestre'), ('acuatico'), ('aereo');
                     
                     -- Insertar un estado activo (true / 1)
INSERT INTO estados (estado) VALUES (true);

-- Insertar un estado inactivo (false / 0)
INSERT INTO estados (estado) VALUES (false);

delete from usuarios where id>2 ;
#Superadmin
#julian@example.com
#123456

#Cliente
#Cliente@gmail.com
#Contra#123

INSERT INTO permisos (nombre, descripcion) VALUES
('usuarios.index', 'Listar todos los usuarios'),
('usuarios.view', 'Listar un usuario'),
('usuarios.create', 'Crear un usuario'),
('usuarios.update', 'Editar un usuario'),
('usuarios.delete', 'Eliminar un usuario'),
('viajes.index', 'Listar todos los viajes'),
('superadmin.index', 'Ver menu de administracion'),
('ciudades.create', 'Crear una ciudad'),
('ciudades.index', 'Listar todas las ciudades'),
('ruta.create', 'Crear una ruta'),
('ruta.index', 'Listar las rutas'),
('transporte.index', 'Listar los transportes'),
('transporte.create', 'Crear los transportes'),
('viajes.create', 'Crear viajes'),
('reserva.create', 'Crear reservas'),
('reserva.index', 'Listar las reservas'),
('ticket.index', 'Listar los tickets');


INSERT INTO permisos_roles (id_rol, id_permiso) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(3, 6),
(2, 6),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(2, 6),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(2, 15),
(2, 16),
(2, 17),
(3, 6),
(3, 10),
(3, 11),
(2, 12),
(2, 13),
(2, 14),
(2, 15),
(2, 16),
(2, 17),
(2, 18),
(3, 6),
(3, 10),
(3, 11),
(3, 12),
(3, 13),
(3, 14),
(3, 15),
(3, 16),
(3, 17),
(3, 18);

