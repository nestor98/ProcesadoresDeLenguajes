%-------------------------------------------------------------------
programa adivinarMal;
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

%-------------------------------------------------------------------
accion pedirletra;
%-------------------------------------------------------------------

 caracter c,ln;

 principio
  c := " ";
  mq (c < "A") or (c > "Z")
    escribir("letra:");
    leer(c, ln);
  fmq
  respuesta := c;
 fin

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
