/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.japo.java.libraries;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Juan Alcocer Canet - juanasir1995@gmail.com
 */
public class UtilesBD {

    // Propiedades BBDD
    private static final String FICHERO_PRP = "db.properties";

    // Valores Predeterminados Conexión BBDD
    private static final String DEF_PROT = "jdbc:mysql";
    private static final String DEF_HOST = "localhost";
    private static final String DEF_PORT = "3306";
    private static final String DEF_DBAM = "agenda";
    private static final String DEF_USER = "usuario";
    private static final String DEF_PASS = "usuario";

    // Propiedades Conexión BBDD
    private static final String PRP_PROT = "protocol";
    private static final String PRP_HOST = "host";
    private static final String PRP_PORT = "port";
    private static final String PRP_DBAM = "db";
    private static final String PRP_USER = "user";
    private static final String PRP_PASS = "password";

    // Formato Conexión
    private static final String FORMATO_CON = "%s://%s:%s/%s?user=%s&password=%s";

    // Cadena Conexión  Predeterminada
    private static final String DEF_CADENA_CON = String.format(
            FORMATO_CON, DEF_PROT, DEF_HOST, DEF_PORT, DEF_DBAM, DEF_USER, DEF_PASS);

    // Obtiene Conexión con BD - Predeterminada
    public static final Connection obtenerConexion() throws SQLException {
        // Referencia a la Conexión
        Connection con;
        
        if (new File(FICHERO_PRP).exists()) {
            // Cargar Propiedades
            Properties prp = UtilesApp.importarPropiedades(FICHERO_PRP);
            
            // Obtener Conexión
            con = obtenerConexion(prp);
        } else {
            // Aviso 
            System.out.println("ERROR: Fichero Propiedades BD NO existe");
            
            // Obtener Conexión
            con = obtenerConexion(DEF_CADENA_CON);
        }
        
        // Devolver Conexión
        return con;
    }

    // Obtiene Conexión con BD - Cadena Conexión
    public static final Connection obtenerConexion(String cadena) throws SQLException {
        return DriverManager.getConnection(cadena);
    }

    // Obtiene Conexión con BD - Parámetros
    public static final Connection obtenerConexion(
            String protocolo, String host, String puerto, String db,
            String usuario, String password) throws SQLException {
        // Definir cadena de conexión
        String cadenaConexion = String.format(
                FORMATO_CON, protocolo, host, puerto, db, usuario, password);

        // Realizar la conexión
        return DriverManager.getConnection(cadenaConexion);
    }

    // Obtiene Conexión con BD - Propiedades
    public static final Connection obtenerConexion(Properties prp) throws SQLException {
        // Definir cadena de conexión
        String cadenaConexion = String.format(
                FORMATO_CON,
                prp.getProperty(PRP_PROT, DEF_PROT),
                prp.getProperty(PRP_HOST, DEF_HOST),
                prp.getProperty(PRP_PORT, DEF_PORT),
                prp.getProperty(PRP_DBAM, DEF_DBAM),
                prp.getProperty(PRP_USER, DEF_USER),
                prp.getProperty(PRP_PASS, DEF_PASS));

        // Realizar la conexión
        return DriverManager.getConnection(cadenaConexion);
    }
}
