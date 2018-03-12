/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.japo.java.main;
import java.sql.SQLException;
import org.japo.java.app.App;

/**
 *
 * @author Juan Alcocer Canet - juanasir1995@gmail.com
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        //Instanciar App
        App app = new App();
        
        //Lanzar App
        app.launchApp();
    }
    
}
