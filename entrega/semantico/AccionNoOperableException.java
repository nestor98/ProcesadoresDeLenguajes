package semantico;

import simbolos.Simbolo;

// Accion invocada con el numero incorrecto de parametros
public class AccionNoOperableException extends Exception {
  // Las acciones no pueden formar parte de una expresion
  public AccionNoOperableException() {
    super("No se pueden usar acciones en operaciones, solo invocarlas"); 
  }
  
}
