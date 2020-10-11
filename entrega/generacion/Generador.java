package generacion;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Generador {
  private int iEtiq; // indice de etiqueta
  
  private String f_out; // nombre del fichero de salida
  
  private boolean comentarCodigo = true;
  
  // Constructor a partir del fichero de entrada f_in:
  public Generador(String f_in, boolean noComentar) {
    // Comprobaciones de f_in:
    List<String> separados = Arrays.asList(f_in.split("\\.")); // (split toma una ER)
    if (separados.size() != 2) {
      // Error, tiene que tener un solo punto
    }
    else if (!separados.get(1).equals("ml")) {
      // Error, debe terminar en .ml
    }
    // Todo ok:
    f_out = separados.get(0) + ".code";
    comentarCodigo = !noComentar;
    iEtiq = 0;
    
    // TODO: mas inicializaciones?
  }
  
  // Escribe la instruccion, un espacio y una nueva etiqueta
  public String nuevaEtiq() {
    return "L" + iEtiq++;
  }
  

  
  // Comentarios (si !comentarCodigo, no se hace nada): 
  public String comentar(String com) {
    String comentario = "";
    if (comentarCodigo) {
      comentario = "; " + com + "\n";
    }
    return comentario;
  }
  
  // Utilidad:
  
  // Para una instruccion con un argumento
  public String instruccion(String ins, String arg) {
    return ins + " " + arg + "\n";
  }
  
  
  // para el bloque de la etiqueta
  public String bloqueEtiq(String etiq, String instrucciones) {
    return etiq + ":\n" + instrucciones;
  }
  
  // para el bloque de la etiqueta, pero sin instrucciones
  public String bloqueEtiq(String etiq) {
    return etiq + ":\n";
  }
  
  /***********************************************************/
  /* Devuelven strings con el codigo correspondiente a los parametros
   */

  // Booleanos
  public String constFalse() {
    return "STC 0\n";
  }
    
  public String constTrue() {
    return "STC 1\n";
  }

  public String constBool(boolean b) {
    if (b) {
      return constTrue();
    }
    else {
      return constFalse();
    }
  }
  
  // Enteros (y char):
  // (invocar con parseInt en ambos casos)
  public String constEnt(int n) {
    return "STC " + n + "\n";
  }
  
  // Close Stack Frame, al final de las funciones
  public String cerrarBloque() {
    return "CSF\n";
  }
  
  /////// Operaciones:
  
  // 
  public String negar(String exp) {
    return exp + "NGB\n";
  }
  
  // Cambiar signo
  public String cambiarSigno(String exp) {
    return exp + "NGI\n";
  }
  
  public String sumar(String exp1, String exp2) {
    return exp1 + exp2 + "PLUS\n";
  }
  
  // Para sumar una constante entera con una expresion:
  // (usado para obtener dir de una componente de un vector)
  public String sumar(int constInt, String exp2) {
    //String exp1 = constEnt(constInt);
    return sumar(constInt + "\n", exp2);
  }
  
  public String restar(String exp1, String exp2) {
    return exp1 + exp2 + "SBT\n";
  }
  
  // div
  public String div(String exp1, String exp2) {
    return exp1 + exp2 + "DIV\n";
  }
  
  public String mod(String exp1, String exp2) {
    return exp1 + exp2 + "MOD\n";
  }
  
  public String mult(String exp1, String exp2) {
    return exp1 + exp2 + "TMS\n";
  }
  
  // logicos:
  public String and(String exp1, String exp2) {
    return exp1 + exp2 + "AND\n";
  } 
  
  public String or(String exp1, String exp2) {
    return exp1 + exp2 + "OR\n";
  } 
  
  public String igual(String exp1, String exp2) {
    return exp1 + exp2 + "EQ\n";
  } 
  
  public String distinto(String exp1, String exp2) {
    return exp1 + exp2 + "NEQ\n";
  } 
  
  public String menor(String exp1, String exp2) {
    return exp1 + exp2 + "LT\n";
  }
  
  public String menorIgual(String exp1, String exp2) {
    return exp1 + exp2 + "LTE\n";
  } 
  
  public String mayor(String exp1, String exp2) {
    return exp1 + exp2 + "GT\n";
  } 
  
  public String mayorIgual(String exp1, String exp2) {
    return exp1 + exp2 + "GTE\n";
  } 
  
  // Entrada/Salida:
  public String leerChar() {
    return "RD 0\n";
  }
  
  public String leerInt() {
    return "RD 1\n";
  }
  
  public String escribirChar() {
    return "WRT 0\n";
  }
  
  public String escribirInt() {
    return "WRT 1\n";
  }
  
  
  
  // Asignacion de la expresion (con codigo cExp) a la variable (con codigo cIdent)
  public String asignacion(String cIdent, String cExp) {
    String cod = comentar("------ asignacion: ------");
    return cod + cIdent + cExp + "ASG\n";
  }
  
  // Asignacion inversa, para parametros
  public String asignacionInv() {
    return "ASGI\n";
  }
  
  public String asignarVect(String nombre, int difNiv1, int difNiv2, int dir1, int dir2, int nComp) {
    String codigo = comentar("------ Asignacion a vector " + nombre + " ------");
    String aux;
    for (int i = 0; i < nComp; i++) { // cada componente
      aux = sumar((int) dir1, constEnt(i)); // direccion de la componente
      codigo += refVector(nombre, difNiv1, aux); // SRF
      aux = sumar((int) dir2, constEnt(i)); // dir de la comp del otro vector
      codigo += valVector("", difNiv2, aux); // SRF, DRF
      codigo += "ASG\n";
    }
    return codigo;
  }
  
  
  
  // Funcion predefinida leer, exp es el codigo correspondiente a la expresion (identificador, generalmente)
  public String leer(String exp, boolean entero) {
    String cod = comentar("------ leer ------");
    cod += exp; // Codigo de la expresion
    if (entero) { // Si es tipo entero, se lee entero
      cod += leerInt();
    }
    else { // Si no, char
      cod += leerChar();
    }
    return cod;
  }
  

  // devuelve el codigo que escribe la expresion
  // (como entero si <entero>, char si no
  public String escribir(String exp, boolean entero) {
    String cod = comentar("Escribir expresion:");
    cod += exp;
    if (entero) {
      cod += escribirInt();
    }
    else {
      cod += escribirChar();
    }
    return cod;
  }
  
  // Codigo para escribir una cadena de caracteres en salida est.
  public String escribirCadena(String cadena) {
    String cod = comentar("Escribir cadena " + cadena);
    // Ignoramos el primer y ultimo caracter, que son las comillas
    for (int i = 1; i < cadena.length()-1; i++) {
      cod += constEnt((int)(cadena.charAt(i))); // se apila cada caracter
      cod += escribirChar(); // Y se escribe
    }
    return cod;
  }
  
  // De control:
  
  // Si ... si_no:
  // In: Strings con el codigo de la condicion, las instrucciones del si y las
  //     del si_no 
  // Out: string del codigo completo.
  // Si no hay si_no (es una cadena vacía), se evitan algunas instrucciones
  public String seleccion(String condicion, String instr, String instrSiNo) {
    String codigo = comentar("------ si: ------");
    codigo += condicion; // comparacion
    if (instrSiNo.isEmpty()) { // No hay si_no
      codigo += comentar("si falso, saltamos las instrucciones");
      String eFin = nuevaEtiq(); // etiqueta de fin
      codigo += instruccion("JMF", eFin);
      codigo += comentar("instrucciones del si:");
      codigo += instr;
      codigo += bloqueEtiq(eFin);
    }
    else { // Si que hay si_no
      // Etiquetas:
      String eSiNo = nuevaEtiq(); 
      String eFin = nuevaEtiq();
      codigo += comentar("si falso, saltamos al si_no");
      codigo += instruccion("JMF", eSiNo);
      codigo += comentar("instrucciones del si:");
      codigo += instr;
      codigo += instruccion("JMP", eFin); // Si hemos ejecutado el si, saltamos el si_no
      codigo += comentar("instrucciones del si_no:");
      codigo += bloqueEtiq(eSiNo, instrSiNo); // si_no y sus instrucciones
      codigo += bloqueEtiq(eFin); // fin
    }
    codigo += comentar("fin del si");
    return codigo; 
  }
  
  // Mientras que:
  // In: codigo de la condicion y de las instrucciones
  // Out: codigo completo del bucle 
  public String mientrasQue(String condicion, String instr) {
    String eCon = nuevaEtiq(); // Etiqueta de condicion
    String eIns = nuevaEtiq(); // y de las instrucciones
    String codigo = comentar("------ mientras que: ------");
    codigo += comentar("Salto a la condicion:");
    codigo += instruccion("JMP", eCon);
    codigo += comentar("instrucciones del mq:");
    codigo += bloqueEtiq(eIns, instr);
    codigo += comentar("condicion del mq:");
    codigo += bloqueEtiq(eCon, condicion);
    codigo += comentar("Si true, volvemos a las instrucciones:");
    codigo += instruccion("JMT", eIns);
    codigo += comentar("Fin del mientras que");
    return codigo;
  }
  
  // Variable en expresion (se usa el valor)
  public String variable(String nombre, int difNivel, long dir) {
    return comentar("variable " + nombre) + "SRF " + difNivel + " " + dir + " DRF\n";
  }
  
  // Parametro: solo se apila
  public String parametro(String nombre, int difNivel, long dir) {
    return comentar("parametro " + nombre) + "SRF " + difNivel + " " + dir + "\n";
  }  
  
  // Vector
  public String refVector(String nombre, int difNivel, String codigoDir) {
    return comentar("Vector " + nombre) + "SRF " + difNivel + " " + codigoDir;
  }
  
  // Vector
  public String valVector(String nombre, int difNivel, String codigoDir) {
    return comentar("Vector " + nombre) + "SRF " + difNivel + " " + codigoDir + "DRF\n";
  }  
  
  
  
  // In: nombre de la accion, etiqueta su etiqueta, cParams codigo de los parametros, 
  //     cAcciones de las declaraciones de acciones anidadas, cSents de las sentencias
  public String declaracionAccion(String nombre, String etiqueta, String cAcciones, String cParams, String cSents) {
    String cod = comentar("------ Accion " + nombre + " ------");
    cod += bloqueEtiq(etiqueta); // etiqueta
    cod += cParams;
    cod += comentar("sentencias:");
    cod += cSents;
    cod += cerrarBloque(); // CSF
    cod += comentar("------ Fin de accion " + nombre + " ------");
    cod += cAcciones; // Acciones anidadas al final, ahorramos saltos
    return cod;
  }
  
  // Invocar accion: OSF s l addr
  // s: tam bloque actual (num de variables declaradas): sig
  // l = nivel - accion.nivel
  // addr = accion.etiqueta
  public String invocacion(String nombre, String cArgs, int s, int l, String addr) {
    String codigo = comentar("------ Invocacion de " + nombre + " ------");
    codigo += cArgs; // Apilar parametros
    codigo += "OSF " + s + " " + l + " " + addr + "\n";
    return codigo;
  }
  
  
  // Genera el programa a partir de los codigos en cacciones y cbloqueinstr
  // Lo escribe en el fichero de salida inicializado con el constructor
  public void generarPrograma(String nombre, String cacciones, String cbloqueinstr) {
    //etiq = nueva_etiqueta();
    String etiq = nuevaEtiq();
    String codigo = comentar("------ Programa " + nombre + " ------");
    codigo += instruccion("ENP", etiq);
    codigo += comentar("Declaraciones de acciones:");
    codigo += cacciones;
    codigo += comentar("------ Instrucciones del programa " + nombre + ": ------");
    codigo += bloqueEtiq(etiq, cbloqueinstr);
    codigo += comentar("------ Fin del programa ------");
    codigo += "LVP";
    // Se guarda en el fichero de salida:
    // Fuente: https://stackoverflow.com/a/1053475 (cierra el fichero automaticamente)
    try (PrintWriter out = new PrintWriter(f_out)) {
      out.println(codigo);
      System.out.println("Compilación finalizada. Se ha generado el fichero " + f_out);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}