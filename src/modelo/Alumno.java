/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import exception.MyException;
import java.util.HashSet;

/**
 *
 * @author Alumno
 */
public class Alumno {
    private String codAlumno;
    private static int COD_EXP = 0;
    private String nombre;
    private HashSet<Asignatura> asignaturas;

    public Alumno(String nombre, HashSet<Asignatura> asignaturas) throws MyException {
        setNombre(nombre);
        setCodAlumno();
        setAsignaturas(asignaturas);
    }

    /**
     * MÉTODO QUE DEVUELVE EL CÓDIGO DEL ALUMNO
     * @return String con el código identificativo del alumno
     */
    
    public String getCodAlumno() {
        return codAlumno;
    }

    /**
     * MÉTODO QUE ASIGNA EL CÓDIGO AL ALUMNO
     * Genera un código / expediente para el alumno con el formato "ENLACES_XXXXX"
     * incrementando el número de expediente por cada alumno creado.
     */
    
    public void setCodAlumno() {
        String codigo = String.format("ENLACES_%06d", COD_EXP++);
        codAlumno = codigo;
        
    }

    /**
     * MÉTODO QUE DEVUELVE EL NOMBRE DEL ALUMNO
     * @return String con el nombre del alumno
     */
    
    public String getNombre() {
        return nombre;
    }
    
    /**
     * MÉTODO QUE COMPRUEBA Y ASIGNA EL NOMBRE DEL ALUMNO
     * Comprueba que el nombre solo contenga letras (mayúsculas, minúsculas o con caracteres especiales) y espacios.
     * La longitud debe estar entre 2 y 25 caracteres.
     * @param nombre cadena con el nombre que se quiere asignar al alumno
     * @throws MyException
     */

    private void setNombre(String nombre) throws MyException {
        String regExp = "^[a-zA-ZáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛäëïöüÄËÏÖÜãõñÃÕÑçÇ\\s]{2,25}$";
        if (nombre.trim().isEmpty()) {
            throw new MyException("El nombre del alumno no puede estar vacío.");
        }
        if (!nombre.matches(regExp)) {
                throw new MyException("El nombre del alumno solo puede tener de 2 a 25 carácteres.");
        } 
        this.nombre = nombre; 

    }

    /**
     * MÉTODO QUE ASIGNA LA COLECCIÓN DE ASIGNATURAS AL ALUMNO
     * @param asignaturas HashSet con las asignaturas en las que está matriculado
     */
    
    public void setAsignaturas(HashSet<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
    
    /**
     * MÉTODO QUE DEVUELVE LA COLECCIÓN DE ASIGNATURAS DEL ALUMNO
     * @return HashSet con las asignaturas del alumno
     */

    public HashSet<Asignatura> getAsignaturas() {
        return asignaturas;
    }
    
    /**
     * MÉTODO QUE DEVUELVE EL ESTADO GLOBAL DEL ALUMNO EN FORMATO TEXTO
     * @return "APROBADO" si el alumno tiene todas las asignaturas aprobadas, "SUSPENDIDO" en caso contrario
     */
    
    public String isAprobadoString () {
        if (isAprobado()) {
            return "PROMOCIONA";
        }
        
        return "NO PROMOCIONA";
    }
    
    /**
     * MÉTODO QUE CALCULA SI EL ALUMNO ESTÁ APROBADO GLOBALMENTE
     * Recorre la colección de asignaturas. Si detecta al menos una suspensa, el alumno estará suspendido.
     * @return true si todas las asignaturas están aprobadas, false si hay alguna suspensa
     */
    
    public boolean isAprobado () {
        for (Asignatura asig : asignaturas) {
            if (asig.isAprobado() == false) {
                return false;
            }
        }
        return true;
        
    }
    
    /**
     * MÉTODO SOBREESCRITO QUE DEVUELVE LA INFORMACIÓN BÁSICA DEL ALUMNO
     * @return String con el código y el nombre del alumno
     */

    @Override
    public String toString() {
        String resultado = "";
        return resultado.format("%-12s | %-12s", codAlumno, nombre);
    }
}
