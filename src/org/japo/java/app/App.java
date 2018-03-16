/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.japo.java.app;

import org.japo.java.entities.BussinestLogicManager;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.japo.java.entities.DataAccessManager;
import org.japo.java.libraries.UtilesBD;


/**
 *
 * @author Juan Alcocer Canet - juanasir1995@gmail.com
 */
public class App {
    
    //version Bd
    public void launchApp() throws SQLException{
        System.out.println("Iniciando acceso a la Base de Datos...");
        System.out.println("-----");
        
        try(Connection con = UtilesBD.obtenerConexion();
                Statement stmt = con.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE)){
            System.out.println("Acceso a Base de Datos INICIADO");
            System.out.println("------");
            DataAccessManager dam = new DataAccessManager(con,stmt);
            
            BussinestLogicManager.launchMenu(dam);
            
            System.out.println("------");
            System.out.println("Acceso a Base de Datos FINALIZADO");
        
        } catch(SQLException e) {
            System.out.println("ERROR: Acceso a la base de datos CANCELADO");
        } 
    }
    
}
