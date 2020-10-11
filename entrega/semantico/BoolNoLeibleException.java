package semantico;

import simbolos.Simbolo;

// La funcion leer no acepta booleanos
public class BoolNoLeibleException extends Exception {
  public BoolNoLeibleException(Simbolo parametro) {
    super("leer no acepta booleanos (" + parametro.getNombre() + ")");
  }
  
}
