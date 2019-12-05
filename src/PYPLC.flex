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
LineComment = "#" {InputCharacter}* {LineBreak}?
MultiLineComment = "'''" ~"'''"

InputCharacter = [^\r\n]
Identifier = [A-Za-z_][A-Za-z0-9_]*
Number = 0 | [1-9][0-9]*

// Indentation = \t|\ {2}
// Indentation = \t|\ {4}
// Indentation = \t|\ {2,4}
Indentation = \t{1,20}|\ {2,20}
LineBreak = \n|\r|\r\n
SingleSpace = [ \f]
WhiteSpace = {LineBreak} | {SingleSpace}

%% // Token definitions - highest to lowest

"," { return symbol(sym.COMMA); }
":" { return symbol(sym.COLON); }
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }

"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"/" { return symbol(sym.DIV); }
"=" { return symbol(sym.ASSIGN); }
"%" { return symbol(sym.MOD); }
"**" { return symbol(sym.POWER); }
"//" { return symbol(sym.INTDIV); }

"<" { return symbol(sym.LT); }
">" { return symbol(sym.GT); }
"==" { return symbol(sym.EQ); }
"!=" { return symbol(sym.NEQ); }
"<=" { return symbol(sym.LE); }
">=" { return symbol(sym.GE); }

"print" { return symbol(sym.PRINT); }
"in" { return symbol(sym.IN); }
"else" { return symbol(sym.ELSE); }
"if" { return symbol(sym.IF, Translator.getNewLabel()); }
"elif" { return symbol(sym.ELIF, Translator.getNewLabel()); }
"for" { return symbol(sym.FOR, Translator.getNewLabel()); }
"while" { return symbol(sym.WHILE, new Condition()); }
"range" { return symbol(sym.RANGE, null); /* We assign the condition on behalf of the range limits */ }

{Identifier} { return symbol(sym.ID, yytext()); }
{Number} { return symbol(sym.NUMBER, yytext()); }

{Indentation} { return symbol(sym.TAB); }
{WhiteSpace} { /* ignore */ }
{Comment} { /* ignore */ }

// only catches 1 character at a time
// [^] { throw new Error("Illegal character: " + yytext()); }
[^] { /* ignore */ }
