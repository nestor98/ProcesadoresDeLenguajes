package simbolos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vectores.IndiceFueraDeRangoException;

public class Simbolo {
  public enum tipo_sim {PROGRAMA, VARIABLE, ACCION, PARAMETRO};
  
  // Tipo de variable:
  public enum tipo_var {
    DESCONOCIDO, ENTERO, BOOLEANO, CHAR, CADENA;
    
    // Para manejo de errores
    public String toString() {
      String tipostr;
      switch(this) {
      case DESCONOCIDO:
        tipostr = "desconocido";
        break;
      case ENTERO:
        tipostr = "entero";
        break;
      case CHAR:
        tipostr = "char";
        break;
      case BOOLEANO:
        tipostr = "booleano";
        break;
      default: // No deberia darse nunca
        tipostr = "INDEFINIDO";
      }
      return tipostr;
    }
  };
  
  // Clase de parametro:
  public enum clase_parametro {VAL, REF};
  
  // De la instancia:
  private String nombre;
  private int nivel; // bloque, 0 o más
  private tipo_sim tipo; // tipo de simbolo
  private boolean visible; 
  private long dir; // Direccion en memoria
  
  // Si es variable:
  private tipo_var variable; // tipo de variable 
  // Si es parametro:
  private clase_parametro parametro; // val o ref
  // Si es accion:
  private List<Simbolo> lista_parametros; // sus parametros 
  
  private String etiqueta; // Etiqueta de la accion en el codigo
  
  // Vectores:
  private int componentes = 0; // numero de componentes
  
  //--------------------------------------------------------
  // Constructores:
  // Vacío
  public Simbolo() {
    
  }
  
  public Simbolo(String nombre, int nivel, tipo_sim tipo, boolean visible) {
    // TODO Auto-generated constructor stub
  }
  
  // --------------------------------------------------------
  // Getters y setters:
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getNivel() {
    return nivel;
  }

  public void setNivel(int nivel) {
    this.nivel = nivel;
  }

  public tipo_sim getTipo() {
    return tipo;
  }

  public void setTipo(tipo_sim tipo) {
    this.tipo = tipo;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public long getDir() {
    return dir;
  }

  public void setDir(long dir) {
    this.dir = dir;
  }

  public tipo_var getVariable() {
    return variable;
  }

  public void setVariable(tipo_var variable) {
    this.variable = variable;
  }

  public clase_parametro getParametro() {
    return parametro;
  }

  public void setParametro(clase_parametro parametro) {
    this.parametro = parametro;
  }

  public List<Simbolo> getLista_parametros() {
    return lista_parametros;
  }

  public void setLista_parametros(List<Simbolo> lista_parametros) {
    this.lista_parametros = lista_parametros;
  }

  // Vectores:
  public boolean esVector() {
    return componentes>0;
  }
  
  public void comprobarRango(int indice) throws IndiceFueraDeRangoException {
    if (indice < 0 || indice >= componentes) {
      throw new IndiceFueraDeRangoException(componentes, indice);
    }
  }

  
  //--------------------------------------------------------
  // Funciones extra para introducir los distintos símbolos:
  
  // Extra: para introducir todos los datos de un símbolo de tipo programa:
  public void introducir_programa(String nombre, int dir) {
    this.setTipo(tipo_sim.PROGRAMA);
    this.setNombre(nombre);
    this.setDir(dir);
    this.setNivel(0); // el programa siempre esta en nivel 0 
  }
  
  // Extra: para introducir todos los datos de un símbolo de tipo parámetro:
  public void introducir_parametro(String nombre, tipo_var tipo, clase_parametro parametro, int dir, int nivel) {
    this.setTipo(tipo_sim.PARAMETRO);
    this.setNombre(nombre);
    this.setVariable(tipo);
    this.setParametro(parametro);
    this.setDir(dir);
    this.setNivel(nivel);
    this.setVisible(true);
  }
  
  // Extra: ídem para uno de tipo variable:
  public void introducir_variable(String nombre, tipo_var tipo, int componentes, int dir, int nivel) {
    this.setTipo(tipo_sim.VARIABLE);
    this.setNombre(nombre);
    this.setVariable(tipo);
    this.setDir(dir);
    this.setNivel(nivel);
    this.setComponentes(componentes);
  }
  
  // Extra: ídem para uno de tipo accion:
  public void introducir_accion(String nombre, List<Simbolo> parametros, String etiq, int dir, int nivel) {
    this.setTipo(tipo_sim.ACCION);
    this.setNombre(nombre);
    // this.setVariable(tipo); // Las acciones no tienen tipo de var
    this.setNivel(nivel);
    this.setDir(dir);
    this.setLista_parametros(parametros);
    this.etiqueta = etiq;
  }
  
  //--------------------------------------------------------
  // Funciones extra de utilidad:
  
  // Devuelve true sii es un programa:
  public boolean es_progrma() {
    return this.tipo==tipo_sim.PROGRAMA;
  }
  
  // Devuelve true sii es una variable:
  public boolean es_variable() {
    return this.tipo==tipo_sim.VARIABLE;
  }
  
  // Devuelve true sii es un parametro:
  public boolean es_parametro() {
    return this.tipo==tipo_sim.PARAMETRO;
  }
  
  //Devuelve true sii es un parametro por valor:
   public boolean es_valor() {
     return this.es_parametro() && this.parametro==clase_parametro.VAL;
   }
   
  //Devuelve true sii es un parametro por referencia:
   public boolean es_referencia() {
     return this.es_parametro() && this.parametro==clase_parametro.REF;
   }
   
   //Devuelve true sii es de tipo booleano:
   public boolean es_booleano() {
     return this.variable==tipo_var.BOOLEANO;
   }
   
   //Devuelve true sii es de tipo char:
   public boolean es_char() {
     return this.variable==tipo_var.CHAR;
   }
   
   //Devuelve true sii es de tipo entero:
   public boolean es_entero() {
     return this.variable==tipo_var.ENTERO;
   }
   
  //----------------------------------------
  // Para acciones:
   
  // Devuelve true sii es una accion:
  public boolean es_accion() {
    return this.tipo==tipo_sim.ACCION;
  }
  
  // Devuelve true sii es una accion cuyo i-esimo parametro es por valor
  public boolean i_parametro_es_valor(int i) {
    // true sii: i es un indice valido, es una accion, y ese indice es por val
    return (lista_parametros.size() > i && this.es_accion() 
            && lista_parametros.get(i).es_valor());
  }
  
  // Devuelve true sii es una accion cuyo i-esimo parametro es por ref
  public boolean i_parametro_es_ref(int i) {
    // true sii: i es un indice valido, es una accion, y ese indice es por ref
    return (lista_parametros.size() > i && this.es_accion() 
            && lista_parametros.get(i).es_referencia());
  }
  
  // Devuelve el numero de parametros de una accion. 0 si no lo es
  public int n_parametros() {
    int n = 0;
    if (this.es_accion()) {
      n = this.lista_parametros.size();
      //System.out.println("num_param: " + nombre + " ... "+ n);
    }
    return n;
  }
  
  // Devuelve una string con el tipo de cada parametro separado por coma
  // Ej: "VAL,VAL,REF"
  public String tipo_parametros() {
    String s = "";
    Map<clase_parametro, String> traduccion = new HashMap<clase_parametro,String>();
    traduccion.put(clase_parametro.VAL, "VAL");
    traduccion.put(clase_parametro.REF, "REF");
    for (Simbolo param : lista_parametros) { // 
      s += traduccion.get(param.getParametro()) + ",";
    }
    return s.substring(0, s.length() - 1); // para quitar la coma final
  }
  
  
  // Devuelve una string con el resumen del simbolo (muestra el nombre, tipo, y nivel)
  // Ej de salida: "num (variable); 2"
  public String toString() {
    String tipostr;
    switch(tipo) {
    case ACCION:
      tipostr = "accion";
      break;
    case PARAMETRO:
      tipostr = "parametro";
      break;
    case PROGRAMA:
      tipostr = "programa";
      break;
    case VARIABLE:
      tipostr = "variable";
      break;
    default: // No deberia darse nunca
      tipostr = "INDEFINIDO";
    }
    if (esVector()) {
      tipostr = "vector " + tipostr;
    }
    return nombre + " (" + tipostr + "); " + nivel;
  }
  
  
  // 
  
  
  // Devuelve una string con otro resumen del simbolo (muestra el nombre y tipo de variable)
  // Ej de salida: "num (variable)"
  public String nombreYTipo() {
    /*String tipostr;
    switch(variable) {
    case DESCONOCIDO:
      tipostr = "desconocido";
      break;
    case ENTERO:
      tipostr = "entero";
      break;
    case CHAR:
      tipostr = "char";
      break;
    case BOOLEANO:
      tipostr = "booleano";
      break;
    default: // No deberia darse nunca
      tipostr = "INDEFINIDO";
    }*/
    return nombre + " (" + variable + ")";
  }

  public String getEtiqueta() {
    return etiqueta;
  }

  public void setEtiqueta(String etiqueta) {
    this.etiqueta = etiqueta;
  }

  public int getComponentes() {
    return componentes;
  }

  public void setComponentes(int componentes) {
    this.componentes = componentes;
  }
}
