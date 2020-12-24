package fpoly.tn.helper;

import java.awt.Color;

public class Gobal {

    public interface ColorForm {

        public final static Color sliderBackground = new Color(25, 38, 61);
        public final static Color sliderItemHover = new Color(21, 99, 135);
        public final static Color itemWarrningColor = new Color(255, 153, 153);
    }

    public interface PrefKey {

        public interface FormDangNhap {

            final String taikhoan = "taikhoan";
            final String matkhau = "matkhau";
            final String nhodangnhap = "nhodangnhap";
        }

        public interface FormSettingSql {

            final String TCPPort = "TCPPort";
            final String DatabaseName = "DatabaseName";
            final String PASS = "PASS";
            final String ID = "ID";
        }
    }

    public interface SQL {

        public interface tenBang {

            final String chucvu = "dbo.CHUCVU";
            final String chitietHDB = "dbo.CTHDBAN";
            final String chitietHDN = "dbo.CTHDNHAP";
            final String donggoi = "dbo.DONGGOI";
            final String donvidonggoi = "dbo.DONVIDONGGOI";
            final String donvitinh = "dbo.DONVITINH";
            final String hoadonban = "dbo.HOADONBAN";
            final String hoadonnhap = "dbo.HOADONNHAP";
            final String khachhang = "dbo.KHACHHANG";
            final String kho = "dbo.KHO";
            final String loaithuoc = "dbo.LOAITHUOC";
            final String lothuoc = "dbo.LOTHUOC";
            final String nhacungcap = "dbo.NHACUNGCAP";
            final String nhanvien = "dbo.NHANVIEN";
            final String thuoc = "dbo.THUOC";
        }

        public interface tenProc {

            public interface KhoHang {

                final String timKiemThuoc = "dbo.TimKiemThuoc";
                final String laysoTrangTimKiemThuoc = "dbo.LaySoTrangTimKiemThuoc";
                final String kiemTraMaLoTonTai = "dbo.kiemTraMaLoTonTai";
                final String timKiemLoThuoc = "dbo.timKiemLoThuoc";
                final String laySoTrangTimKiemLoThuoc = "dbo.laySoTrangTimKiemLoThuoc";
            }

            public interface Thuoc {

                final String layDanhSachDongGoi = "dbo.layDongGoiThuoc";
                final String kiemTraMaThuoc = "dbo.KiemTraMaThuocTonTai";
                final String layLoThuoc = "dbo.layLoThuoc";
                final String kiemTraDongGoiCoTheXoa = "dbo.cotheXoaDongGoiThuoc";

            }

            public interface DonViTinh {

                final String kiemTraMaDVTTonTai = "dbo.kiemTraMaDVTTonTai";
                final String kiemTraDVTCoTheXoa = "dbo.kiemTraDonViTinhCoTheXoa";
                final String layDanhSachDonViTinhCuaThuoc = "dbo.layDanhSachDonViTinhThuoc";
                final String laySoLuongChiaTheoDonViTinh = "dbo.laySoLuongChiaTheoDonViTinh";
            }

            public interface DonViDongGoi {

                final String kiemTraDonViTinhDVDGTrung = "dbo.kiemTraDonViTinhDVDGTrung";
                final String kiemTraMaDVDGTrung = "dbo.kiemTraMaDVDGTrung";
                final String kiemTraCoTheXoaDVDG = "dbo.kiemTraCoTheXoaDVDG";
            }

            public interface DongGoi {

                final String kiemTraDongGoiThuocTrung = "dbo.kiemTraDongGoiThuocTrung";
            }

            public interface LoaiThuoc {

                final String kiemTraMaLoai = "dbo.KiemTraMaLoaiTonTai";
                final String timKiemLoaiThuoc = "dbo.TimKiemLoaiThuoc";
                final String laysoTrangTimKiemLoaiThuoc = "dbo.LaySoTrangTimKiemLoaiThuoc";
                final String layLoaiThuoc = "dbo.layLoaiThuoc";
            }

            public interface NhaCungCap {

                final String kiemTraNhaCungCap = "dbo.KiemTraMaNhaCungCapTonTai";
                final String timKiemNhaCungCap = "dbo.TimKiemNhaCungCap";
                final String laysoTrangTimKiemNhaCungCap = "dbo.[LaySoTrangTimKiemNhaCungCap]";
                final String layNhaCungCap = "dbo.layNhaCungCap";
            }

            public interface khachHang {

                final String laySoTrangTimKiemKhachHang = "dbo.[LaySoTrangTimKiemKhachHang]";
                final String timKhachHang = "dbo.TimKiemKhachHang";
                final String kiemTraMaKhachHang = "dbo.KiemTraMaKHTonTai";
                final String kiemTraCMND = "dbo.KiemTraCMNDTonTai";
            }

            public interface NhanVien {

                final String laySoTrangTimKiemNhanVien = "dbo.LaySoTrangTimKiemNhanVien";
                final String timKiemNhanVien = "dbo.timKiemNhanVien";
            }
        }
    }

}
