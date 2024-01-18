import compilerTools.TextColor;
import java.awt.Color;

%%
%class LexerColor
%type TextColor
%char
%{
    private TextColor textColor(long start, int size, Color color){
        return new TextColor((int) start, size, color);
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
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); }
{EspacioEnBlanco} { /*Ignorar*/ }


'([a-zA-Z0-9_.-]*{EspacioEnBlanco}*)*' { return textColor(yychar, yylength(), new Color(0, 0, 153)); }

/* Distancia */
distancia |
DISTANCIA |
Distancia { return textColor(yychar, yylength(), new Color(0, 0, 255)); }

/* Boolean */
booleano |
BOOLEANO |
Booleano { return textColor(yychar, yylength(), new Color(0, 0, 255)); }

/* String */
cadena |
CADENA |
Cadena { return textColor(yychar, yylength(), new Color(0, 0, 255)); }

/* ENTERO */
entero |
ENTERO |
Entero { return textColor(yychar, yylength(), new Color(0, 0, 255)); }

/* Texto */
texto |
TEXTO |
Texto { return textColor(yychar, yylength(), new Color(0, 255, 255)); }

/* Arreglo */
arreglo |
Arreglo |
ARREGLO { return textColor(yychar, yylength(), new Color(102, 255, 102)); }

/* Condicionales */
"SI" |
"si" |
"Si" { return textColor(yychar, yylength(), new Color(0, 139, 139)); }
"SINO" |
"sino" |
"SINO" { return textColor(yychar, yylength(), new Color(0, 139, 139)); }
"VERDADERO" |
"verdadero" |
"Verdadero" { return textColor(yychar, yylength(), new Color(0, 204, 0)); }
"FALSO" |
"falso" |
"Falso" { return textColor(yychar, yylength(), new Color(255, 0, 0)); }
"ELIF" |
"elif" |
"ElIf" { return textColor(yychar, yylength(), new Color(0, 153, 153)); }

/* Ciclos */
"repetir" |
"REPETIR" |
"Repetir" { return textColor(yychar, yylength(), new Color(255, 0, 127)); }


/* Ciclo Mientras */
"mientras" |
"Mientras" |
"MIENTRAS" { return textColor(yychar, yylength(), new Color(255, 0, 127)); }

/* Ciclo Para */
"para" |
"PARA" |
"Para" { return textColor(yychar, yylength(), new Color(255, 0, 127)); }
"conpaso" |
"Conpaso" |
"CONPASO" { return textColor(yychar, yylength(), new Color(127, 255, 0)); }
"hacer" |
"Hacer" |
"HACER" { return textColor(yychar, yylength(), new Color(127, 255, 0)); }
"HASTA" |
"hasta" |
"Hasta" { return textColor(yychar, yylength(), new Color(127, 255, 0)); }

"finsi" |
"finsino" { return textColor(yychar, yylength(), new Color(0, 139, 139)); }

/* Movimiento */
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
Derecha { return textColor(yychar, yylength(), new Color(34,139,34)); }

/* Plantar */
plantar |
Plantar |
PLANTAR { return textColor(yychar, yylength(), new Color(34,139,34)); }


/* luces*/
Luces |
LUCES |
luces { return textColor(yychar, yylength(), new Color(139, 128, 0)); }


/* obstaculo */
verAdelante |
verIzquierda |
verDerecha |
verAtras { return textColor(yychar, yylength(), new Color(255, 153, 51)); }

/* imprimir */
Imprimir |
IMPRIMIR |
imprimir { return textColor(yychar, yylength(), new Color(0, 153, 0)); }


/* Identificador */
"_"{Identificador} { return textColor(yychar, yylength(), new Color(255,140,0)); }

. { /* Ignorar */ }