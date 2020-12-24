/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.helper;

import java.text.DecimalFormat;

/**
 *
 * @author DELL
 */
public class XuLyInput {
    
     public static String formatThemChamVaoSo(double so ) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(so);
    }
     
     public static String themKiTuTimKiemSQL(String input){
         
        input = "%" + input +"%";
        
        return input;
     }
}
