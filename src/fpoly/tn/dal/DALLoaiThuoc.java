/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.dal;

import fpoly.tn.dto.LoaiThuoc;
import fpoly.tn.dto.Thuoc;
import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.XuLyInput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAM
 */
public class DALLoaiThuoc {
    public static LoaiThuoc layThongTinLoaiThuoc(String maLoai){
        LoaiThuoc loaiThuoc = new LoaiThuoc();
        String cauLenh = String.format("Select * From %s Where ma = ?", Gobal.SQL.tenBang.loaithuoc);
        ResultSet rs = SqlHelper.executeQuery(cauLenh, maLoai);
        try {
            if(rs.next()){
                String ma = rs.getString("ma");
                String ten = rs.getString("ten");
                String ghiChu = rs.getString("ghichu");
                String maNhom = rs.getString("ma_nhom");
                loaiThuoc = new LoaiThuoc(ma, ten, ghiChu, maNhom);
            }
        } catch (SQLException e) {
            Logger.getLogger(DALThuoc.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            SqlHelper.closeConnection();
        }
        return loaiThuoc;
    }
    
    public static boolean themLoaiThuocMoi(LoaiThuoc loaiThuoc) {
        String cauLenh = String.format("INSERT INTO %s(ma,ten,ghichu,ma_nhom) VALUES(?,?,?,?)",
                Gobal.SQL.tenBang.loaithuoc);
        
        return SqlHelper.executeUpdate(cauLenh,
                loaiThuoc.getMa(),
                loaiThuoc.getTen(),
                loaiThuoc.getGhiChu(),
                loaiThuoc.getMaNhom()
                );

    }
    
    public static boolean suaLoaiThuoc(LoaiThuoc loaiThuoc) {
        String cauLenh = String.format("UPDATE %s SET ten = ? , ghichu = ?, ma_nhom = ? WHERE ma = ?",Gobal.SQL.tenBang.loaithuoc);
        return SqlHelper.executeUpdate(cauLenh,
                loaiThuoc.getTen(),
                loaiThuoc.getGhiChu(),
                loaiThuoc.getMaNhom(),
                loaiThuoc.getMa());
    }
    
    public static boolean xoaLoaiThuoc(String ma) {
        String cauLenh = String.format("DELETE %s WHERE ma = ?", Gobal.SQL.tenBang.loaithuoc);
        return SqlHelper.executeUpdate(cauLenh, ma);
    }
    
    public static boolean kiemTraMaLoaiThuocTonTai(String maLoai){
         String cauLenh = String.format("%s ?", Gobal.SQL.tenProc.LoaiThuoc.kiemTraMaLoai);
         
         ResultSet rs = SqlHelper.executeQuery(cauLenh, maLoai);
         
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
    
    public static ResultSet timKiemLoaiThuoc_chiaTrang(String search, int SoTrang, int SoHangCuaTrang) {

        search = XuLyInput.themKiTuTimKiemSQL(search);

        String cauLenh = String.format("%s ?,?,?", Gobal.SQL.tenProc.LoaiThuoc.timKiemLoaiThuoc);
        return SqlHelper.executeQuery(cauLenh, search, SoTrang, SoHangCuaTrang);
    }
    
    public static int laySoTrangTimKiem(String search, int SoHangCuaTrang) {

        String cauLenh = String.format("%s ?,?", Gobal.SQL.tenProc.LoaiThuoc.laysoTrangTimKiemLoaiThuoc);

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
    
}
