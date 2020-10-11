package simbolos;

public class SimboloYaDefinidoException extends Exception {

  SimboloYaDefinidoException(String nombre) {
    super("Identificador " + nombre + " duplicado");
  }
  
  /*
  public String getNombre() {
    return this.nombre;
  }
  */
}
