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
public class AsignaturaPresencial extends Asignatura {
    private int numPracticas;
    private float[] notas;

    public AsignaturaPresencial(String codAsignatura, String nombre, String numPracticas) throws MyException {
        super(codAsignatura, nombre);
        setNumPracticas(numPracticas);
        this.notas = new float[this.numPracticas];
        
    }
    
    public String getNotasString() {
        String notasMostradas = "";
        for (int i = 0; i < notas.length; i++) {
            notasMostradas += notas[i];
            if (i < (notas.length - 1)) {
                notasMostradas += ", ";
            }
        }
        return notasMostradas;
    }

    public float[] getNotas() {
        return notas;
    }

    public int getNumPracticas() {
        return numPracticas;
    }
    
    public void setNotasFloat(float [] notas) throws MyException {
        for (float nota : notas) {
            if (nota < 1) {
                throw new MyException("Las notas no pueden ser menor que 1.");
            } else if (nota > 10) {
                throw new MyException("Las notas no pueden ser mayor que 10.");
            }
        }
        
        this.notas = notas;
        
    }

    public void setNumPracticas(String numPracticas) throws MyException {
        try {
            int intNumPracticas = Integer.parseInt(numPracticas);
            if (intNumPracticas > 14) {
                throw new MyException("El número de prácticas no puede ser mayor a 14.");
            }
            if (intNumPracticas < 1) {
                throw new MyException("El número de prácticas no puede ser menor a 1.");
            }
            this.numPracticas = intNumPracticas;
        } catch (NumberFormatException ex) {
            throw new MyException("Por favor coloque un digito en el numero de prácticas.");
        } catch (NullPointerException ex2) {
            throw new MyException("Por favor coloque un valor en el numero de prácticas.");
        }
            
    }

    /**
     * MÉTODO QUE COMPRUEBA SI LA ASIGNATURA PRESENCIAL ESTÁ APROBADA O NO
     * Todas las prácticas deben ser mayor o igual que 4 y la media de estas 
     * por encima del 5.
     * @return true si el alumno está aprobado.
     */
    @Override
    public boolean isAprobado() {
        float media = 0; //INICIALIZO LA MEDIA PARA PODER OPERAR CON ELLA
        for (int i = 0; i < numPracticas; i++) {
            if (notas[i] < 4) {
                return false;
            }
            media += notas[i];
        } //SI SALE DEL FOR (NO HA DEVUELTO NADA) COMPROBARÁ LA MEDIA
        
        if ((media / numPracticas) < 5) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return nombre.toUpperCase();
    }
}
