%-------------------------------------------------------------------
programa sintactico2; % programa demasiado corto
%-------------------------------------------------------------------
%-------------------------------------------------------------------
accion pedirletra;
%-------------------------------------------------------------------

 caracter c,ln;

 principio
  c := " "; % '=' en lugar de ':='
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
   escribir("("; min, ",", max, ")",": has pensado en la letra ", letra, "?");
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





principio
% no se permiten programas vacíos
%fin % y falta el fin





