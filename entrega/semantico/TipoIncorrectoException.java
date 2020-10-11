package semantico;

import expresiones.RegistroExpr;
import expresiones.RegistroExpr.Operador;
import expresiones.RegistroExpr.OperadorRelacional;
import simbolos.Simbolo;
import simbolos.Simbolo.tipo_var;

public class TipoIncorrectoException extends Exception {
  // Para casos concretos
  public TipoIncorrectoException(String mensaje) {
    super(mensaje);
  }
  
  // Para dos simbolos de distinto tipo
  public TipoIncorrectoException(Simbolo s1, Simbolo s2) {
    super(s1.nombreYTipo() + " y " + s2.nombreYTipo() + " deben concordar");
  }
  
  // Para dos expresiones de distinto tipo
  public TipoIncorrectoException(RegistroExpr e1, RegistroExpr e2) {
    super("No se puede operar con " + e1.getTipo() + " y " + e2.getTipo());
  }
  
 // Operacion no compatible con el tipo de la expresion
 public TipoIncorrectoException(RegistroExpr e1, Operador op) {
   super("La operacion " + op + " no es compatible con el tipo " + e1.getTipo());
 }
 
  // Comparacion (op relacional) no compatible con el tipo de la expresion
  public TipoIncorrectoException(RegistroExpr e1, OperadorRelacional op) {
   super("La comparacion " + op + " no es compatible con el tipo " + e1.getTipo());
  }
  
  // i-esimo parametro de tipo incorrecto
  public TipoIncorrectoException(Simbolo accion, int i, tipo_var t1, tipo_var t2) {
   super("El parametro " + i + " de " + accion.getNombre() + " es " + t1 + ", no " + t2);
  }
}
