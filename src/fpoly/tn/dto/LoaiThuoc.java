/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.dto;

/**
 *
 * @author NAM
 */
public class LoaiThuoc {
    
    String ma;
    String ten;
    String ghiChu;
    String maNhom;

    public LoaiThuoc() {
        
    }

    public LoaiThuoc(String ma, String ten, String ghiChu, String maNhom) {
        this.ma = ma;
        this.ten = ten;
        this.ghiChu = ghiChu;
        this.maNhom = maNhom;
    }
    
    

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNhom() {
        return maNhom;
    }

    public void setMaNhom(String maNhom) {
        this.maNhom = maNhom;
    }
    
    
    
    
}
