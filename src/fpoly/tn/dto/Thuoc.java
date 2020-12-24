package fpoly.tn.dto;

public class Thuoc {

    String ma;
    String ten;
    String dvtcb;
    String maloai;
    String maKho;
    String nhaSx;
    String ghiChu;
    boolean suDung;
    int gianhap;
    int giaban;

    public Thuoc() {
    }

    public Thuoc(String ma, String ten, String dvtcb, String maloai, String maKho, String nhaSx, String ghiChu, boolean suDung, int gianhap, int giaban) {
        this.ma = ma;
        this.ten = ten;
        this.dvtcb = dvtcb;
        this.maloai = maloai;
        this.maKho = maKho;
        this.nhaSx = nhaSx;
        this.ghiChu = ghiChu;
        this.suDung = suDung;
        this.gianhap = gianhap;
        this.giaban = giaban;
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

    public String getDvtcb() {
        return dvtcb;
    }

    public void setDvtcb(String dvtcb) {
        this.dvtcb = dvtcb;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public String getNhaSx() {
        return nhaSx;
    }

    public void setNhaSx(String nhaSx) {
        this.nhaSx = nhaSx;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public boolean isSuDung() {
        return suDung;
    }

    public void setSuDung(boolean suDung) {
        this.suDung = suDung;
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
    
    
    
}
