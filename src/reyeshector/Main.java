package reyeshector;

import exception.*;
import java.awt.Font;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.*;

/**
 * PROYECTO DE LA 2DA EVALUACION DE LA ASIGNATURA PROGRAMACIÓN
 * DAM en CPIPF Los Enlaces, Zaragoza, España
 * 2025/26
 * @author Héctor Reyes Medina
 */
public class Main {
    
    /**
     * MÉTODO PARA CARGAR DATOS DE PRUEBA AUTOMÁTICAMENTE.
     * Crea 3 alumnos (Juan, María y Daniel) con sus respectivas asignaturas
     * y algunas notas ya puestas para facilitar las pruebas del programa.
     */
    private static void cargarDatosPrueba() {
        try {
            // --- ALUMNO 1: JUAN ---
            HashSet<Asignatura> asigJuan = new HashSet<>();
            
            AsignaturaPresencial progJuan = new AsignaturaPresencial("P001", "Programacion", "3");
            progJuan.setNotasFloat(new float[]{7.5f, 8.0f, 6.5f}); // Aprobado
            
            AsignaturaEmpresa bdJuan = new AsignaturaEmpresa("E001", "Bases de Datos", "Oracle Iberica");
            bdJuan.setNotaFinal(8.5f); // Aprobado
            
            asigJuan.add(progJuan);
            asigJuan.add(bdJuan);
            gestor.GestorMatrícula.addAlumno(new Alumno("Juan", asigJuan));

            // --- ALUMNO 2: MARÍA ---
            HashSet<Asignatura> asigMaria = new HashSet<>();
            
            AsignaturaPresencial entMaria = new AsignaturaPresencial("P002", "Entornos", "2");
            entMaria.setNotasFloat(new float[]{9.0f, 9.5f}); // Aprobado con notaza
            
            asigMaria.add(entMaria);
            gestor.GestorMatrícula.addAlumno(new Alumno("María", asigMaria));

            // --- ALUMNO 3: DANIEL ---
            HashSet<Asignatura> asigDaniel = new HashSet<>();
            
            AsignaturaEmpresa folDaniel = new AsignaturaEmpresa("E002", "Formacion", "Ministerio");
            folDaniel.setNotaFinal(4.0f); // Suspenso
            
            AsignaturaPresencial sisDaniel = new AsignaturaPresencial("P003", "Sistemas", "4");
            sisDaniel.setNotasFloat(new float[]{5.0f, 4.0f, 3.5f, 5.5f}); // Suspenso por tener notas menores a 4
            
            asigDaniel.add(folDaniel);
            asigDaniel.add(sisDaniel);
            gestor.GestorMatrícula.addAlumno(new Alumno("Daniel", asigDaniel));

        } catch (MyException ex) {
            System.out.println("Error al cargar los datos de prueba: " + ex.getMessage());
        }
    }
    
    /**
     * MÉTODO QUE BUSCA A UN ALUMNO EN EL GESTOR DEL ALUMNADO CON SU CODIGO RESPECTIVO
     * Primero, el método comprobará que el codigo no sea nulo, no esté vacío.
     * Y segundo, que en su búsqueda, lo haya encontrado.
     * @param mensajeBusqueda mensaje de ayuda al usuario en su busqueda
     * @return el alumno si ha sido encontrado
     */
    
    private static Alumno buscarAlumno(String mensajeBusqueda) throws MyException {
        Alumno alu = null; //inicializo al posible alumno antes q nada
        String codAlu;
        boolean codNotNull = false;
        boolean alumnoCorrecto = false;
        do {
            do { //comprueba que el codigo no sea nulo o vacío
                codAlu = JOptionPane.showInputDialog(mensajeBusqueda);
                if (codAlu == null || codAlu.trim().isEmpty()) {
                    int opAdv = ventanaAdvertencia(
                            "No se ha detectado un alumno, ¿Desea cancelar la busqueda?", 
                            "Sí, cancelar.", 
                            "No, seguir buscando."
                    );
                    switch (opAdv) {
                        case 0 -> throw new MyException("Busqueda cancelada\nVolviendo al menú principal..."); // EN CASO DE QUE EL USUARIO CLICKE DE SALIR;
                        case 1 -> codNotNull = false;
                        default -> throw new MyException("Busqueda cancelada\nVolviendo al menú principal..."); // EN CASO DE QUE EL USUARIO NOS VACILE Y LE VUELVA A DAR A LA X JAJAJA;
                    }
                } else {
                    codNotNull = true;
                } 

            } while (!codNotNull);

            //INTENTAMOS BUSCAR AL ALUMNO EN LA LISTA, SI NO EXISTE O NO ES CORRECTO LANZARÁ UNA EXCEPCIÓN
            try {
                alu = gestor.GestorMatrícula.getAlumno(codAlu.toUpperCase());
                alumnoCorrecto = true;
                
            } catch (MyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la busqueda del alumno", JOptionPane.ERROR_MESSAGE);
                codNotNull = false;
            }
        } while (!alumnoCorrecto);    
        return alu;
    }
    
    /**
     * MÉTODO QUE ABRE UNA VENTANA QUE MUESTRA ASIGNATURAS COMO BOTONES
     * @param coleccionAsignaturas HashSet de asignaturas del alumno.
     * @return asignatura elegida por el usuario
     */
    
    private static Asignatura ventanaAsignaturas(HashSet<Asignatura> coleccionAsignaturas) {
        Object[] arrayAsignaturas = coleccionAsignaturas.toArray();
        return (Asignatura) JOptionPane.showInputDialog(
                null,
                "Seleccione una asignatura para poner sus notas:",
                "Asignaturas del Alumno",
                JOptionPane.QUESTION_MESSAGE,
                null,
                arrayAsignaturas,
                arrayAsignaturas[0]
        );
    }
    
    /**
    * MÉTODO QUE CONVIERTE UN STRING A FLOAT
    * Comprobaremos que la nota sea correcta en formato String y la añadiremos al array.
     * @param nota cadena que debería representar un numero real
     * @return devuelve una nota Float
     * @throws exception.MyException
    */
    private static float comprobarNota(String nota) throws MyException {
        // COMPRUEBO QUE EL USUARIO NO HAYA PUESTO UNA NOTA NULA O VACÍA
        if (nota == null) {
            throw new MyException("CANCELAR"); // SI EL USUARIO NO PONE NINGUN NUMERO
        }
        if (nota.trim().isEmpty()) {
            throw new MyException("ADVERTENCIA");
        }
        try {
            // INTENTAMOS CONVERTIR LA NOTA A FLOAT
            return Float.parseFloat(nota);
            
        } catch (NumberFormatException e) {
            // SI EL NUMERO ES INCORRECTO O INVALIDO, SALTARÁ EL AVISO
            throw new MyException("Formato incorrecto. Por favor, introduce un número válido.(Ejemplo: 7.5)");
        }
    }
    
    /**
     * MÉTODO QUE BUSCA AL ALUMNO EN LA LISTA DE LOS MISMOS PARA CAMBIAR O COLOCARLE SUS NOTAS.
     * Primero buscaremos al alumno con su codigo. Comprobará todas las opciones posibles
     * Si todo sale bien, sacará al alumno del gestor con ese codigo y mostrará todas las
     * asignaturas del alumno en una ventana.
     * El usuario elegirá que asignatura quiere ponerle nota y podrá marcharse
     * del menú sin necesidad de ponerle nota a TODOS los alumnos.
     * 
     * @throws MyException
     */
    
    private static void ponerNotas () throws MyException {    
        Alumno alu = null;
        try {
            alu = buscarAlumno("Inserte el codigo del alumno que quiere poner sus notas.");
        } catch (MyException ex) {
            throw new MyException(ex.getMessage());
        }
        
        /* 
        INICIALIZO LAS ASIGNATURAS DE ESTE ALUMNO Y HAGO UN ARRAY DE ELLAS 
        PARA MOSTRARLO EN UNA VENTANA Y QUE EL USUARIO PUEDA ELEGIR
        LAS ASIGNATURAS A LAS QUE LE QUIERE PONER NOTAS
        */
        
        HashSet<Asignatura> coleccionAsignaturas = alu.getAsignaturas();
        boolean finalizar = false;
        do {
            Asignatura asig = ventanaAsignaturas(coleccionAsignaturas);
            if (asig == null) {
                throw new MyException("Asignación de notas cancelada. Volviendo al menú principal...");
            }

            /*
            COMPRUEBO SI LA ASIGNATURA ES PRESENCIAL O DE EMPRESA
                - SI ES PRESENCIAL SACAREMOS Q NOTAS QUIERE EL USUARIO Y UNA VEZ TENGAMOS ¡TODAS! LAS NOTAS
                SE AÑADIRÁN AL ALUMNO
                - SI ES DE EMPRESA, PEDIREMOS AL USUARIO QUE LE PONGA LA ÚNICA NOTA AL ALUMNO
            */

            if (asig instanceof AsignaturaPresencial) {
                float[] notasNuevas = eleccionNotasAsignaturaPresencial((AsignaturaPresencial) asig);
                ((AsignaturaPresencial) asig).setNotasFloat(notasNuevas);
            }
            
            if (asig instanceof AsignaturaEmpresa) {
                float notaFinal = eleccionNotaAsignaturaEmpresa((AsignaturaEmpresa) asig);
                ((AsignaturaEmpresa) asig).setNotaFinal(notaFinal);
            }
            finalizar = finalizarPonerNotas();
            
        } while (!finalizar);
    }
    
    /**
     * MÉTODO EXCLUSIVO PARA LA ASIGNACIÓN DE NOTAS A ASIGNATURA EMPRESA
     * El método recibe una asignatura de empresa y se le pide al usuario
     * que elija que nota (Float) ponerle al alumno.
     * Esta nota reemplaza a las nota que tenga el alumno.
     * Se introducirá una nota y se comprobará:
     *  - Que sea menor o igual que 10
     *  - Que sea mayor o igual que 0
     *  - Que no sea nula
     *  - Que no esté vacía
     * @param asigEmp objeto Asignatura de tipo Empresa
     * @return un float con la nueva nota del Alumno
     * @throws MyException 
     */

    private static float eleccionNotaAsignaturaEmpresa(AsignaturaEmpresa asigEmp) throws MyException {
        String notaSt;
        float notaFinal = 0;
        boolean correcto = false;
        do {
            try {
                notaSt = JOptionPane.showInputDialog(""
                        + "¿Que nota quiere ponerle a la asignatura " + asigEmp.getNombre()
                        + " con la empresa " + ((AsignaturaEmpresa) asigEmp).getNombreEmpresa() + "?");
                notaFinal = comprobarNota(notaSt);
                
            } catch (MyException ex) {
                if (ex.getMessage().equals("CANCELAR")) {
                    throw new MyException("Operación cancelada. Las notas no se han guardado.");
                }
                if (ex.getMessage().equals("ADVERTENCIA")) {
                    int opAdv = ventanaAdvertencia(
                            "La nota colocada no puede estar vacía.\n"
                                    + "Si no coloca todas las notas correctamente no se podrá continuar con el proceso\n"
                                    + "y se regresará al menú principal. ¿Está seguro de ello?",
                            "Cancelar proceso",
                            "Seguir creando colocando las notas");
                    switch (opAdv) {
                        case 0 -> throw new MyException("Operación cancelada. Las notas no se han guardado."); // EN CASO DE QUE EL USUARIO CLICKE DE SALIR;
                        case 1 -> correcto = false;
                        default -> throw new MyException("Operación cancelada. Las notas no se han guardado."); // EN CASO DE QUE EL USUARIO NOS VACILE Y LE VUELVA A DAR A LA X JAJAJA;
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la asignación de la nota", JOptionPane.WARNING_MESSAGE);
                }
            }
            correcto = true;
            
        } while(!correcto);
        
        return notaFinal;
    }
    
    /**
     * METODO QUE PREGUNTA AL USUARIO SI DESEA PONER LAS NOTAS A OTRA ASIGNATURA
     * Si el usuario clicka otra cosa que no sea "Continuar", finalizará la colocación de notas.
     * @return 
     */

    private static boolean finalizarPonerNotas() {
        boolean finalizar = false;
        int op = ventanaAdvertencia("¿Desea ponerle las notas a otra asignatura?", "Continuar", "Finalizar"); //NO ES UNA ADVERTENCIA PERO USO EL METODO PARA UNA MISMA FUNCION
        switch (op) {
            case 1 -> finalizar = true;
        }
        
        return finalizar;
    }
    
    /**
     * MÉTODO EXCLUSIVO PARA LA ASIGNACIÓN DE NOTAS A ASIGNATURA PRESENCIAL
     * El método recibe una asignatura presencial y de ella se saca el numero de prácticas
     * que posee y se realiza un float notasNuevas con las notas nuevas que quiere el usuario.
     * Estas notasNuevas reemplazan a las notas que tiene el alumno.
     * Se introducirá nota de la práctica una por una y se comprobará:
     *  - Que sea menor o igual que 10
     *  - Que sea mayor o igual que 1
     *  - Que no sea nula
     *  - Que no esté vacía
     * @param asigPres objeto Asignatura de tipo Presencial
     * @return array de floats con las notas nuevas que quiere el usuario.
     * @throws MyException 
     */

    private static float[] eleccionNotasAsignaturaPresencial(AsignaturaPresencial asigPres) throws MyException {
        int totalNotas = asigPres.getNumPracticas(); //Sacamos el numero de practicas que tiene la asignatura.
        float[] notasNuevas = new float[totalNotas]; //Inicializamos un array vacío que modificaremos y mandaremos al método de la clase AsignaturaPrensencial.
        int NUM_PRACTICA = 1;
        // Bucle para pedir cada nota al usuario
        for (int i = 0; i < totalNotas; i++) {
            boolean notaCorrecta = false;
            do {
                try {
                    String entrada = JOptionPane.showInputDialog("Introduce la nota de la práctica " + (NUM_PRACTICA) + ":");
                    float nota = comprobarNota(entrada); // El método comprueba que la nota es un float correcto.
                    
                    if (nota < 1 || nota > 10) {
                        throw new MyException("La nota debe estar entre 1 y 10.");
                    }
                    
                    notasNuevas[i] = nota;
                    notaCorrecta = true;
                    NUM_PRACTICA++;
                } catch (MyException ex) {
                    if (ex.getMessage().equals("CANCELAR")) {
                        throw new MyException("Operación cancelada. Las notas no se han guardado.");
                    }
                    if (ex.getMessage().equals("ADVERTENCIA")) {
                        int opAdv = ventanaAdvertencia(
                                "La nota colocada no puede estar vacía.\n"
                                        + "Si no coloca todas las notas correctamente no se podrá continuar con el proceso\n"
                                        + "y se regresará al menú principal. ¿Está seguro de ello?",
                                "Cancelar proceso",
                                "Seguir creando colocando las notas");
                        switch (opAdv) {
                            case 0 -> throw new MyException("Operación cancelada. Las notas no se han guardado."); // EN CASO DE QUE EL USUARIO CLICKE DE SALIR;
                            case 1 -> notaCorrecta = false;
                            default -> throw new MyException("Operación cancelada. Las notas no se han guardado."); // EN CASO DE QUE EL USUARIO NOS VACILE Y LE VUELVA A DAR A LA X JAJAJA;
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la asignación de la nota", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } while (!notaCorrecta);
        }
        return notasNuevas;     
    }
        
    /**
     * MÉTODO QUE CREA UNA ASIGNATURA DE EMPRESA NUEVA
     * Si todo lo rellenado es correcto saldrá de un bucle y devolverá el objeto.
     * El metodo comprobará que no lance ninguna excepcion (Ni la propia ni NULL)
     * 
     * @return Devuelve la clase AsignaturaEmpresa preguntando todos sus valores:
     *  Codigo, nombre y nombre de la empresa.
     * @throws MyException excepcion propia que comprueba las limitaciones del constructor de la clase
     */
    
    private static AsignaturaEmpresa crearEmpresa () throws MyException {
        Asignatura asig = null;
        boolean correcto = false;
        do {
            try {
                asig = new AsignaturaEmpresa(
                    JOptionPane.showInputDialog(null, "¿Qué codigo tendrá su asignatura?", "Codigo de la Asignatura"), 
                    JOptionPane.showInputDialog(null, "¿Qué nombre tendrá su asignatura?", "Nombre de la Asignatura"), 
                    JOptionPane.showInputDialog(null, "¿Qué nombre tiene la empresa?", "Nombre de la Empresa")          
                );
                
                correcto = true;
                
            } catch (MyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la creación de la asignatura", JOptionPane.WARNING_MESSAGE);

            } catch (NullPointerException ex2) {
                int opAdv = ventanaAdvertencia(
                        "Está cancelando el proceso de creación de la asignatura.\n"
                        + "Volverá al menú de asignaturas. ¿Está seguro de ello?",
                        "Cancelar proceso",
                        "Seguir creando la asignatura de empresa");
                switch (opAdv) {
                    case 0 -> throw new MyException("No se ha podido crear la asignatura de empresa.\nVolviendo al menú de asignatura..."); // EN CASO DE QUE EL USUARIO CLICKE DE SALIR;
                    case 1 -> correcto = false;
                    default -> throw new MyException("No se ha podido crear la asignatura de empresa.\nVolviendo al menú de asignatura..."); // EN CASO DE QUE EL USUARIO NOS VACILE Y LE VUELVA A DAR A LA X JAJAJA;
                }
            } 
            
        } while (!correcto);
        
        return (AsignaturaEmpresa) asig;
        
    }
    
    /**
     * MÉTODO QUE CREA UNA ASIGNATURA PRESENCIAL NUEVA
     * Si todo lo rellenado es correcto saldrá de un bucle y devolverá el objeto.
     * El metodo comprobará que no lance ninguna excepcion (Ni la propia, ni NULL, ni NumberFormat)
     * 
     * @return Devuelve la clase AsignaturaPresencial preguntando todos sus valores:
     *  Codigo, nombre y num. de notas.
     * @throws MyException excepcion propia que comprueba las limitaciones del constructor de la clase
     */
    
    private static AsignaturaPresencial crearPresencial () throws MyException {
        Asignatura asig = null;
        boolean correcto = false;
        do {
            try {
                asig = new AsignaturaPresencial(
                    JOptionPane.showInputDialog(null, "¿Qué codigo tendrá su asignatura?\n(Debe tener 1 letra y 3 numeros)"), 
                    JOptionPane.showInputDialog(null, "¿Qué nombre tendrá su asignatura?\n(Entre 1 y 20 carácteres)"), 
                    JOptionPane.showInputDialog(null, "¿Cuántas prácticas quiere que tenga la asignatura?")           
                );
                
                correcto = true;
                
            } catch (MyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la creación de la asignatura", JOptionPane.WARNING_MESSAGE);
            } catch (NullPointerException ex2) {
                int opAdv = ventanaAdvertencia(
                        "Está cancelando el proceso de creación de la asignatura.\n"
                        + "Volverá al menú de asignaturas. ¿Está seguro de ello?",
                        "Cancelar proceso",
                        "Seguir creando la asignatura presencial");
                switch (opAdv) {
                    case 0 -> throw new MyException("No se ha podido crear la asignatura presencial.\nVolviendo al menú de asignatura..."); // EN CASO DE QUE EL USUARIO CLICKE DE SALIR;
                    case 1 -> correcto = false;
                    default -> throw new MyException("No se ha podido crear la asignatura presencial.\nVolviendo al menú de asignatura..."); // EN CASO DE QUE EL USUARIO NOS VACILE Y LE VUELVA A DAR A LA X JAJAJA;
                }
            }
            
        } while (!correcto);
        
        return (AsignaturaPresencial) asig;       
    }
    
    /**
     * MÉTODO DE VENTANA DE JOPTIONPANE DONDE SALEN 3 BOTONES:
     *     ("Presencial", "Empresa", "Finalizar")
     * Pedirá al usuario un tipo de asignatura que añadir al alumno.
     * Cada botón representa un int:
     * - Presencial : 0
     * - Empresa : 1
     * - Finalizar : 2
     * 
     * @return Devuelve un int elegido desde los botones
     */
    
    private static int pideOpcionAsignatura() {
        String[] opciones = {"PRESENCIAL", "EMPRESA", "FINALIZAR Y GUARDAR"};
        return JOptionPane.showOptionDialog(
                null, 
                "¿Qué tipos de asignatura quiere añadirle al alumno?", 
                "Asignaturas del Alumno", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opciones, 
                opciones[2]);
    }
    
    /**
     * METODO QUE GENERA UNA VENTANA DE EMERGENCIA/ADVERTENCIA AL USUARIO
     * Si el usuario comete un error o le da a un sitio "peligroso", saltará la ventana
     * y le advertirá de algo.
     * @param advertencia cadena que podemos insertar para adecuar la advertencia a cualquier situación
     * @param op1 cadena que representará al primer botón que pueda pulsar el usuario
     * @param op2 cadena que representará al segundo botón que pueda pulsar el usuario
     * @return integer con la opción elegida
     */
    
    private static int ventanaAdvertencia(String advertencia, String op1, String op2) {
        String[] opciones = {op1, op2};
        return JOptionPane.showOptionDialog(
                null, 
                advertencia, 
                null, 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, 
                null, 
                opciones, 
                opciones[0]);
    }
    
    /**
     * METODO QUE CREA UN HASHSET PARA LA COLECCION DE ASIGNATURAS
     * Inicializará la colección y posteriormente, con un método aparte donde enseñamos
     * los botones de elección, el usuario elegirá que asignaturas crear.
     * En el momento en el que le dé a "Finalizar" (int = 2) el método terminará.
     * 
            * En caso de cualquier error por imprevisto (poco posible) la asignatura lamentablemente
            * no se podrá crear.
            * Si el usuario cierra la ventana devolverá null y finalizará el método.
            * 
     * @param coleccionAsignaturas coleccion de asignaturas ya inicializada de antes
     * @return HashSet coleccionAsignaturas con las asignaturas creadas por el usuario
     * @throws MyException
     */
    
    private static HashSet<Asignatura> submenuAsignaturas() throws MyException {
        int op; // INT PARA LA ELECCION DE LAS OPCIONES
        HashSet<Asignatura> coleccionAsignaturas = new HashSet();
        boolean finalizar = false; // BOOLEAN EN FALSO Q NOS PERMITE HACER EL BUCLE
        do {
            try {
                op = pideOpcionAsignatura(); //ENSEÑAMOS LAS OPCIONES DEL USUARIO MEDIANTE EL METODO Y ELIJA EN ESTE
                switch (op) {
                    case 0 -> { // CREA ASIG. PRESENCIAL
                        Asignatura asigNueva = crearPresencial();
                        //COMPROBAMOS QUE SI LA AÑADIMOS NO HAYA UNA CON EL MISMO CODIGO
                        if (!coleccionAsignaturas.add(asigNueva)) {
                            throw new MyException("Ya existe una asignatura con el código " + asigNueva.getCodAsignatura()+ ".");
                        }
                        JOptionPane.showMessageDialog(null, "Asignatura presencial añadida con éxito.");
                    }
                    case 1 -> { // CREA ASIG. DE EMPRESA
                        Asignatura asigNueva = crearEmpresa();
                        //COMPROBAMOS QUE SI LA AÑADIMOS NO HAYA UNA CON EL MISMO CODIGO
                        if (!coleccionAsignaturas.add(asigNueva)) {
                            throw new MyException("Ya existe una asignatura con el código " + asigNueva.getCodAsignatura()+ ".");
                        }
                        JOptionPane.showMessageDialog(null, "Asignatura de empresa añadida con éxito.");
                    }
                    case 2 -> finalizar = true; // FINALIZA 
                    default -> {
                        int opAdv = ventanaAdvertencia(
                                "¿Está seguro que quiere cancelar la creación de asignaturas?\nSi cancela este proceso no se podrá crear el alumno.",
                                "Salir de la creación de alumno",
                                "Volver a la creación de asignaturas"
                                );
                        switch (opAdv) {
                            case 0 -> throw new MyException("CANCELAR"); // EN CASO DE QUE EL USUARIO CLICKE DE SALIR;
                            case 1 -> finalizar = false;
                            default -> throw new MyException("CANCELAR"); // EN CASO DE QUE EL USUARIO NOS VACILE Y LE VUELVA A DAR A LA X JAJAJA;
                        }
                    }
                }
            } catch (MyException ex) {
                // SI EL MENSAJE QUE HA PILLADO ES EL DE CANCELAR, LANZARÁ OTRA EXCEPCIÓN CON EL MENSAJE CLARO
                if (ex.getMessage().equals("CANCELAR")) {
                    throw new MyException("No se ha podido crear al alumno. Volviendo al menú principal...");
                }
                // SI NO PASA ESTO, LANZARÁ UN MENSAJE NORMAL
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la creación del alumno", JOptionPane.WARNING_MESSAGE);
            } 
            
        } while (!finalizar); //MIENTRAS EL BOOLEAN NO SEA TRUE NO SALDRÁ DEL BUCLE
        return coleccionAsignaturas; //DEVUELVE EL HASHSET CON LAS ASIGNATURAS
    }
    
    /**
     * MÉTODO QUE CREA UN ALUMNO TEMPORAL PARA LA COMPROBACIÓN DEL NOMBRE DEL MISMO
     * @return el Alumno creado con una colección de asignaturas vacía
     * @throws MyException 
     */
    private static Alumno nombreAlumno() throws MyException {
        Alumno aluTemporal = null;
        boolean nombreCorrecto = false;
        do {
            try {
                String nombre = JOptionPane.showInputDialog("¿Qué nombre quieres que tenga el alumno?");
                if (nombre == null) { 
                    throw new MyException("CANCELAR"); // EN CASO DE QUE EL USUARIO CLICKE LA X;
                } 
                // INTENTO DE CREACIÓN
                // Le paso un HashSet vacío por si el nombre no cumple el regex.
                aluTemporal = new Alumno(nombre, new HashSet<>());
                nombreCorrecto = true; //SI EL REGEX O LA CONDICIONAL NO SALTAN, SALDRÁ DEL BUCLE 
            } catch (MyException ex) {
                // SI EL MENSAJE QUE HA PILLADO ES EL DE CANCELAR, LANZARÁ OTRA EXCEPCIÓN CON EL MENSAJE CLARO
                if (ex.getMessage().equals("CANCELAR")) {
                    throw new MyException("No se ha añadido un alumno. Volviendo al menú principal...");
                }
                // SI NO PASA ESTO, LANZARÁ UN MENSAJE NORMAL
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la creación del alumno", JOptionPane.WARNING_MESSAGE);
            }  
        } while (!nombreCorrecto);
        return aluTemporal;
    }
    
    /**
     * MÉTODO QUE CREA UN HASHSET TEMPORAL PARA LA COMBROBACIÓN DEL NOMBRE DEL MISMO
     * @return el Alumno creado con una colección de asignaturas vacía
     * @throws MyException 
     */
    
    private static boolean crearAsignaturasAlumno(Alumno aluTemporal) throws MyException {
        boolean todoCorrecto = false;
        do { 
            try {
                aluTemporal.setAsignaturas(submenuAsignaturas());
                
                if (aluTemporal.getAsignaturas() == null || aluTemporal.getAsignaturas().isEmpty()) {
                    int opAdv = ventanaAdvertencia(
                            "No ha creado ninguna asignatura...\n¿Está seguro que desea cancelarlo?",
                            "Sí, quiero salir",
                            "No, seguir con la creación");
                    
                    switch (opAdv) {
                        case 0 -> throw new MyException("CANCELAR"); 
                        case 1 -> todoCorrecto = false; 
                        default -> throw new MyException("CANCELAR"); 
                    }
                } else {
                    todoCorrecto = true; 
                }
            } catch (MyException ex) {
                if (ex.getMessage().equals("CANCELAR")) {
                    throw new MyException("La creación del alumno se ha cancelado.\nVolviendo al menú principal...");
                }
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la creación de la asignatura", JOptionPane.WARNING_MESSAGE);
            }    
        } while (!todoCorrecto);
        return true;
    }
    
    /**
     * MÉTODO QUE LLAMANDO A DIFERENTES MÉTODOS, CREA A UN ALUMNO CON SUS ASIGNATURAS
     * Y LO AÑADE AL GESTOR.
     * Primero pedirá al usuario el nombre del alumno y posteriormente que tipo de asignaturas
     * quiere añadirle al alumno. El programa comprobará que no haya nada vacío y si hay algún error
     * saltará la excepción.
     * @throws MyException 
     */
    
    private static void altaAlumno() throws MyException {
        try {
            Alumno alu = nombreAlumno();
            boolean asignaturasCorrectas = crearAsignaturasAlumno(alu);
            
            if(asignaturasCorrectas) {
                gestor.GestorMatrícula.addAlumno(alu);
                JOptionPane.showMessageDialog(null, "¡El alumno " + alu.getNombre() + " ha sido creado con éxito!"); //SI LLEGA A ESTE PUNTO SIN ERRORES SALTARÁ ESTE MENSAJE
            }
        } catch (MyException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la creación del alumno", JOptionPane.WARNING_MESSAGE);
   
        }     
    }
    
    /**
     * MÉTODO QUE ENSEÑA UN EXPEDIENTE DEL ALUMNO CON:
     *  - Su nombre
     *  - Su código
     *  - Sus asignaturas + datos de estas
     *  - Si está aprobado en el curso o no
     * @throws MyException 
     */
    
    private static void informacionAlumno() throws MyException {
        Alumno alu = null;
        try {
            alu = buscarAlumno("Inserte el codigo del alumno que quiere ver su información.");
        } catch (MyException ex) {
            throw new MyException(ex.getMessage());
        }
        String textoInformacion = "";
        textoInformacion += 
                "CPIFP Los Enlaces\n" + 
                alu.toString().toUpperCase() + 
                "\n-----------------------------------------------"+
                "\n\n" +
                "ASIGNATURAS:\n";
        for (Asignatura asig : alu.getAsignaturas()) {
            String lineaAsig = String.format("  %-6s | %-15s | ", 
                    asig.getCodAsignatura(), 
                    asig.getNombre().toUpperCase());
            
            if (asig instanceof AsignaturaPresencial) {
                String notasStr = "NOTAS: " + ((AsignaturaPresencial) asig).getNotasString();
                lineaAsig += String.format("%-35s | %s\n", notasStr, asig.isAprobadoString());
            } 
            else if (asig instanceof AsignaturaEmpresa) {
                String notasStr = "NOTA FINAL: " + ((AsignaturaEmpresa) asig).getNotaFinal();
                lineaAsig += String.format("%-35s | %s\n", notasStr, asig.isAprobadoString());
            }
            
            textoInformacion += lineaAsig;
        }
        
        textoInformacion += "\n-------------------------------------------------------------------------------";
        textoInformacion += "\nESTADO ALUMNO: " + alu.isAprobadoString();
        JOptionPane.showMessageDialog(null, textoInformacion);
    }
    
    /**
     * MÉTODO QUE GENERA UNA VENTANA CON BOTONES EN JOPTIONPANE
     * Aparecerá una ventana con 6 opciones diferentes. Cada botón tiene de valor un Integer
     * @return la opción señalada por el usuario
     */
    
    private static int pideOpcionPrincipal() {
        String[] opciones = {
            "ALTA ALUMNO", "PONER NOTAS", 
            "INF. ALUMNO", "LISTADO GENERAL", 
            "ALUMNOS PROMOCIONADOS", "FIN"
        };

        return JOptionPane.showOptionDialog(
            null,
            "Seleccione una acción a realizar:", // Mensaje de la ventana
            "Menú Principal", // Título de la ventana
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE, // Tipo de mensaje (sin icono de alerta/error)
            null,
            opciones, // Botones
            opciones[0] // Botón seleccionado por defecto
        );     

    }
    
    private static void mostrarListadoAlumnosPromocionados() {
        String lista = gestor.GestorMatrícula.listadoAlumnadoPromocionado(); 
        JOptionPane.showMessageDialog(null, lista, "Listado de alumnos que promocionan", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void mostrarListadoAlumnos() {
        String lista = gestor.GestorMatrícula.listadoAlumnado(); 
        JOptionPane.showMessageDialog(null, lista, "Listado de alumnos", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * MÉTODO QUE NOS GENERA UNA VENTANA PARA EL MENÚ PRINCIPAL
     * En este metodo, tendremos un bucle con un switch y el respectivo try catch
     * que llamará a un método que abre una ventana con opciones.
     * Según la opción que marque el usuario, inicializaremos un método
     * diferente.
     */
    
    private static void menuPrincipal() {
        int op;
        boolean finalizar = false;
        while (!finalizar) {
            try {
                op = pideOpcionPrincipal();
                switch (op) {
                    case 0 -> altaAlumno();
                    case 1 -> ponerNotas();
                    case 2 -> informacionAlumno();
                    case 3 -> mostrarListadoAlumnos();
                    case 4 -> mostrarListadoAlumnosPromocionados();   
                    default -> finalizar = true;
                }
            } catch (MyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // DEFINO LA FUENTE QUE QUIERO USAR EN EL JOPTIONPANE
        Font fuenteNormal  = new Font("DialogInput", Font.PLAIN, 14);
        Font fuenteBotones = new Font("Segoe UI", Font.BOLD, 12);
        // LO  APLICA A TODO EL CODIGO
        UIManager.put("OptionPane.messageFont", fuenteNormal);
        UIManager.put("OptionPane.buttonFont", fuenteBotones);
        cargarDatosPrueba(); //esto inicializa los 3 alumnos de prueba
        menuPrincipal();
    }
}