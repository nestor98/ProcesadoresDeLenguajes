package semantico;

import simbolos.Simbolo;

// Accion invocada con el numero incorrecto de parametros
public class AccionMalInvocadaException extends Exception {
  // <accion> la accion, <n> el numero de parametros con el que se ha invocado
  public AccionMalInvocadaException(Simbolo accion, int n) {
    super(accion.getNombre() + " tiene " + accion.n_parametros() + " parametros, no " + n); 
  }
 
}
