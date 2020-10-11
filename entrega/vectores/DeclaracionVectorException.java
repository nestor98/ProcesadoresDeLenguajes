package vectores;

public class DeclaracionVectorException extends Exception {
  // No son asignables ni las acciones ni los parametros por valor
  public DeclaracionVectorException() {
    super("Los vectores solo se pueden declarar con constantes enteras, no variables");
  }
}
