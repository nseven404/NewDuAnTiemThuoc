package fpoly.tn.bll;

import fpoly.tn.dal.DALNhanVien;
import fpoly.tn.dto.NhanVien;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLNhanVien {
    
    /// Hàm kiểm tra thông tin đăng nhập
    public static boolean KiemTraThongTin(String id, String pass){
        return true;
    }
    
    public static boolean themNhanVien(NhanVien nv){
        
        try {
            return DALNhanVien.themNhanVien(nv);
        } catch (SQLException ex) {
         
            Logger.getLogger(BLLNhanVien.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

     
    
}
