/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import exception.MyException;

/**
 *
 * @author Alumno
 */
public class AsignaturaEmpresa extends Asignatura {
    private String nombreEmpresa;
    private float notaFinal = 0;

    public AsignaturaEmpresa(String codAsignatura, String nombre, String nombreEmpresa) throws MyException {
        super(codAsignatura, nombre);
        setNombreEmpresa(nombreEmpresa);
    }

    public String getCodAsignatura() {
        return codAsignatura;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    private void setNombreEmpresa(String nombreEmpresa) throws MyException {
        String regExp = "^[a-zA-ZáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛäëïöüÄËÏÖÜãõñÃÕÑçÇ\\s]{1,20}$"; 
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) {
            throw new MyException("El nombre de la empresa no puede ser nulo.");
        } else if (nombre.matches(regExp)) {
                this.nombreEmpresa = nombreEmpresa;
        } else {
            throw new MyException("El nombre de la empresa solo puede tener de 1 a 20 carácteres.");
        } 
    }

    public float getNotaFinal() {
        return notaFinal;
    }
    
    public void setNotaFinal(float notaFinal) throws MyException {
        if (notaFinal < 0) {
            throw new MyException("La nota no puede ser menor que 0.");
        }
        if (notaFinal > 10) {
            throw new MyException("La nota no puede ser mayor que 10.");
        }
        this.notaFinal = notaFinal;
    }

    public void setNotaFinal(String notaFinal) throws MyException {
        try {
            float floatNotaFinal = Float.parseFloat(notaFinal);
            setNotaFinal(floatNotaFinal);
        } catch (NumberFormatException ex) {
            throw new MyException("Por favor coloque un digito en el numero de prácticas.");
        } catch (NullPointerException ex2) {
            throw new MyException("Por favor coloque un valor en el numero de prácticas.");
        }

    }

    /**
     * MÉTODO QUE COMPRUEBA SI LA ASIGNATURA EMPRESA ESTÁ APROBADA O NO
     * La nota final debe ser mayor o igual que 5.
     * @return true si el alumno está aprobado.
     */
    
    @Override
    public boolean isAprobado() {    
        if (notaFinal < 5) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return nombre.toUpperCase();
    }
}
