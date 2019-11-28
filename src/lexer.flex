import java_cup.runtime.*;
import java.util.*;

%% // options, scanner code and macros

%class Lexer
%unicode
%cup
%line
%column

%{
    // public ArrayList<String> ignored = new ArrayList<>();

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

Identifier = [A-Za-z_][A-Za-z0-9_]*
Number = 0 | [1-9][0-9]*

LineBreak = \n|\r|\r\n
SingleSpace = [ \f]
WhiteSpace = {LineBreak} | {SingleSpace}

%% // Token definitions - highest to lowest

";" { return symbol(sym.SEMI); }
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }

"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"**" { return symbol(sym.POWER); }
"/" { return symbol(sym.DIV); }
"//" { return symbol(sym.INTDIV); }
"=" { return symbol(sym.ASSIGN); }
"%" { return symbol(sym.MOD); }

"not" { return symbol(sym.NOT); }
"and" { return symbol(sym.AND); }
"or" { return symbol(sym.OR); }

"<" { return symbol(sym.LT); }
">" { return symbol(sym.GT); }
"==" { return symbol(sym.EQ); }
"!=" { return symbol(sym.NEQ); }
"<=" { return symbol(sym.LET); }
">=" { return symbol(sym.GET); }
"True" { return symbol(sym.TRUE); }
"False" { return symbol(sym.FALSE); }


"print" { return symbol(sym.PRINT); }
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }
"while" { return symbol(sym.WHILE); }
"return" { return symbol(sym.RETURN); }
\t|([ \f]{4}) { return symbol(sym.TAB); } // block indentation

{Identifier} { return symbol(sym.ID, yytext()); }
{Number} { return symbol(sym.NUMBER, new Integer(yytext())); }

{WhiteSpace} { /* ignore */ }

// only catches 1 character at a time
. { throw new Error("Illegal character <"+yytext()+">"); }
