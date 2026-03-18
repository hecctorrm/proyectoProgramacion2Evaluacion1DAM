/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import exception.MyException;

/**
 * Clase Asignatura con dos hijas, Presencial y Empresa
 * @author Héctor Reyes Medina
 */
public abstract class Asignatura {
    protected String codAsignatura;
    protected String nombre;

    public Asignatura(String codAsignatura, String nombre) throws MyException {
        setCodAsignatura(codAsignatura);
        setNombre(nombre);
        
    }
    
    /**
     * MÉTODO QUE DEVUELVE EL CODIGO DE LA ASIGNATURA
     * @return String con el código de la asignatura 
     */

    public String getCodAsignatura() {
        return codAsignatura;
    }

    /**
     * MÉTODO QUE COMPRUEBA EL CÓDIGO A LA ASIGNATURA.
     * Comprobará que sea 1 letra y 3 números seguidos.
     * En caso de no serlo, devolverá una excepción con el mensaje de apoyo.
     * @param codAsignatura cadena con el código de la asignatura que quiere el usuario
     * @throws MyException 
     */
    
    private void setCodAsignatura(String codAsignatura) throws MyException {
        String regExp = "^[a-zA-Z]\\d{3}$";
        if (codAsignatura.matches(regExp)) {
                this.codAsignatura = codAsignatura.toUpperCase();
        } else {
            throw new MyException("El codigo debe tener de primer carácter letra y 3 números.");
        }
    }

    /**
     * MÉTODO QUE DEVUELVE EL NOMBRE DE LA ASIGNATURA
     * @return String con el nombre de la asignatura
     */
    
    public String getNombre() {
        return nombre;
    }

    /**
     * MÉTODO QUE COMPRUEBA EL NOMBRE DE LA ASIGNATURA
     * Comprobará que sea cualquier tipo de carácter:
     *  - De la "a" a la "z" (Minúsculas).
     *  - De la "A" a la "Z" (Mayúsculas).
     *  - Toda letra que incluya un signo especial (Por ejemplo una tilde o diéresis).
     * Siendo una palabra o varias de no más 20 carácteres.
     * @param nombre
     * @throws MyException 
     */
    
    private void setNombre(String nombre) throws MyException {
        String regExp = "^[a-zA-ZáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛäëïöüÄËÏÖÜãõñÃÕÑçÇ\\s]{1,20}$";     
        if (nombre.trim().isEmpty()) {
            throw new MyException("El nombre de la asignatura no puede estar vacío.");
        }
        if (!nombre.matches(regExp)) {
                throw new MyException("El nombre de la asignatura solo puede tener de 1 a 20 carácteres.");
        } 
        this.nombre = nombre; 
    }
    
    /**
     * MÉTODO QUE DEVUELVE UNA CADENA SEGÚN BOOLEAN DE isAprobado()
     * @return
        * Si el alumno devuelve True, devolverá "Aprobado".
        * Si es false, devolverá "Suspendido"
     */
    
    public String isAprobadoString() {
        if (isAprobado()) {
            return "APROBADO";
        }       
        return "SUSPENDIDO";
    }
    /**
     * Método abstracto que llega a las dos clases hijas. En ellas se comprobará que
     * la asignatura esté aprobada.
     * @return true si la asignatura está aprobada.
     */
    public abstract boolean isAprobado();
    
    /**
     * COMPRUEBA QUE SI DOS ASIGNATURAS SEAN LAS MISMAS / TIENEN EL MISMO CODIGO NO SE PUEDAN AÑADIR 
     */ 
     
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }       
        if (!(obj instanceof Asignatura)) {
            return false;
        }      
        Asignatura otra = (Asignatura) obj;
        return this.codAsignatura.equalsIgnoreCase(otra.getCodAsignatura());
    }

    @Override
    public int hashCode() {
        return this.codAsignatura.toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return nombre.toUpperCase();
    }
    
}
