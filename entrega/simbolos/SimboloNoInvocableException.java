package simbolos;

import simbolos.Simbolo;

public class SimboloNoInvocableException extends Exception {

  public SimboloNoInvocableException(Simbolo s) {
    super(s.getNombre() + " no es una accion");
  }
  
  /*
  public String getNombre() {
    return this.nombre;
  }
  */
}
