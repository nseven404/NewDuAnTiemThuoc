package fpoly.tn.dal;

import fpoly.tn.dto.DonViDongGoi;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALDonViDongDoi {

    public static DonViDongGoi loadThongTinDVDG(String maDVDG) {
        DonViDongGoi DVDG = new DonViDongGoi();
        String cauLenh = String.format("SELECT * FROM %s WHERE ma = ?", Gobal.SQL.tenBang.donvidonggoi);

        ResultSet rs = SqlHelper.executeQuery(cauLenh, maDVDG);

        try {
            if (rs.next()) {
                String MaDVDG = rs.getString("ma");
                String MaDVT1 = rs.getString("ma_donvitinh1");
                String MaDVT2 = rs.getString("ma_donvitinh2");
                String GhiChu = rs.getString("ghichu");
                DVDG = new DonViDongGoi(MaDVDG, MaDVT1, MaDVT2, GhiChu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonViDongDoi.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

        return DVDG;
    }
    
    public static boolean themMoiDonViDongGoi(DonViDongGoi DVDG){
        String cauLenh = String.format("INSERT INTO %s (ma,ma_donvitinh1,ma_donvitinh2,ghichu)VALUES(?,?,?,?)", Gobal.SQL.tenBang.donvidonggoi);
        
        return SqlHelper.executeUpdate(cauLenh, DVDG.getMaDVDG(),DVDG.getMaDVT1(),DVDG.getMaDVT2(),DVDG.getGhiChu());
    }
    
    public static boolean suaThongTinDonViDongGoi(DonViDongGoi DVDG){
        String cauLenh = String.format("UPDATE %s SET ma_donvitinh1 = ?,ma_donvitinh2 = ?,ghichu = ? WHERE ma = ?", Gobal.SQL.tenBang.donvidonggoi);
        return SqlHelper.executeUpdate(cauLenh, DVDG.getMaDVT1(),DVDG.getMaDVT2(),DVDG.getGhiChu(),DVDG.getMaDVDG());
    }
    
    public static boolean xoaDVDG(String maDVDG){
        String cauLenh = String.format("DELETE %s WHERE ma = ?", Gobal.SQL.tenBang.donvidonggoi);
        return SqlHelper.executeUpdate(cauLenh, maDVDG);
    }
    
    public static boolean kiemTra2DonViTinhDVDGTrung(String maDVT1, String maDVT2){
        
        String cauLenh = String.format("%s ?,?", Gobal.SQL.tenProc.DonViDongGoi.kiemTraDonViTinhDVDGTrung);
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maDVT1,maDVT2);
        
        try {
            if(rs.next()){
                return rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonViDongDoi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return false;
    }
    
    public static boolean kiemTraMaDVDGTrung(String maDVDG){
        
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.DonViDongGoi.kiemTraMaDVDGTrung);
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maDVDG);
        
        try {
            if(rs.next()){
                return rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonViDongDoi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return false;
    }
    
     public static boolean kiemTraCoTheXoaDVDG(String maDVDG){
        
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.DonViDongGoi.kiemTraCoTheXoaDVDG);
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maDVDG);
        
        try {
            if(rs.next()){
                return rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonViDongDoi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return false;
    }
    
}
