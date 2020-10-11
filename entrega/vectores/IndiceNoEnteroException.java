package vectores;

import expresiones.RegistroExpr;

public class IndiceNoEnteroException extends Exception {
  // No son asignables ni las acciones ni los parametros por valor
  public IndiceNoEnteroException(RegistroExpr exp) {
    super("El indice del vector debe ser entero, no " + exp.getTipo());
  }
}
