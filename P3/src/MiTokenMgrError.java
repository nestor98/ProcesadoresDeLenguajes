public class MiTokenMgrError extends TokenMgrError {

	public MiTokenMgrError() {
		// TODO Auto-generated constructor stub
	}

	public MiTokenMgrError(String message, int reason) {
		super(message, reason);
		// TODO Auto-generated constructor stub
	}
	
	 /**
	   * Returns a detailed message for the Error when it is thrown by the
	   * token manager to indicate a lexical error.
	   * Parameters :
	   *    EOFSeen     : indicates if EOF caused the lexical error
	   *    curLexState : lexical state in which this error occurred
	   *    errorLine   : line number when the error occurred
	   *    errorColumn : column number when the error occurred
	   *    errorAfter  : prefix that was seen before this error occurred
	   *    curchar     : the offending character
	   * Note: You can customize the lexical error message by modifying this method.
	   */
	// Redefinido para que devuelva comentario de tipo "ERROR L�XICO (<l�nea, columna>): s�mbolo no reconocido: <s�mbolo>"
	  protected static String miLexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar) {
	    return("ERROR L�XICO (" + errorLine + "," + errorColumn +"): s�mbolo no reconocido: "+ errorAfter);
	  }

	public MiTokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter,
			char curChar, int reason) {
		super(miLexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
		// System.out.println("Nunca llego a esta linea");
	}

}
