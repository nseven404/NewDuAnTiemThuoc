/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.bll;

import fpoly.tn.dal.DALChucVu;
import fpoly.tn.dto.ChucVu;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DINH QUOC HAI
 */
public class BLLChucVu {
    
    public static boolean themChucVu(ChucVu cv){        
        try {
            return DALChucVu.themChucVu(cv);
        } catch (SQLException ex) {         
            Logger.getLogger(BLLNhanVien.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean suaChucVu(ChucVu cv){        
        try {
            return DALChucVu.suaChucVu(cv);
        } catch (SQLException ex) {         
            Logger.getLogger(BLLNhanVien.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
}
