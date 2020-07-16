drop table aeropuerto cascade constraint;
drop table usuario cascade constraint; 
drop table vuelo cascade constraint; 
drop table reserva cascade constraint; 
drop table listaPasajeros cascade constraint; 
drop table pagos cascade constraint; 

drop sequence seq_reservas;
PROMPT Sequences for reservas
create sequence seq_reservas start with 1 increment by 1;

drop sequence seq_listaPasajeros;
PROMPT Sequences for pasajeros
create sequence seq_listaPasajeros start with 1 increment by 1;

drop sequence seq_pagos;
PROMPT Sequences for seq_pagos
create sequence seq_pagos start with 1 increment by 1;

CREATE TABLE aeropuerto(
nombre varchar(50),
codigo varchar(50) primary key,
telefono int,
correo varchar(50),
direccion varchar(50));
 
CREATE TABLE usuario(
nombre VARCHAR(50),
apellido1 VARCHAR(50),
apellido2 VARCHAR(50),
cedula VARCHAR(50) primary key,
telefono VARCHAR(50),
direccion VARCHAR(50),
nacionalidad VARCHAR(50),
fechaNacimiento date,
correo VARCHAR(50),
clave VARCHAR(50),
estado_civil VARCHAR(50),
edad int,
perfil varchar(50));

CREATE TABLE vuelo(
codigo VARCHAR(50)  primary key,
origen VARCHAR(50),
destino VARCHAR(50),
hora_salida VARCHAR(50) ,
hora_llegada VARCHAR(50),
num_escalas VARCHAR(50), 
comida VARCHAR(50),
duracion integer,
avion VARCHAR(50),
fecha_salida varchar(50),
contador_pasajeros integer,
numeroMaximoPasajeros integer,
precio integer);

create table listaPasajeros(
codigo varchar(50),
nombre    varchar(50),
apellido1 varchar(50),
apellido2 varchar(50),
cedula  varchar(50),
idVuelo varchar(50),
idReserva   varchar(50));

alter table listaPasajeros add constraint
id_vueloLista_fk foreign key (idVuelo) references vuelo(codigo);
 
  
CREATE TABLE reserva(
codigo VARCHAR(50)  primary key,
idVuelo VARCHAR(50),
num_pasajeros integer,
id_usuario VARCHAR(50),
contador_pasajeros  integer);


alter table reserva add constraint
id_vuelo_fk foreign key (idVuelo) references vuelo(codigo);

alter table reserva add constraint
id_usuario_fk foreign key (id_usuario) references usuario(cedula);
create table pagos(
codigo varchar(50),
idReserva    varchar(50),
idVuelo varchar(50),
idUsuario varchar(50),
montoTotal varchar(50),
fecha varchar(50));


alter table pagos add constraint
idVuelo_fk foreign key (idVuelo) references vuelo(codigo);

alter table pagos add constraint
idUsuario_fk foreign key (idUsuario) references usuario(cedula);


alter table pagos add constraint
idReserva_fk foreign key (idReserva) references reserva(codigo);

 
 
ALTER TABLE usuario add constraint usuario_ck_perfil check ( perfil in ('Administrador', 'Cliente') );
ALTER TABLE vuelo add constraint usuario_ck_comida check ( comida in ('SI', 'NO') );


CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

-------------------------------------------- FUNCIONES Y PROCEDIMIENTOS LISTA DE PASAJEROS---------------------------------------------------------------------------------------------

create or replace trigger
listaPasajeros_trg_bir before insert on listaPasajeros
referencing old as old new as new
for each row
declare
  contador number;
  contador2 number;
begin
  --VMontoSalario := fun_sal_emp(:new.employee_id);
  select  reserva.contador_pasajeros into    contador from    reserva where   reserva.codigo = :new.idReserva;
   select  reserva.num_pasajeros into    contador2 from    reserva where   reserva.codigo = :new.idReserva;

  if contador = contador2 then
    raise_application_error(-20001, 'Ha excedido cantidad de pasajeros dentro de la reserva');
   else 
    update reserva set reserva.contador_pasajeros=reserva.contador_pasajeros+1 where reserva.codigo=:new.idReserva;
  end if;
end reserva_trg_bir;
/
show error

CREATE OR REPLACE PROCEDURE insertarListaPasajeros(nomb IN listaPasajeros.nombre%TYPE,
 ape1 IN listaPasajeros.apellido1%TYPE,ape2 IN listaPasajeros.apellido2%TYPE,ced IN listaPasajeros.cedula%TYPE,vueloP IN listaPasajeros.idVuelo%TYPE,reservaV IN listaPasajeros.idReserva%TYPE)
AS
BEGIN
	INSERT INTO listaPasajeros VALUES(seq_listaPasajeros.nextval,nomb,ape1,ape2,ced,vueloP,reservaV);
END;
/
show error
 
 CREATE OR REPLACE FUNCTION listarListaPasajeros
RETURN Types.ref_cursor 
AS 
        lista_cursor types.ref_cursor; 
BEGIN 
  OPEN lista_cursor FOR 
       SELECT  codigo, nombre , apellido1 , apellido2,cedula,idVuelo   FROM listaPasajeros ; 
RETURN lista_cursor; 
END;
/

----------------------------------------------FUNCIONES Y PROCEDIMIENTOS PAGOS -----------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE insertarPago(reserva IN pagos.idReserva%TYPE,usuario IN pagos.idUsuario%TYPE,
 vuelo IN pagos.idVuelo%TYPE,pago IN pagos.montoTotal%TYPE,fecha IN pagos.fecha%TYPE)
AS
BEGIN
	INSERT INTO pagos VALUES(seq_pagos.nextval,reserva,vuelo,usuario,pago,fecha);
END;
/
show error

CREATE OR REPLACE FUNCTION buscarPago(ced IN pagos.idUsuario%TYPE)
RETURN Types.ref_cursor 
AS 
        pagos_cursor types.ref_cursor; 
BEGIN 
  OPEN pagos_cursor FOR 
       SELECT  codigo, idUsuario,idReserva,idVuelo,fecha,montoTotal   FROM pagos WHERE idUsuario=ced ; 
RETURN pagos_cursor; 
END;
/
-------------------------------------------- FUNCIONES Y PROCEDIMIENTOS RESERVA---------------------------------------------------------------------------------------------

create or replace trigger
reserva_trg_bir before insert on reserva
referencing old as old new as new
for each row
declare
  cantidadP number;
begin
  --VMontoSalario := fun_sal_emp(:new.employee_id);
  select  vuelo.contador_pasajeros into    cantidadP from    vuelo where   vuelo.codigo = :new.idVuelo;
  
  if cantidadP-:new.num_pasajeros < 0 then
    raise_application_error(-20001, 'No hay cupo disponible en el vuelo');
   else 
    update vuelo set vuelo.contador_pasajeros=vuelo.contador_pasajeros- :new.num_pasajeros;
  end if;
end reserva_trg_bir;
/
show error

CREATE OR REPLACE PROCEDURE insertarReserva(cod IN reserva.codigo%TYPE,idV IN reserva.idVuelo%TYPE,
 numP IN reserva.num_pasajeros%TYPE,idU IN reserva.id_usuario%TYPE)
AS
BEGIN
	INSERT INTO reserva VALUES(seq_reservas.nextval,idV,numP,idU,0);
END;
/
show error

CREATE OR REPLACE FUNCTION buscarReserva(cod IN reserva.codigo%TYPE )
RETURN Types.ref_cursor 
AS 
        reserva_cursor types.ref_cursor; 
BEGIN 
  OPEN reserva_cursor FOR 
       SELECT  codigo, idVuelo , id_usuario , num_pasajeros   FROM reserva WHERE codigo=cod; 
RETURN reserva_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listarReserva
RETURN Types.ref_cursor 
AS 
        reserva_cursor types.ref_cursor; 
BEGIN 
  OPEN reserva_cursor FOR 
       SELECT  codigo, idVuelo , id_usuario , num_pasajeros   FROM reserva ; 
RETURN reserva_cursor; 
END;
/

CREATE OR REPLACE FUNCTION obtenerNumeroPasajerosReserva(ced IN reserva.id_usuario%TYPE )
RETURN VARCHAR 
AS 
        numP integer; 
BEGIN 
 	 SELECT num_pasajeros into numP FROM reserva WHERE id_usuario=ced;
	 RETURN numP;                                                         
END;
/         

CREATE OR REPLACE FUNCTION obtenerCodigoPasajerosReserva(ced IN reserva.id_usuario%TYPE )
RETURN VARCHAR 
AS 
        numP varchar(50); 
BEGIN 
 	 SELECT codigo into numP FROM reserva WHERE id_usuario=ced;
	 RETURN numP;                                                         
END;
/         
 ------------------------------------------------- FUNCIONES Y PROCEDIMIENTOS AEROPUERTO ------------------------------------------------------
CREATE OR REPLACE PROCEDURE insertarAeropuerto(nomb IN aeropuerto.nombre%TYPE,cod IN aeropuerto.codigo%TYPE,
 tel IN aeropuerto.telefono%TYPE,email IN aeropuerto.correo%TYPE ,
 dir IN aeropuerto.direccion%TYPE )
AS
BEGIN
	INSERT INTO aeropuerto VALUES(nomb,cod,tel,email, dir);
END;
/
show error
CREATE OR REPLACE PROCEDURE modificarAeropuerto (nomb IN aeropuerto.nombre%TYPE,cod IN aeropuerto.codigo%TYPE,
 tel IN aeropuerto.telefono%TYPE,email IN aeropuerto.correo%TYPE ,
 dir IN aeropuerto.direccion%TYPE )

AS
BEGIN
      IF  cod IS NOT NULL THEN
	  
--IF nomb IS NOT NULL THEN 	  LA CEDULA NO SE PUEDE MODIFICAR PORQUE ES PK!!!!!!!!!!!!!!!!!!!!!
 --END IF;
IF nomb IS NOT NULL THEN 	  
UPDATE aeropuerto SET nombre=nomb  WHERE codigo=cod;
END IF; 
IF tel IS NOT NULL THEN 	  
UPDATE aeropuerto SET telefono=tel  WHERE codigo=cod;
END IF;
IF dir IS NOT NULL THEN 	  
UPDATE aeropuerto SET direccion=dir  WHERE codigo=cod;
END IF;
IF email IS NOT NULL THEN 	  
UPDATE aeropuerto SET correo=email  WHERE codigo=cod;
END IF; 
     END IF;
END;
/ 
show error;

CREATE OR REPLACE FUNCTION buscarAeropuerto(nomb IN aeropuerto.nombre%TYPE , cod IN aeropuerto.codigo%TYPE)
RETURN Types.ref_cursor 
AS 
        aeropuerto_cursor types.ref_cursor; 
BEGIN 
  OPEN aeropuerto_cursor FOR 
       SELECT  nombre, codigo , telefono , direccion, correo   FROM aeropuerto WHERE codigo=cod and nombre=nomb ; 
RETURN aeropuerto_cursor; 
END;
/

CREATE OR REPLACE FUNCTION listarAeropuerto
RETURN Types.ref_cursor 
AS 
        aeropuerto_cursor types.ref_cursor; 
BEGIN 
  OPEN aeropuerto_cursor FOR 
      SELECT nombre, codigo , telefono , direccion, correo FROM aeropuerto ; 
RETURN aeropuerto_cursor; 
END;
/

create or replace procedure eliminarAeropuerto(id IN aeropuerto.codigo%TYPE)
as
begin
    delete from aeropuerto where codigo=id;
end;
/
 
show error 

-------------------------------------------------------------------------funciones y procedimientos seguridad --------------------------------------
 
-------------------------------------------------------------------------funciones y procedimientos vuelo ------------------------------------------

 CREATE OR REPLACE PROCEDURE insertarVuelo(cod IN vuelo.codigo%TYPE,lugarOrigen IN vuelo.origen%TYPE,
 lugarDestino IN vuelo.destino%TYPE,horaLlegada IN vuelo.hora_llegada%TYPE,horaSalida IN vuelo.hora_salida%TYPE , escalas IN vuelo.num_escalas%TYPE,hayComida IN vuelo.comida%TYPE ,
 duracionV IN vuelo.duracion%TYPE,avionV IN vuelo.avion%TYPE
,dia IN vuelo.fecha_salida%TYPE,contP IN vuelo.contador_pasajeros%TYPE,numMax IN vuelo.numeroMaximoPasajeros%TYPE,prec IN vuelo.precio%TYPE )
AS
BEGIN
	INSERT INTO vuelo VALUES(cod,lugarOrigen,lugarDestino,horaSalida,horaLlegada, escalas,hayComida,duracionV,avionV,dia,contP,numMax,prec);
END;
/
show error

CREATE OR REPLACE PROCEDURE modificarVuelo(cod IN vuelo.codigo%TYPE,lugarOrigen IN vuelo.origen%TYPE,
 lugarDestino IN vuelo.destino%TYPE,horaLlegada IN vuelo.hora_llegada%TYPE,horaSalida IN vuelo.hora_salida%TYPE, escalas IN vuelo.num_escalas%TYPE,hayComida IN vuelo.comida%TYPE ,
 duracionV IN vuelo.duracion%TYPE,avionV IN vuelo.avion%TYPE
,dia IN vuelo.fecha_salida%TYPE,contP IN vuelo.contador_pasajeros%TYPE,numMax IN vuelo.numeroMaximoPasajeros%TYPE,prec IN vuelo.precio%TYPE )
 

AS
BEGIN
      IF  cod IS NOT NULL THEN
	  
--IF nomb IS NOT NULL THEN 	  LA CEDULA NO SE PUEDE MODIFICAR PORQUE ES PK!!!!!!!!!!!!!!!!!!!!!
 --END IF;
IF lugarOrigen IS NOT NULL THEN 	  
UPDATE vuelo SET origen=lugarOrigen  WHERE codigo=cod;
END IF; 
IF lugarDestino IS NOT NULL THEN 	  
UPDATE vuelo SET destino=lugarDestino  WHERE codigo=cod;
END IF;
IF horaSalida IS NOT NULL THEN 	  
UPDATE vuelo SET hora_salida=horaSalida  WHERE codigo=cod;
END IF; 
IF horaLlegada IS NOT NULL THEN 	  
UPDATE vuelo SET hora_llegada=horaLlegada  WHERE codigo=cod;
END IF;
IF escalas IS NOT NULL THEN 	  
UPDATE vuelo SET num_escalas=escalas  WHERE codigo=cod;
END IF; 
     END IF;
 

IF hayComida IS NOT NULL THEN 	  
UPDATE vuelo SET comida=hayComida  WHERE codigo=cod;
END IF; 
IF duracionV IS NOT NULL THEN 	  
UPDATE vuelo SET duracion=duracionV  WHERE codigo=cod;
END IF;
IF avionV IS NOT NULL THEN 	  
UPDATE vuelo SET AVION=avionV  WHERE codigo=cod;
END IF;
 IF dia IS NOT NULL THEN 	  
UPDATE vuelo SET fecha_salida=dia  WHERE codigo=cod;
END IF; 
IF numMax IS NOT NULL THEN 	  
UPDATE vuelo SET numeroMaximoPasajeros=numMax  WHERE codigo=cod;
END IF;
IF contP IS NOT NULL THEN 	  
UPDATE vuelo SET contador_pasajeros=contP  WHERE codigo=cod;
END IF;
IF prec IS NOT NULL THEN 	  
UPDATE vuelo SET precio=prec  WHERE codigo=cod;
END IF;
END;
/ 
show error;

CREATE OR REPLACE FUNCTION buscarVuelo(cod IN vuelo.codigo%TYPE , origenV IN vuelo.origen%TYPE, destinoV IN vuelo.destino%TYPE)
RETURN Types.ref_cursor 
AS 
       vuelo_cursor types.ref_cursor; 
BEGIN 
  OPEN vuelo_cursor FOR 
       SELECT  codigo,origen,destino,hora_salida,hora_llegada,num_escalas ,comida,duracion,avion ,fecha_salida, contador_pasajeros ,precio , numeroMaximoPasajeros FROM vuelo WHERE codigo=cod and destino=destinoV and origen=origenV; 
RETURN vuelo_cursor; 
END;
/
SHOW ERROR;

CREATE OR REPLACE FUNCTION buscarVueloReserva(origenV IN vuelo.origen%TYPE, destinoV IN vuelo.destino%TYPE,diaSalida IN vuelo.fecha_salida%TYPE, horaSalida IN vuelo.hora_salida%TYPE)
RETURN Types.ref_cursor 
AS 
       vuelo_cursor types.ref_cursor; 
BEGIN 
  OPEN vuelo_cursor FOR 
       SELECT  codigo,origen,destino,hora_salida,hora_llegada,num_escalas ,comida,duracion,avion,precio ,contador_pasajeros ,fecha_salida ,numeroMaximoPasajeros FROM vuelo WHERE  destino=destinoV and origen=origenV and fecha_salida=diaSalida and hora_salida=horaSalida; 
RETURN vuelo_cursor; 
END;
/
SHOW ERROR;

CREATE OR REPLACE FUNCTION listarVuelo
RETURN Types.ref_cursor 
AS 
        vuelo_cursor types.ref_cursor; 
BEGIN 
  OPEN vuelo_cursor FOR 
      SELECT  codigo,origen,destino,hora_salida,hora_llegada,num_escalas ,comida,duracion,avion ,precio, contador_pasajeros  , numeroMaximoPasajeros ,fecha_salida FROM vuelo ; 
RETURN vuelo_cursor; 
END;
/
show error 

create or replace procedure eliminarVuelo(id IN vuelo.codigo%TYPE)
as
begin
    delete from vuelo where codigo=id;
end;
/
 
show error 

-------------------------------------------------------------------------------------------------------------------------Funciones  y procedimientos Usuario

CREATE OR REPLACE PROCEDURE insertarUsuario (nomb IN usuario.nombre%TYPE , ape1 IN usuario.apellido1%TYPE,ape2 IN usuario.apellido2%TYPE,
                                             ced IN usuario.cedula%TYPE , tel IN usuario.telefono%TYPE,dir IN usuario.direccion%TYPE,
											 nacion IN usuario.nacionalidad%TYPE , fechaN IN usuario.fechaNacimiento%TYPE,email IN usuario.correo%TYPE,
											 clav IN usuario.clave%TYPE , estadoC IN usuario.estado_civil%TYPE,edadU IN usuario.edad%TYPE,
											 perf IN usuario.perfil%TYPE)
AS
BEGIN
	INSERT INTO Usuario VALUES(nomb,ape1,ape2,ced,tel,dir,nacion,fechaN,email,clav,estadoC,edadU,perf);
END;
/

 
---Login

 CREATE OR REPLACE FUNCTION login(email IN VARCHAR, clav IN VARCHAR)
RETURN Types.ref_cursor 
AS 
        n_cursor types.ref_cursor; 
BEGIN 
  OPEN n_cursor FOR 
	  SELECT COUNT(cedula) AS esta FROM usuario WHERE correo=email AND clave=clav;
	 RETURN n_cursor; 
END;
/

CREATE OR REPLACE FUNCTION obtenerPerfilUsuario(email IN VARCHAR, clav IN VARCHAR)
RETURN VARCHAR 
AS 
        rol VARCHAR(50); 
BEGIN 
 	 SELECT perfil into rol FROM usuario WHERE correo=email AND clave=clav;
	 RETURN rol;                                                         
END;
/                                                                                      
show error;          


-- exec insertarVuelo('1122','sjo','mia','14','12',0,'SI',120,'BOEING-W1','12/12/2019',10,10,1200);
--exec insertarVuelo('2233','sjo','mia','12','14',0,'SI',120,'BOEING-W1','12/12/2019',10,10,1200);

--exec insertarUsuario('Luis','Perez','Rojas','1212',22332222,'sjo , cr','Costarricense', to_date('01-02-1950', 'dd-mm-yyyy'),'@luis','123','soltero',60,'Cliente');
--exec insertarUsuario('Luis','Perez','Rojas','5050',22332222,'sjo , cr','Costarricense', to_date('01-02-1950', 'dd-mm-yyyy'),'@kevin','as123','soltero',60,'Administrador');
--exec insertarReserva('0','1144',3,'5050');

--exec insertarListaPasajeros("Luis","mora","perez","1122","1122","3");;
--exec  insertarUsuario('as','sd','dd','sss',22,'2','cr',TO_DATE('1980-12-10', 'yyyy-MM-dd'),'@se','123','s',12,'empleado');


--http://localhost:8081/BackEndProyectoFinalMoviles/ProyectoServlet?accion=insertarV&codigo=2223&origen=sjo&destino=mia&horaLlegada=12&horaSalida=10&comida=SI&escalas=1&avion=123&duracion=100&diaSalida=10/10/2019&contP=0&numMaxP=50