import java.nio.charset.StandardCharsets;
import java_cup.runtime.*;
import java.util.*;

%% // options, scanner code and macros

%unicode
%cup
%line
%column

%{
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
UnicodeCharacter = \' \\u [0-9A-Fa-f]{4} \'
SpecialCharacter = \' ("\\b" | "\\n" | "\\f" | "\\r" | "\\t" | "\\\\" | "\'" | "\"") \'
Character = \' {InputCharacter}+ \'
String = \" {InputCharacter}* \"
Identifier = [A-Za-z_][A-Za-z0-9_]*
TemporalIdentifier = "t"{Number}

// see https://codingfox.com/2-6-constants-in-c-language-part-1/
Number = 0 | [1-9][0-9]*
OctalNumber = "0" {Number}
HexNumber = "0x" {Number}

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

"print" { return symbol(sym.PRINT); }
"if" { return symbol(sym.IF, Translator.getNewLabel()); }
"else" { return symbol(sym.ELSE, Translator.getNewLabel()); }
"for" { return symbol(sym.FOR, new Condition()); }
"do" { return symbol(sym.DO, Translator.getNewLabel()); }
"while" { return symbol(sym.WHILE, Translator.getNewLabel()); }

{TemporalIdentifier} {
    throw new RuntimeException("Cannot use temporal variable names: " + yytext());
}
{Identifier} { return symbol(sym.ID, yytext()); }

{UnicodeCharacter} {
    // We have to preprocess the string to remove the double backslashes
    String result = yytext().replace("'", "").substring(2); // takes out \', \\ and u
    result = String.valueOf((char) Integer.parseInt(result, 16)); // converts from hex to dec then to char

    return symbol(sym.CHAR_CONST, "'" + result + "'");
}
{SpecialCharacter} {
    // Preprocess it
    String special = yytext().replace("'", "").substring(1);
    String result = String.valueOf(Chars.matchControl(special)); // matches each letter with its char equivalent

    return symbol(sym.CHAR_CONST, "'" + result + "'");
}
{Character} { return symbol(sym.CHAR_CONST, yytext()); } // all char constants have \'

{String} { return symbol(sym.STRING_CONST, yytext()); } // all string constants have \"

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

// only catches 1 character at a time
[^] { String txt = yytext(); throw new Error("Illegal character: (" + txt + ")\n"); }
