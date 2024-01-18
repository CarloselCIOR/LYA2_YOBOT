import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column
%{
    private Token token(String lexeme, String lexicalComp, int line, int column){
        return new Token(lexeme, lexicalComp, line+1, column+1);
    }
%}
/* Variables básicas de comentarios y espacios */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Identificador */
Letra = [A-Za-z]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*

/* Número */
Numero = 0 | [1-9][0-9]*
%%

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }

/* Identificador */
"_"{Identificador} {return token(yytext(), "IDENTIFICADOR", yyline, yycolumn); }

/* Identificador */
"$"{Identificador} {return token(yytext(), "ID", yyline, yycolumn); }

/* Distancia */
distancia |
DISTANCIA |
Distancia { return token(yytext(), "DISTANCIA", yyline, yycolumn); }

/* Booleano */
booleano |
BOOLEANO |
Booleano { return token(yytext(), "BOOLEANO", yyline, yycolumn); }

/* String */
cadena |
CADENA |
Cadena { return token(yytext(), "STRING", yyline, yycolumn); }

/* ENTERO */
entero |
ENTERO |
Entero { return token(yytext(), "ENTERO", yyline, yycolumn); }


/* Texto */
texto |
TEXTO |
Texto { return token(yytext(), "TEXTO", yyline, yycolumn); }

/* ARREGLO */
arreglo |
Arreglo |
ARREGLO { return token(yytext(), "ARREGLO", yyline, yycolumn); }


/* Condicionales */
"SI" |
"si" |
"Si" { return token(yytext(), "ESTRUCTURA_SI", yyline, yycolumn); }
"SINO" |
"sino" |
"Sino" { return token(yytext(), "ESTRUCTURA_SINO", yyline, yycolumn); }
"ELIF" |
"elif" |
"ElIf" { return token(yytext(), "ESTRUCTURA_SINOSI", yyline, yycolumn); }

"VERDADERO" |
"verdadero" |
"Verdadero" |
"FALSO" |
"falso" |
"Falso" { return token(yytext(), "BOOLEANVALOR", yyline, yycolumn); }



/* Ciclos */
"repetir" |
"REPETIR" |
"Repetir" { return token(yytext(), "REPETIR", yyline, yycolumn); }

/*Ciclo Mientras */
"mientras" |
"Mientras" |
"MIENTRAS" { return token(yytext(), "MIENTRAS", yyline, yycolumn); }

/* Ciclo Para */
"para" |
"PARA" |
"Para" { return token(yytext(), "CICLO_PARA", yyline, yycolumn); }
"conpaso" |
"Conpaso" |
"CONPASO" { return token(yytext(), "CICLO_PARA_CONPASO", yyline, yycolumn); }
"hacer" |
"Hacer" |
"HACER" { return token(yytext(), "CICLO_PARA_HACER", yyline, yycolumn); }
"hasta" |
"Hasta" |
"HASTA" { return token(yytext(), "CICLO_PARA_HASTA", yyline, yycolumn); }

"finsi" |
"finsino" { return token(yytext(), "FINALES", yyline, yycolumn); }

/* Numero */
{Numero} { return token(yytext(), "NUMERO", yyline, yycolumn); }



/* Decimal */
{Numero} "." {Numero} { return token(yytext(), "DECIMAL", yyline, yycolumn); }

/* Operadores Agrupacion */
"(" { return token(yytext(), "PARENTESIS_A", yyline, yycolumn); }
")" { return token(yytext(), "PARENTESIS_C", yyline, yycolumn); }
"{" { return token(yytext(), "LLAVE_A", yyline, yycolumn); }
"}" { return token(yytext(), "LLAVE_C", yyline, yycolumn); }
"[" { return token(yytext(), "CORCHETE_A", yyline, yycolumn); }
"]" { return token(yytext(), "CORCHETE_C", yyline, yycolumn); }

 
/* Signos Puntuacion */
"," { return token(yytext(), "COMA", yyline, yycolumn); }
";" { return token(yytext(), "PUNTO_COMA", yyline, yycolumn); }
":" { return token(yytext(), "DOS_PUNTOS", yyline, yycolumn); }
"." { return token(yytext(), "PUNTO", yyline, yycolumn); }
"'"   { return token(yytext(), "COMILLA_SIMPLE", yyline, yycolumn); }
'([a-zA-Z0-9_.-]*{EspacioEnBlanco}*)*' { return token(yytext(), "CADENA", yyline, yycolumn); }


/* Asignacion */
"=" { return token(yytext(), "ASIGNACION", yyline, yycolumn); }


/* Asignacion */
"-->" { return token(yytext(), "IGUALACION", yyline, yycolumn); }

/* movimiento */
adelante |
atras |
izquierda |
derecha |
ADELANTE |
ATRAS |
IZQUIERDA |
DERECHA |
Adelante |
Atras |
Izquierda |
Derecha { return token(yytext(), "MOVIMIENTO", yyline, yycolumn); }


/* plantacion */
plantar |
Plantar |
PLANTAR { return token(yytext(), "PLANTACION", yyline, yycolumn); }


/* obstaculo */
verAdelante |
verIzquierda |
verDerecha |
verAtras { return token(yytext(), "OBSTACULO", yyline, yycolumn); }


/* luces */
Luces |
LUCES |
luces { return token(yytext(), "LUCES", yyline, yycolumn); }

/* imprimir */
Imprimir |
IMPRIMIR |
imprimir { return token(yytext(), "IMPRIMIR", yyline, yycolumn); }


/* Identificador Erroneo */
{Identificador} { return token (yytext(), "ERROR_1", yyline, yycolumn); } 

. { return token(yytext(), "ERROR", yyline, yycolumn); }