package simbolos;

import simbolos.Simbolo;

public class SimboloNoAsignableException extends Exception {
  // No son asignables ni las acciones ni los parametros por valor
  public SimboloNoAsignableException(Simbolo s) {
    super(s.getNombre() + (s.es_accion() ? " es una accion" : " es un parametro por valor"));
  }
}
