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


"int" { return symbol(sym.INT); }
"main" { return symbol(sym.MAIN); }
"return" { return symbol(sym.RETURN); }

"if" { return symbol(sym.IF, Translator.getNewLabel()); }
"else" { return symbol(sym.ELSE, Translator.getNewLabel()); }
"while" { return symbol(sym.WHILE, Translator.getNewLabel()); }

{Identifier} { return symbol(sym.ID, yytext()); }
{Number} { return symbol(sym.NUMBER, yytext()); }

{WhiteSpace} { /* ignore */ }
{Comment} { /* ignore */ }

// only catches 1 character at a time
[^] { String txt = yytext(); throw new Error("Illegal character:\n" + txt + /*^"\n" + getCharInfo(txt) + */ "\n"); }
