/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.dal;

import fpoly.tn.dto.NhaCungCap;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.XuLyInput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DINH QUOC HAI
 */
public class DALNhaCungCap {
    
    public static NhaCungCap layThongTinNhaCungCap(String maLoai){
        NhaCungCap nhacungcap = new NhaCungCap();
        String cauLenh = String.format("Select * From %s Where ma = ?", Gobal.SQL.tenBang.nhacungcap);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maLoai);
        try {
            if(rs.next()){
                String ma = rs.getString("ma");
                String ten = rs.getString("ten");
                String diachi = rs.getString("diachi");
                String sdt = rs.getString("sodienthoai");
                String ghiChu = rs.getString("ghichu");
                nhacungcap = new NhaCungCap(ma, ten, diachi, sdt, ghiChu);
            }
        } catch (SQLException e) {
            Logger.getLogger(DALNhaCungCap.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            SqlHelper.closeConnection();
        }
        return nhacungcap;
    }
    public static boolean themNhaCungCapMoi(NhaCungCap nhacc) {
        String cauLenh = String.format("INSERT INTO %s(ma,ten,diachi,sodienthoai,ghichu) VALUES(?,?,?,?,?)",
                Gobal.SQL.tenBang.nhacungcap);
        
        return SqlHelper.executeUpdate(cauLenh,
                nhacc.getMa(),
                nhacc.getTen(),
                nhacc.getDiachi(),
                nhacc.getSdt(),
                nhacc.getGhichu()
                );

    }
    
    public static boolean suaNhaCungCap(NhaCungCap nhacc) {
        String cauLenh = String.format("UPDATE %s SET ten = ? ,diachi = ? ,sodienthoai = ? , ghichu = ? WHERE ma = ?",Gobal.SQL.tenBang.nhacungcap);
        return SqlHelper.executeUpdate(cauLenh,
                nhacc.getTen(),
                nhacc.getDiachi(),
                nhacc.getSdt(),
                nhacc.getGhichu(),
                nhacc.getMa());
    }
    
    public static boolean xoaNhaCungCap(String ma) {
        String cauLenh = String.format("DELETE %s WHERE ma = ?", Gobal.SQL.tenBang.nhacungcap);
        return SqlHelper.executeUpdate(cauLenh, ma);
    }
    
    public static boolean kiemTraMaNhaCungCapTonTai(String maNCC){
         String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.NhaCungCap.kiemTraNhaCungCap);
         
         ResultSet rs = SqlHelper.executeQuery(cauLenh, maNCC);
         
        try {
            if(rs.next()){
                return rs.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhaCungCap.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        
        return false;
    }
    
    public static ResultSet timKiemNhaCungCap_chiaTrang(String search, int SoTrang, int SoHangCuaTrang) {

        search = XuLyInput.themKiTuTimKiemSQL(search);

        String cauLenh = String.format("%s ?,?,?", Gobal.SQL.tenProc.NhaCungCap.timKiemNhaCungCap);
        return SqlHelper.executeQuery(cauLenh, search, SoTrang, SoHangCuaTrang);
    }
    
    public static int laySoTrangTimKiem(String search, int SoHangCuaTrang) {

        String cauLenh = String.format("%s ?,?", Gobal.SQL.tenProc.NhaCungCap.laysoTrangTimKiemNhaCungCap);

        search = XuLyInput.themKiTuTimKiemSQL(search);

        int soTrang = 0;

        ResultSet rs = SqlHelper.executeQuery(cauLenh, search, SoHangCuaTrang);

        try {
            if (rs.next()) {
                soTrang = rs.getInt("SoTrang");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DALNhaCungCap.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }
        return soTrang;
    }
    
    public static ResultSet loadDanhSachNhaCungCap(){
        String cauLenh = String.format("SELECT * FROM %s", Gobal.SQL.tenBang.nhacungcap);
        return SqlHelper.executeQuery(cauLenh);
    }
}
