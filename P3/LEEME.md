# Especificación del compilador de Minileng
### Procesadores de Lenguajes

Néstor Monzón (735418)
2019-2020   

## Analizador léxico
Se ha seguido la especificación del guión de la práctica 2. Sus principales características son las siguientes:
- No distingue entre mayúsculas y minúsculas en ningún caso.
- Existen una serie de palabras reservadas que tienen preferencia sobre los identificadores definidos por el usuario.
  Entre estas palabras, se encuentran "programa"; "acción"; los tipos de datos ("entero", "caracter" y "booleano"); las directivas
  predefinidas "mq" (mientras que) y su correspondiente "fmq"; "si", "ent" (entonces), "si_no", "fsi"; y las cuatro funciones predefinidas:
  "escribir", "leer", "entacar" y "caraent".
- Los identificadores son una cadena de letras, barras bajas y números, pero no pueden empezar por números ni acabar por barras bajas.
- Solo se permiten cadenas de caracteres como argumentos para la función "escribir".
- Tanto las cadenas de caracteres como los caracteres simples se definen con comillas dobles alrededor.
- Los valores de tipo booleano son "true" y "false" (sin las comillas).
- Los valores enteros, aunque para el lenguaje pueden ser positivos o negativos, desde el punto de vista del analizador léxico solo pueden ser
  positivos. En la práctica, el analizador semántico se ocupará de convertirlos en negativos, analizando el símbolo "-" como un operador.
- Además, el lenguaje cuenta con una serie de operadores, aritméticos, lógicos y de asignación y comparación.
- Permite la utilización de comentarios de una línea, comenzados por "%". Se permite empezar un comentario en cualquier posición de la línea
  ("%" no tiene por qué ser el primer carácter).

## Analizador sintáctico
La estructura de todo programa es la siguiente:
```
programa minimo; % identificador

% declaraciones de variables

% declaraciones de acciones

principio
% sentencias, al menos una
fin
```
Las acciones pueden definirse con parámetros por valor o referencia. Si una accion no tiene parámetros se pueden omitir los paréntesis. Así,
las dos expresiones siguientes son equivalentes:
```
accion a;
accion a();
```

Las acciones siguen la misma estructura que los programas, y también permiten la definición de más acciones anidadas.
Todos los bloques de sentencias (entre principio y fin) deben contener al menos una sentencia.

La gramática se ha construido de forma que los operadores multiplicativos (como "and", mod, etc.) tienen preferencia sobre los aditivos
(como "+", "or", etc.), y estos a su vez sobre los relacionales (comparaciones, como <>, =, <, etc.). La gramática permite modificar la
preferencia utilizando paréntesis, como es común en otros lenguajes de programación.

En cuanto a los errores, se ha dividido la gramática lo máximo posible para identificar las causas de la mayoría de los errores más comunes.
Así, el compilador no solo dice el símbolo en el que ha detectado el error sintáctico, sino que muestra el que esperaba.

Se ha implementado un panic mode para el error en el que falta un ';' (al detectar que falta uno, ignora la entrada hasta el siguiente o el final
del fichero), aunque es aplicable para cualquier otro token. Podría resultar interesante también para los distintos marcadores de fin de bloque
("fmq", "fsi", "fin").
