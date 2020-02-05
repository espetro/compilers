import java.nio.charset.StandardCharsets;
import java_cup.runtime.*;
import java.util.*;

%% // options, scanner code and macros

%unicode
%cup
%line
%column

%{
    StringBuffer stringBuff = new StringBuffer();
    boolean isCharSent = false;

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

Comment = {LineComment} | {MultiLineComment}
LineComment = "//" {InputCharacter}* {LineBreak}?
MultiLineComment = "/*" ~"*/"

InputCharacter = [^\r\n]
Unicode = [0-9A-Fa-f]{4}
Identifier = [A-Za-z_][A-Za-z0-9_]*
TemporalIdentifier = "t"{Number}

// see https://codingfox.com/2-6-constants-in-c-language-part-1/
Number = 0 | [1-9][0-9]*
OctalNumber = "0" {Number}
HexNumber = "0x" {Number}

LineBreak = \n|\r|\r\n
SingleSpace = [ \f\t]
WhiteSpace = {LineBreak} | {SingleSpace}

%xstate STRING, CHAR

%% // Token definitions - highest to lowest

";" { return symbol(sym.SEMI); }
"," { return symbol(sym.COMMA); }
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
"{" { return symbol(sym.LCURLY); }
"}" { return symbol(sym.RCURLY); }
"[" { return symbol(sym.LBRACKET); }
"]" { return symbol(sym.RBRACKET); }

"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"/" { return symbol(sym.DIV); }

"=" { return symbol(sym.ASSIGN); }

"<" { return symbol(sym.LT); }
">" { return symbol(sym.GT); }
"==" { return symbol(sym.EQ); }
"!=" { return symbol(sym.NEQ); }
"<=" { return symbol(sym.LE); }
">=" { return symbol(sym.GE); }

"!" { return symbol(sym.NOT); }
"&&" { return symbol(sym.AND); }
"||" { return symbol(sym.OR); }

"int" { return symbol(sym.INT); }
"char" { return symbol(sym.CHAR); }
"string" { return symbol(sym.STRING); }

".length" { return symbol(sym.LENGTH_PROP); }
"(char)" { return symbol(sym.CHAR_CAST); }
"(string)" { return symbol(sym.STRING_CAST); }

"print" { return symbol(sym.PRINT); }
"if" { return symbol(sym.IF, Translator.getNewLabel()); }
"else" { return symbol(sym.ELSE, Translator.getNewLabel()); }
"for" { return symbol(sym.FOR, new Condition()); }
"do" { return symbol(sym.DO, Translator.getNewLabel()); }
"while" { return symbol(sym.WHILE, Translator.getNewLabel()); }

{TemporalIdentifier} { return symbol(sym.ID, "_" + yytext()); } // t0 is parsed as _t0
{Identifier} { return symbol(sym.ID, yytext()); }

\' { isCharSent = false; yybegin(CHAR); }
\" { stringBuff.setLength(0); yybegin(STRING); }

{Number} { return symbol(sym.NUMBER, yytext()); }
{OctalNumber} {
    int result = Integer.parseInt(yytext(), 8);
    return symbol(sym.NUMBER, String.valueOf(result));
}
{HexNumber} {
    int result = Integer.parseInt(yytext().substring(2), 16);
    return symbol(sym.NUMBER, String.valueOf(result));
}

{WhiteSpace} { /* ignore */ }
{Comment} { /* ignore */ }

<CHAR> {
    \' {
        if (!isCharSent) { throw new Error("empty character literal"); }
        yybegin(YYINITIAL);
    }

    \\u {Unicode} {
        String result = yytext().substring(2);
        result = "" + ((char) Integer.parseInt(result, 16));
        isCharSent = true;
        return symbol(sym.CHAR_CONST, "'" + result + "'");
    }
    \\b { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\b" + "'"); }
    \\n { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\n" + "'"); }
    \\f { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\f" + "'"); }
    \\r { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\r" + "'"); }
    \\t { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\t" + "'"); }
    \\ { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\\" + "'"); }
    \\\' { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\'" + "'"); }
    \\\" { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + "\"" + "'"); }

    // a single "\" is illegal
    \\ { throw new Error("Illegal character <" + yytext() + ">"); }

    // this one can also read all of the characters above - but it's the last one checked
    [^\r\n\'\\] { isCharSent = true; return symbol(sym.CHAR_CONST, "'" + yytext() + "'"); }
}

<STRING> {
    \" { yybegin(YYINITIAL); return symbol(sym.STRING_CONST, "\"" + stringBuff.toString() + "\""); }

    \\u {Unicode} {
        String result = yytext().substring(2);
        result = "" + ((char) Integer.parseInt(result, 16));
        stringBuff.append(result);
    }

    \\b { stringBuff.append("\b"); }
    \\n { stringBuff.append("\n"); }
    \\f { stringBuff.append("\f"); }
    \\r { stringBuff.append("\r"); }
    \\t { stringBuff.append("\t"); }
    \\\\ { stringBuff.append("\\"); }
    \\\' { stringBuff.append("\'"); }
    \\\" { stringBuff.append("\""); }

    // a single "\" is illegal
    \\ { throw new Error("Illegal character <" + yytext() + ">"); }

    // this one can also read all of the characters above - but it's the last one checked
    [^\r\n\"\\]+ { stringBuff.append(yytext()); }
}

// only catches 1 character at a time
[^] { throw new Error("Illegal character: <" + yytext() + ">\n"); }
