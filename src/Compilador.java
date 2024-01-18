
import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.CodeBlock;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.io.FileWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;


/**
 *
 * @author MamarreTeam
 */
public class Compilador extends javax.swing.JFrame {

    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<TextColor> textsColor;
    private Timer timerKeyReleased;
    private ArrayList<Production> identProd;
    private ArrayList<Production> funcionProd;
    private ArrayList<Production> ifProd;
    private ArrayList<Production> CIProd;
    private HashMap<String, String> identificadores;
    private HashMap<String, String> tipoDato;
    public Cuadruplos cFrame;
    public TablaTokens cFrame2;
    public TablaSimbolos cFrame3;
    private String producciones;
    private int t, count, f,data = 0;
    Boolean a = false;
    String tI = "", tF;
    int cicloRepetirContador = 0;
    int estructuraSiContador=0;
    int nivelAnidacion=0;
    //CAMBIAR RUTA A LA QUE TIENE SU PROYECTO EN SU MÁQUINA
    String rutaAsm="C:\\Users\\solda\\Desktop\\COMPILADOR_V3\\Compiler-main\\src\\Programas\\CodigoObjeto.asm";

    public int Letra = 16; 
    
   // private Tabla cFrameT;
   
    
    private boolean codeHasBeenCompiled = false;

    /**
     * Creates new form Compilador
     */
    public Compilador() {
        initComponents();
        
        
        
        btnCodigoIntermedio.setVisible(false); 
        btnTTokens.setVisible(false);
        jButton1.setVisible(false);
        btnEjecutar.setVisible(false);
        
        init();
    }

    private void init() {
        
        
        
        
        
        btnNuevo.setContentAreaFilled(false);
        
          btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNuevo.setBackground(Color.GREEN); // Cambia el color al pasar el mouse
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNuevo.setBackground(UIManager.getColor("control")); // Restablece el color original
            }
          });
        
        
        btnAbrir.setFocusPainted(false);
        btnAbrir.setBorderPainted(false);
        btnAbrir.setContentAreaFilled(false); 
        
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false); 
        
        btnGuardarC.setFocusPainted(false);
        btnGuardarC.setBorderPainted(false);
        btnGuardarC.setContentAreaFilled(false); 
        
        btnCompilar.setFocusPainted(false);
        btnCompilar.setBorderPainted(false);
        btnCompilar.setContentAreaFilled(false); 
        
         btnEjecutar.setFocusPainted(false);
        btnEjecutar.setBorderPainted(false);
       btnEjecutar.setContentAreaFilled(false); 
        
        //Tamaño de los botones
       
        
        
        title = "Compiler";
        setLocationRelativeTo(null);
        setTitle(title);
        directorio = new Directory(this, jtpCode, title, ".adro");
        
        addWindowListener(new WindowAdapter() {// Cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });
        Functions.setLineNumberOnJTextComponent(jtpCode);
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();

            int posicion = jtpCode.getCaretPosition();
            jtpCode.setText(jtpCode.getText().replaceAll("[\r]+", ""));
            jtpCode.setCaretPosition(posicion);

            colorAnalysis();
        });
        Functions.insertAsteriskInName(this, jtpCode, () -> {
            timerKeyReleased.restart();
        });
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        textsColor = new ArrayList<>();
        identProd = new ArrayList<>();
        funcionProd = new ArrayList<>();
        ifProd = new ArrayList<>();
        CIProd = new ArrayList<>();
        identificadores = new HashMap<>();
        tipoDato = new HashMap<>();
        t = 0;
        f = 0;
        count = 0;
        limpiarArchivo();
        Functions.setAutocompleterJTextComponent(new String[]{"else",
            "para(/*Valor Inicial (Numérico)*/:/*Tamaño del ciclo (Numérico)*/:/*Incrementos del ciclo (Numérico)*/){/*Argumentos u Operaciones*/}",
            "PARA(/*Valor Inicial (Numérico)*/:/*Tamaño del ciclo (Numérico)*/:/*Incrementos del ciclo (Numérico)*/){/*Argumentos u Operaciones*/}",
            "Para(/*Valor Inicial (Numérico)*/:/*Tamaño del ciclo (Numérico)*/:/*Incrementos del ciclo (Numérico)*/){/*Argumentos u Operaciones*/}",
            "repetir(/*Cantidad de repeticiones (Numérico)*/){/*Argumentos u Operaciones*/}",
            "REPETIR(/*Cantidad de repeticiones (Numérico)*/){/*Argumentos u Operaciones*/}",
            "Repetir(/*Cantidad de repeticiones (Numérico)*/){/*Argumentos u Operaciones*/}",
            "MIENTRAS(/*Función de tipo obstáculo*/){/*Argumentos u Operaciones*/}",
            "Mientras(/*Función de tipo obstáculo*/){/*Argumentos u Operaciones*/}",
            "mientras(/*Función de tipo obstáculo*/){/*Argumentos u Operaciones*/}",
            "Si(/*Función de tipo obstáculo*/){/*Argumentos u Operaciones*/}",
            "si(/*Función de tipo obstáculo*/){/*Argumentos u Operaciones*/}",
            "SI(/*Función de tipo obstáculo*/){/*Argumentos u Operaciones*/}",
            "SINO(){/*Argumentos u Operaciones*/}",
            "Sino(){/*Argumentos u Operaciones*/}",
            "sino(){/*Argumentos u Operaciones*/}",
            //FUNCIONES DE PLANTAR
            "plantar(/*Valor Numerico o Identificador de tipo BOOLEANO*/);",
            "Plantar(/*Valor Numerico o Identificador de tipo BOOLEANO*/);",
            "PLANTAR(/*Valor Numerico o Identificador de tipo BOOLEANO*/);",
            //FUNCIONES DE OBSTACULO
            "verAdelante(); /*Arroja un valor BOOLEANO*/",
            "verAtras(); /*Arroja un valor BOOLEANO*/",
            "verDerecha(); Arroja un valor BOOLEANO*/",
            "verIzquierda(); Arroja un valor BOOLEANO*/",
            //Funciones de MOVIMIENTO
            "atras(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "Atras(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "ATRAS(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "Adelante(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "ADELANTE(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "adelante(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "derecha(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "DERECHA(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "DERECHA(/*Valor Numerico o Identificador de tipo DISTANCIA*/);",
            "derecha();",
            "DERECHA();",
            "DERECHA();",
            "IZQUIERDA();",
            "izquierda();",
            "cadena "
            }, jtpCode, () -> {
            timerKeyReleased.restart();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        rootPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpCode = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaOutputConsole = new javax.swing.JTextArea();
        btnNuevo = new javax.swing.JButton();
        btnAbrir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGuardarC = new javax.swing.JButton();
        btnCompilar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCodigoIntermedio = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        btnTTokens = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jLabel6.setText("jLabel6");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        rootPanel.setBackground(new java.awt.Color(51, 51, 255));

        jScrollPane1.setViewportView(jtpCode);

        jtaOutputConsole.setEditable(false);
        jtaOutputConsole.setBackground(new java.awt.Color(255, 255, 255));
        jtaOutputConsole.setColumns(20);
        jtaOutputConsole.setRows(5);
        jScrollPane2.setViewportView(jtaOutputConsole);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Nuevo_64.png"))); // NOI18N
        btnNuevo.setAutoscrolls(true);
        btnNuevo.setMaximumSize(new java.awt.Dimension(518, 519));
        btnNuevo.setMinimumSize(new java.awt.Dimension(518, 519));
        btnNuevo.setPreferredSize(new java.awt.Dimension(60, 60));
        btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevoMouseExited(evt);
            }
        });
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/carpeta.png"))); // NOI18N
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disco.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGuardarC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editar.png"))); // NOI18N
        btnGuardarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCActionPerformed(evt);
            }
        });

        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tocar.png"))); // NOI18N
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nuevo");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Abrir");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Guardar");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Guardar Como");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Compilar");

        btnCodigoIntermedio.setText("Intermedio");
        btnCodigoIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoIntermedioActionPerformed(evt);
            }
        });

        btnEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/actualizar(1).png"))); // NOI18N
        btnEjecutar.setName("btnEjecutar"); // NOI18N
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        btnTTokens.setText("Tabla Tokens");
        btnTTokens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTTokensActionPerformed(evt);
            }
        });

        jButton1.setText("Tabla Simbolos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Ejecutar");

        jButton2.setText("-");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("+");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("-");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("+");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnGuardar)
                        .addGap(31, 31, 31)
                        .addComponent(btnGuardarC)
                        .addGap(33, 33, 33))
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(btnCompilar)
                        .addGap(106, 106, 106)
                        .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addComponent(btnTTokens, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCodigoIntermedio)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(btnEjecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(38, 38, 38))))
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)))))
                .addContainerGap())
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAbrir)
                    .addComponent(btnGuardarC)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCompilar)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnEjecutar)
                                .addComponent(btnCodigoIntermedio)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addComponent(jLabel7)))
                    .addComponent(btnGuardar)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(btnTTokens)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(rootPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTTokensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTTokensActionPerformed
        TablaTokens();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTTokensActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        cicloRepetirContador = 0;
        if (codeHasBeenCompiled) {

            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede ejecutar el código ya que se encontró uno o más errores",
                    "Error en la compilación", JOptionPane.ERROR_MESSAGE);
            } else {
                CodeBlock codeBlock = Functions.splitCodeInCodeBlocks(tokens, "{", "}", ";");
                ArrayList<String> blocksOfCode = codeBlock.getBlocksOfCodeInOrderOfExec();
                escribir("include \"p16F84A.inc\" ");
                
                escribir("__CONFIG	_CP_OFF &  _WDT_OFF & _PWRTE_ON & _XT_OSC\n"
                        + "\n"
                    + "LIST P=16F84A\n"
                    + "portb equ h'06'\n"
                    + "timer equ h'01'\n"
                    + "status equ h'03'\n"
                    + "Cont1 equ 0x0E\n"
                    + "contador equ 0x0D\n"
                    + "contadorRep1 equ 0x10\n"
                    + "contadorRep2 equ 0x11\n"
                    + "contadorRep3 equ 0x12\n"
                    + "contadorRep4 equ 0x13\n"
                    + "contadorRep5 equ 0x14\n"
                    + "contadorRep6 equ 0x15\n"
                    + "contadorRep7 equ 0x16\n"
                    + "contadorRep8 equ 0x17\n"
                    + "contadorRep0 equ 0x18\n"
                    + "contadorRep10 equ 0x19\n"
                    + "Cont2 equ 0x0F\n"
                    + "org 0\n"
                    + "; --------- iniciamos puertos y temporizador --\n"
                    + "bsf status,5 ; Pasamos al banco 1\n"
                    + "movlw b'11010101' ; Cargamos el valor en W\n"
                    + "movwf timer ; Registro Option del timer\n"
                    + "movlw b'00110000' ; Cargamos 00 en el registro\n"
                    + "movwf portb ; Puerto B (salidas)\n"
                    + "bcf status,5 ; Pasamos al banco 0\n"
                    + "clrf portb ; Ponemos la salida PORTB a 0\n"
                    + ";======================================\n"
                    + ";IZQUIERDA 1s\n"
                    + "izquierda MACRO\n"
                    + "bsf portb,0 ; Ponemos un 1 en la salida 0 del port B\n"
                    + "bsf portb,2 ; Ponemos un 1 en la salida 2 del port B\n"
                    + "call DELAY\n"
                    + "call DELAY\n"
                    + "call DELAY\n"
                    + "call DELAY\n"
                    + "call DELAY\n"
                    + "bcf portb,0\n"
                    + "bcf portb,2; Ponemos a 0 el bit 3 del port B\n"     
                + "call DELAY\n"
                + "endm\n"
                + ";====================\n"
                + ";DERECHA 1s\n"
                + "derecha MACRO\n"
                + "bsf portb,1 ; Ponemos un 1 en la salida 1 del port B\n"
                + "bsf portb,3 ; Ponemos un 1 en la salida 3 del port B\n"
                + "call DELAY \n"
                + "call DELAY\n"
                + "call DELAY\n"
                + "call DELAY\n"
                + "call DELAY\n"
                + "bcf portb,1; Ponemos a 0 el bit 1 del port B \n"
                + "bcf portb,3; Ponemos a 0 el bit 3 del port B\n"
                + "call DELAY\n"
                + "endm\n"
                + ";===========\n"
                + ";ATRAS 1s\n"
                + "call DELAY\n"
                + ";ATRAS 1s\n"
                + "atras MACRO\n"
                + "bsf portb,0 ; Ponemos un 1 en la salida 0 del port B\n"
                + "bsf portb,3 ; Ponemos un 1 en la salida 3 del port B\n"
                + "call DELAY\n"
                + "call DELAY\n"
                + "call DELAY\n"
                + "call DELAY\n"
                + "call DELAY\n"
                + "bcf portb,0; Ponemos a 0 el bit 0 del port B\n"
                + "bcf portb,3; Ponemos a 0 el bit 3 del port B\n"
                + "call DELAY\n"
                + "endm\n"
                + ";=======\n"
                + ";ADELANTE 1s\n"
                + "adelante MACRO\n"
                + "bsf portb,1 ; Ponemos un 1 en la salida 1 del port B\n"
                + "bsf portb,2 ; Ponemos un 1 en la salida 2 del port B\n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "bcf portb,1; Ponemos a 0 el bit 0 del port B\n"
                + "bcf portb,2; Ponemos a 0 el bit 3 del port B\n"
                + "call DELAY \n"
                + "endm\n"
                + ";===========\n"
                + ";SEMILLAS 1s\n"
                + "plantar MACRO\n"
                + "bsf portb,6 ; Ponemos un 1 en la salida 1 del port B\n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "bcf portb,6; Ponemos a 0 el bit 1 del port B\n"
                + "call DELAY\n"
                + "endm\n"
                + ";=====\n"
                + ";LUCES 1s\n"
                + "luces MACRO\n"
                + "bsf portb,7 ; Ponemos un 1 en la salida 1 del port B\n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "call DELAY \n"
                + "bcf portb,7 ; Ponemos un 1 en la salida 1 del port B\n"
                + "call DELAY \n"
                + "endm\n" 
                + "; ========= Programa principal \n");
                //imprimir en consola identificadores encontrados
                for (Map.Entry<String, String> entry : identificadores.entrySet()) {
                    System.out.println("Identificador: " + entry.getKey() + ", Valor: " + entry.getValue()+"\n");
                }
                //empezara la conversión
                executeCode(blocksOfCode, 1);
                
                escribir("goto final\n"
                + "; -- Subrutina de temporizacion -- \n"
                + "DELAY\n"
                + "movlw 0xFF;\n"
                + "movwf Cont1 ; Iniciamos cont\n"
                + "CICLO1\n"
                + "movlw 0xFF;\n"
                + "movwf Cont2 ; Iniciamos cont2\n"
                + "CICLO2\n"
                + "decfsz Cont2,1 ; Decrementa Cont2 y si es 0 sale\n"
                + "goto CICLO2 ; Si no es 0 repetimos ciclo\n"
                + "decfsz Cont1,1 ; Decrementa Contador1\n"
                + "goto CICLO1 ; Si no es cero repetimos ciclo\n"
                + "return ; Regresa de la subrutina\n"
                + "final:\n"
                + "sleep\n"
                + "retrun\n"
                + "END ; FIN DE PROGRAMA\n");
                
            }
        }

        
        //executeCode();
        //File archivo = new File("C:\\Users\\solda\\Desktop\\COMPILADOR_V2\\Compiler-main\\src\\Programas\\CodigoObjeto.asm");
        //Desktop desktop = Desktop.getDesktop();
        //try {
         //   desktop.open(archivo);
        //} catch (IOException ex) {
         //   Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        //}        // TODO add your handling code here:
        //executeCode();
        File archivo = new File(rutaAsm);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(archivo);
        } catch (IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
         // Rutas de los programas ensamblador y cargador
            String rutaProgramaEnsamblador = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Microchip\\MPLAB IDE v8.43\\MPASMWIN.lnk";
            String rutaCargador = "C:\\Users\\solda\\Downloads\\SP MAU-20231211T190625Z-001\\SP MAU\\PIC Programmer Software\\microbrn.exe";

            // Abrir el programa ensamblador
            abrirProgramaEnsamblador(rutaProgramaEnsamblador);

            // Abrir el cargador
            abrirCargador(rutaCargador);        // TODO add your handling code here:

    }//GEN-LAST:event_btnEjecutarActionPerformed

     private void abrirProgramaEnsamblador(String rutaProgramaEnsamblador) {
    File programaEnsamblador = new File(rutaProgramaEnsamblador);
    Desktop desktop = Desktop.getDesktop();

    try {
        desktop.open(programaEnsamblador);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

private void abrirCargador(String rutaCargador) {
    File cargador = new File(rutaCargador);
    Desktop desktop = Desktop.getDesktop();

    try {
        desktop.open(cargador);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

    
    private void btnCodigoIntermedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoIntermedioActionPerformed
        cFrame = new Cuadruplos();
        cFrame.setVisible(true);
        cFrame.setDefaultCloseOperation(Cuadruplos.DO_NOTHING_ON_CLOSE);
        etiquetas();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCodigoIntermedioActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        StyledDocument doc= jtpCode.getStyledDocument();
        String texto="";
        try {
            texto = doc.getText(0, doc.getLength()); // Obtener el texto completo del JTextPane
            if (texto.contains("finsino")) { // Verificar si la cadena "finsino" está presente
                texto = texto.replace("finsino", "finsino\n plantar();"); // Concatenar "plantar();" después de "finsino"
            }
            // Usar el texto modificado como sea necesario
            System.out.println(texto); // Por ejemplo, imprimirlo en la consola
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        if (getTitle().contains("*") || getTitle().equals(title)) {
            if (directorio.Save()) {
                compile();
               
            }
        } else {
            compile();
            
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void btnGuardarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCActionPerformed
        if (directorio.SaveAs()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarCActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (directorio.Save()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        if (directorio.Open()) {
            colorAnalysis();
            clearFields();
            btnCodigoIntermedio.setVisible(false);
            btnTTokens.setVisible(false);
            jButton1.setVisible(false);
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        directorio.New();
        
        clearFields();
        btnCodigoIntermedio.setVisible(false);
        btnTTokens.setVisible(false);
        jButton1.setVisible(false);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnNuevoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoMouseExited

    private void btnNuevoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseEntered

    }//GEN-LAST:event_btnNuevoMouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TablaSimbolos(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    Font font = jtpCode.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), Letra--); // Tamaño de letra en puntos
        jtpCode.setFont(newFont);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Font font = jtpCode.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), Letra++); // Tamaño de letra en puntos
        jtpCode.setFont(newFont);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Font font = jtaOutputConsole.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), Letra++); // Tamaño de letra en puntos
        jtaOutputConsole.setFont(newFont);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Font font = jtaOutputConsole.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), Letra--); // Tamaño de letra en puntos
        jtaOutputConsole.setFont(newFont);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    
    private void TablaSimbolos(){
        cFrame3 = new TablaSimbolos();
        cFrame3.setVisible(true);
        cFrame3.setDefaultCloseOperation(TablaSimbolos.DO_NOTHING_ON_CLOSE);
        fillTableSimbolos(); 
    }
    
    private void TablaTokens(){
        cFrame2 = new TablaTokens();
        cFrame2.setVisible(true);
        cFrame2.setDefaultCloseOperation(TablaTokens.DO_NOTHING_ON_CLOSE);
        fillTableTokens2();
    }
    private void executeCode(ArrayList<String> blocksOfCode, int repeats) {
        int mj = 0;
        for (int j = 1; j <= repeats; j++) {
            int repeatCode = -1;
            for (int i = 0; i < blocksOfCode.size(); i++) {
                String blockOfCode = blocksOfCode.get(i);
                if (repeatCode != -1) {
                    int[] posicionMarcador = CodeBlock.getPositionOfBothMarkers(blocksOfCode, blockOfCode);
                    executeCode(new ArrayList<>(blocksOfCode.subList(posicionMarcador[0], posicionMarcador[1])), repeatCode);
                    //escribir("LOOP ciclo");
                    
                    
                    escribir("decfsz contadorRep"+cicloRepetirContador+", F\n"
                               + "goto loop"+cicloRepetirContador);
                    repeatCode = -1;
                    //cicloRepetirContador--;
                    i = posicionMarcador[1];
                } else {
                    String[] sentences = blockOfCode.split(";");
                    
                    for (String sentence : sentences) {
                        sentence = sentence.trim();
                        if (sentence.startsWith("caden")) {
                                String[] mensaje = sentence.split(" ");
                                String msje = "";
                                int c=0;
                                for (int m = 0; m<mensaje.length; m++) {
                                    if (mensaje[m].contains("\'")) {
                                        if(c==0){mj = m;c++;} 
                                    }
                                }
                                if (mj != 0) {
                                    for (int m = mj; m<mensaje.length; m++) {
                                        msje += mensaje[m] + " ";
                                    }
                                    
                                    data++;
                                }
                                if (data == 1) {
                                    
                                }
                        }
                        /*else if(sentence.startsWith("sino")){
                                escribir("sino:\n");
                                System.out.println("estructura de control: "+sentence);
                            } */  
                        
                        if(sentence.startsWith("{")) {
                            nivelAnidacion++;
                            System.out.println("nivel de anidación: "+nivelAnidacion);                            
                        }
                        if(sentence.startsWith("}")) {
                            nivelAnidacion--;
                            System.out.println("nivel de anidación: "+nivelAnidacion);
                        }
                        /*else if(sentence.contains("verAtras")){
                            escribir(";==verAtras==");
                            escribir("btfss PORTB, 4;");
                            escribir("goto sino"+estructuraSiContador);
                            escribir("bsf PORTA,0");
                        }
                        else if(sentence.contains("verAdelante")){
                            escribir(";==verAdelante==");
                            escribir("btfss PORTB, 5;");
                            escribir("goto sino"+estructuraSiContador);
                            escribir("bsf PORTA,0");
                            }
                        else if(sentence.contains("verDerecha")){
                            escribir(";==verDerecha==");
                            escribir("derecha");
                            escribir("btfss PORTB, 5;");
                            escribir("goto sino"+estructuraSiContador);
                            escribir("bsf PORTA,0");
                            escribir("izquierda");
                        }
                        else if(sentence.contains("verIzquierda")){
                            escribir(";==verIzquierda==");
                            escribir("izquierda");
                            escribir("btfss PORTB, 5;");
                            escribir("goto sino"+estructuraSiContador);
                            escribir("bsf PORTA,0");
                            escribir("derecha");
                        }*/
                        else if (sentence.startsWith("si") && sentence.contains("ver")) {
                                estructuraSiContador++;
                                System.out.println("estructura de control: "+sentence);
                                if(sentence.contains("verAdelante") || sentence.contains("verAtras") || sentence.contains("verDerecha") || sentence.contains("verIzquierda")){
                                    if(sentence.contains("verAtras")){
                                        escribir(";==verAtras==");
                                        escribir("btfss PORTB, 4;");
                                        escribir("goto sino"+estructuraSiContador);
                                        escribir("bsf PORTA,0");
                                    }
                                    else if(sentence.contains("verAdelante")){
                                        escribir(";==verAdelante==");
                                        escribir("btfss PORTB, 5;");
                                        escribir("goto sino"+estructuraSiContador);
                                        escribir("bsf PORTA,0");
                                        }
                                    else if(sentence.contains("verDerecha")){
                                        escribir(";==verDerecha==");
                                        escribir("derecha");
                                        escribir("btfss PORTB, 5;");
                                        escribir("goto sino"+estructuraSiContador);
                                        escribir("bsf PORTA,0");
                                        escribir("izquierda");
                                    }
                                    else if(sentence.contains("verIzquierda")){
                                        escribir(";==verIzquierda==");
                                        escribir("izquierda");
                                        escribir("btfss PORTB, 5;");
                                        escribir("goto sino"+estructuraSiContador);
                                        escribir("bsf PORTA,0");
                                        escribir("derecha");
                                    }
                            }
                            else if (sentence.contains(">|<|==|!=|>=|<=")){
                                System.out.println(sentence);
                                String expresion= sentence.substring(3, sentence.length() - 2);
                                String[] partes = expresion.split(">|<|==|!=|>=|<=");
                                String parte1 = partes[0].trim();
                                String parte2 = partes[2].trim();
                                String operador = partes[1].trim();
                                if(parte1.contains("_")) {
                                    parte1 = identificadores.get(parte1);
                                    System.out.println("Valor de parte1: "+parte1);
                                }
                                if(parte2.contains("_")) {
                                    parte2 = identificadores.get(parte2);
                                    System.out.println("Valor de parte2: "+parte2);
                                }
                                int valor1 = Integer.parseInt(parte1);
                                int valor2 = Integer.parseInt(parte2);
                                switch (operador) {
                                    case ">":
                                        if (valor1 > valor2) {
                                            String[] bloque = blockOfCode.split(";");
                                            for (String bloque1 : bloque) {
                                                escribir(bloque1);
                                            }
                                        }
                                        break;
                                    case "<":
                                        if (valor1 < valor2) {
                                            String[] bloque = blockOfCode.split(";");
                                            for (String bloque1 : bloque) {
                                                escribir(bloque1);
                                            }
                                        }
                                        break;
                                    case "==":
                                        if (valor1 == valor2) {
                                            String[] bloque = blockOfCode.split(";");
                                            for (String bloque1 : bloque) {
                                                escribir(bloque1);
                                            }
                                        }
                                        break;
                                    case "!=":
                                        if (valor1 != valor2) {
                                            String[] bloque = blockOfCode.split(";");
                                            for (String bloque1 : bloque) {
                                                escribir(bloque1);
                                            }
                                        }
                                        break;
                                    case ">=":
                                        if (valor1 >= valor2) {
                                            String[] bloque = blockOfCode.split(";");
                                            for (String bloque1 : bloque) {
                                                escribir(bloque1);
                                            }
                                        }
                                        break;
                                    case "<=":
                                        if (valor1 <= valor2) {
                                            String[] bloque = blockOfCode.split(";");
                                            for (String bloque1 : bloque) {
                                                escribir(bloque1);
                                            }
                                        }
                                        break;
                                }
                            }
                        }
                        else if (sentence.startsWith("sino")) {
                            nivelAnidacion++;
                            escribir("goto finsino"+estructuraSiContador);
                            escribir("sino"+estructuraSiContador+":\n");
                            
                            //estructuraSiContador++;
                            System.out.println("estructura de control: " + sentence);
                            System.out.println("nivel de anidación: "+nivelAnidacion);
                        }
                        else if(sentence.startsWith("finsino")){
                            nivelAnidacion--;
                            escribir("finsino"+estructuraSiContador+":");
                            escribir("plantar");
                        }
                        else if(sentence.startsWith("finsi")){
                            nivelAnidacion--;
                            escribir("finsi"+estructuraSiContador+":");
                            escribir("plantar");
                        }
                        else if (sentence.toLowerCase().startsWith("adelante")) {
                            String parametro;
                            if (sentence.contains("_")) {
                                parametro = identificadores.get(sentence.substring(10, sentence.length() - 2).trim());
                                escribir(";==adelante("+parametro+")==");
                            } else {
                                parametro = sentence.substring(10, sentence.length() - 2).trim();
                                escribir(";==adelante("+parametro+")==");
                            }
                            if (parametro == null || parametro.isEmpty()) {
                                escribir(";==adelante(1)==");
                                // Establecer el valor predeterminado
                                parametro = "1";
                            }

                            int in = Integer.parseInt(parametro);
                            for (int a = 0; a < in; a++) {
                                escribir("adelante");
                            }
                        } else if (sentence.toLowerCase().startsWith("derecha")) {
                            String parametro;
                            System.out.println("Identificador encontrado en sentencia 'deracha': " + sentence.substring(9, sentence.length() - 2).trim());
                            
                            String subcadena = sentence.substring(9, sentence.length() - 2).trim();
                            System.out.println("Subcadena: \"" + subcadena + "\", Longitud: " + subcadena.length());

                            if (subcadena.contains("_")) {
                                parametro = identificadores.get(subcadena);
                                escribir(";==derecha("+parametro+")==");
                                System.out.println("valor de parámetro: "+parametro);
                            } else {
                                parametro = subcadena;
                            }
                            if (parametro == null || parametro.isEmpty()) {
                                escribir(";==derecha(1)==");
                                // Establecer el valor predeterminado
                                parametro = "1";
                            }
                            int in = Integer.parseInt(parametro);
                            for (int b = 0; b < in; b++) {
                                escribir("derecha");
                            }
                        } else if (sentence.toLowerCase().startsWith("izquierda")) {
                            String parametro;
                            if (sentence.contains("_")) {
                                parametro = identificadores.get(sentence.substring(11, sentence.length() - 2).trim());
                                System.out.println("valor de parámetro sacado de una variable: "+parametro);
                                escribir(";==izquierda("+parametro+")==");
                                int in = Integer.parseInt(parametro);
                                for (int c = 0; c < in; c++) {
                                    escribir("izquierda");
                                }
                            }else if (sentence.substring(11, sentence.length() - 2).isEmpty() || sentence.substring(11, sentence.length() - 2)==null) {
                                // Establecer el valor predeterminado
                                System.out.println("valor de parámetro por defecto: 1");
                                parametro = "1";
                                escribir(";==izquierda(1)==");
                                escribir("izquierda");
                            }else if(!"".equals(sentence.substring(11, sentence.length() - 2))){
                                parametro = sentence.substring(11, sentence.length() - 2).trim();
                                System.out.println("supuesto valor constante: "+sentence.substring(11, sentence.length() - 2));
                                System.out.println("valor de parámetro dado por una constante: "+parametro);
                                escribir(";==izquierda("+parametro+")==");
                                int in = Integer.parseInt(parametro);
                                for (int c = 0; c < in; c++) {
                                    escribir("izquierda");
                                }
                            }
                            
                        }else if (sentence.toLowerCase().startsWith("luces")) {
                            String parametro;
                            if (sentence.contains("_")) {
                                parametro = identificadores.get(sentence.substring(7, sentence.length() - 2));
                            } else {
                                parametro = sentence.substring(7, sentence.length() - 2);
                            }
                            if (parametro == null || parametro.isEmpty()) {
                                // Establecer el valor predeterminado
                                parametro = "1";
                            }

                            int in = Integer.parseInt(parametro);
                            for (int d = 0; d < in; d++) {
                                escribir("luces");
                            }
                        }else if (sentence.toLowerCase().startsWith("plantar")) {
                            String parametro;
                            if (sentence.contains("_")) {
                                parametro = identificadores.get(sentence.substring(9, sentence.length() - 2).trim());
                                escribir(";==plantar("+parametro+")==");
                            } else {
                                parametro = sentence.substring(9, sentence.length() - 2).trim();
                                escribir(";==plantar("+parametro+")==");
                            }
                            if (parametro == null || parametro.isEmpty()) {
                                // Establecer el valor predeterminado
                                parametro = "1";
                                escribir(";==plantar(1)==");
                            }

                            int in = Integer.parseInt(parametro);
                            for (int e = 0; e < in; e++) {
                                escribir("plantar");
                            }
                        } else if (sentence.toLowerCase().startsWith("atras")) {
                            String parametro;
                            
                            System.out.println("identificador encontrado en sentencia 'atras': "+sentence.substring(7,sentence.length()-2).trim());
                            System.out.println("Subcadena: \"" + sentence.substring(7, sentence.length() - 2) + "\", Longitud: " + sentence.substring(7, sentence.length() - 2).trim().length());
                            
                            if (sentence.trim().contains("_")) {
                                parametro = identificadores.get(sentence.substring(8, sentence.length() - 2).trim());
                                escribir(";==atras("+parametro+")==");
                                if(parametro==null){
                                    parametro="1";
                                }
                               System.out.println("valor de parámetro: "+parametro);
                            } else {
                                parametro = sentence.substring(7, sentence.length() - 2).trim();
                                escribir(";==atras("+parametro+")==");
                            }
                            if (parametro.isEmpty()) {
                                // Establecer el valor predeterminado
                                parametro = "1";
                                escribir(";==atras("+parametro+")==");
                            }

                            int in = Integer.parseInt(parametro);
                            for (int f = 0; f < in; f++) {
                                escribir("atras");
                            }
                        }else if (sentence.toLowerCase().startsWith("imprimir")){
                            String parametro;
                            if (sentence.contains("_")) {
                                parametro = identificadores.get(sentence.substring(10, sentence.length() - 2).trim());
                                System.out.println("valor de parámetro sacado de una variable: "+parametro);
                                escribir(";==imprimir("+parametro+")==");
                                System.out.println(""+parametro);
                            }else if (sentence.substring(10, sentence.length() - 2).isEmpty() || sentence.substring(11, sentence.length() - 2)==null) {
                                // Establecer el valor predeterminado
                                System.out.println("Imprimir vacio");
                            }else if(!"".equals(sentence.substring(10, sentence.length() - 2))){
                                parametro = sentence.substring(10, sentence.length() - 2).trim();
                                System.out.println("supuesto valor constante: "+sentence.substring(10, sentence.length() - 2));
                                System.out.println("valor de parámetro dado por una constante: "+parametro);
                                escribir(";==imprimir("+parametro+")==");
                                System.out.println(""+parametro);
                            }
                        }else if (sentence.toLowerCase().startsWith("imprimir")){
                            String parametro;
                            if (sentence.contains("_")) {
                                parametro = identificadores.get(sentence.substring(10, sentence.length() - 2).trim());
                                System.out.println("valor de parámetro sacado de una variable: "+parametro);
                                escribir(";==imprimir("+parametro+")==");
                                System.out.println(""+parametro);
                            }else if (sentence.substring(10, sentence.length() - 2).isEmpty() || sentence.substring(11, sentence.length() - 2)==null) {
                                // Establecer el valor predeterminado
                                System.out.println("Imprimir vacio");
                            }else if(!"".equals(sentence.substring(10, sentence.length() - 2))){
                                parametro = sentence.substring(10, sentence.length() - 2).trim();
                                System.out.println("supuesto valor constante: "+sentence.substring(10, sentence.length() - 2));
                                System.out.println("valor de parámetro dado por una constante: "+parametro);
                                escribir(";==imprimir("+parametro+")==");
                                System.out.println(""+parametro);
                            }
                        }else if (sentence.startsWith("repetir")) {
                            String parametro = sentence.substring(10, sentence.length() - 2).trim();
                            cicloRepetirContador++;
                            escribir(";==repetir("+(parametro)+")==");
                            escribir("movlw D'" + parametro + "'\n" 
                                    +"movwf contadorRep" + cicloRepetirContador+ "\n"
                                    + "loop"+cicloRepetirContador+":");
                            repeatCode = 1;
                            //String parametro = sentence.substring(10, sentence.length() - 2);
                            //escribir("mov cx," + parametro + "\n" + "ciclo:");
                            //repeatCode = 1;
                        }else if(sentence.equals("}")){
                            escribir("decfsz contador, 1\\n\"\n" +
"                            + \"goto loop\" + j");
                        }
                    }
                }
            }
        }
    }

    public void escribir(String msj) {
        try {
            FileWriter escritor = new FileWriter(rutaAsm, true);
            escritor.write(msj + "\n");
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void etiquetas() {
        //Genera los renglones en la tabla de Cuadruplos
        ArrayList<Token> token = new ArrayList<>();
        int ini = 0, w = 0, tnum = tokens.size();
        //Recorre los tokens
        for (int i = 0; i < tnum; i++) {
            //Cuando encuentra un if, busca las llaves 
            if (tokens.get(i).getLexicalComp().equals("ESTRUCTURA_SI")) {
                //Pone las producciones de antes del if
                codigoIntermedio(token);
                token.clear();
                buscarProducciones();
                CIProd.clear();
                ini = i;
                w = ini;
                System.out.println(ini + "I-> ");
                while (!tokens.get(w).getLexicalComp().equals("LLAVE_C")) {
                    token.add(tokens.get(w));
                    w++;
                }
                i = w;
                //Pone las producciones del if
                codigoIntermedio(token);
                token.clear();
                buscarProducciones();
                CIProd.clear();
                if (a && f == 2) {
                    Object[] data = new Object[]{count++, "_", tI, 1, tI};
                    Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                    data = new Object[]{count++, "if", tI, "GOTO", count - 12};
                    Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                }
            }
            token.add(tokens.get(i));
            if (tokens.get(i).getLexicalComp().equals("REPETIR")) {
                ini = i;
                w = ini;
                int la = 0, lc = 0;
                while (w < tnum) {
                    if (tokens.get(w).getLexicalComp().equals("LLAVE_A")) {
                        la++;
                    }
                    if (tokens.get(w).getLexicalComp().equals("LLAVE_C")) {
                        lc++;
                    }
                    if (la != 0 && la == lc) {
                        break;
                    }
                    w++;
                }
                w = ini;
                while (lc > 0) {
                    if (tokens.get(w).getLexicalComp().equals("LLAVE_C")) {
                        lc--;
                    }
                    //token.add(tokens.get(w));
                    w++;
                }
            }
            
            if (tokens.get(i).getLexicalComp().equals("MIENTRAS")) {
                ini = i;
                w = ini;
                int la = 0, lc = 0;
                while (w < tnum) {
                    if (tokens.get(w).getLexicalComp().equals("LLAVE_A")) {
                        la++;
                    }
                    if (tokens.get(w).getLexicalComp().equals("LLAVE_C")) {
                        lc++;
                    }
                    if (la != 0 && la == lc) {
                        break;
                    }
                    w++;
                }
                w = ini;
                while (lc > 0) {
                    if (tokens.get(w).getLexicalComp().equals("LLAVE_C")) {
                        lc--;
                    }
                    //token.add(tokens.get(w));
                    w++;
                }
            }
            
            
        }
        codigoIntermedio(token);
        token.clear();
        buscarProducciones();
        CIProd.clear();
        /*
            tokens antes del if o repetir
            tokens del if o repetir
            si hay un if o repetir dentro de uno de cualquiera
            
         */
    }

    public void buscarProducciones() {
        int c = count;
        for (Production id : CIProd) {
            String tipo = id.lexicalCompRank(0);
            if (tipo.equals("REPETIR")) {
                c += 2;
            }
            if (tipo.equals("MIENTRAS")) {
                c += 2;
            }
            if (tipo.equals("ESTRUCTURA_SI")) {
                c++;
            }
            if (tipo.equals("DISTANCIA") || tipo.equals("BOOLEANO") || tipo.equals("STRING")) {
                c += 2;
            }
            if (tipo.equals("MOVIMIENTO") || tipo.equals("IMPRIMIR") || tipo.equals("LUCES") || tipo.equals("PLANTACION")) {
                if (id.lexemeRank(2).equals(")")) {
                    c++;
                } else {
                    c += 2;
                }
            }
        }

        for (Production id : CIProd) {
            String tipo = id.lexicalCompRank(0);
            if (tipo.equals("REPETIR")) {
                System.out.println(id.lexemeRank(0, -1));
                Object[] data;
                data = new Object[]{count++, "NUMERO", id.lexemeRank(2), null, "T" + (t++)};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                data = new Object[]{count++, ">", "T" + (t - 1), 0, "T" + (t++)};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                data = new Object[]{count++, "ifFalse", "T" + (t - 1), "GOTO", count + c + 5};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                a = true;
                tI = "T" + (t - 1);
            }
            if (tipo.equals("MIENTRAS")) {
                System.out.println(id.lexemeRank(0, -1));
                Object[] data;
                data = new Object[]{count++, "PARENTESIS_A", id.lexemeRank(2), null, "T" + (t++)};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                data = new Object[]{count++, "ifFalse", "T" + (t - 1), "GOTO", count + c + 5};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                a = true;
                tI = "T" + (t - 1);
            }
            if (tipo.equals("ESTRUCTURA_SI")) {
                Object[] data;
                data = new Object[]{count++, "call", id.lexemeRank(2), null, "T" + (t++)};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                data = new Object[]{count++, "ifFalse", "T" + (t - 1), "GOTO", c + 1};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                f++;
            }
            if (tipo.equals("DISTANCIA") || tipo.equals("BOOLEANO") || tipo.equals("STRING")) {
                Object[] data = new Object[]{count++, id.lexemeRank(0), id.lexemeRank(3), null, "T" + (t++)};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                data = new Object[]{count++, id.lexemeRank(2), "T" + (t - 1), null, id.lexemeRank(1)};
                Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
            }
            if (tipo.equals("MOVIMIENTO") || tipo.equals("IMPRIMIR") || tipo.equals("LUCES") || tipo.equals("PLANTACION") ) {
                if (id.lexemeRank(2).equals(")")) {
                    Object[] data = new Object[]{count++, "call", id.lexemeRank(0), null, null};
                    Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                } else {
                    Object[] data = new Object[]{count++, "param", id.lexemeRank(2), null, "T" + (t++)};
                    Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                    data = new Object[]{count++, "call", id.lexemeRank(0), "T" + (t - 1)};
                    Functions.addRowDataInTable(cFrame.tblCuadruplos, data);
                }
            }

        }
    }
    
    
    
    //LO DEL MIGUEL
      private void syntacticAnalysis_2() {
        Grammar gramatica = new Grammar(tokens,errors);
        
        //Eliminacion de errores
        /* Eliminacion de errores */
        gramatica.delete(new String[]{"ERROR", "ERROR_1"}, 1);

        /* Mostrar gramáticas */
        gramatica.group("VALOR", "(NUMERO | DECIMAL | BOOLEANVALOR | CADENA | NATURAL)", true);
        
        gramatica.show();
    }

    private void compile() {
        clearFields();
        lexicalAnalysis();
        fillTableTokens();
        syntacticAnalysis();
        //syntacticAnalysis_2();
        semanticAnalysis();
        printConsole();
        codeHasBeenCompiled = true;
    }

    private void lexicalAnalysis() {
        // Extraer tokens
        Lexer lexer;
        try {
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = jtpCode.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF-8"));
            lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
    }

    private void syntacticAnalysis() {
        Grammar gramatica = new Grammar(tokens, errors);

        /* Eliminacion de errores */
        gramatica.delete(new String[]{"ERROR", "ERROR_1"}, 1);

        /* Mostrar gramáticas */
        gramatica.group("VALOR", "(NUMERO | DECIMAL | BOOLEANVALOR | CADENA | NATURAL)", true);
        
        gramatica.group("VALORES", "NUMERO", true);

        
        //LO DEL MIGUELON
            //DECLARAR VARIABLE QUE ES DE TIPO ENTERA
   
       //------------------------------------------
       
        /* Declaracion de variables*/
        
        gramatica.group("DEC_VAR", "(DISTANCIA|BOOLEANO|STRING|ENTERO) IDENTIFICADOR ASIGNACION VALOR", true, identProd);
        
        
        
        gramatica.group("DEC_VAR", "(DISTANCIA|BOOLEANO|STRING|ENTERO) ASIGNACION VALOR",true,
                2, "Error sintáctico {} en el renglón [#]: Falta el Identificador \n × El identificador debe comenzar con _ para ser considerado un Identificador");
        gramatica.finalLineColumn();
        gramatica.group("DEC_VAR", "(DISTANCIA|BOOLEANO|STRING|ENTERO) IDENTIFICADOR ASIGNACION ", true,
                4, "Error sintáctico {} en el renglón [#]: Falta el valor en la declaración del Identificador");
        gramatica.finalLineColumn();
        gramatica.group("DEC_VAR", "(DISTANCIA|BOOLEANO|STRING|ENTERO) IDENTIFICADOR VALOR ", true,
                101, "Error sintáctico {} en el renglón [#]: Falta el simbolo de asignacion (=) en la declaración del Identificador");
        gramatica.finalLineColumn();
        gramatica.group("DEC_VAR", " IDENTIFICADOR ASIGNACION VALOR", true,
                101, "Error sintáctico {} en el renglón [#]: Falta el tipo de dato para hacer la declaración \n × Los tipos de dato son los siguientes: booleano, cadena, distancia");
        gramatica.finalLineColumn();

        gramatica.delete("DISTANCIA", 12, "Error sintáctico {} en el renglón [#]: El tipo de dato no está contenido en la declaración de un Identificador");
        gramatica.delete("BOOLEANO", 13, "Error sintáctico {} en el renglón [#]: El tipo de dato no está contenido en la declaración de un Identificador");
        gramatica.delete("ENTERO", 15, "Error sintáctico {} en el renglón [#]: El tipo de dato no está contenido en la declaración de un Identificador");
        gramatica.delete("STRING", 78, "Error sintáctico {} en el renglón [#]: El tipo de dato no está contenido en la declaración de un Identificador");
        gramatica.delete("ASIGNACION", 14, "Error sintáctico {} en el renglón [#]: El tipo de dato no está contenido en la declaración de un Identificador");

        
        
        //Declaracion del arreglo
        gramatica.group("DEC_ARREGLO","ARREGLO CORCHETE_A CORCHETE_C TEXTO ID ASIGNACION VALOR",true);
        
        gramatica.group("DEC_ARREGLO", "ARREGLO CORCHETE_A CORCHETE_C TEXTO ASIGNACION VALOR",true,
                2, "Error sintáctico {} en el renglón [#]: Falta el Identificador para poder declarar el arreglo");
        
        gramatica.group("DEC_ARREGLO", "ARREGLO CORCHETE_A CORCHETE_C ASIGNACION VALOR",true,
                2, "Error sintáctico {} en el renglón [#]: Falta el Tipo de dato del para poder declarar arreglo");
        
        gramatica.group("DEC_ARREGLO", "CORCHETE_A CORCHETE_C TEXTO ASIGNACION VALOR",true,
                2, "Error sintáctico {} en el renglón [#]: Falta el especificar que es Arreglo");
        
        
        /* Agrupar de identificadores y definicion de parametros */
 /* Agrupacion de funciones */
        gramatica.group("FUNCION", "(MOVIMIENTO | LUCES | IMPRIMIR | PLANTACION )", true);

        gramatica.group("FUNCION_COMP", "FUNCION PARENTESIS_A (VALOR|IDENTIFICADOR)? PARENTESIS_C", true, funcionProd);

        gramatica.group("FUNCION_COMP", "FUNCION (VALOR|IDENTIFICADOR)? PARENTESIS_C ", true,
                15, "Error sintáctico {} en el renglón [#]: Falta el Parentesis que abre en la funcion");
        gramatica.finalLineColumn();
        gramatica.group("FUNCION_COMP", "FUNCION PARENTESIS_A (VALOR|IDENTIFICADOR)", true,
                16, "Error sintáctico {} en el renglón [#]: Falta el Parentesis que cierra en la funcion");
        gramatica.initialLineColumn();

        /* Eliminacion de funciones incompletas*/
        gramatica.delete("FUNCION", 17, "Error sintáctico {} en el renglón [#]: La función no está declarada correctamente");

        /* Agrupacion de estructuras de control */
        gramatica.group("PARAMETRO", "(NUMERO | IDENTIFICADOR)", true);
        gramatica.group("OBSTACULOS", "OBSTACULO PARENTESIS_A PARENTESIS_C", true);

        /* estructura de control IF*/
        gramatica.group("EST_CONTROL_IF", "(ESTRUCTURA_SI|MIENTRAS) PARENTESIS_A OBSTACULOS PARENTESIS_C", true, ifProd);

        gramatica.group("EST_CONTROL_IF", "(ESTRUCTURA_SI|MIENTRAS)  PARENTESIS_A OBSTACULOS", true,
                19, "Error sintáctico {} en el renglón [#]: Falta el Parentesis que cierra en la estructura");

        gramatica.group("EST_CONTROL_IF", "(ESTRUCTURA_SI|MIENTRAS)  PARENTESIS_C", true,
                78, "Error sintáctico {} en el renglon [#]: Falta el Parentesis que abre en la estructura");

        gramatica.group("EST_CONTROL_IF", "(ESTRUCTURA_SI|MIENTRAS) PARENTESIS_A PARENTESIS_C", true,
                79, "Error sintáctico {} en el renglón [#]: Falta el Parámetro");

        /* Eliminacion de estructuras de control IF */
        gramatica.delete("ESTRUCTURA_SI|MIENTRAS", 23, "Error Sintáctico {} en el renglón [#]: La estructura de control no esta bien declarada ");

        /* estructura de control REPETIR*/
        gramatica.group("EST_CONTROL_REPETIR", "REPETIR PARENTESIS_A VALOR PARENTESIS_C");

        gramatica.group("EST_CONTROL_REPETIR", "REPETIR PARENTESIS_A VALOR", true,
                394, "Error sintáctico {} en el renglón [#]: Falta el Parentesis que cierra en la estructura");

        gramatica.group("EST_CONTROL_REPETIR", "REPETIR VALOR PARENTESIS_C", true,
                48, "Error sintáctico {} en el renglón [#]: Falta el Parentesis que abre en la estructura");

        gramatica.group("EST_CONTROL_REPETIR", "REPETIR PARENTESIS_A PARENTESIS_C", true,
                49, "Error sintáctico {} en el renglón [#]: Falta el Parámetro ");

        /* Eliminacion de estructuras de control REPETIR */
        gramatica.delete("REPETIR", 23, "Error Sintáctico {} en el renglón [#]: La estructura de control no esta bien declarada ");

        
         //Estructura de Control PARA
        gramatica.group("EST_CONTROL_PARA","CICLO_PARA" ,true);
       
        gramatica.group("EST_CONTROL_PARA_COM","EST_CONTROL_PARA PARENTESIS_A (VALOR|IDENTIFICADOR)? DOS_PUNTOS VALOR DOS_PUNTOS VALOR PARENTESIS_C" ,true);
           
        
        gramatica.group("EST_CONTROL_PARA_COM", "EST_CONTROL_PARA PARENTESIS_A (VALOR|IDENTIFICADOR)?  VALOR DOS_PUNTOS VALOR PARENTESIS_C", true,
                15, "Error sintáctico {} en el renglón [#]: Faltan los dos puntos de inicializacion del ciclo");
                                        //PARA(50 50:50)
        gramatica.group("EST_CONTROL_PARA_COM", "EST_CONTROL_PARA PARENTESIS_A (VALOR|IDENTIFICADOR)? DOS_PUNTOS VALOR  VALOR PARENTESIS_C", true,
                15, "Error sintáctico {} en el renglón [#]: Faltan los dos puntos de incremento del ciclo");
                                        //PARA(50:50 50)
        
        gramatica.group("EST_CONTROL_PARA_COM", "EST_CONTROL_PARA (VALOR|IDENTIFICADOR)? DOS_PUNTOS VALOR DOS_PUNTOS VALOR PARENTESIS_C", true,
                15, "Error sintáctico {} en el renglón [#]: Falta el Parentesis que abre en la funcion");
                                                            //PARA 50:50:50)
        gramatica.group("EST_CONTROL_PARA_COM", "EST_CONTROL_PARA PARENTESIS_A (VALOR|IDENTIFICADOR)? DOS_PUNTOS VALOR DOS_PUNTOS VALOR", true,
                16, "Error sintáctico {} en el renglón [#]: Falta el Parentesis que cierra en la funcion");
                                                            //PARA(50:50:50
        gramatica.group("EST_CONTROL_PARA_COM", "EST_CONTROL_PARA PARENTESIS_A DOS_PUNTOS VALOR DOS_PUNTOS VALOR PARENTESIS_C", true,
                16, "Error sintáctico {} en el renglón [#]: Faltan los argumentos de inicializacion del ciclo");
                                                            //PARA(:50:50)
        gramatica.group("EST_CONTROL_PARA_COM", "EST_CONTROL_PARA PARENTESIS_A (VALOR|IDENTIFICADOR)? DOS_PUNTOS  DOS_PUNTOS VALOR PARENTESIS_C ", true,
                16, "Error sintáctico {} en el renglón [#]: Falta los argumentos del tamaño del ciclo");
                                                            //PARA(50::50)
        gramatica.group("EST_CONTROL_PARA_COM", "EST_CONTROL_PARA PARENTESIS_A (VALOR|IDENTIFICADOR)? DOS_PUNTOS VALOR DOS_PUNTOS PARENTESIS_C ", true,
                16, "Error sintáctico {} en el renglón [#]: Falta los argumentos de los incrementos del ciclo");
                                                            //PARA(50:50:)
        
      
        gramatica.initialLineColumn();

        /* Eliminación de estructuras de control mal declaradas */
        gramatica.delete("EST_CONTROL_PARA",
                15, "Error sintáctico {} en el renglón [#]: La estructura de control no está declarada correctamente");
        
        //----------------------
        
        /* estructura de control ELSE*/
        //gramatica.group("EST_CONTROL_ELSE", "ESTRUCTURA_SINO LLAVE_A");
        /* Eliminacion de estructuras de control IF */
        //gramatica.delete("ESTRUCTURA_SINO", 23, "Error Sintáctico {}: La estructura de control no esta bien declarada [#, %]");
        //**********************************************************************
        /*PUNTO Y COMA DE TIPO_DATO*/
        gramatica.group("VARIABLE_PC", "DEC_VAR PUNTO_COMA", true);
        gramatica.group("VARIABLE_PC", "DEC_VAR|DEC_ARREGLO", true,
                11, "Error Sintáctico {} en el renglón [#]: Falta el punto y coma al final de la declaración de un Identificador");

        /* Verificacion de puunto y coma al final de una sentencia */
        gramatica.group("FUNCION_COMP_PC", "FUNCION_COMP PUNTO_COMA");
        gramatica.group("FUNCION_COMP_PC", "FUNCION_COMP",
                25, "Error sintáctico {} en el renglón [#]: Falta el punto y coma al final de la declaración de función");

        /* Eliminación del punto y coma */
         gramatica.delete("PUNTO_COMA", 26, "Error sintáctico {} en el renglón [#]: El punto y coma no está al final de una sentencia \n × El punto y coma debe ir al final de una declaración de Identificadores "
                + "\n × El punto y coma debe ir al final de la declaración de una función");

        /* Sentencias */
        gramatica.group("SENTENCIAS", "(VARIABLE_PC | FUNCION_COMP_PC)+");

        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.group("EST_CONTROL_COMP_LASLC", "(EST_CONTROL_IF|EST_CONTROL_REPETIR|ESTRUCTURA_SINO|EST_CONTROL_PARA_COM) LLAVE_A (SENTENCIAS)? LLAVE_C", true);
            gramatica.group("SENTENCIAS", "(SENTENCIAS | EST_CONTROL_COMP_LASLC)+ ");
        });
         
        
        /* Eliminación de paréntesis */
        gramatica.delete(new String[]{"PARENTESIS_A", "PARENTESIS_C"},
                16, " × Error sintáctico {} en el renglón [#]: El paréntesis [] no está contenido en una agrupación \n × El parentesis debe estar en la declaración de un ciclo "
                        + "\n × El parentesis debe estar en la declaración de una función \n × El parentesis debe estar en la declaración de una estructura de control ");

        /* Eliminación de valores */
        gramatica.delete("VALOR",
                17, " × Error sintáctico {} en el renglón [#]: El valor no está contenido en una función o estructura de control");

        gramatica.finalLineColumn();

        
            /* Estructturas de funcion incompletas */
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.initialLineColumn();
            gramatica.group("EST_CONTROL_COMP_LASLC", "(EST_CONTROL_IF|EST_CONTROL_REPETIR|ESTRUCTURA_SINO|EST_CONTROL_PARA_COM) (SENTENCIAS)? LLAVE_C", true, 27, "Error sintáctico {} en el renglón [#]:  Falta la llave que abre en la estructura de control");

            gramatica.finalLineColumn();
            gramatica.group("EST_CONTROL_COMP_LASLC", "(EST_CONTROL_IF|EST_CONTROL_REPETIR|ESTRUCTURA_SINO|EST_CONTROL_PARA_COM) LLAVE_A (SENTENCIAS)?", true, 28, "Error sintáctico {} en el renglón [#]:  Falta la llave que cierra en la estructura de control");

            gramatica.group("SENTENCIAS", "(SENTENCIAS | EST_CONTROL_COMP_LASLC)");
        });

        
        
        gramatica.show();
        gramatica.toString();
    }

    private void codigoIntermedio(ArrayList<Token> tokenz) {
        Grammar gramatica = new Grammar(tokenz, errors);

        gramatica.group("OBSTACULOS", "OBSTACULO PARENTESIS_A PARENTESIS_C", true);
        /* estructura de control IF*/
        gramatica.group("EST_CONTROL_IF", "ESTRUCTURA_SI PARENTESIS_A OBSTACULOS PARENTESIS_C", true, CIProd);
        /* Mostrar gramáticas */
        gramatica.group("VALOR", "(NUMERO | DECIMAL | BOOLEANVALOR | CADENA)", true);
        /* Declaracion de variables ENTEROS*/
        gramatica.group("DEC_VAR", "(DISTANCIA|BOOLEANO|STRING) IDENTIFICADOR ASIGNACION VALOR", true, CIProd);
        /* Agrupar de identificadores y definicion de parametros */
 /* Agrupacion de funciones */
        gramatica.group("FUNCION", "(MOVIMIENTO | LUCES | IMPRIMIR | OBSTACULO | PLANTACION)", true);
        gramatica.group("FUNCION_COMP", "FUNCION PARENTESIS_A (VALOR|IDENTIFICADOR)? PARENTESIS_C", true, CIProd);
        /* estructura de control REPETIR*/
        gramatica.group("EST_CONTROL_REPETIR", "REPETIR PARENTESIS_A VALOR PARENTESIS_C", true, CIProd);
    }

    private void semanticAnalysis() {
        /* ASOCIAR TIPOS DE DATOS A LOS VALORES */
        HashMap<String, String> identDataType = new HashMap<>();
        identDataType.put("distancia", "NUMERO");
        identDataType.put("cadena", "CADENA");
        identDataType.put("booleano", "BOOLEANVALOR");
        identDataType.put("entero", "NUMERO");
        identDataType.put("Distancia", "NUMERO");
        identDataType.put("Cadena", "CADENA");
        identDataType.put("Booleano", "BOOLEANVALOR");
        identDataType.put("Entero", "NUMERO");
        identDataType.put("DISTANCIA", "NUMERO");
        identDataType.put("CADENA", "CADENA");
        identDataType.put("BOOLEANO", "BOOLEANVALOR");
        identDataType.put("ENTERO", "NUMERO");
        
        //Recorre las variables declaradas y revisa su valor 
        for (Production id : identProd) {
            //Checa si su valor es del tipo de dato requerido
            //distancia -a = 5;
            if (!identDataType.get(id.lexemeRank(0)).equals(id.lexicalCompRank(-1))) {
                errors.add(new ErrorLSSL(1, "Error semántico {} en el renglón [#]: El valor no corresponde con el tipo de dato asignado", id, true));
            } else {
                //Checa si la variable ya existe
                if (identificadores.containsKey(id.lexemeRank(1))) {
                    errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: La variable "+" ("+id.lexemeRank(1)+") " + "ya existe favor de reemplazarla por una no existente.", id, true));
                } else {
                    identificadores.put(id.lexemeRank(1), id.lexemeRank(-1));
                    tipoDato.put(id.lexemeRank(1), id.lexemeRank(0));
                }
            }
        }

        //--------------------------------------
        
        HashMap<String, String> funcDataType = new HashMap<>();
        funcDataType.put("imprimir", "STRING");
        funcDataType.put("adelante", "NUMERO");
        funcDataType.put("atras", "NUMERO");
        funcDataType.put("luces", "BOOLEANVALOR");
        funcDataType.put("Imprimir", "STRING");
        funcDataType.put("Adelante", "NUMERO");
        funcDataType.put("Atras", "NUMERO");
        funcDataType.put("Luces", "BOOLEANVALOR");
        funcDataType.put("IMPRIMIR", "STRING");
        funcDataType.put("ADELANTE", "NUMERO");
        funcDataType.put("ATRAS", "NUMERO");
        funcDataType.put("LUCES", "BOOLEANVALOR");
        funcDataType.put("plantar", "BOOLEANVALOR");
        funcDataType.put("Plantar", "BOOLEANVALOR");
        funcDataType.put("PLANTAR", "BOOLEANVALOR");
        funcDataType.put("plantar", "NUMERO");
        funcDataType.put("Plantar", "NUMERO");
        funcDataType.put("PLANTAR", "NUMERO");
        
        //ArrayList<Production> FuncionIdent = new 
        for (Production id : funcionProd) {
            if (id.lexicalCompRank(2).equals("IDENTIFICADOR")) {
                //Si se usa una variable que no se declaro, da error
                if (!tipoDato.containsKey(id.lexemeRank(2))) {
                    errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: Variable no declarada", id, true));
                } else {
                    //Checa que las funciones usen el tipo de valor correcto
                    if (id.lexemeRank(0).equals("imprimir")|| id.lexemeRank(0).equals("Imprimir") || id.lexemeRank(0).equals("IMPRIMIR")) {
                        if (!tipoDato.get(id.lexemeRank(2)).equals("cadena")) {
                            errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: Variable incompatible o Tipo de dato no compatible con la funcion ", id, true));
                        }
                    } else if (id.lexemeRank(0).equals("luces")||id.lexemeRank(0).equals("Luces")||id.lexemeRank(0).equals("LUCES")) {
                        if (!tipoDato.get(id.lexemeRank(2)).equals("booleano")) {
                            errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: Variable incompatible o Tipo de dato no compatible con la funcion", id, true));
                        }
                    } else if (id.lexemeRank(0).equals("adelante") || id.lexemeRank(0).equals("Adelante")||id.lexemeRank(0).equals("ADELANTE")||
                                id.lexemeRank(0).equals("atras")||id.lexemeRank(0).equals("Atras")||id.lexemeRank(0).equals("ATRAS")|| 
                                id.lexemeRank(0).equals("plantar")||id.lexemeRank(0).equals("PLANTAR")||id.lexemeRank(0).equals("Plantar")) {
                        if (!tipoDato.get(id.lexemeRank(2)).equals("distancia")) {
                            errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: Variable incompatible o Tipo de dato no compatible con la funcion", id, true));
                        }   
                        
                    }
                    else if (id.lexemeRank(0).equalsIgnoreCase("derecha") || id.lexemeRank(0).equalsIgnoreCase("Derecha") ||
                        id.lexemeRank(0).equalsIgnoreCase("DERECHA") || id.lexemeRank(0).equalsIgnoreCase("izquierda") ||
                        id.lexemeRank(0).equalsIgnoreCase("Izquierda") || id.lexemeRank(0).equalsIgnoreCase("IZQUIERDA")) {
                        if (!tipoDato.get(id.lexemeRank(2)).equals("distancia")) {
                            errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: Variable incompatible o Tipo de dato no compatible con la función", id, true));
                        }
                    }
                }
            } 
        }
        
        
           //--------------------------------------PARA
        
        HashMap<String, String> cicloPara = new HashMap<>();
        cicloPara.put("para", "NUMERO");
        cicloPara.put("Para", "NUMERO");
        cicloPara.put("PARA", "NUMERO");
        
        
        //ArrayList<Production> FuncionIdent = new 
        for (Production id : funcionProd) {
            if (id.lexicalCompRank(2).equals("CICLO_PARA")) {
                //Si se usa una variable que no se declaro, da error
                if (!tipoDato.containsKey(id.lexemeRank(2))) {
                    errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: Variable no declarada", id, true));
                } else {
                    //Checa que las funciones usen el tipo de valor correcto
                    if (id.lexemeRank(0).equals("para")|| id.lexemeRank(0).equals("Para") || id.lexemeRank(0).equals("PARA")) {
                        if (!tipoDato.get(id.lexemeRank(2)).equals("cadena")) {
                            errors.add(new ErrorLSSL(2, "Error semántico {} en el renglón [#]: Variable incompatible o Tipo de dato no compatible con la funcion", id, true));
                        }
                    
                    }
                }
            }
        }
    }

    private void colorAnalysis() {
        /* Limpiar el arreglo de colores */
        textsColor.clear();
        /* Extraer rangos de colores */
        LexerColor lexerColor;
        try {
            File codigo = new File("color.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = jtpCode.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexerColor = new LexerColor(entrada);
            while (true) {
                TextColor textColor = lexerColor.yylex();
                if (textColor == null) {
                    break;
                }
                textsColor.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        Functions.colorTextPane(textsColor, jtpCode, new Color(40, 40, 40));
    }

    private void fillTableTokens() {
        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
           // Functions.addRowDataInTable(tblTokens, data);
            
        });
    }
    
    private void fillTableSimbolos(){
        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(),"-","[" + token.getLine() + ", " + token.getColumn() + "]"};
           Functions.addRowDataInTable(cFrame3.tblSimbolos2, data);
            
        });
    }
    
    private void fillTableTokens2() {
        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            Functions.addRowDataInTable(cFrame2.tblTokens, data);
            
        });
    }

    private void printConsole() {
        int sizeErrors = errors.size();
        if (sizeErrors > 0) {
            Functions.sortErrorsByLineAndColumn(errors);
            String strErrors = "\n";
            for (ErrorLSSL error : errors) {
                String strError = String.valueOf(error);
                strErrors += strError + "\n";
            }
            jtaOutputConsole.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
            btnCodigoIntermedio.setVisible( false); 
            btnTTokens.setVisible(false);
            jButton1.setVisible(false);
            btnEjecutar.setVisible(false);
        } else {
            jtaOutputConsole.setText("Compilación terminada...");
            btnCodigoIntermedio.setVisible(true); 
            btnTTokens.setVisible(true);
            jButton1.setVisible(false);
            btnEjecutar.setVisible(true);
        }
        jtaOutputConsole.setCaretPosition(0);
    }

    private void limpiarArchivo() {
        try {
            FileWriter escritor = new FileWriter(rutaAsm, false);
            escritor.write("");
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearFields() {
        //Functions.clearDataInTable(tblTokens);
        jtaOutputConsole.setText("");
        tokens.clear();
        errors.clear();
        identProd.clear();
        funcionProd.clear();
        ifProd.clear();
        CIProd.clear();
        identificadores.clear();
        codeHasBeenCompiled = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                System.out.println("LookAndFeel no soportado: " + ex);
            }
            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCodigoIntermedio;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarC;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnTTokens;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jtaOutputConsole;
    private javax.swing.JTextPane jtpCode;
    private javax.swing.JPanel rootPanel;
    // End of variables declaration//GEN-END:variables

  
}
