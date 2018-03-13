/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.japo.java.entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.japo.java.libraries.UtilesEntrada;

/**
 *
 * @author Juan Alcocer Canet - juanasir1995@gmail.com
 */
public class DataAccessManager {

    
    public static final String CAB_REG_MOD1 = "Proceso de borrado - Registro %02d";
    public static final String CAB_REG_MOD2 = "================================";
    //Sentencias SQL
    public static final String DEF_SQL_MOD1 = "SELECT * FROM modulo";
    public static final String DEF_SQL_MOD2 = "DELETE FROM modulo WHERE acronimo='PRG'";
    public static final String DEF_SQL_MOD3 = "INSERT INTO modulo VALUES ('1','PRG','Programacion','MP0485','310','1')";
    public static final String DEF_SQL_MOD4 = "UPDATE modulo SET horasCurso = '156' WHERE acronimo LIKE 'PRG'";

    public static final String DEF_SQL_ALU1 = "SELECT * FROM alumno";
    public static final String DEF_SQL_ALU2 = "DELETE FROM alumno WHERE UPPER(nombre)='JUAN'";
    public static final String DEF_SQL_ALU3 = "INSERT INTO alumno VALUES ('1','juan','alcocer','18451243F','2018-03-14','645646234','hola@gmail.com','palmereta 34','sagunto','valencia','46520','user','pass','')";
    public static final String DEF_SQL_ALU4 = "UPDATE alumno SET nombre = 'paco' WHERE UPPER(nombre) LIKE 'JUAN'";

    public static final String DEF_SQL_PRO1 = "SELECT * FROM profesor";
    public static final String DEF_SQL_PRO2 = "DELETE * FROM profesor";
    public static final String DEF_SQL_PRO3 = "INSERT * FROM profesor";
    public static final String DEF_SQL_PRO4 = "UPDATE * FROM profesor";

    public static final String CAB_LST_MOD1
            = "#    Id         Acrónimo  Nombre              Codigo Horas Curso";
    public static final String CAB_LST_MOD2
            = "===  =========  ========  ===================  ===== ===== =====";
    public static final String CAB_LST_ALU1
            = "#    exp 	nombre     apellidos 	      nif 	 nac 	        telefono      email 	           domicilio 	         localidad 	   provincia   cp     user 	pass 	   foto";
    public static final String CAB_LST_ALU2
            = "===  ====  =========  =================  =========  =============  ============  ===================  ====================  ================  ==========  =====  ========  =========  ==========";
    public static final String CAB_LST_PRO1
            = "#    id 	     nombre    apellidos 	    departamento   especialidad   tipo ";
    public static final String CAB_LST_PRO2
            = "===  =========  ========  ===================  =============  =============  ======";

    private Connection con;
    private Statement stmt;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public DataAccessManager(Connection con) {
        this.con = con;
    }

    public DataAccessManager(Connection con, Statement stmt) {
        this.con = con;
        this.stmt = stmt;
    }

    //-----Metodos Modulos------
    public final void listarModulos() throws SQLException {
        System.out.println("Listado de modulos ...");
        System.out.println("-----");
        try (ResultSet rs = stmt.executeQuery(DEF_SQL_MOD1)) {
            if (rs.next()) {
                System.out.println(CAB_LST_MOD1);
                System.out.println(CAB_LST_MOD2);
                do {
                    int fila = rs.getRow();
                    int id = rs.getInt("id");
                    String acronimo = String.format("%-10s", rs.getString("acronimo"));
                    String nombre = String.format("%-21s", rs.getString("nombre"));
                    String codigo = String.format("%-7s", rs.getString("codigo"));
                    int horasCurso = rs.getInt("horasCurso");
                    int curso = rs.getInt("curso");

                    System.out.printf("%03d %-11d %-10s %-25s %-10s %4d %4d %n", fila, id, acronimo, nombre, codigo, horasCurso, curso);

                } while (rs.next());
            } else {
                System.out.println("No hay datos que mostrar");
            }
        } catch (Exception e) {
        }
    }

    public final void listarModulosInteractivo(int lineasPagina) throws SQLException {
        if (lineasPagina <= 0) {
            listarModulos();
        } else {
            System.out.println("Listado de modulos ...");
            System.out.println("-----");
            try (ResultSet rs = stmt.executeQuery(DEF_SQL_MOD1)) {
                if (rs.next()) {
                    boolean nuevaLineaOk;
                    int lineaAct = 1;
                    int paginaAct = 1;
                    do {
                        System.out.printf("págian ...:%02d%n", paginaAct);
                        System.out.println(CAB_LST_MOD1);
                        System.out.println(CAB_LST_MOD2);
                        do {
                            int fila = rs.getRow();
                            int id = rs.getInt("id");
                            String acronimo = String.format("%-10s", rs.getString("acronimo"));
                            String nombre = String.format("%-21s", rs.getString("nombre"));
                            String codigo = String.format("%-7s", rs.getString("codigo"));
                            int horasCurso = rs.getInt("horasCurso");
                            int curso = rs.getInt("curso");

                            System.out.printf("%03d %-11d %-10s %-25s %-10s %4d %4d %n", fila, id, acronimo, nombre, codigo, horasCurso, curso);

                            lineaAct++;
                            nuevaLineaOk = rs.next();
                        } while (lineaAct <= lineasPagina && nuevaLineaOk);
                        if (nuevaLineaOk) {
                            System.out.println("-----");
                            char respuesta = UtilesEntrada.leerOpcion("sSnN", "Siguiente pagina (S/N)...:", "ERROR, Entrada Incorrecta");

                            if (respuesta == 's' || respuesta == 'S') {
                                paginaAct++;
                                lineaAct = 1;
                                System.out.println("-----");
                            } else {
                                nuevaLineaOk = false;
                            }
                        } else {
                            System.out.println("No hay datos que mostrar");
                        }
                    } while (nuevaLineaOk);
                }
            } catch (Exception e) {
            }
        }
    }

    public final void borrarModulos() throws SQLException {
        System.out.println("Borrado de modulos....");
        System.out.println("-----");
        int filas = stmt.executeUpdate(DEF_SQL_MOD2);
        System.out.printf("Se han borrado %d modulos%n", filas);
    }

    public final void borrarModulosInteractivo() throws SQLException {
        System.out.println("Borrado de modulos....");
        System.out.println("-----");
        try(ResultSet rs = stmt.executeQuery(DEF_SQL_MOD1)){
            int regBorrados = 0;
            while(rs.next()){
                System.out.printf(CAB_REG_MOD1+"%n", rs.getRow());
                System.out.println(CAB_REG_MOD2);
                System.out.printf("Id.....: %d%n", rs.getInt(1));
                System.out.printf("Acronimo.....: %s%n", rs.getString(2));
                System.out.printf("Nombre.....: %s%n", rs.getString(3));
                System.out.printf("Codigo.....: %s%n", rs.getString(4));
                System.out.printf("horas Curso.....: %d%n", rs.getInt(5));
                System.out.printf("Curso.....: %d%n", rs.getInt(6));
                
                char respuesta = UtilesEntrada.leerOpcion("SsNn", "Borrar Modulo (S/N)...:", "Error, Entrada incorrecta");
                if(respuesta == 'S' || respuesta == 's'){
                    rs.deleteRow();
                    regBorrados++;
                    System.out.println("-----");
                    System.out.println("Modulo actual Borrado");
                }
                System.out.println("----");
            }
            System.out.printf("Se han borrado %d modulos %n", regBorrados);
        }
    }
    
    public final void insertarModulosInteractivo() throws SQLException {
        System.out.println("Insertado de modulos....");
        System.out.println("-----");
        try(ResultSet rs = stmt.executeQuery(DEF_SQL_MOD1)){
            rs.moveToInsertRow();
            rs.updateInt(1,UtilesEntrada.leerEntero("ID....:","Error: Entrada incorrecta"));
            rs.updateString(2, UtilesEntrada.leerTexto("Acronimo....:"));
            rs.updateString(3, UtilesEntrada.leerTexto("Nombre....:"));
            rs.updateString(4, UtilesEntrada.leerTexto("Codigo....:"));
            rs.updateInt(5,UtilesEntrada.leerEntero("Horas...:","Error: Entrada incorrecta"));
            rs.updateInt(6,UtilesEntrada.leerEntero("Curso...:","Error: Entrada incorrecta"));
            System.out.println("-----");
            
            char respuesta = UtilesEntrada.leerOpcion("SsNn","Insertar Modulo (S/N)...:","Error: Entrada incorrecta");
            if(respuesta=='s'||respuesta=='S'){
                rs.insertRow();
                System.out.println("-----");
                System.out.println("Insercion de datos completa");                
            }else {
               System.out.println("-----");
               System.out.println("Insercion de datos cancelada"); 
            }
            rs.moveToCurrentRow();
        }       
    }
    
    public final void insertarModulos() throws SQLException {
        System.out.println("Insertado de modulos....");
        System.out.println("-----");
        int filas = stmt.executeUpdate(DEF_SQL_MOD3);
        System.out.printf("Se han Insertado %d modulos%n", filas);
    }
    
    

    public final void actualizarModulos() throws SQLException {
        System.out.println("actualizado de modulos....");
        System.out.println("-----");
        int filas = stmt.executeUpdate(DEF_SQL_MOD4);
        System.out.printf("Se han actualizado %d modulos%n", filas);
    }

    //-----Metodos Alumnos------
    public final void listarAlumnos() throws SQLException {
        System.out.println("Listado de Alumnos ...");
        System.out.println("-----");
        try (ResultSet rs = stmt.executeQuery(DEF_SQL_ALU1)) {
            if (rs.next()) {
                System.out.println(CAB_LST_ALU1);
                System.out.println(CAB_LST_ALU2);
                do {
                    String fila = String.format("%03d", rs.getRow());
                    String exp = String.format(" %-4s", rs.getString("exp"));
                    String nombre = String.format("%-21s", rs.getString("nombre"));
                    String apellidos = String.format("%-33s", rs.getString("apellidos"));
                    String nif = String.format("%-9s", rs.getString("nif"));
                    String nac = String.format("%-20s", rs.getString("nac"));
                    String telefono = String.format("%-12s", rs.getString("telefono"));
                    String email = String.format("%-20s", rs.getString("email"));
                    String domicilio = String.format("%-20s", rs.getString("domicilio"));
                    String localidad = String.format("%-15s", rs.getString("localidad"));
                    String provincia = String.format("%-20s", rs.getString("provincia"));
                    String cp = String.format("%-6s", rs.getString("cp"));
                    String user = String.format("%-20s", rs.getString("user"));
                    String pass = String.format("%-20s", rs.getString("pass"));
                    String foto = String.format("%-20s", rs.getString("foto"));

                    System.out.println(fila + exp + nombre + apellidos + nif + nac + telefono + email + domicilio + localidad + provincia + cp + user + pass + foto);

                } while (rs.next());
            } else {
                System.out.println("No hay datos que mostrar");
            }
        } catch (Exception e) {
            System.out.println("algo falla");
        }
    }

    public final void borrarAlumnos() throws SQLException {
        System.out.println("Borrado de alumnos....");
        System.out.println("-----");
        int filas = stmt.executeUpdate(DEF_SQL_ALU2);
        System.out.printf("Se han borrado %d alumnos%n", filas);
    }

    public final void insertarAlumnos() throws SQLException {
        System.out.println("Insertado de alumnos....");
        System.out.println("-----");
        int filas = stmt.executeUpdate(DEF_SQL_ALU3);
        System.out.printf("Se han Insertado %d alumnos%n", filas);
    }

    public final void actualizarAlumnos() throws SQLException {
        System.out.println("actualizado de alumnos....");
        System.out.println("-----");
        int filas = stmt.executeUpdate(DEF_SQL_ALU4);
        System.out.printf("Se han actualizado %d alumnos%n", filas);
    }

    //-----Metodos Profesores------
    public final void listarProfesores() throws SQLException {
        System.out.println("Listado de Profesores ...");
        System.out.println("-----");
        try (ResultSet rs = stmt.executeQuery(DEF_SQL_PRO1)) {
            if (rs.next()) {
                System.out.println(CAB_LST_PRO1);
                System.out.println(CAB_LST_PRO2);
                do {
                    String fila = String.format("%03d", rs.getRow());
                    String id = String.format("%-11d", rs.getInt("id"));
                    String nombre = String.format("%-31s", rs.getString("nombre"));
                    String apellidos = String.format("%-21s", rs.getString("apellidos"));
                    String departamento = String.format("%-7s", rs.getString("departamento"));
                    String especialidad = String.format("%-6s", rs.getInt("especialidad"));
                    String tipo = String.format("%-5s", rs.getInt("tipo"));

                    System.out.println(fila + id + nombre + apellidos + departamento + especialidad + tipo);

                } while (rs.next());
            } else {
                System.out.println("No hay datos que mostrar");
            }
        } catch (Exception e) {
        }
    }

}
