CREATE DEFINER=`root`@`localhost` PROCEDURE `listado_parcial_clientes`(IN in_dni CHAR(9), INOUT inout_long INT)
BEGIN
	DECLARE apell VARCHAR(32) DEFAULT NULL;
    SELECT apellidos FROM CLIENTES where dni = in_dni INTO apell;
    SET inout_long = inout_long + length(apell);
    select DNI, apellidos from clientes
		where apellidos <= apell order by APELLIDOS;
end