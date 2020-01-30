/* The following code was generated by JFlex 1.6.0 */

import java.nio.charset.StandardCharsets;
import java_cup.runtime.*;
import java.util.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.0
 * from the specification file <tt>app/src/PLXC.flex</tt>
 */
class Yylex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\21\1\20\1\0\1\21\1\3\22\0\1\21\1\35\1\0"+
    "\3\0\1\36\1\4\1\24\1\25\1\2\1\30\1\23\1\31\1\0"+
    "\1\1\1\17\11\7\1\0\1\22\1\33\1\32\1\34\2\0\6\16"+
    "\24\15\1\0\1\5\2\0\1\15\1\0\1\43\1\10\1\41\1\51"+
    "\1\45\1\12\1\15\1\42\1\40\2\15\1\46\1\15\1\11\1\50"+
    "\1\44\1\15\1\13\1\47\1\14\1\6\1\15\1\52\3\15\1\26"+
    "\1\37\1\27\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uff92\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\1\1\5\1\6"+
    "\1\5\1\6\1\4\1\7\1\10\1\11\1\12\1\13"+
    "\1\14\1\15\1\16\1\17\1\20\1\21\1\22\2\1"+
    "\6\5\1\4\3\0\1\5\2\0\1\23\1\24\1\25"+
    "\1\26\1\27\1\30\1\5\1\31\3\5\1\32\1\5"+
    "\1\0\1\33\2\0\1\34\2\0\1\35\4\5\1\36"+
    "\3\0\1\37\1\5\1\40\1\5\1\0\1\41\1\0"+
    "\1\42\1\43\1\0\1\44\1\0\1\45";

  private static int [] zzUnpackAction() {
    int [] result = new int[80];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\53\0\126\0\53\0\201\0\254\0\327\0\u0102"+
    "\0\u012d\0\53\0\53\0\53\0\53\0\u0158\0\53\0\53"+
    "\0\53\0\53\0\53\0\u0183\0\u01ae\0\u01d9\0\u0204\0\u022f"+
    "\0\u025a\0\u0285\0\u02b0\0\u02db\0\u0306\0\u0331\0\u035c\0\u0387"+
    "\0\u03b2\0\u03dd\0\u0408\0\u0433\0\u045e\0\u0489\0\53\0\53"+
    "\0\53\0\53\0\53\0\53\0\u04b4\0\327\0\u04df\0\u050a"+
    "\0\u0535\0\327\0\u0560\0\u058b\0\53\0\u05b6\0\u05e1\0\327"+
    "\0\u060c\0\u0637\0\327\0\u0662\0\u068d\0\u06b8\0\u06e3\0\53"+
    "\0\u070e\0\u0739\0\u0764\0\327\0\u078f\0\327\0\u07ba\0\u07e5"+
    "\0\53\0\u0810\0\327\0\327\0\u083b\0\53\0\u0866\0\53";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[80];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\2\1\7\1\10"+
    "\2\7\1\11\4\7\1\12\2\13\1\14\1\15\1\16"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26"+
    "\1\27\1\30\1\31\1\32\1\33\2\7\1\34\1\35"+
    "\3\7\1\36\1\37\54\0\1\40\1\41\70\0\1\13"+
    "\32\0\3\42\1\0\1\42\1\43\12\42\1\0\32\42"+
    "\6\0\12\7\20\0\13\7\7\0\1\10\7\0\1\10"+
    "\41\0\12\7\20\0\10\7\1\44\2\7\40\0\1\45"+
    "\1\46\43\0\1\47\52\0\1\50\52\0\1\51\52\0"+
    "\1\52\56\0\1\53\53\0\1\54\21\0\3\7\1\55"+
    "\1\56\5\7\20\0\13\7\6\0\12\7\20\0\2\7"+
    "\1\57\10\7\6\0\5\7\1\60\4\7\20\0\13\7"+
    "\6\0\12\7\20\0\6\7\1\61\4\7\6\0\12\7"+
    "\20\0\10\7\1\62\2\7\6\0\12\7\20\0\2\7"+
    "\1\63\10\7\3\40\1\5\14\40\1\13\32\40\2\41"+
    "\1\64\50\41\4\0\1\65\52\0\1\65\1\66\1\67"+
    "\1\0\5\66\44\0\5\7\1\70\4\7\20\0\13\7"+
    "\11\0\1\71\103\0\1\72\16\0\6\7\1\73\3\7"+
    "\20\0\13\7\6\0\12\7\20\0\3\7\1\74\7\7"+
    "\6\0\12\7\20\0\1\75\12\7\6\0\12\7\20\0"+
    "\7\7\1\76\3\7\6\0\12\7\20\0\1\77\12\7"+
    "\1\41\1\13\1\64\50\41\4\0\1\100\55\0\2\101"+
    "\1\0\1\101\3\0\2\101\21\0\1\101\1\0\1\101"+
    "\1\0\1\101\3\0\1\101\15\0\1\102\101\0\1\103"+
    "\15\0\5\7\1\104\4\7\20\0\13\7\6\0\3\7"+
    "\1\105\6\7\20\0\13\7\6\0\12\7\20\0\5\7"+
    "\1\106\5\7\6\0\12\7\20\0\6\7\1\107\4\7"+
    "\7\0\2\110\1\0\1\110\3\0\2\110\21\0\1\110"+
    "\1\0\1\110\1\0\1\110\3\0\1\110\26\0\1\111"+
    "\40\0\1\112\45\0\6\7\1\113\3\7\20\0\13\7"+
    "\6\0\12\7\20\0\5\7\1\114\5\7\7\0\2\115"+
    "\1\0\1\115\3\0\2\115\21\0\1\115\1\0\1\115"+
    "\1\0\1\115\3\0\1\115\26\0\1\116\34\0\2\117"+
    "\1\0\1\117\3\0\2\117\21\0\1\117\1\0\1\117"+
    "\1\0\1\117\3\0\1\117\5\0\1\120\46\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2193];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\5\1\4\11\1\1\5\11"+
    "\15\1\3\0\1\1\2\0\6\11\7\1\1\0\1\11"+
    "\2\0\1\1\2\0\5\1\1\11\3\0\4\1\1\0"+
    "\1\11\1\0\2\1\1\0\1\11\1\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[80];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
    private char getSpecialChar(String control) {
        switch (control) {
            case "b": return '\b';
            case "n": return '\n';
            case "f": return '\f';
            case "r": return '\r';
            case "t": return '\t';
            case "'": return '\'';
            case "\"": return '\"';
            default: return '\\';
        }
    }

    private String getCharInfo(String txt) {
        String out = "";
        byte[] codes = txt.getBytes(StandardCharsets.US_ASCII);
        for(byte b : codes) {
            out += b + " ";
        }
        return out;
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Yylex(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 158) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;           
    int totalRead = 0;
    while (totalRead < requested) {
      int numRead = zzReader.read(zzBuffer, zzEndRead + totalRead, requested - totalRead);
      if (numRead == -1) {
        break;
      }
      totalRead += numRead;
    }

    if (totalRead > 0) {
      zzEndRead += totalRead;
      if (totalRead == requested) { /* possibly more input available */
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      return false;
    }

    // totalRead = 0: End of stream
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { String txt = yytext(); throw new Error("Illegal character:\n" + txt + "\n" + getCharInfo(txt) + "\n");
          }
        case 38: break;
        case 2: 
          { return symbol(sym.DIV);
          }
        case 39: break;
        case 3: 
          { return symbol(sym.TIMES);
          }
        case 40: break;
        case 4: 
          { /* ignore */
          }
        case 41: break;
        case 5: 
          { return symbol(sym.ID, yytext());
          }
        case 42: break;
        case 6: 
          { return symbol(sym.NUMBER, yytext());
          }
        case 43: break;
        case 7: 
          { return symbol(sym.SEMI);
          }
        case 44: break;
        case 8: 
          { return symbol(sym.COMMA);
          }
        case 45: break;
        case 9: 
          { return symbol(sym.LPAREN);
          }
        case 46: break;
        case 10: 
          { return symbol(sym.RPAREN);
          }
        case 47: break;
        case 11: 
          { return symbol(sym.LCURLY);
          }
        case 48: break;
        case 12: 
          { return symbol(sym.RCURLY);
          }
        case 49: break;
        case 13: 
          { return symbol(sym.PLUS);
          }
        case 50: break;
        case 14: 
          { return symbol(sym.MINUS);
          }
        case 51: break;
        case 15: 
          { return symbol(sym.ASSIGN);
          }
        case 52: break;
        case 16: 
          { return symbol(sym.LT);
          }
        case 53: break;
        case 17: 
          { return symbol(sym.GT);
          }
        case 54: break;
        case 18: 
          { return symbol(sym.NOT);
          }
        case 55: break;
        case 19: 
          { return symbol(sym.EQ);
          }
        case 56: break;
        case 20: 
          { return symbol(sym.LE);
          }
        case 57: break;
        case 21: 
          { return symbol(sym.GE);
          }
        case 58: break;
        case 22: 
          { return symbol(sym.NEQ);
          }
        case 59: break;
        case 23: 
          { return symbol(sym.AND);
          }
        case 60: break;
        case 24: 
          { return symbol(sym.OR);
          }
        case 61: break;
        case 25: 
          { return symbol(sym.IF, Translator.getNewLabel());
          }
        case 62: break;
        case 26: 
          { return symbol(sym.DO, Translator.getNewLabel());
          }
        case 63: break;
        case 27: 
          { return symbol(sym.CHAR_CONST, yytext());
          }
        case 64: break;
        case 28: 
          { return symbol(sym.FOR, new Condition());
          }
        case 65: break;
        case 29: 
          { return symbol(sym.INT);
          }
        case 66: break;
        case 30: 
          { // Preprocess it
    String special = yytext().replace("'", "").substring(1);
    String result = String.valueOf(getSpecialChar(special)); // matches each letter with its char equivalent

    return symbol(sym.CHAR_CONST, "'" + result + "'");
          }
        case 67: break;
        case 31: 
          { return symbol(sym.CHAR);
          }
        case 68: break;
        case 32: 
          { return symbol(sym.ELSE, Translator.getNewLabel());
          }
        case 69: break;
        case 33: 
          { return symbol(sym.INT_CAST);
          }
        case 70: break;
        case 34: 
          { return symbol(sym.PRINT);
          }
        case 71: break;
        case 35: 
          { return symbol(sym.WHILE, Translator.getNewLabel());
          }
        case 72: break;
        case 36: 
          { return symbol(sym.CHAR_CAST);
          }
        case 73: break;
        case 37: 
          { // We have to preprocess the string to remove the double backslashes
    String result = yytext().replace("'", "").substring(2); // takes out \', \\ and u
    result = String.valueOf((char) Integer.parseInt(result, 16)); // converts from hex to dec then to char

    return symbol(sym.CHAR_CONST, "'" + result + "'");
          }
        case 74: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
