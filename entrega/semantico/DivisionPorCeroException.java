package semantico;


import expresiones.RegistroExpr.Operador;

public class DivisionPorCeroException extends Exception {
  // Puede ser por MOD o DIV
  public DivisionPorCeroException(Operador op) {
    super(op == Operador.MOD ? "Modulo 0 indefinido" : "No se puede dividir entre cero");
  }
}
