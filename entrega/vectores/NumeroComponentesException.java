package vectores;

import simbolos.Simbolo;

public class NumeroComponentesException extends Exception {
  // No son asignables ni las acciones ni los parametros por valor
  public NumeroComponentesException(Simbolo v1, Simbolo v2) {
    super(v1.getNombre() + " (" + v1.getComponentes() + ") y " 
          + v2.getNombre() + " (" + v2.getComponentes() 
          + ") deben tener el mismo numero de componentes");
  }
}
