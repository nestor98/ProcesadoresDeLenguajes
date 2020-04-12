%-------------------------------------------------------------------
programa lexico2;
%-------------------------------------------------------------------


%-------------------------------------------------------------------
accion siono;
%-------------------------------------------------------------------

 caracter c:="bien para el lexico, mal para el sintactico";
 entero e;
 principio
  c := " ";
  e := "1"; % bien para lexico y sintactico, mal para el semantico (tipos)
  d := 'mal para todos' % no se usan comillas simples
  mq [c != "S"] and (c != "N") % operadores incorrectos
    escribir("(S/N)?");
    leer(c, ln);
  fmq
  respuesta := c;
 fin
 
% el resto no importa para el lexico 
