package simbolos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import simbolos.Simbolo;


import vectores.RegistroIdentificador;

public class TablaSimbolos {
  private int M = 20; // num de eltos de la tabla (recomendacion de las diapositivas)
  private LinkedList<Simbolo> [] tabla; // al ser abierta, cada pos de la tabla debe ser una lista
  //  private ArrayList<LinkedList<Simbolo>> tabla; // al ser abierta, cada pos de la tabla debe ser una lista

  
  public TablaSimbolos() {
    
  }

  /**********************************************************************
  Crea una tabla de símbolos vacía.  Este procedimiento debe invocarse antes de 
  hacer ninguna operacióncon la tabla de símbolos.
  **********************************************************************/
  public void inicializar_tabla() {
    tabla = (LinkedList<Simbolo> []) new LinkedList[M]; // expresion rara para poder usar arrays de listas genericas
    // Hay que inicializar cada lista (M en total):
    for (int i = 0; i<M; i++) {
      tabla[i] = new LinkedList<Simbolo>();
    }
    //tabla = new ArrayList<LinkedList<Simbolo>>();
  }
  /**********************************************************************/
  
  /**********************************************************************
  Resume por salida estandar el estado actual de la tabla de simbolos 
  **********************************************************************/
  public void mostrar() {
    int pos = 0;
    int cuenta = 0;
    System.out.println("------- Resumen de la tabla -------");
    System.out.println("Formato: <nombre> (<tipo>); <nivel>");
    for (List<Simbolo> l : tabla) {
      if (!l.isEmpty()) {
        System.out.println("------- Lista en posicion " + pos++ + " -------");
        for (Simbolo s : l) { // Mostramos la representacion en String de cada simbolo 
          System.out.println(s.toString());
          cuenta++;
        }
      }
      
    }
    System.out.println("Numero de simbolos en total: " + cuenta);
    System.out.println("-----------------------------------\n-----------------------------------");
  }
  /**********************************************************************/
  
  /**********************************************************************
  Devuelve el numero de simbolos almacenado 
  **********************************************************************/
  public int n_simbolos() {
    int cuenta = 0;
    for (List<Simbolo> l : tabla) {
      cuenta += l.size();
    }
    return cuenta;
  }
  /**********************************************************************/
  
  
  /**********************************************************************
  Funcion de dispersion. Devuelve el entero correspondiente a la clave <clave>
  Utilizo djb2, originalmente de Dan Bernstein. He adaptado a Java el codigo
  en C de http://www.cse.yorku.ca/~oz/hash.html
   **********************************************************************/
  private int dispersion(String clave) {
    int hash = 5381; // se supone que es un buen punto para empezar
    for (int i = 0; i < clave.length(); i++) {
      // hash*33 + clave[i] pero mas rapido, en teoria:
      hash = ((hash << 5) + hash) + clave.charAt(i);       
    }
    //System.out.println("Hash: " + hash + "\n%M: " + Math.floorMod(hash, M));
    // hash % M no funciona ya que puede devolver negativos, no es el modulo que buscamos 
    return Math.floorMod(hash, M);// como maximo el tamaño de la tabla-1
  }
  
  /**********************************************************************
  Busca en la posicion <idx> de la Tabla el símbolo de mayor nivel con 
  <nombre>. Si existe, lo devuelve, si no, devuelve null
   **********************************************************************/
  private Simbolo buscar_en_pos (int idx, String nombre) {
    //System.out.println("buscando " + nombre + " en " + idx);
    int maxNivel = 0;
    int nivel;
    Simbolo buscado = null;
    for (Simbolo s : tabla[idx]) {
      //System.out.println("buscando en tabla en nivel... " + s.getNivel() + " simbolo " + s.getNombre());
      nivel = s.getNivel();
      if (s.getNombre().equals(nombre) && nivel >= maxNivel && (s.es_parametro() && s.isVisible() || !s.es_parametro())) {
        buscado = s;
        maxNivel = nivel;
      }
    }
    return buscado;
  }
  
  /**********************************************************************
  Busca en la posicion <idx> de la Tabla la accion de mayor nivel con 
  <nombre>. Si existe, lo devuelve, si no, devuelve null
   **********************************************************************/
  private Simbolo buscar_accion_en_pos (int idx, String nombre) {
    //System.out.println("buscando " + nombre + " en " + idx);
    int maxNivel = 0;
    int nivel;
    Simbolo buscado = null;
    for (Simbolo s : tabla[idx]) {
      //System.out.println("buscando en tabla en nivel... " + s.getNivel() + " simbolo " + s.getNombre());
      nivel = s.getNivel();
      if (s.getNombre().equals(nombre) && s.es_accion() && nivel >= maxNivel) {
        buscado = s;
        maxNivel = nivel;
      }
    }
    return buscado;
  }
  
  /**********************************************************************
  Busca en la tabla el símbolo de mayor nivel cuyo nombre coincida
  con el del parámetro (se distinguen minúsculas y mayúsculas).  Si
  existe, devuelve un puntero como resultado, de lo contrario devuelve
  NULL o lanza una excepción (se deja a vuestra elección un mecanismo u otro).
   **********************************************************************/
  public Simbolo buscar_simbolo(String nombre) throws SimboloNoEncontradoException {
    int idx = dispersion(nombre);
    Simbolo buscado = buscar_en_pos(idx, nombre);
    if (buscado == null) {
      throw new SimboloNoEncontradoException(nombre);
    }
    return buscado;
  }
  /**********************************************************************/
  
  /**********************************************************************
  Busca en la tabla la accion de mayor nivel cuyo nombre coincida
  con el del parámetro (se distinguen minúsculas y mayúsculas).  Si
  existe, devuelve un puntero como resultado, de lo contrario devuelve
  NULL o lanza una excepción (se deja a vuestra elección un mecanismo u otro).
   **********************************************************************/
  public Simbolo buscar_accion(String nombre) throws SimboloNoEncontradoException {
    int idx = dispersion(nombre);
    Simbolo buscado = buscar_accion_en_pos(idx, nombre);
    if (buscado == null) {
      throw new SimboloNoEncontradoException("Accion " + nombre + " no definida");
    }
    return buscado;
  }
  /**********************************************************************/
  
  
  /**********************************************************************
  Introduce en la tabla el simbolo <s> al principio de su posicion
  (determinada por la f de dispersion)
  **********************************************************************/
  private void introducir_simbolo(Simbolo s, int idx){
    //int idx = dispersion(s.getNombre());
    tabla[idx].push(s); // de LinkedList, se añade al principio
  } // DEPRECATED supongo
  /**********************************************************************/
  
  
  
  /**********************************************************************
  Introduce en la tabla un simbolo PROGRAMA,  con el nombre
  del parametro, de nivel 0, con la direccion del parametro. Dado que debe
  ser el primer simbolo a introducir, NO SE VERIFICA QUE EL SIMBOLO YA
  EXISTA.
  **********************************************************************/
  public Simbolo introducir_programa(String nombre, int dir){
    int idx = dispersion(nombre);
    Simbolo prog = new Simbolo();
    prog.introducir_programa(nombre, dir);
    //this.introducir_simbolo(prog); // lo introducimos en la tabla
    tabla[idx].push(prog);
    return prog;
  }
  /**********************************************************************/
  
  /**********************************************************************
  Devuelve true sii existe un simbolo con <nombre> y <nivel> en la 
  posicion <idx> de la tabla de simbolos
  **********************************************************************/
  private boolean existe_simbolo(int idx, String nombre, int nivel) {
    boolean existe = false;
    //System.out.println("indice: " + idx);
    for (Simbolo s : tabla[idx]) {
      if (s.getNombre().equals(nombre) && s.getNivel() == nivel) {
        //System.out.println(s.getNombre() + "..." + nombre);
        return true;
      }
    }
    return existe;
  }
  
  
  /**********************************************************************
  Si existe un símbolo en la tabla del mismo nivel y con el mismo
  nombre, devuelve NULL(o una excepción, esto se deja a vuestra elección.  
  De lo contrario, introduce un símbolo VARIABLE (simple) 
  con los datos de los argumentos.
   * @throws SimboloYaDefinidoException 
  **********************************************************************/
  public Simbolo introducir_variable(RegistroIdentificador id,
      Simbolo.tipo_var tipo,int nivel,int dir) throws SimboloYaDefinidoException {
    String nombre = id.getId(); 
    int componentes = id.getComponentes();
    int idx = dispersion(nombre);
    Simbolo variable = null;
    if (existe_simbolo(idx, nombre, nivel)) {
      throw new SimboloYaDefinidoException(nombre);
    }
    else {
      variable = new Simbolo();
      variable.introducir_variable(nombre, tipo, componentes, dir, nivel); // actualizamos sus tipos
      //this.introducir_simbolo(idx, variable);
      tabla[idx].push(variable);
    }
    return variable;
  }
  /**********************************************************************/
  
  /**********************************************************************
  Si existe un símbolo en la tabla del mismo nivel y con el mismo
  nombre, devuelve NULL. De lo contrario, introduce un símbolo
  ACCION con los datos de los argumentos.
   * @throws SimboloYaDefinidoException 
  **********************************************************************/
  public Simbolo introducir_accion (String nombre, List<Simbolo> parametros, String etiq, int nivel, int dir) throws SimboloYaDefinidoException {
    int idx = dispersion(nombre); // indice segun el nombre
    Simbolo accion = null;
    if (existe_simbolo(idx, nombre, nivel)) {
      throw new SimboloYaDefinidoException(nombre);
    }
    else {
      accion = new Simbolo();
      accion.introducir_accion(nombre, parametros, etiq, dir, nivel); // actualizamos sus tipos
      //this.introducir_simbolo(idx, variable);
      tabla[idx].push(accion);
    }
    return accion;
  }
  /**********************************************************************/
  
  /**********************************************************************
  Si existe un símbolo en la tabla del mismo nivel y con el mismo
  nombre, devuelve NULL.  De lo contrario, introduce un símbolo
  PARAMETRO con los datos de los argumentos. 
   * @throws SimboloYaDefinidoException 
  **********************************************************************/
  public Simbolo introducir_parametro (String nombre, 
  Simbolo.tipo_var variable, Simbolo.clase_parametro parametro, int nivel, int dir) throws SimboloYaDefinidoException {
    int idx = dispersion(nombre); // indice segun el nombre
    Simbolo par = null; // simbolo parametro
    if (existe_simbolo(idx, nombre, nivel)) {
      throw new SimboloYaDefinidoException(nombre);
    }
    else {
      par = new Simbolo();
      par.introducir_parametro(nombre, variable, parametro, dir, nivel); // actualizamos sus tipos
      //this.introducir_simbolo(idx, variable);
      tabla[idx].push(par);
    }
    return par;
  }
  /**********************************************************************/
  
  /**********************************************************************
  Elimina de la tabla todos los símbolos PROGRAMA de nivel 0 (debería haber uno solo).
  **********************************************************************/
  public void eliminar_programa(){
    for (LinkedList<Simbolo> lista : tabla) { // para cada lista de colisiones
      for (Simbolo s : lista) { // para cada simbolo de esa lista
        if (s.es_progrma() && s.getNivel() == 0) {
          lista.remove(s); // se elimina
          return; // solo debe haber uno
        }
      }
    }
  }
  
  /**********************************************************************/
  
  /**********************************************************************
  Elimina de la tabla todas las variables que sean del nivel del argumento. 
  NO ELIMINA PARÁMETROS.
  **********************************************************************/
  public void eliminar_variables(int nivel) {
    for (LinkedList<Simbolo> lista : tabla) { // para cada lista de colisiones
      Simbolo s; // no se puede usar un foreach para eliminar
      Iterator<Simbolo> iter = lista.iterator();
      while (iter.hasNext()) {
        s = iter.next(); // para cada simbolo de esa lista
        if (s.es_variable() && s.getNivel() == nivel) {
          iter.remove();
        }
      }
    }
  }
  /**********************************************************************/
  
  /**********************************************************************
  Marca todos los parámetros de un nivel como ocultos para que no puedan
  ser encontrados, pero se mantenga la definición completa de la acción 
  donde están declarados para verificación de invocaciones a la acción.
  **********************************************************************/
  public void ocultar_parametros(int nivel){
    for (LinkedList<Simbolo> lista : tabla) { // para cada lista de colisiones
      for (Simbolo s : lista) { // para cada simbolo de esa lista
        if (s.es_parametro() && s.getNivel() == nivel) {
          s.setVisible(false); // es un parametro del nivel, se oculta
        }
      }
    }
  }
  /**********************************************************************/
  
  /**********************************************************************
  In: parametro par
  Elimina de la tabla la accion que contenia al parametro par
  **********************************************************************/
  public boolean eliminar_accion_de_parametro(Simbolo par) { // nivel es inutil??
    boolean encontrado = false;
    for (LinkedList<Simbolo> lista : tabla) { // para cada lista de colisiones
      Simbolo s; // no se puede usar un foreach para eliminar
      Iterator<Simbolo> iter = lista.iterator();
      while (iter.hasNext()) { // para cada simbolo de esa lista
        s = iter.next();
        if (s.es_accion() && s.getLista_parametros().contains(par)) {
          //System.out.println("eliminando " + s.getNombre() + " gracias a " + par.getNombre());
          iter.remove();
          encontrado = true;
        }
      }
    }
    return encontrado;
  }
  
  
  /**********************************************************************
  Elimina de la tabla todas los parámetros que hayan sido ocultados
  previamente.  LOS PROCEDIMIENTOS Y FUNCIONES DONDE ESTABAN DECLARADOS
  DEBEN SER ELIMINADOS TAMBIEN PARA MANTENER LA COHERENCIA DE LA TABLA.
  **********************************************************************/
  public void eliminar_parametros_ocultos(int nivel) { 
    for (LinkedList<Simbolo> lista : tabla) { // para cada lista de colisiones
      Simbolo s; // no se puede usar un foreach para eliminar
      Iterator<Simbolo> iter = lista.iterator();
      while (iter.hasNext()) {
        s = iter.next(); // Para cada simbolo // para cada simbolo de esa lista
        if (s.es_parametro() && !s.isVisible() && s.getNivel() >= nivel) {
          this.eliminar_accion_de_parametro(s);
          iter.remove();
        }
      }
    }
  }
  
  /**********************************************************************/
  
  
  /**********************************************************************
  Elimina de la tabla el simbolo s
  **********************************************************************/
  private void eliminar_simbolo (Simbolo s) {
    int idx = dispersion(s.getNombre()); // buscamos su pos por el nombre
    tabla[idx].remove(s); // se elimina el objeto que coincide de su pos
  }
  
  /**********************************************************************
  Elimina de la tabla todas los procedimientos de un nivel.  
  LOS PARAMETROS DE ESTAS ACCIONES DEBEN SER ELIMINADOS TAMBIEN PARA 
  MANTENER LA COHERENCIA DE LA TABLA.
  **********************************************************************/
  public void eliminar_acciones(int nivel){
    for (LinkedList<Simbolo> lista : tabla) { // para cada lista de colisiones
      // No se puede usar un foreach para la lista porque da error al eliminar:
      Simbolo s;
      Iterator<Simbolo> iter = lista.iterator();
      while (iter.hasNext()) {
        s = iter.next(); // Para cada simbolo
        if (s.es_accion() && s.getNivel() == nivel) {
          List<Simbolo> parametros = s.getLista_parametros(); 
          // eliminamos sus parametros:
          //System.out.println("Antes clear params (" + s.getNombre()+ "): "+ n_simbolos());

          iter.remove(); 
          for (Simbolo par : parametros) {
            eliminar_simbolo(par);
          }
          //parametros.clear();
          //System.out.println("Despues: " + n_simbolos());
          // Y la accion:
           /*
          for (int i = 0; i < parametros.size(); i++) {
            eliminar_simbolo(parametros.get(i));
          }/*
          Simbolo par;
          Iterator<Simbolo> iter = parametros.iterator();

          while (iter.hasNext()) {
              par = (Simbolo) iter.next();
              iter.remove();
          }
          
          /*for (Iterator<Simbolo> iterator = parametros.iterator(); iterator.hasNext(); ) {
            par=iterator.next();
            // String value = iterator.next();
            //eliminar_simbolo(par);
            iterator.remove();
          }*/
          /*for (Simbolo par : parametros) {
            eliminar_simbolo(par);
          }*/
          // Y la accion:
          //lista.remove(s);
        }
      }
    }
  }
 

  /**********************************************************************/
};