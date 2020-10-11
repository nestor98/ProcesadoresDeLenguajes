package semantico;



public class CondicionNoBoolException extends Exception {
  // No son asignables ni las acciones ni los parametros por valor
  public CondicionNoBoolException(String expresion) {
    super("La condicion de " + expresion + " debe ser un booleano");
  }
}
