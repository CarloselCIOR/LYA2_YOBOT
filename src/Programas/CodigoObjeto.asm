include "p16F84A.inc" 
__CONFIG	_CP_OFF &  _WDT_OFF & _PWRTE_ON & _XT_OSC

LIST P=16F84A
portb equ h'06'
timer equ h'01'
status equ h'03'
Cont1 equ 0x0E
contador equ 0x0D
contadorRep1 equ 0x10
contadorRep2 equ 0x11
contadorRep3 equ 0x12
contadorRep4 equ 0x13
contadorRep5 equ 0x14
contadorRep6 equ 0x15
contadorRep7 equ 0x16
contadorRep8 equ 0x17
contadorRep0 equ 0x18
contadorRep10 equ 0x19
Cont2 equ 0x0F
org 0
; --------- iniciamos puertos y temporizador --
bsf status,5 ; Pasamos al banco 1
movlw b'11010101' ; Cargamos el valor en W
movwf timer ; Registro Option del timer
movlw b'00110000' ; Cargamos 00 en el registro
movwf portb ; Puerto B (salidas)
bcf status,5 ; Pasamos al banco 0
clrf portb ; Ponemos la salida PORTB a 0
;======================================
;IZQUIERDA 1s
izquierda MACRO
bsf portb,0 ; Ponemos un 1 en la salida 0 del port B
bsf portb,2 ; Ponemos un 1 en la salida 2 del port B
call DELAY
call DELAY
call DELAY
call DELAY
call DELAY
bcf portb,0
bcf portb,2; Ponemos a 0 el bit 3 del port B
call DELAY
endm
;====================
;DERECHA 1s
derecha MACRO
bsf portb,1 ; Ponemos un 1 en la salida 1 del port B
bsf portb,3 ; Ponemos un 1 en la salida 3 del port B
call DELAY 
call DELAY
call DELAY
call DELAY
call DELAY
bcf portb,1; Ponemos a 0 el bit 1 del port B 
bcf portb,3; Ponemos a 0 el bit 3 del port B
call DELAY
endm
;===========
;ATRAS 1s
call DELAY
;ATRAS 1s
atras MACRO
bsf portb,0 ; Ponemos un 1 en la salida 0 del port B
bsf portb,3 ; Ponemos un 1 en la salida 3 del port B
call DELAY
call DELAY
call DELAY
call DELAY
call DELAY
bcf portb,0; Ponemos a 0 el bit 0 del port B
bcf portb,3; Ponemos a 0 el bit 3 del port B
call DELAY
endm
;=======
;ADELANTE 1s
adelante MACRO
bsf portb,1 ; Ponemos un 1 en la salida 1 del port B
bsf portb,2 ; Ponemos un 1 en la salida 2 del port B
call DELAY 
call DELAY 
call DELAY 
call DELAY 
call DELAY 
bcf portb,1; Ponemos a 0 el bit 0 del port B
bcf portb,2; Ponemos a 0 el bit 3 del port B
call DELAY 
endm
;===========
;SEMILLAS 1s
plantar MACRO
bsf portb,6 ; Ponemos un 1 en la salida 1 del port B
call DELAY 
call DELAY 
call DELAY 
call DELAY 
call DELAY 
bcf portb,6; Ponemos a 0 el bit 1 del port B
call DELAY
endm
;=====
;LUCES 1s
luces MACRO
bsf portb,7 ; Ponemos un 1 en la salida 1 del port B
call DELAY 
call DELAY 
call DELAY 
call DELAY 
call DELAY 
bcf portb,7 ; Ponemos un 1 en la salida 1 del port B
call DELAY 
endm
; ========= Programa principal 

;==adelante(5)==
adelante
adelante
adelante
adelante
adelante
;==atras(5)==
atras
atras
atras
atras
atras
;==atras()==
;==atras(1)==
atras
;==izquierda(1)==
izquierda
;==adelante(2)==
adelante
adelante
;==verAdelante==
btfss PORTB, 5;
goto sino2
bsf PORTA,0
luces
goto finsino2
sino2:

;==adelante()==
;==adelante(1)==
adelante
finsino2:
plantar
;==plantar(5)==
plantar
plantar
plantar
plantar
plantar
derecha
;==adelante(2)==
adelante
adelante
;==plantar(1)==
plantar
;==atras(5)==
atras
atras
atras
atras
atras
;==plantar(5)==
plantar
plantar
plantar
plantar
plantar
;==repetir(4)==
movlw D'4'
movwf contadorRep1
loop1:
;==izquierda(1)==
izquierda
;==adelante()==
;==adelante(1)==
adelante
;==derecha(1)==
derecha
;==adelante()==
;==adelante(1)==
adelante
decfsz contadorRep1, F
goto loop1
goto final
; -- Subrutina de temporizacion -- 
DELAY
movlw 0xFF;
movwf Cont1 ; Iniciamos cont
CICLO1
movlw 0xFF;
movwf Cont2 ; Iniciamos cont2
CICLO2
decfsz Cont2,1 ; Decrementa Cont2 y si es 0 sale
goto CICLO2 ; Si no es 0 repetimos ciclo
decfsz Cont1,1 ; Decrementa Contador1
goto CICLO1 ; Si no es cero repetimos ciclo
return ; Regresa de la subrutina
final:
sleep
retrun
END ; FIN DE PROGRAMA

