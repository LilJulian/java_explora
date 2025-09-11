#Tablas relacionadas con los usuarios y sus capacidades

CREATE TABLE permisos (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre varchar(100),
    descripcion VARCHAR(255)
);

drop table permisos;

CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

drop table roles;

CREATE TABLE permisos_roles(
	id int auto_increment primary key,
	id_rol int,
    id_permiso int,    
    FOREIGN KEY (id_rol) REFERENCES roles(id),
    FOREIGN KEY (id_permiso) REFERENCES permisos(id)
);

drop table permisos_roles;

CREATE TABLE usuarios (
	id int auto_increment primary key,
    nombre varchar(50) not null,
    correo varchar(50) not null,
    telefono varchar(10),
    contrasena varchar(255),
    id_rol int not null,
    foreign key (id_rol) references roles(id)
);

drop table usuarios;

#Tabla para verificar estados(activos o inactivos)

create table estados ( #(activo, inactivo / (true, false)
	id int auto_increment primary key,
    estado boolean default true
);

drop table estados;

# Tablas para crear un viaje

CREATE TABLE tipo_transportes ( #(terrestre, aereo, acuatico)
	id int auto_increment primary key,
    nombre varchar(50) not null
);

drop table tipo_transportes;

CREATE TABLE transportes (
	id int auto_increment primary key,
    nombre varchar(50) not null,
    matricula varchar(8),
    asientos_totales int,
    descripcion text,
    id_estado int not null,
    id_tipo_transporte int not null,
    foreign key (id_estado) references estados(id),
    foreign key (id_tipo_transporte) references tipo_transportes(id)
);

drop table transportes;

create table paises(
	id int auto_increment primary key,
    nombre varchar(100)
);

drop table paises;

CREATE TABLE ciudades (
	id int auto_increment primary key,
    nombre varchar(100) not null,
    id_pais int not null,
    foreign key (id_pais) references paises(id)
);

ALTER TABLE ciudades
ADD COLUMN tiene_terminal BOOLEAN DEFAULT FALSE,
ADD COLUMN tiene_aeropuerto BOOLEAN DEFAULT FALSE,
ADD COLUMN tiene_puerto BOOLEAN DEFAULT FALSE;


drop table ciudades;

CREATE TABLE ruta_ciudad (
	id int auto_increment primary key,
    id_ciudad_origen int not null,
    id_ciudad_destino int not null,
    foreign key (id_ciudad_origen) references ciudades(id),
    foreign key (id_ciudad_destino) references ciudades(id)
);

drop table ruta_ciudad;

#Tabla del viaje

CREATE TABLE viaje (
	id int auto_increment primary key,
    id_ciudades int not null,
    id_transporte int not null,
    fecha_salida datetime not null,
    fecha_llegada datetime not null,
    fecha_vuelta datetime,
    precio_unitario decimal (10,2) not null,
    asientos_disponibles int not null,
    foreign key (id_ciudades) references ruta_ciudad(id),
    foreign key (transporte) references transportes(id)
);

drop table viaje;

#Tablas de reservacion de un cliente

CREATE TABLE tipo_reservas ( #(ida, ida y vuelta)
	id int auto_increment primary key,
    nombre varchar(50)
);

drop table tipo_reservas;

CREATE TABLE estado_reserva ( #(pendiente, confirmada, cancelada)
	id int auto_increment primary key,
    nombre varchar(50)
);

drop table estado_reserva;

CREATE TABLE reservas (
	id int auto_increment primary key,
    id_usuario int not null,
    id_viaje int not null,
    id_tipo_reserva int not null,
    fecha_reserva datetime default current_timestamp not null,
    cantidad_personas int not null,
    precio_total decimal(10,2) not null,
    id_estado int not null,
    foreign key (id_usuario) references usuarios(id),
    foreign key (id_tipo_reserva) references tipo_reservas(id),
    foreign key (id_estado) references estado_reserva(id),
    foreign key (id_viaje) references viaje(id)
);

drop table reservas;

CREATE TABLE ticket (
	id int auto_increment primary key,
    id_reserva int not null,
    nombre varchar(100),
    tipo_documento varchar(30),
    documento varchar(20),
    comentarios text,
    asiento varchar(3),
    foreign key (id_reserva) references reservas(id)
);

drop table ticket;