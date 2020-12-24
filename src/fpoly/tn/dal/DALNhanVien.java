package fpoly.tn.dal;

import fpoly.tn.dto.NhanVien;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.XuLyInput;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALNhanVien {

    public static boolean themNhanVien(NhanVien nv) throws SQLException { // edit
        String cauLenh = "INSERT INTO nhanvien VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        return SqlHelper.executeUpdate(cauLenh,
                nv.getMa(),
                nv.getTen(),
                nv.getGioitinh(),
                nv.getNgaysinh(),
                nv.getCmnd(),
                nv.getDiachi(),
                nv.getSodienthoai(),
                nv.getTaikhoan(),
                nv.getMatkhau(),
                nv.getGhichu(),
                nv.getMacv());
    }

    public static ResultSet timNhanVien(String tuKhoa) throws SQLException { // edit
        String cauLenh = "";
        tuKhoa = "%" + tuKhoa + "%";
        return SqlHelper.executeQuery(cauLenh, tuKhoa, tuKhoa,tuKhoa,tuKhoa);
    }

    public static ResultSet layDanhSachNhanVien() throws SQLException { // edit
        String cauLenh = "SELECT * FROM dbo.NHANVIEN";
        return SqlHelper.executeQuery(cauLenh);
    }

    public static boolean kiemTraTaiKhoanVienTonTai(String taiKhoan) throws SQLException { // edit
        String cauLenh = "select * from nhanvien where taikhoan = ?";
        boolean daCoMa = false;

        ResultSet rs = SqlHelper.executeQuery(cauLenh, taiKhoan);
        if (rs != null) {
            if (rs.next()) {
                daCoMa = true;
            }
        }
        return daCoMa;
    }

    public static boolean suaNhanVien(NhanVien nv) throws SQLException { //edit
        String cauLenh = "UPDATE nhanvien set ten = ? , gioitinh = ?, ngaysinh = ?, cmnd = ?, diachi = ?, "
                + "sodienthoai = ?, taikhoan = ?, matkhau = ?, ghichu = ?, ma_chucvu = ? WHERE ma = ?";
        return SqlHelper.executeUpdate(cauLenh,
                nv.getTen(),
                nv.getGioitinh(),
                NgayThang.doiDinhDangNgay(nv.getNgaysinh(), "dd-MM-yyyy", "MM-dd-yyyy"),
                nv.getCmnd(),
                nv.getDiachi(),                
                nv.getSodienthoai(),
                nv.getTaikhoan(),
                nv.getMatkhau(),
                nv.getGhichu(),
                nv.getMa());
    }

    public static boolean xoaNhanVien(String MaNv) { // edit
        String cauLenh = String.format("delete nhanvien where ma = ?");
        return SqlHelper.executeUpdate(cauLenh, MaNv);
    }
    
    public static boolean kiemTraCoTheXoaNhanVien(String maNv){ // edit
        String cauLenh = "";
        ResultSet rs;
        
        boolean coTrongHoaDonXuat = false;
        boolean coTrongHoaDonNhap = false;
        boolean coTrongDanhSachNhanVienKho = false;
        
        
        cauLenh = "SELECT TOP 1* FROM dbo.donhangxuat WHERE ma_nhanvien = ?";
        rs = SqlHelper.executeQuery(cauLenh, maNv);
        try {
            coTrongHoaDonXuat = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cauLenh = "SELECT TOP 1* FROM dbo.donhangnhap WHERE ma_nhanvien = ?";
        rs = SqlHelper.executeQuery(cauLenh, maNv);
        try {
            coTrongHoaDonNhap = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cauLenh = "SELECT TOP 1* FROM dbo.donhangnhap WHERE ma_nhanvien = ?";
        rs = SqlHelper.executeQuery(cauLenh, maNv);
        try {
            coTrongDanhSachNhanVienKho = rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return !coTrongHoaDonXuat && !coTrongHoaDonNhap && !coTrongDanhSachNhanVienKho;
        
    }

    public static boolean kiemTraCMNDTonTai(String cmnd, String manv) throws SQLException{
        String cauLenh = "SELECT cmnd FROM dbo.NHANVIEN WHERE cmnd = ? and ma != ?";
        boolean daCo = false;
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, cmnd, manv);
        if (rs != null) {
            try {
                if (rs.next()) {
                    daCo = true;
                }
            } catch (SQLException ex) {
                daCo = false;
            }
        }
        return daCo;
    }
    
    public static boolean kiemTraMaNhanVienTonTai(String MaNv) throws SQLException {
        String cauLenh = "select * from nhanvien where ma = ?";
        boolean daCoMa = false;

        ResultSet rs = SqlHelper.executeQuery(cauLenh, MaNv);
        if (rs != null) {
            if (rs.next()) {
                daCoMa = true;
            }
        }
        return daCoMa;
    }

    public static ResultSet layDanhSachChucVu() throws SQLException {
        String cauLenh = "select * from chucvu";

        return SqlHelper.executeQuery(cauLenh);
    }
    
    // NEW
    
    public static boolean LayTrangThaiLockNhanVien(String maNv){
        
        String cauLenh = "select lock from nhanvien where ma = ?";
        boolean khoa = false;
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maNv);
        if (rs != null) {
            try {
                if (rs.next()) {
                    khoa = rs.getBoolean("lock");
                }
            } catch (SQLException ex) {
                
            }
        }
        return khoa;
    }
    
    public static boolean khoaNhanVien(String maNv, boolean lock){
        
        String cauLenh = String.format("UPDATE dbo.NHANVIEN SET lock = ? WHERE ma = ?");
        
        return SqlHelper.executeUpdate(cauLenh, lock,maNv);
    }
    
     public static int laySoTrangTimKiem(String search, int SoHangCuaTrang) {

        String cauLenh = String.format("%s ?,?", Gobal.SQL.tenProc.NhanVien.laySoTrangTimKiemNhanVien);

        search = XuLyInput.themKiTuTimKiemSQL(search);

        int soTrang = 0;

        ResultSet rs = SqlHelper.executeQuery(cauLenh, search, SoHangCuaTrang);

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
     
     public static ResultSet timKiemNhanVien(String search, int SoTrang,int SoHangCuaTrang){
         search = XuLyInput.themKiTuTimKiemSQL(search);

        String cauLenh = String.format("%s ?,?,?", Gobal.SQL.tenProc.NhanVien.timKiemNhanVien);
        return SqlHelper.executeQuery(cauLenh, search, SoTrang, SoHangCuaTrang);
     }
    
    public static boolean doiMatKhau(String maNv, String matKhau){
        String caulenh = "UPDATE dbo.NHANVIEN SET matkhau = ? WHERE ma = ?";
        return SqlHelper.executeUpdate(caulenh, matKhau, maNv);
    }
    
    //Lưu thông tin nhân viên
    
    public static boolean themMoiNhanVien(NhanVien nv){
        
        String cauLenh = "INSERT INTO dbo.NHANVIEN(ma,ten,gioitinh,ngaysinh,cmnd,diachi,sodienthoai,taikhoan,matkhau,ghichu,ma_chucvu,lock) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        
        return SqlHelper.executeUpdate(cauLenh,
                nv.getMa(),
                nv.getTen(),
                nv.getGioitinh(),
                nv.getNgaysinh(),
                nv.getCmnd(),
                nv.getDiachi(),
                nv.getSodienthoai(),
                nv.getTaikhoan(),
                nv.getMatkhau(),
                nv.getGhichu(),
                nv.getMacv(),
                nv.isLock());
    }
    
    public static boolean suaThongTinNhanVien(NhanVien nv){
        String caulenh = "UPDATE dbo.NHANVIEN SET ten = ?, gioitinh = ?, ngaysinh = ?, cmnd = ?, diachi = ?, sodienthoai = ?, ghichu = ?, lock = ? WHERE ma = ?";
        return SqlHelper.executeUpdate(caulenh, 
                nv.getTen(), 
                nv.getGioitinh(), 
                NgayThang.doiDinhDangNgay(nv.getNgaysinh(), "dd-MM-yyyy", "yyyy-MM-dd"), 
                nv.getCmnd(), 
                nv.getDiachi(), 
                nv.getSodienthoai(), 
                nv.getGhichu(),nv.isLock(), nv.getMa());
    }
    
    public static boolean matKhauKhop(String maNv, String matkhauCu){
        boolean khop = false;
        
        String caulenh = "SELECT * FROM dbo.NHANVIEN WHERE ma = ? AND matkhau = ?";
        ResultSet rs = SqlHelper.executeQuery(caulenh, maNv, matkhauCu);
        
        try {
            if(rs.next()){
                khop = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return khop;
    }
    
    public static String layTenChucVu(String macv){
        String ten = "";
        String caulenh = "select ten from CHUCVU where ma = ? ";
        ResultSet rs = SqlHelper.executeQuery(caulenh, macv);
        try {
            if(rs.next()){
               ten = rs.getString("ten");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }   
        return ten;
    }
    
    
    public static String layTTChucVu(String manv){
        String cv = "";
        String caulenh = "select ma_chucvu from NHANVIEN where ma = ? ";
        ResultSet rs = SqlHelper.executeQuery(caulenh, manv);
        try {
            if(rs.next()){
               cv = rs.getString("ma_chucvu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }   
        return cv;
    }
    
    public static NhanVien layThongTinNhanVien(String MaNv) {
        NhanVien nv = new NhanVien();
        try {
            String cauLenh = "select * from nhanvien where ma = ?";

            ResultSet rs = SqlHelper.executeQuery(cauLenh, MaNv);
            if (rs != null) {
                while (rs.next()) {
                    nv.setTen(rs.getString("ten"));
                    nv.setMa(rs.getString("ma"));
                    nv.setGioitinh(rs.getInt("gioitinh"));
                    nv.setNgaysinh(rs.getString("ngaysinh"));
                    nv.setCmnd(rs.getString("cmnd"));
                    nv.setDiachi(rs.getString("diachi"));
                    nv.setSodienthoai(rs.getString("sodienthoai"));
                    nv.setTaikhoan(rs.getString("taikhoan"));
                    nv.setMatkhau(rs.getString("matkhau"));
                    nv.setGhichu(rs.getString("ghichu"));
                    nv.setMacv(rs.getString("ma_chucvu"));
                    nv.setLock(rs.getBoolean("lock"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }
        return nv;
    }
    
    public static String layTenNhanVien(String maNV) {
        String cauLenh = "select ten from nhanvien where ma = ?";
        String ten = "";

        ResultSet rs = SqlHelper.executeQuery(cauLenh, maNV);
        
        if (rs != null) {
            try {
                if (rs.next()) {
                    ten = rs.getString("ten");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ten;
    }

    public static String dangNhap(String taiKhoan, String pass) {
        String ma = "";
        String cauLenh = "select ma from nhanvien where taikhoan = ? and matkhau = ?";
        ResultSet rs = SqlHelper.executeQuery(cauLenh, taiKhoan, pass);
        
        try {
            if (rs.next()) {
                ma = rs.getString("ma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        
        return ma;
    }
    
    //lay du lieu
    
    public static boolean checkSoDienThoaiSuaTrung(String maNv, String sodienthoai){
        boolean trung = false;
        
        String cauLenh = "SELECT * FROM dbo.NHANVIEN WHERE ma != ? AND sodienthoai = ?";
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maNv,sodienthoai);
        
        try {
            if(rs.next()){
                trung = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return trung;
    }
    
    public static boolean checkSoDienThoaiThemMoiTrung(String sodienthoai){
        boolean trung = false;
        
        String cauLenh = "SELECT * FROM dbo.NHANVIEN WHERE sodienthoai = ?";
        
        ResultSet rs = SqlHelper.executeQuery(cauLenh,sodienthoai);
        
        try {
            if(rs.next()){
                trung = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return trung;
    }
    
    public static String LayChucVuNV(String manv) {
        String cv = "";
        String cauLenh = "SELECT ma_chucvu AS [ma_chucvu]   FROM [QuanLyTiemThuocTay].[dbo].NHANVIEN where ma = ?";
        ResultSet rs = SqlHelper.executeQuery(cauLenh, manv);
        
        try {
            if (rs.next()) {
                cv = rs.getString("ma_chucvu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        return cv;
    }
    
    //check admin
    public static boolean nhanVienLaAdmin(String maCV){
        return maCV.trim().equals("CV1");
    }
}
