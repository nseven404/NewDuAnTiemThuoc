package fpoly.tn.dal;

import fpoly.tn.dto.LoThuoc;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALKho {
        
    
    public static ResultSet layDanhSachThuocLoThuoc(){
        String cauLenh = String.format("SELECT ten,ma FROM %s", Gobal.SQL.tenBang.thuoc);
        
        return SqlHelper.executeQuery(cauLenh);
    }
    
    public static LoThuoc layThongTinLoThuoc(String maLo){
        LoThuoc lo = new LoThuoc();
        
        String cauLenh = String.format("SELECT * FROM %s WHERE ma = ?", Gobal.SQL.tenBang.lothuoc);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maLo);
        
        try {
            if(rs.next()){
                 String MaLo = rs.getString("ma");
                 String MaThuoc = rs.getString("ma_thuoc");
                 String ngaySanXuat = rs.getString("ngaysanxuat");
                 String hanSuDung = rs.getString("ngayhethan");
                 String ncc = rs.getString("ma_nhacungcap");
                 int SoLuong = rs.getInt("soluong");
                 String ghiChu = rs.getString("ghichu");
                 
                lo = new LoThuoc(MaLo, MaThuoc,ncc, ngaySanXuat, hanSuDung, SoLuong, ghiChu);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALKho.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return lo;
    } 
    
    public static boolean kiemTraMaThuocTonTai(String maThuoc){
         String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.Thuoc.kiemTraMaThuoc);
         
         ResultSet rs = SqlHelper.executeQuery(cauLenh, maThuoc);
         
        try {
            if(rs.next()){
                return rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALKho.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return false;
    }
    
    public static ResultSet loadDanhSachDonViTinh(){
        String cauLenh = String.format("select * from %s", Gobal.SQL.tenBang.donvitinh);
        return SqlHelper.executeQuery(cauLenh);
    }
    
    public static ResultSet loadDanhSachKho(){
        String cauLenh = String.format("select * from %s", Gobal.SQL.tenBang.kho);
        return SqlHelper.executeQuery(cauLenh);
    }
    
    public static ResultSet loadDanhSachLoai(){
        String cauLenh = String.format("SELECT * FROM %s", Gobal.SQL.tenBang.loaithuoc);
        return SqlHelper.executeQuery(cauLenh);
    }
    
    public static ResultSet loadDanhSachDongGoi(){
        String cauLenh = String.format(" SELECT dvdg.ma,dvt1.ten as tendvt1,dvt2.ten as tendvt2 FROM %s dvdg \n" +
                                        " JOIN  %s dvt1 on dvt1.ma = dvdg.ma_donvitinh1 JOIN %s dvt2 on dvt2.ma = dvdg.ma_donvitinh2 ",
                
                Gobal.SQL.tenBang.donvidonggoi,
                Gobal.SQL.tenBang.donvitinh,
                Gobal.SQL.tenBang.donvitinh);
        return SqlHelper.executeQuery(cauLenh);
    }
    
    public static ResultSet loadDanhSachDongGoiChuaCoTrongThuoc(String maThuoc){
        String cauLenh = String.format(" SELECT dvdg.ma,dvt1.ten as dvt1,dvt2.ten as dvt2 FROM %s dvdg \n" +
                                        " JOIN  %s dvt1 on dvt1.ma = dvdg.ma_donvitinh1 JOIN %s dvt2 on dvt2.ma = dvdg.ma_donvitinh2 "
                + "WHERE dvdg.ma NOT IN (SELECT ma_donvidonggoi FROM dbo.DONGGOI WHERE ma_thuoc = ?)",
                
                Gobal.SQL.tenBang.donvidonggoi,
                Gobal.SQL.tenBang.donvitinh,
                Gobal.SQL.tenBang.donvitinh,
                maThuoc);
        return SqlHelper.executeQuery(cauLenh,maThuoc);
    }
}
