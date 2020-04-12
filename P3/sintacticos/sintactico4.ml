%-------------------------------------------------------------------
programa sintactico4; % programa ilegible inutil
%-------------------------------------------------------------------

% se permiten acciones anidadas:
accion a1(val enteromal i); % entero mal escrito

	accion a2(ref caracter c); % ok
		
		
		accion a3;

			accion a4(valmal ent n); % val esta mal escrito

			principio
				a1(3); % ok para el sintactico, mal para el semantico (parametro no coincide)
				% tampoco se sabe si tendra acceso a esa accion
			fin
			
		principio % principio de accion3
			a4; % ok
		fin
	principio % de accion2
		a2("Solo se pueden pasar cadenas a escribir");
	fin
	
principio
	leer(1); % Error, no se puede asignar a una constante
fin	


principio
	a1;a2;a3;a4;esta_accion_no_existe(true); % ok para el sintactico, mal semanticamente
fin
		
		
	 
	
	
				