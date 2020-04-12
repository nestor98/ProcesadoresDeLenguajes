%-------------------------------------------------------------------
programa sintactico1; % como adivinar, pero con muchos errores sintacticos
%-------------------------------------------------------------------

 caracter respuesta, letra, min, max % falta el ;
 booleano headivinado;
 caracter c, d;
 


%-------------------------------------------------------------------
accion siono estonoesvalido; % no puede tener un parametro sin parentesis
%-------------------------------------------------------------------
entero a;
 caracter c ln; % los identificadores se separan por ',', no ' '

principio 
  c := " ";
  mq (c <> "S") and (c <> "N")
    escribir("(S/N)?");
    leer(c, ln);
   fin%fin en lugar de fmq
  respuesta := c;


fin

principio
siono; % programa minimo correcto
fin