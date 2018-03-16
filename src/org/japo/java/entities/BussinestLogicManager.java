/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.japo.java.entities;
import java.sql.SQLException;
import org.japo.java.libraries.UtilesEntrada;
/**
 *
 * @author Juan Alcocer Canet - juanasir1995@gmail.com
 */
public class BussinestLogicManager {
    public static final String TXT_MENU =
            "GESTION DE MODULOS - MENU PRINCIPAL \n" +
            "=================================== \n" +
            " [A] Alta de Modulos \n" +
            " [B] Baja de Modulos \n" +
            " [C] Consulta de Modulos \n" +
            " [M] Modificacion de Modulos \n" +
            " --- \n" +
            " [S] Salir del Programa \n" +
            " --- ";
    
    public static final String OPC_MENU = "AaBbCcMmSs";
    public static final int LINEAS_PAGINA = 10;
    
    public static final void launchMenu(DataAccessManager dam) {
        char option = 0;
        do{
            System.out.println(TXT_MENU);
            option = UtilesEntrada.leerOpcion(OPC_MENU, "Seleccionar opcion...: ","ERROR: Entrada incorrecta");
            try{
                switch(option){
                    case 'A':
                    case 'a':
                        dam.insertarModulosInteractivo();
                        break;
                    case 'B':
                    case 'b':
                        dam.borrarModulosInteractivo();
                        break;
                    case 'C':
                    case 'c':
                        dam.listarModulosInteractivo(LINEAS_PAGINA);
                        break;
                    case 'M':
                    case 'm':
                        dam.actualizarModulosInteractivo();
                        break;
                }
            }catch(SQLException e){
            }
        }while(option != 'S' && option != 's');
    }   
}
