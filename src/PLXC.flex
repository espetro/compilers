import java.nio.charset.StandardCharsets;
import java_cup.runtime.*;
import java.util.*;

%% // options, scanner code and macros

%unicode
%cup
%line
%column

%{
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
%}

Comment = {LineComment} | {MultiLineComment}
LineComment = "//" {InputCharacter}* {LineBreak}?
MultiLineComment = "/*" ~"*/"

InputCharacter = [^\r\n]
// BEWARE in "Unicode" that we're reading the backslash too: then at parsing it'll be recognized as ['\\', 'u', ...]
UnicodeCharacter = \' \\u [0-9A-Fa-f]{4} \'
SpecialCharacter = \' ("\\b" | "\\n" | "\\f" | "\\r" | "\\t" | "\\\\" | "\'" | "\"") \'
Character = \' [^\r\n] \'
Identifier = [A-Za-z_][A-Za-z0-9_]*
Number = 0 | [1-9][0-9]*

LineBreak = \n|\r|\r\n
SingleSpace = [ \f\t]
WhiteSpace = {LineBreak} | {SingleSpace}

%% // Token definitions - highest to lowest

";" { return symbol(sym.SEMI); }
"," { return symbol(sym.COMMA); }
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
"{" { return symbol(sym.LCURLY); }
"}" { return symbol(sym.RCURLY); }

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

"!" { return symbol(sym.NOT); } // also switches char to Mayus
"&&" { return symbol(sym.AND); }
"||" { return symbol(sym.OR); }

"~" { return symbol(sym.TILDE); }

"int" { return symbol(sym.INT); }
"char" { return symbol(sym.CHAR); }
"(int)" { return symbol(sym.INT_CAST); }
"(char)" { return symbol(sym.CHAR_CAST); }

"print" { return symbol(sym.PRINT); }
"if" { return symbol(sym.IF, Translator.getNewLabel()); }
"else" { return symbol(sym.ELSE, Translator.getNewLabel()); }
"for" { return symbol(sym.FOR, new Condition()); }
"do" { return symbol(sym.DO, Translator.getNewLabel()); }
"while" { return symbol(sym.WHILE, Translator.getNewLabel()); }

{Identifier} { return symbol(sym.ID, yytext()); }
{Character} { return symbol(sym.CHAR_CONST, yytext()); }
{UnicodeCharacter} {
    // We have to preprocess the string to remove the double backslashes
    String result = yytext().replace("'", "").substring(2); // takes out \', \\ and u
    result = String.valueOf((char) Integer.parseInt(result, 16)); // converts from hex to dec then to char

    return symbol(sym.CHAR_CONST, "'" + result + "'");
}
{SpecialCharacter} {
    // Preprocess it
    String special = yytext().replace("'", "").substring(1);
    String result = String.valueOf(getSpecialChar(special)); // matches each letter with its char equivalent

    return symbol(sym.CHAR_CONST, "'" + result + "'");
}
{Number} { return symbol(sym.NUMBER, yytext()); }

{WhiteSpace} { /* ignore */ }
{Comment} { /* ignore */ }

// only catches 1 character at a time
[^] { String txt = yytext(); throw new Error("Illegal character:\n" + txt + "\n" + getCharInfo(txt) + "\n"); }
