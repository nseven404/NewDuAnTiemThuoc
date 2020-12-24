package fpoly.tn.dal;

import fpoly.tn.dto.KhachHang;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.XuLyInput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALKhachHang {

    public static KhachHang laythongtinKhachHang(String maKH) {
        KhachHang kh = new KhachHang();
        String cauLenh = String.format("Select * From %s Where ma = ?", Gobal.SQL.tenBang.khachhang);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maKH);
//        String gioiTinh;
        try {
            if (rs.next()) {
                String ma = rs.getString("ma");
                String ten = rs.getString("ten");
                boolean gioiTinh = rs.getBoolean("gioitinh");
                String ngaySinh = rs.getString("ngaysinh");
                String cmnd = rs.getString("cmnd");
                String diaChi = rs.getString("diachi");
                String ghiChu = rs.getString("ghichu");
                kh = new KhachHang(ma, ten, gioiTinh, ngaySinh, cmnd, diaChi, ghiChu);
            }
        } catch (SQLException e) {
            Logger.getLogger(DALKhachHang.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            SqlHelper.closeConnection();
        }
        return kh;
    }

    public static int laySoTrangTimKiem(String search, int SoHangCuaTrang) {

        String cauLenh = String.format("%s ?,?", Gobal.SQL.tenProc.khachHang.laySoTrangTimKiemKhachHang);

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

    public static ResultSet timKiemKhachHang_chiaTrang(String search, int SoTrang, int SoHangCuaTrang) {

        search = XuLyInput.themKiTuTimKiemSQL(search);

        String cauLenh = String.format("%s ?,?,?", Gobal.SQL.tenProc.khachHang.timKhachHang);
        return SqlHelper.executeQuery(cauLenh, search, SoTrang, SoHangCuaTrang);
    }

    public static boolean kiemTraMaLoaiKhachHangTonTai(String maKH) {
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.khachHang.kiemTraMaKhachHang);

        ResultSet rs = SqlHelper.executeQuery(cauLenh, maKH);

        try {
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALKhachHang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

        return false;
    }

    public static boolean kiemTraCMNDTonTai(String cmnd) {
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.khachHang.kiemTraCMND);

        ResultSet rs = SqlHelper.executeQuery(cauLenh, cmnd);

        try {
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALKhachHang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

        return false;
    }

    public static boolean themKhachHang(KhachHang khachhang) {
        String cauLenh = String.format("INSERT INTO %s(ma,ten,gioitinh,ngaysinh,cmnd,diachi,ghichu) VALUES(?,?,?,?,?,?,?)",
                Gobal.SQL.tenBang.khachhang);

        return SqlHelper.executeUpdate(cauLenh,
                khachhang.getMa(),
                khachhang.getTen(),
                khachhang.isGioitinh(),
                khachhang.getNgaysinh(),
                khachhang.getCmnd(),
                khachhang.getDiachi(),
                khachhang.getGhichu()
        );
    }

    public static boolean suaKhachHang(KhachHang khachhang) {
        String cauLenh = String.format("UPDATE %s SET ten = ? , gioitinh = ?, ngaysinh = ?, cmnd = ?, diachi = ?, ghichu = ? WHERE ma = ?", Gobal.SQL.tenBang.khachhang);
        return SqlHelper.executeUpdate(cauLenh,
                khachhang.getTen(),
                khachhang.isGioitinh(),
                khachhang.getNgaysinh(),
                khachhang.getCmnd(),
                khachhang.getDiachi(),
                khachhang.getGhichu(),
                khachhang.getMa());
    }

    public static boolean xoaKhachHang(String maKH) {
        String cauLenh = String.format("DELETE %s WHERE ma = ?", Gobal.SQL.tenBang.khachhang);
        return SqlHelper.executeUpdate(cauLenh, maKH);
    }
}
