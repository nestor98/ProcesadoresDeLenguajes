package expresiones;

import semantico.DivisionPorCeroException;
import semantico.TipoIncorrectoException;
import simbolos.Simbolo;
import generacion.Generador;

public class RegistroExpr {
  private int valorEnt;
  private boolean valorBool;
  private char valorChar;
  private Simbolo.tipo_var tipo;
  
  private boolean esSimbolo = false; // puede ser un simbolo (parametro/variable...)
  private boolean esConst = false; // tiene un valor constante
  
  private Simbolo s;
  
  private String codigo;
  
  
  private boolean esVector = false; // true si es vector sin desreferenciar
  // por ej, v[3] NO es un vector, v si (para llamadas a funciones)
  
  
  // Operadores (aditivos y multiplicativos):
  public enum Operador {
    SUMA, RESTA, OR, MULT, DIV, MOD, AND;
    // Para manejo de errores
    public String toString() {
      String tipostr;
      switch(this) {
      case SUMA:
        tipostr = "suma";
        break;
      case RESTA:
        tipostr = "resta";
        break;
      case OR:
        tipostr = "or";
        break;
      case MULT:
        tipostr = "multiplicacion";
        break;
      case DIV:
        tipostr = "division";
        break;
      case MOD:
        tipostr = "modulo";
        break;
      case AND:
        tipostr = "and";
        break;
      default: // No deberia darse nunca
        tipostr = "INDEFINIDO";
      }
      return tipostr;
    }
  };
  
  //Operadores relacionales (comparaciones):
  public enum OperadorRelacional {
    IGUAL, MENIGUAL, MAYIGUAL, MENOR, MAYOR, DISTINTO;
    
 // Para manejo de errores
    public String toString() {
      String tipostr;
      switch(this) {
      case DISTINTO:
        tipostr = "<>";
        break;
      case IGUAL:
        tipostr = "=";
        break;
      case MAYIGUAL:
        tipostr = ">=";
        break;
      case MAYOR:
        tipostr = ">";
        break;
      case MENIGUAL:
        tipostr = "<=";
        break;
      case MENOR:
        tipostr = "<";
        break;
      default:
        tipostr = "INDEFINIDO";
        break;
      }
      return tipostr;
    }
  };
  
  
  public RegistroExpr(Simbolo.tipo_var tipo) {
    this.tipo = tipo;
  }
  
  // Para valor entero:
  public RegistroExpr(int valor) {
    this.tipo = Simbolo.tipo_var.ENTERO;
    this.valorEnt = valor;
    esConst = true;
  }
  
  // Para valor bool:
  public RegistroExpr(boolean valor) {
    this.tipo = Simbolo.tipo_var.BOOLEANO;
    this.valorBool = valor;
    esConst = true;
  }
  
  // Para char:
  public RegistroExpr(char valor) {
    this.tipo = Simbolo.tipo_var.CHAR;
    this.valorChar = valor;
    esConst = true;
  }
  
  
  // Si es un simbolo:
  public RegistroExpr(Simbolo s) {
    this.esSimbolo = true;
    this.s = s;
    if (!s.es_accion()) {
      tipo = s.getVariable();      
    }
    else { // Si es una accion, tipo desconocido
      tipo = Simbolo.tipo_var.DESCONOCIDO;
    }
  }
  
  // es un parametro por valor:
  public boolean esParamValor() {
    return esSimbolo && s.es_valor();
  }
  
 //es un parametro por referencia:
 public boolean esParamRef() {
   return esSimbolo && s.es_referencia();
 }
 
  
  // Getters y setters:
  public int getValorEnt() {
    return valorEnt;
  }
  
  public boolean esSimbolo() {
    return esSimbolo;
  }

  public void setEsSimbolo(boolean esSimbolo) {
    this.esSimbolo = esSimbolo;
  }

  public Simbolo getSimbolo() {
    return s;
  }

  public void setSimbolo(Simbolo s) {
    this.esSimbolo = true;
    this.s = s;
  }

  public void setValorEnt(int valorEnt) {
    this.tipo = Simbolo.tipo_var.ENTERO;
    this.valorEnt = valorEnt;
  }
  public boolean getValorBool() {
    return valorBool;
  }
  public void setValorBool(boolean valorBool) {
    this.tipo = Simbolo.tipo_var.BOOLEANO;
    this.valorBool = valorBool;
  }
  public char getValorChar() {
    return valorChar;
  }
  public void setValorChar(char valorChar) {
    this.tipo = Simbolo.tipo_var.CHAR;
    this.valorChar = valorChar;
  }
  public Simbolo.tipo_var getTipo() {
    return tipo;
  }
  public void setTipo(Simbolo.tipo_var tipo) {
    this.tipo = tipo;
  }
  
  // Utilidades, devuelven true si es el tipo que dice el nombre:
  public boolean esChar() {
    return tipo == Simbolo.tipo_var.CHAR;
  }
  
  public boolean esEnt() {
    return tipo == Simbolo.tipo_var.ENTERO;
  }
  
  public boolean esBool() {
    return tipo == Simbolo.tipo_var.BOOLEANO;
  }
  
  public void toBool() {
    tipo = Simbolo.tipo_var.BOOLEANO;
  }
  
  public void toChar() {
    tipo = Simbolo.tipo_var.CHAR;
  }
  
  public void toEnt() {
    tipo = Simbolo.tipo_var.ENTERO;
  }
  
  
  
  public boolean esCadena() {
    return tipo == Simbolo.tipo_var.CADENA;
  }
  
  public boolean esDesconocido() {
    return tipo == Simbolo.tipo_var.DESCONOCIDO;
  }
  
  // Devuelve true sii el tipo de este registro es compatible con
  // la operación <op>
  private boolean compatible(Operador op) {
    boolean ok = false; 
    switch (getTipo()) {
    case BOOLEANO: // ok si son operaciones logicas
      ok = (op == Operador.OR || op == Operador.AND);
      break;
    case CADENA:
      break;
    case CHAR: // no se pueden operar chars
      break;
    case DESCONOCIDO: // no deberia darse, pero por defecto no ok
      break;
    case ENTERO: // ok si NO es operador logico
      ok = !(op == Operador.OR || op == Operador.AND);
      break;
    default:
      break;
    }
    return ok;
  }
  
  // Se realiza la operacion op entre los registros this y otro.
  // El valor resultante se almacena en este registro
  // Si no coinciden los tipos o el operador no es compatible, lanza una excepcion de tipos
  // Si se intenta dividir entre 0, lanza una excepcion de DivisionPorCeroException
  public void operar(RegistroExpr otro, Operador op, Generador gc) throws TipoIncorrectoException, DivisionPorCeroException {
    
    this.setEsSimbolo(false); // Al operar deja de ser un solo simbolo
    if (getTipo() != otro.getTipo()) { // Comprobacion de tipos
      throw new TipoIncorrectoException(this, otro);
    }
    if (!this.compatible(op)) { 
      // el tipo puede no ser compatible con la operacion (bool y mod, por ej)
      throw new TipoIncorrectoException(this, op);
    }
    if (this.esVector() || otro.esVector()) {
      throw new TipoIncorrectoException("No se permiten operaciones con vectores");
    }
    else { // Solo puede ser entero o bool
      if (this.esEnt()) { // Si es entero
        int valor1 = this.getValorEnt();
        int valor2 = otro.getValorEnt();
        switch (op) {
        case DIV:
          if (otro.esConst() && valor2 == 0) {
            throw new DivisionPorCeroException(op);
          }
          // Generacion:
          this.codigo = gc.div(this.getCodigo(), otro.getCodigo());
          if (esConst() && otro.esConst) { // si ambas son constantes
            this.setValorEnt(valor1/valor2);
          }
          else {
            esConst = false;
          }
          
          break;
        case MOD:
          // Como modulo utilizo el matematico, no el resto de la division 
          // como el operador % de java
          if (otro.esConst() && valor2 == 0) {
            throw new DivisionPorCeroException(op);
          }
          //this.setValorEnt(Math.floorMod(valor1, valor2));
          // Generacion:
          this.codigo = gc.mod(this.getCodigo(), otro.getCodigo());
          if (esConst() && otro.esConst) { // si ambas son constantes
            this.setValorEnt(Math.floorMod(valor1, valor2));
          }
          else {
            esConst = false;
          }
          break;
        case MULT:
          this.setValorEnt(valor1*valor2);
         // Generacion:
          this.codigo = gc.mult(this.getCodigo(), otro.getCodigo());
          if (esConst() && otro.esConst) { // si ambas son constantes
            this.setValorEnt(valor1 * valor2);
          }
          else {
            esConst = false;
          }
          break;
        case RESTA:
          this.setValorEnt(valor1-valor2);
          // Generacion:
          if (esConst() && otro.esConst) { // si ambas son constantes
            this.setValorEnt(valor1 - valor2);
            this.codigo = gc.constEnt(this.valorEnt);
          }
          else {
            esConst = false;
            this.codigo = gc.restar(this.getCodigo(), otro.getCodigo());
          }
          break;
        case SUMA:
          this.setValorEnt(valor1+valor2);
          // Generacion:
          if (esConst() && otro.esConst) { // si ambas son constantes
            this.setValorEnt(valor1 + valor2);
            this.codigo = gc.constEnt(this.valorEnt);
          }
          else {
            esConst = false;
            this.codigo = gc.sumar(this.getCodigo(), otro.getCodigo());
          }
          break;
        default:
          break;
        }
      }
      else { // Si es booleano (el operador solo puede ser OR o AND
        boolean b1 = this.getValorBool();
        boolean b2 = this.getValorBool();
        if (op == Operador.AND) {
          //this.setValorBool(b1 && b2);
          // Generacion:
          if (esConst() && otro.esConst) { // si ambas son constantes
            this.setValorBool(b1 && b2);
            this.codigo = gc.constBool(this.valorBool);
          }
          else {
            esConst = false;
            this.codigo = gc.and(this.getCodigo(), otro.getCodigo());
          }
        }
        else { // OR
          // Generacion:
          if (esConst() && otro.esConst) { // si ambas son constantes
            this.setValorBool(b1 || b2);
            this.codigo = gc.constBool(this.valorBool);
          }
          else {
            esConst = false;
            this.codigo = gc.or(this.getCodigo(), otro.getCodigo());
          }
          //this.setValorBool(b1 || b2);
        }
      }
    }
  }
  
  // Devuelve true sii el tipo de este registro es compatible con
  // la operación relacional <op>
  private boolean compatible(OperadorRelacional op) {
    // compatibles si no es ni tipo bool ni desconocido o si es bool y el operador es o IGUAL o DISTINTO
    return ((!esBool() && !esDesconocido()) 
        || (esBool() && (op == OperadorRelacional.IGUAL || op == OperadorRelacional.DISTINTO)));
  }
  
  // Se realiza la comparacion op entre los registros this y otro.
  // El valor resultante se almacena en este registro
  // Si no coinciden los tipos o el operador no es compatible, lanza una excepcion de tipos
  // Si se intenta dividir entre 0, lanza una excepcion de DivisionPorCeroException
  public void comparar(RegistroExpr otro, OperadorRelacional op, Generador gc) throws TipoIncorrectoException {
    if (getTipo() != otro.getTipo()) { // Comprobacion de tipos
      throw new TipoIncorrectoException(this, otro);
    }
    if (!this.compatible(op)) { 
      // el tipo puede no ser compatible con la operacion (bool y <, por ej)
      throw new TipoIncorrectoException(this, op);
    }
    if (this.esVector() || otro.esVector()) {
      throw new TipoIncorrectoException("No se permiten comparaciones con vectores");
    }
    else { 
      this.toBool(); // Pasa a ser booleano
      if (!(this.esConst() && otro.esConst())) { // si uno de los dos no es const
        this.esConst = false;
        switch (op) {
        case DISTINTO:
          //this.setValorBool(valor1!=valor2);
          // Generacion:
          this.codigo = gc.distinto(this.getCodigo(), otro.getCodigo());
          break;
        case IGUAL:
          //this.setValorBool(valor1==valor2);
          // Generacion:
          this.codigo = gc.igual(this.getCodigo(), otro.getCodigo());
          break;
        case MAYIGUAL:
          //this.setValorBool(valor1>=valor2);
          // Generacion:
          this.codigo = gc.mayorIgual(this.getCodigo(), otro.getCodigo());
          break;
        case MAYOR:
          //this.setValorBool(valor1>valor2);
          // Generacion:
          this.codigo = gc.mayor(this.getCodigo(), otro.getCodigo());
          break;
        case MENIGUAL:
          //this.setValorBool(valor1<=valor2);
          // Generacion:
          this.codigo = gc.menorIgual(this.getCodigo(), otro.getCodigo());
          break;
        case MENOR:
          //this.setValorBool(valor1<valor2);
          // Generacion:
          this.codigo = gc.menor(this.getCodigo(), otro.getCodigo());
          break;
        default:
          break;
        }
      }
      else { // Son constantes
        if (this.esEnt()) { // Si es entero
          int valor1 = this.getValorEnt();
          int valor2 = otro.getValorEnt();
          switch (op) {
          case DISTINTO:
            this.setValorBool(valor1!=valor2);
            break;
          case IGUAL:
            this.setValorBool(valor1==valor2);
            break;
          case MAYIGUAL:
            this.setValorBool(valor1>=valor2);
            break;
          case MAYOR:
            this.setValorBool(valor1>valor2);
            break;
          case MENIGUAL:
            this.setValorBool(valor1<=valor2);
            break;
          case MENOR:
            this.setValorBool(valor1<valor2);
            break;
          default:
            break;
          }
        }
        else if (esBool()) { // Si es booleano (el operador solo puede ser = o <>
          boolean b1 = this.getValorBool();
          boolean b2 = otro.getValorBool();
          if (op == OperadorRelacional.IGUAL) {
            this.setValorBool(b1 == b2);
          }
          else { // OR
            this.setValorBool(b1 != b2);
          }
        }
        else { // Es char
          char valor1 = this.getValorChar();
          char valor2 = otro.getValorChar();
          switch (op) {
          case DISTINTO:
            this.setValorBool(valor1!=valor2);
            break;
          case IGUAL:
            this.setValorBool(valor1==valor2);
            break;
          case MAYIGUAL:
            this.setValorBool(valor1>=valor2);
            break;
          case MAYOR:
            this.setValorBool(valor1>valor2);
            break;
          case MENIGUAL:
            this.setValorBool(valor1<=valor2);
            break;
          case MENOR:
            this.setValorBool(valor1<valor2);
            break;
          default:
            break;
          }
        } // tenemos el valor sea el tipo que sea:
        this.codigo = gc.constBool(valorBool);
      }
    }
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public boolean esVector() {
    return esVector;
  }

  public void setEsVector(boolean esVector) {
    this.esVector = esVector;
  }

  public boolean esConst() {
    return esConst;
  }

  public void setEsConst(boolean esConst) {
    this.esConst = esConst;
  }
}
