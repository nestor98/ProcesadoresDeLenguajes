%-----------------------------------------------------------
PROGRAMA sintactico5;

% Tras introducir un numero devuelve por pantalla su factorial
%-----------------------------------------------------------


entero n, fact;    
    
%-----------------------------------------------------------
ACCION factorial(val entero n);
% asigna a fact el factorial de n
       principio
           si (((((n<2))))) % da igual el numero de parentesis
              ent    fact := 1;
              si_no  
              	factorial(n-1);
              	fact := n*fact;
              %fsi % Se puede probar un fsi de mas
           fsi
       fin
       
%------------------------------------------

PRINCIPIO
     escribir("Introduce un entero:");
     leer(n);
     factorial(n);
     escribir("El factorial de ", n, " es ", entacar(fact)); 
     % la funcion escribir acepta n por ser una variable. Con el semántico se definirá si 
     % habrá conversión implícita de tipos o no
FIN
