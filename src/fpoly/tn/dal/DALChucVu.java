
package fpoly.tn.dal;

import fpoly.tn.dto.ChucVu;
import fpoly.tn.helper.SqlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DALChucVu {
    
    public static ResultSet layDanhSachChucVu(){
        String caulenh = "SELECT * FROM CHUCVU";
        return SqlHelper.executeQuery(caulenh);
    }
    
    public static ChucVu layThongTinNhanVienChucVu(String MaCV) {
        ChucVu cv = new ChucVu();
        try {
            String cauLenh = "select * from CHUCVU where ma = ?";

            ResultSet rs = SqlHelper.executeQuery(cauLenh, MaCV);
            if (rs != null) {
                while (rs.next()) {
                    cv.setTenCV(rs.getString("ten"));
                    cv.setMaCV(rs.getString("ma"));
                    cv.setGhiChu(rs.getString("ghichu"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }
        return cv;
    }
    
    public static boolean suaChucVu(ChucVu cv) throws SQLException { 
        String cauLenh = "UPDATE CHUCVU set ten = ? , ghichu = ? where ma = ?";
        return SqlHelper.executeUpdate(cauLenh,
                cv.getTenCV(),
                cv.getGhiChu(),
                cv.getMaCV());
    }

    public static boolean xoaChucVu(String MaCV) { // edit
        String cauLenh = String.format("delete CHUCVU where ma = ?");
        return SqlHelper.executeUpdate(cauLenh, MaCV);
    }
    
    public static boolean themChucVu(ChucVu cv) throws SQLException { // edit
        String cauLenh = "INSERT INTO CHUCVU VALUES (?,?,?)";
        return SqlHelper.executeUpdate(cauLenh,
                cv.getMaCV(),
                cv.getTenCV(),
                cv.getGhiChu());
    }
    
    public static boolean kiemTraMaChuVuTonTai(String MaCV) throws SQLException {
        String cauLenh = "select * from CHUCVU where ma = ?";
        boolean daCoMa = false;

        ResultSet rs = SqlHelper.executeQuery(cauLenh, MaCV);
        if (rs != null) {
            if (rs.next()) {
                daCoMa = true;
            }
        }
        return daCoMa;
    }
}
