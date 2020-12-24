package fpoly.tn.dto;

import java.util.Date;

public class DonHangXuat {
    
    String ma;
    String ma_khachhang;
    String ma_nhanvien;
    int tongtien = 0;
    int tiendatra = 0;
    int trangthai;
    Date ngaythang;
    String ghichu;
    int chietkhau;

    public DonHangXuat() {}

    public DonHangXuat(String ma, String ma_khachhang, String ma_nhanvien,int tongtien,int tiendatra, int trangthai, Date ngaythang, String ghichu, int chietkhau) {
        this.ma = ma;
        this.ma_khachhang = ma_khachhang;
        this.ma_nhanvien = ma_nhanvien;
        this.trangthai = trangthai;
        this.ngaythang = ngaythang;
        this.ghichu = ghichu;
        this.chietkhau = chietkhau;
        this.tiendatra = tiendatra;
        this.tongtien = tongtien;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getMa_khachhang() {
        return ma_khachhang;
    }

    public void setMa_khachhang(String ma_khachhang) {
        this.ma_khachhang = ma_khachhang;
    }

    public String getMa_nhanvien() {
        return ma_nhanvien;
    }

    public void setMa_nhanvien(String ma_nhanvien) {
        this.ma_nhanvien = ma_nhanvien;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getTiendatra() {
        return tiendatra;
    }

    public void setTiendatra(int tiendatra) {
        this.tiendatra = tiendatra;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public Date getNgaythang() {
        return ngaythang;
    }

    public void setNgaythang(Date ngaythang) {
        this.ngaythang = ngaythang;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getChietkhau() {
        return chietkhau;
    }

    public void setChietkhau(int chietkhau) {
        this.chietkhau = chietkhau;
    }
    
   
    
}
