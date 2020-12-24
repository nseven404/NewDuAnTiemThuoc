package fpoly.tn.dal;

import fpoly.tn.dto.DonViTinh;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALDonViTinh {
    
    public static DonViTinh loadThongTinDonViTinh(String maDVT){
        DonViTinh donViTinh = new DonViTinh();
        String cauLenh = String.format("SELECT * FROM %s WHERE ma = ?", Gobal.SQL.tenBang.donvitinh);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maDVT);
        
        try {
            if(rs.next()){
                String MaDVT = rs.getString("ma");
                String TenDVT = rs.getString("ten");
                String GhiChu = rs.getString("ghichu");
                donViTinh = new DonViTinh(MaDVT, TenDVT, GhiChu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonViTinh.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return donViTinh;
    }
    
    public static boolean themMoiDonViTinh(DonViTinh donViTinh){
        String cauLenh = String.format("INSERT INTO %s (ma,ten,ghichu) VALUES (?,?,?)", Gobal.SQL.tenBang.donvitinh);
        
        return SqlHelper.executeUpdate(cauLenh, donViTinh.getMaDVT(),donViTinh.getTenDVT(),donViTinh.getGhiChu());
    }
    
    public static boolean suaThongTinDonViTinh(DonViTinh donViTinh){
        String cauLenh = String.format("UPDATE %s SET ten = ?,ghichu = ? WHERE ma = ?", Gobal.SQL.tenBang.donvitinh);
        return SqlHelper.executeUpdate(cauLenh, donViTinh.getTenDVT(), donViTinh.getGhiChu(), donViTinh.getMaDVT());
    }
    
    public static boolean xoaDonViTinh(String MaDVT){
        String cauLenh = String.format("DELETE %s WHERE ma = ?", Gobal.SQL.tenBang.donvitinh);
        return SqlHelper.executeUpdate(cauLenh, MaDVT);
    }
    
    public static boolean kiemTraMaDVTTonTai(String MaDVT){
        
        boolean tonTai = false;
        
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.DonViTinh.kiemTraMaDVTTonTai);
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, MaDVT);
        
        try {
            if(rs.next()){
                tonTai = rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonViTinh.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        return tonTai;
    }
    
    public static boolean kiemTraDVTCoTheXoa(String MaDVT){
         boolean coTheXoa = false;
        
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.DonViTinh.kiemTraDVTCoTheXoa);
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, MaDVT);
        
        try {
            if(rs.next()){
                coTheXoa = rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonViTinh.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        return coTheXoa;
    }
    
    public static ResultSet layDanhSanhDVTThuoc(String maThuoc, int xapXep){
        String cauLenh = String.format("%s ?,?", Gobal.SQL.tenProc.DonViTinh.layDanhSachDonViTinhCuaThuoc);
        return SqlHelper.executeQuery(cauLenh, maThuoc,xapXep);
    }
    
}
