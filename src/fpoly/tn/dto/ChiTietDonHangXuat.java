/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.dto;

/**
 *
 * @author DELL
 */
public class ChiTietDonHangXuat {
    
    int id;
    String ma_donhang;
    String ma_sanPham;
    String ma_donvitinh;
    float soluong;
    int gianhap;
    int giaban;
    int thanhtien;
    String ghichu;
    

    public ChiTietDonHangXuat() {}

    public ChiTietDonHangXuat(int id, String ma_donhang, String ma_sanPham, String ma_donvitinh, float soluong, int gianhap, int giaban, int thanhtien, String ghichu) {
        this.id = id;
        this.ma_donhang = ma_donhang;
        this.ma_sanPham = ma_sanPham;
        this.ma_donvitinh = ma_donvitinh;
        this.soluong = soluong;
        this.gianhap = gianhap;
        this.giaban = giaban;
        this.thanhtien = thanhtien;
        this.ghichu = ghichu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMa_donhang() {
        return ma_donhang;
    }

    public void setMa_donhang(String ma_donhang) {
        this.ma_donhang = ma_donhang;
    }

    public String getMa_sanPham() {
        return ma_sanPham;
    }

    public void setMa_sanPham(String ma_sanPham) {
        this.ma_sanPham = ma_sanPham;
    }

    public String getMa_donvitinh() {
        return ma_donvitinh;
    }

    public void setMa_donvitinh(String ma_donvitinh) {
        this.ma_donvitinh = ma_donvitinh;
    }

    public float getSoluong() {
        return soluong;
    }

    public void setSoluong(float soluong) {
        this.soluong = soluong;
    }

    public int getGianhap() {
        return gianhap;
    }

    public void setGianhap(int gianhap) {
        this.gianhap = gianhap;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    
}
