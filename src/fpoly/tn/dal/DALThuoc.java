package fpoly.tn.dal;

import fpoly.tn.dto.DongGoiThuoc;
import fpoly.tn.dto.Thuoc;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.XuLyInput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALThuoc {


    public static ResultSet layThongTinSanPham(String maSp) {
        String cauLenh = "select * from sanpham where ma= ?";
        return SqlHelper.executeQuery(cauLenh, maSp);
    }

    public static ResultSet kiemTraMa(String MaSp) {
        String cauLenh = "SELECT * FROM dbo.sanpham where ma = ?";
        return SqlHelper.executeQuery(cauLenh, MaSp);
    }

    public static boolean kiemTraSanPhamCoTrongHoaDon(String maSp) {
        String cauLenh = "SELECT TOP 1* FROM dbo.chitietdonhangxuat WHERE ma_sanpham = ?";
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maSp);

        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    
    /// NEW
    
    // chưa làm proc mới,
    
//    public static boolean KiemTraThuocCoTheXoa(String maThuoc){
//        boolean coTheXoa = false;
//        
//        String cauLenh = "kiemTraMaThuocCoTheXoa ?";
//        ResultSet rs = SqlHelper.executeQuery(cauLenh, maThuoc);
//        
//        try {
//            
//            if(rs.next()){
//               coTheXoa =  ( rs.getInt(1) == 1 );
//            }
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return coTheXoa;
//    }
    
    public static String layTenThuoc(String maThuoc){
        String ten = "";
        String cauLenh = String.format("SELECT ten FROM %s WHERE ma = ?", Gobal.SQL.tenBang.thuoc);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maThuoc);
        
        try {
            if(rs.next()){
                ten = rs.getString("ten");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return ten;
    }
    
    public static boolean suaDongGoiThuoc(DongGoiThuoc donggoi){
        String cauLenh = String.format("UPDATE %s SET ma_donvidonggoi = ?, soluong = ?, ghichu = ? WHERE id = ?", Gobal.SQL.tenBang.donggoi);
        
      return  SqlHelper.executeUpdate(cauLenh, donggoi.getMaDongGoi(),donggoi.getSoLuong(),donggoi.getGhiChu(),donggoi.getId());
    }
    
    public static boolean themDongGoiThuoc(DongGoiThuoc donggoi){
        String cauLenh = String.format("INSERT INTO dbo.DONGGOI(ma_thuoc,ma_donvidonggoi,soluong,ghichu) VALUES (?,?,?,?)", Gobal.SQL.tenBang.donggoi);
        
       return SqlHelper.executeUpdate(cauLenh, donggoi.getMaThuoc(),donggoi.getMaDongGoi(),donggoi.getSoLuong(),donggoi.getGhiChu());
    }
    
    public static DongGoiThuoc loadDongGoiThuoc(String id){
        DongGoiThuoc donggoi = new DongGoiThuoc();
        
        String cauLenh = String.format("SELECT * FROM %s WHERE id = ?", Gobal.SQL.tenBang.donggoi);
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, id);
        
        try {
            if(rs.next()){
                String maDongGoi = rs.getString("ma_donvidonggoi");
                String maThuoc = rs.getString("ma_thuoc");
                String GhiChu = rs.getString("ghichu");
                int soLuong = rs.getInt("soluong");
                donggoi = new DongGoiThuoc(id, maThuoc, maDongGoi, GhiChu, soLuong);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return donggoi;
    }
    
    public static ResultSet loadLoThuoc(String maThuoc){
        
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.Thuoc.layLoThuoc);
        
        return SqlHelper.executeQuery(cauLenh,maThuoc);
    }
    
    public static ResultSet loadDanhSachDongGoiThuoc(String maThuoc){
        
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.Thuoc.layDanhSachDongGoi);
        
        return SqlHelper.executeQuery(cauLenh,maThuoc);
    }
    
    public static boolean suaThuoc(Thuoc thuoc) {
        String cauLenh = String.format("UPDATE %s SET ten = ? , ma_loaithuoc = ?,ma_kho = ?,nhasanxuat = ?,ma_donvitinhCB = ?,gianhap = ?,giaban = ?,ghichu = ?,sudung = ? WHERE ma = ?",Gobal.SQL.tenBang.thuoc);
        return SqlHelper.executeUpdate(cauLenh,
                thuoc.getTen(),
                thuoc.getMaloai(),
                thuoc.getMaKho(),
                thuoc.getNhaSx(),
                thuoc.getDvtcb(),
                thuoc.getGianhap(),
                thuoc.getGiaban(),
                thuoc.getGhiChu(),
                thuoc.isSuDung(),
                thuoc.getMa());
    }
    
    public static boolean themThuocMoi(Thuoc thuoc) {
        String cauLenh = String.format("INSERT INTO %s(ma,ten,ma_loaithuoc,ma_kho,nhasanxuat,ma_donvitinhCB,gianhap,giaban,ghichu,sudung) VALUES(?,?,?,?,?,?,?,?,?,?)",
                Gobal.SQL.tenBang.thuoc);
        
        return SqlHelper.executeUpdate(cauLenh,
                thuoc.getMa(),
                thuoc.getTen(),
                thuoc.getMaloai(),
                thuoc.getMaKho(),
                thuoc.getNhaSx(),
                thuoc.getDvtcb(),
                thuoc.getGianhap(),
                thuoc.getGiaban(),
                thuoc.getGhiChu(),
                thuoc.isSuDung()? 1:0);

    }
    
    
    
    public static Thuoc layThongTinThuoc(String maThuoc) {
        Thuoc thuoc = new Thuoc();

        String cauLenh = String.format("select * from %s where ma = ?", Gobal.SQL.tenBang.thuoc);

        ResultSet rs = SqlHelper.executeQuery(cauLenh,maThuoc);

        try {

            if (rs.next()) {
                
                String ma = rs.getString("ma");
                String ten = rs.getString("ten");
                String dvtcb = rs.getString("ma_donvitinhCB");
                String maloai = rs.getString("ma_loaithuoc");
                String maKho = rs.getString("ma_kho");
                String nhaSx = rs.getString("nhasanxuat");
                String ghiChu = rs.getString("ghichu");
                boolean suDung = rs.getBoolean("sudung");
                int gianhap = rs.getInt("gianhap");
                int giaban = rs.getInt("giaban");
                
                thuoc = new Thuoc(ma, ten, dvtcb, maloai, maKho, nhaSx, ghiChu, suDung, gianhap, giaban);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

        return thuoc;
    }


    public static int laySoTrangTimKiem(String search, String maLoai, String maKho, int SoHangCuaTrang) {

        String cauLenh = String.format("%s ?,?,?,?", Gobal.SQL.tenProc.KhoHang.laysoTrangTimKiemThuoc);

        search = XuLyInput.themKiTuTimKiemSQL(search);

        int soTrang = 0;

        ResultSet rs = SqlHelper.executeQuery(cauLenh, search, maLoai, maKho, SoHangCuaTrang);

        try {
            if (rs.next()) {
                soTrang = rs.getInt("SoTrang");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }
        return soTrang;
    }

    public static ResultSet timKiemSanPham_chiaTrang(String search, String maLoai, String maKho, int SoTrang, int SoHangCuaTrang) {

        search = XuLyInput.themKiTuTimKiemSQL(search);

        String cauLenh = String.format("%s ?,?,?,?,?", Gobal.SQL.tenProc.KhoHang.timKiemThuoc);
        return SqlHelper.executeQuery(cauLenh, search, maLoai, maKho, SoTrang, SoHangCuaTrang);
    }
    
    public static boolean kiemTraDongGoiThuocTrung(String maThuoc, String maDVDG){
        
        boolean trung = false;
        String cauLenh  = String.format("%s ?,?", Gobal.SQL.tenProc.DongGoi.kiemTraDongGoiThuocTrung);
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maThuoc,maDVDG);
        
        try {
            if(rs.next()){
                trung = rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return false;
        
    }

}
