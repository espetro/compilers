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
"{" { return symbol(sym.LCURLY); }
"}" { return symbol(sym.RCURLY); }

"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"/" { return symbol(sym.DIV); }
"=" { return symbol(sym.ASSIGN); }
"%" { return symbol(sym.MOD); }

"<" { return symbol(sym.LT); }
">" { return symbol(sym.GT); }
"==" { return symbol(sym.EQ); }
"!=" { return symbol(sym.NEQ); }
"<=" { return symbol(sym.LE); }
">=" { return symbol(sym.GE); }

"!" { return symbol(sym.NOT); }
"&&" { return symbol(sym.AND); }
"||" { return symbol(sym.OR); }
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }


"print" { return symbol(sym.PRINT); }
"if" { return symbol(sym.IF, yytext()); }
"else" { return symbol(sym.ELSE, yytext()); }
"do" { return symbol(sym.DO, new Condition()); }
"while" { return symbol(sym.WHILE, new Condition()); }
"for" { return symbol(sym.FOR, new Condition()); }
//"return" { return symbol(sym.RETURN); }

{Identifier} { return symbol(sym.ID, yytext()); }
{Number} { return symbol(sym.NUMBER, yytext()); }

// \t|[ \f]{4} { System.out.println("TAB found"); }
{WhiteSpace} { /* ignore */ }

// only catches 1 character at a time
. { throw new Error("Illegal character <"+yytext()+">"); }
