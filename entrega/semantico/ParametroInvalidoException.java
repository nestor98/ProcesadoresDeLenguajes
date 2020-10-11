package semantico;

import simbolos.Simbolo;

// Cuando se intenta invocar una accion pasando por referencia 
// un parametro declarado por valor
public class ParametroInvalidoException extends Exception {
  // <accion> la accion, <parametro> el parametro declarado por valor
  // <i> el indice del parametro en cuestion
  public ParametroInvalidoException(Simbolo accion, Simbolo parametro) {
    super(parametro.getNombre() + " ha sido pasado por valor. " + accion.getNombre() 
    + "(" + accion.tipo_parametros() + ")");
  }
  
  // Otros usos, como acciones pasadas como parametro
  public ParametroInvalidoException(String mensaje) {
    super(mensaje);
  }
}
