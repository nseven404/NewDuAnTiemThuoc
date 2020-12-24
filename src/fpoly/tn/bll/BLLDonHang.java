package fpoly.tn.bll;

import fpoly.tn.dal.DALDonHangXuat;
import fpoly.tn.dto.DonHangXuat;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BLLDonHang {

    public static String layMaDonHangMoi(String tienTo){
        return DALDonHangXuat.layMaHoaDonMoi(tienTo);
    }

    public static boolean DaCoHoaDon(String maHD) {
        return DALDonHangXuat.DaCoHoaDon(maHD);
    }

    public static DonHangXuat layThongTinDonHang(String maHD){
        
        DonHangXuat hd = new DonHangXuat();
        hd.setMa(maHD);
        ResultSet rs = DALDonHangXuat.layThongTinDonHang(maHD);
        try {
            if (rs.next()) {
                hd.setGhichu(rs.getString("ghichu"));
                hd.setMa_khachhang(rs.getString("ma_khachhang"));
                hd.setMa_nhanvien(rs.getString("ma_nhanvien"));
                Date ngaythang = NgayThang.layNgayTuTimestamp(rs.getTimestamp("ngaythang"));
                hd.setNgaythang(ngaythang);
                hd.setTiendatra(rs.getInt("tiendatra"));
                hd.setTongtien(rs.getInt("tongtien"));
                hd.setTrangthai(rs.getInt("trangthai"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BLLDonHang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        return hd;
    }

}
