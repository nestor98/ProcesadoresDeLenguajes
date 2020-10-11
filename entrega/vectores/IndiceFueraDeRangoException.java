package vectores;

public class IndiceFueraDeRangoException extends Exception {
  // No son asignables ni las acciones ni los parametros por valor
  public IndiceFueraDeRangoException(int MAX, int indice) {
    super("Indice (" + indice + ") debe estar entre 0 y " + (MAX-1));
  }
}
