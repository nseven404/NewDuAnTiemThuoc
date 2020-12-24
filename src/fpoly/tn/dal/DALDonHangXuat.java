package fpoly.tn.dal;

import fpoly.tn.dto.ChiTietDonHangXuat;
import fpoly.tn.dto.DonHangXuat;
import fpoly.tn.dto.Thuoc;
import fpoly.tn.helper.SqlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALDonHangXuat {

    public static int laySoDonHangTheoNgay(String ngaythang) {
        int soDonHang = 0;
        String cauLenh = "select count(ma) as [sl] from donhangnhap where ngaythang =  '" + ngaythang + "'";

        ResultSet rs = SqlHelper.executeQuery(cauLenh);

        try {
            if (rs.next()) {
                soDonHang = rs.getInt("sl");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonHangXuat.class.getName()).log(Level.SEVERE, null, ex);
        }

        return soDonHang;
    }

    public static ResultSet layThongTinDonHang(String maHD) {
        String cauLenh = "select * from donhangxuat where ma = ?";
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maHD);
        return rs;
    }
    
    public static ResultSet layChiTietDonHang(String maHD){
        String cauLenh = "select * from chitietdonhangxuat where ma_donhang = ?";
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maHD);
        return rs;
    }

    public static String layMaHoaDonMoi(String tienTo) {
        String cauLenh = "EXEC dbo.layMaDonHangMoi ?";
        String maMoi = "";
        ResultSet rs = SqlHelper.executeQuery(cauLenh, tienTo);
        try {
            if(rs.next()){
                maMoi = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonHangXuat.class.getName()).log(Level.SEVERE, null, ex);
        }

        return maMoi ;
    }

    public static boolean DaCoHoaDon(String maHD) {

        boolean daCoMa = false;
        String cauLenh = "select *  from donhangnhap where ma = ?";

        ResultSet rs = SqlHelper.executeQuery(cauLenh, maHD);

        try {
            if (rs.next()) {
                daCoMa = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALDonHangXuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }

        return daCoMa;
    }

    public static ResultSet layDanhSachKhachHang() {
        String cauLenh = "select * from khachhang";
        ResultSet rs = SqlHelper.executeQuery(cauLenh);

        return rs;
    }

    public static ResultSet layDanhSachSanPhamAuto() {
        String cauLenh = "select ma,ten,giaban from sanpham where sudung = 1";

        ResultSet rs = SqlHelper.executeQuery(cauLenh);

        return rs;
    }

    public static ResultSet layDanhSachDVT() {
        String cauLenh = "select * from donvitinh";

        ResultSet rs = SqlHelper.executeQuery(cauLenh);

        return rs;
    }
    
    public static boolean luuDonHangMoi(DonHangXuat hd){
        String cauLenh = "INSERT INTO dbo.donhangxuat " +
                        "(ma,ma_khachhang,ma_nhanvien,tongtien,tiendatra,trangthai,ghichu,chietkhau) " +
                        "VALUES(?,?,?,?,?,?,?,?)";
        
        return SqlHelper.executeUpdate(cauLenh, hd.getMa(),
                hd.getMa_khachhang(),
                hd.getMa_nhanvien(),
                hd.getTongtien(),
                hd.getTiendatra(),
                hd.getTrangthai(),
                hd.getGhichu(),
                hd.getChietkhau());
        
    }
    
    public static boolean suaDonHang(DonHangXuat dh){
        String cauLenh = "UPDATE dbo.donhangxuat SET ma_khachhang = ?,tongtien = ?,tiendatra =?,trangthai =?,ghichu =? ,chietkhau =? where ma = ?";
        
        return SqlHelper.executeUpdate(cauLenh, 
                dh.getMa_khachhang(),
                dh.getTongtien(),
                dh.getTiendatra(),
                dh.getTrangthai(),
                dh.getGhichu(),
                dh.getChietkhau(),
                dh.getMa());
    }
    

    public static void xoaDonHang(String maHD){
        String cauLenhXoaChiTiet = "DELETE dbo.chitietdonhangxuat WHERE ma_donhang = ?";
        String cauLenhXoaDonHang = "DELETE dbo.donhangxuat WHERE ma = ?";
        SqlHelper.executeUpdate(cauLenhXoaChiTiet,maHD);
        SqlHelper.executeUpdate(cauLenhXoaDonHang, maHD);
        
    }
    
    // dùng để chỉnh sửa số lượng sản phẩm khi thay đổi đơn hàng hoặc để kiểm kho về sau? ai biết được
    
    public static void themMoiChiTietDonHang(ChiTietDonHangXuat ct){
        // Thêm vào chi tiết đơn hàng
        String cauLenh = "INSERT INTO dbo.chitietdonhangxuat(ma_donvitinh,ma_sanpham,ma_donhang,gianhap,giaban,thanhtien,ghichu,soluong)VALUES(?,?,?,?,?,?,?,?)";
        SqlHelper.executeUpdate(cauLenh,ct.getMa_donvitinh(),ct.getMa_sanPham(),ct.getMa_donhang(),ct.getGianhap(),ct.getGiaban(),ct.getThanhtien(),ct.getGhichu(),ct.getSoluong());
        suaSoLuongSanPham(ct,true);
    }
    public static void suaChiTietDonHang(ChiTietDonHangXuat ctMoi, ChiTietDonHangXuat ctCu){
        String cauLenh = "UPDATE dbo.chitietdonhangxuat set ma_sanpham = ? , ma_donvitinh = ? ,giaban = ?, thanhtien = ? ,soluong = ?,ghichu = ? where id = ? and ma_donhang = ?";
        SqlHelper.executeUpdate(cauLenh, ctMoi.getMa_sanPham(),ctMoi.getMa_donvitinh(),ctMoi.getGiaban(),ctMoi.getThanhtien(),ctMoi.getSoluong(),ctMoi.getGhichu(),ctMoi.getId(),ctMoi.getMa_donhang());
        suaSoLuongSanPham(ctCu, false);
        suaSoLuongSanPham(ctMoi, true);
    }
    
    public static void xoaChiTietDonHang(ChiTietDonHangXuat ct){
        String cauLenh = "DELETE dbo.chitietdonhangxuat WHERE id = ?";
        SqlHelper.executeUpdate(cauLenh, ct.getId());
        suaSoLuongSanPham(ct,false);
        
    }
    
    private static void suaSoLuongSanPham(ChiTietDonHangXuat ct, boolean tru){
        // Trừ số lượng sản phẩm
        String cauLenh = "UPDATE dbo.sanpham SET tonkho += ? where ma = ?";
        
        if(tru)
            ct.setSoluong( -ct.getSoluong());
            
         SqlHelper.executeUpdate(cauLenh, ct.getSoluong(), ct.getMa_sanPham());
    }
    
    // new
    
    public static ResultSet timKiemDanhSachDonHangBan(String ngayBatDau,String ngayKetThuc ,String search, int trangThai){ 
       String cauLenh = "EXEC dbo.timDanhSachDonHang ?,?,?,?,?";
       return SqlHelper.executeQuery(cauLenh,ngayBatDau,ngayKetThuc,search,trangThai,0);
    }

}
