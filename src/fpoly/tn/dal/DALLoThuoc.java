package fpoly.tn.dal;

import fpoly.tn.dto.LoThuoc;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.XuLyInput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DALLoThuoc {

    public static boolean themMoMoi(LoThuoc loThuoc) {
        String cauLenh = String.format("INSERT INTO %s(ma,ma_thuoc,ma_nhacungcap,ngaysanxuat,ngayhethan,soluong,ghichu)VALUES(?,?,?,?,?,?,?)", Gobal.SQL.tenBang.lothuoc);
        return SqlHelper.executeUpdate(cauLenh, loThuoc.getMaLo(), loThuoc.getMaThuoc(), loThuoc.getMaNCC(), loThuoc.getNgaySanXuat(), loThuoc.getHanSuDung(), loThuoc.getSoLuong(), loThuoc.getGhiChu());
    }

    public static boolean suaLoThuoc(LoThuoc loThuoc) {
        String cauLenh = String.format("UPDATE %s SET ngaysanxuat = ?,ngayhethan = ?,soluong = ?,ghichu = ? WHERE ma = ?", Gobal.SQL.tenBang.lothuoc);
        return SqlHelper.executeUpdate(cauLenh, loThuoc.getNgaySanXuat(), loThuoc.getHanSuDung(), loThuoc.getSoLuong(), loThuoc.getGhiChu(), loThuoc.getMaLo());

    }

    public static boolean kiemTraMaLoTonTai(String maLo) {
        String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.KhoHang.kiemTraMaLoTonTai);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maLo);
        boolean tonTai = false;
        try {
            if (rs.next()) {
                int kq = rs.getInt(1);
                tonTai = kq == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALKho.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }
        return tonTai;
    }

    public static ResultSet timKiemLoThuocPhanTrang(String search, String maKho, int trangThai, int soTrang, int soHangCuaTrang) {
        search = XuLyInput.themKiTuTimKiemSQL(search);
        String cauLenh = String.format("%s ?,?,?,?,?", Gobal.SQL.tenProc.KhoHang.timKiemLoThuoc);
        return SqlHelper.executeQuery(cauLenh, search, maKho, trangThai, soTrang, soHangCuaTrang);
    }

    public static int laySoTrangTimKiemLoThuoc(String search, String maKho, int trangThai, int soHangCuaTrang) {

        int soTrang = 0;

        search = XuLyInput.themKiTuTimKiemSQL(search);

        String cauLenh = String.format("%s ?,?,?,?", Gobal.SQL.tenProc.KhoHang.laySoTrangTimKiemLoThuoc);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, search, maKho, trangThai, soHangCuaTrang);

        try {
            if (rs.next()) {
                soTrang = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALLoThuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

        return soTrang;
    }

    public static boolean xoaLoThuoc(String maLoThuoc) {
        String cauLenh = String.format("DELETE %s WHERE MaLo = ?", Gobal.SQL.tenBang.lothuoc);
        return SqlHelper.executeUpdate(cauLenh, maLoThuoc);
    }

}
