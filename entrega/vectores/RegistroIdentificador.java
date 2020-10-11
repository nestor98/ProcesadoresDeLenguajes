package vectores;

import expresiones.RegistroExpr;

// Como RegistroExpr pero para identificadores, para permitir devolver
// info sobre vectores
public class RegistroIdentificador {
  private String id;
  private int componentes;
  private boolean esVector;
  
  private RegistroExpr exprIndice; // Codigo para el indice (es una expresion)
  
  // Constructor:
  public RegistroIdentificador(String id) {
    this.id = id;
    this.componentes = 0;
    this.setEsVector(false);
  }
  
  public RegistroIdentificador(String id, int componentes) {
    this.id = id;
    this.componentes = componentes;
    this.setEsVector(componentes>0);
  }
  
  public RegistroIdentificador(String id, boolean esVector) {
    this.id = id;
    this.setEsVector(esVector);
  }
  
  public boolean esVector() {
    return esVector;
  }
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public int getComponentes() {
    return componentes;
  }
  public void setComponentes(int componentes) {
    this.componentes = componentes;
  }

  public void setEsVector(boolean esVector) {
    this.esVector = esVector;
  }
  
  public String toString() {
    return "id: " + id + ", componentes: " + componentes + ", esVector: " + esVector + "\n";
  }


  public RegistroExpr getExprIndice() {
    return exprIndice;
  }

  public void setExprIndice(RegistroExpr exprIndice) {
    this.exprIndice = exprIndice;
  }
}
