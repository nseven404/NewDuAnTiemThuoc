package fpoly.tn.dto;

public class NhanVien {
    String ma;
    String ten;
    int gioitinh;
    String ngaysinh; // ngày sinh format "dd-MM-yyyy", thao tác với SQL cần đổi lại format ngày
    String cmnd;
    String diachi;
    String sodienthoai;
    String taikhoan;
    String matkhau;
    String ghichu;
    String macv;
    boolean lock;
    
    public NhanVien() {
    }

    public NhanVien(String ma, String ten, int gioitinh, String ngaysinh, String cmnd, String diachi, String sodienthoai, String taikhoan, String matkhau, String ghichu, String macv, boolean lock) {
        this.ma = ma;
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.cmnd = cmnd;
        this.diachi = diachi;
        this.sodienthoai = sodienthoai;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.ghichu = ghichu;
        this.macv = macv;
        this.lock = lock;
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

    public int getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(int gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMacv() {
        return macv;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
    
    
    
}
