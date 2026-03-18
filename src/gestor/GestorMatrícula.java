/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import exception.MyException;
import java.util.ArrayList;
import java.util.ListIterator;
import modelo.*;

/**
 *
 * @author Alumno
 */
public class GestorMatrícula {
    private static ArrayList<Alumno> listaAlumnos = new ArrayList();
    public static boolean addAlumno(Alumno alu){
        return listaAlumnos.add(alu);
    }
    
    public static String listadoAlumnado () {
        String resultado = "";
        for (Alumno alu : listaAlumnos) {
            resultado += alu.toString() + "\n";
        }
        
        if (resultado.isEmpty()) {
            return "No hay alumnos registrados.";
        }
        return resultado;
    }
    
    public static String listadoAlumnadoPromocionado() {
        String resultado = "";
        for (Alumno alu : listaAlumnos) {
            if (alu.isAprobado()) {
                resultado += alu.toString() + "\n";
            }
        }
        
        if (resultado.isEmpty()) {
            return "No hay alumnos aprobados.";
        }
        return resultado;
    }
    
    public static Alumno getAlumno(String codigoBusqueda) throws MyException {
        //RECORRO LA LISTA COMPLETA HASTA ENCONTRAR UN CODIGO QUE COINCIDA
        for (Alumno alu : listaAlumnos) {
            if (alu.getCodAlumno().equalsIgnoreCase(codigoBusqueda)) {
                return alu; // SI LO ENCUENTRA DEVOLVEMOS AL ALUMNO
            }
        }

        throw new MyException("El alumno buscado no existe o el código insertado es incorrecto");
    }
}
