%-------------------------------------------------------------------
programa sintactico1; % como adivinar, pero con muchos errores sintacticos
%-------------------------------------------------------------------

 caracter respuesta, letra, min, max % falta el ;
 booleano headivinado;
 caracter c, d;
 
; % sentencia vacia, error sintactico

%-------------------------------------------------------------------
accion siono estonoesvalido; % no puede tener un parametro sin parentesis
%-------------------------------------------------------------------

 caracter c; ln; % los identificadores se separan por ',', no ';'

 % falta principio 
  c := " ";
  mq (c <> "S") and (c <> "N")
    escribir("(S/N)?");
    leer(c, ln);
  fin % fin en vez de fmq
  respuesta := c;
 fin

%-------------------------------------------------------------------
accion pedirletra;
%-------------------------------------------------------------------

 caracter c,ln;

 principio
  c = " "; % '=' en lugar de ':='
  mq (c < "A") or (c > "Z" % falta parentesis al final
    escribir("letra:");
    leer(c, ln);
  fmq
  respuesta := c;
 %fin
 % falta fin
%-------------------------------------------------------------------
principio
%-------------------------------------------------------------------
 escribir("Piensa en una letra e intentare adivinarla.", 
          entacar(13),entacar(10));
 escribir("Listo?");
 siono;
 min := "A";
 max := "Z";
 headivinado := false;
 mq (min <> max) and not headivinado
   letra := entacar((caraent(min) + caraent(max)) div 2);
   escribir("(", min, ",", max, ")",": has pensado en la letra ", letra, "?");
   siono;
   si (respuesta = "N") ent
     escribir("La letra que has pensado es mayor?");
     siono;
     si respuesta = "S" ent
      min := entacar(caraent(letra) + 1);
     si_no
      max := entacar(caraent(letra) - 1);
    fsi
   si_no
    headivinado := true;
   fsi
 fmq
 si not headivinado ent
  escribir("La letra es la ", min, entacar(13), entacar (10));
 fsi
fin
