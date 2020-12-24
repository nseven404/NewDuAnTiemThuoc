USE [QuanLyTiemThuocTay]
GO
/****** Object:  StoredProcedure [dbo].[cotheXoaDongGoiThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[cotheXoaDongGoiThuoc]
@idDongGoi int
AS
BEGIN
	DECLARE @maThuoc CHAR(6)
	DECLARE @maDongGoi CHAR(6)
	DECLARE @maDVT1 CHAR(6)
	DECLARE @maDVT2 CHAR(6)
	
	SET @maThuoc = (SELECT ma_thuoc FROM dbo.DONGGOI WHERE ID = @idDongGoi)
	SET @maDongGoi = (SELECT ma_donvidonggoi FROM dbo.DONGGOI WHERE id = @idDongGoi)

	SET @maDVT1 = (SELECT ma_donvitinh1 FROM dbo.DONVIDONGGOI  WHERE ma = @maDongGoi)
	SET @maDVT2 = (SELECT ma_donvitinh2 FROM dbo.DONVIDONGGOI  WHERE ma = @maDongGoi)

	IF EXISTS (SELECT * FROM dbo.CTHDBAN WHERE ma_thuoc = @maThuoc AND (@maDVT1 = ma_donvitinh OR @maDVT2 = ma_donvitinh))
		SELECT 0
	ELSE
    BEGIN
		IF EXISTS (SELECT * FROM dbo.CTHDNHAP WHERE ma_thuoc = @maThuoc AND (@maDVT1 = ma_donvitinh OR @maDVT2 = ma_donvitinh))
		SELECT 0
		ELSE
		SELECT 1
    END

END

GO
/****** Object:  StoredProcedure [dbo].[KiemTraCMNDTonTai]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[KiemTraCMNDTonTai]
@cmnd Char(9)
AS 
BEGIN
	IF EXISTS (SELECT * FROM dbo.KHACHHANG WHERE RTRIM(LTRIM(cmnd)) = @cmnd)
		SELECT 1
		ELSE
        SELECT 0
END
GO
/****** Object:  StoredProcedure [dbo].[KiemTraCMNDTonTaiKhacCMND]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[KiemTraCMNDTonTaiKhacCMND]
@cmnd CHAR(12), @manv CHAR(6)
AS
BEGIN
	IF EXISTS (SELECT * FROM dbo.NHANVIEN WHERE RTRIM(LTRIM(cmnd)) = RTRIM(LTRIM(@cmnd)) AND RTRIM(LTRIM(ma)) <> RTRIM(LTRIM(@manv)))
		SELECT 1
	ELSE
		SELECT 0	
END
GO
/****** Object:  StoredProcedure [dbo].[kiemTraCoTheXoaDVDG]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[kiemTraCoTheXoaDVDG]
@maDVDG CHAR(6)
AS
BEGIN
	IF EXISTS (SELECT TOP(1) ma_donvidonggoi from dbo.DONGGOI WHERE dbo.DONGGOI.ma_donvidonggoi = @maDVDG)
	SELECT 0
	ELSE
    SELECT 1
END

GO
/****** Object:  StoredProcedure [dbo].[kiemTraDonViTinhCoTheXoa]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[kiemTraDonViTinhCoTheXoa]
@maDVT CHAR(6)
AS
BEGIN
	IF EXISTS (SELECT TOP (1) ma_donvitinhCB  FROM dbo.THUOC WHERE ma = @maDVT)
		OR EXISTS (SELECT TOP (1) ma FROM dbo.DONVIDONGGOI WHERE ma_donvitinh2 = @maDVT OR ma_donvitinh1 = @maDVT)
		OR EXISTS (SELECT TOP (1) ID FROM dbo.CTHDBAN WHERE ma_donvitinh = @maDVT)
		OR EXISTS (SELECT TOP (1) ID FROM dbo.CTHDNHAP WHERE ma_donvitinh = @maDVT)
		SELECT 0
	ELSE
		SELECT 1
		
END

GO
/****** Object:  StoredProcedure [dbo].[kiemTraDonViTinhDVDGTrung]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[kiemTraDonViTinhDVDGTrung] 
@maDVT1 CHAR(6),
@maDVT2 CHAR(6)
AS
BEGIN
	IF EXISTS (SELECT * FROM dbo.DONVIDONGGOI WHERE ma_donvitinh1 = @maDVT1 AND ma_donvitinh2 = @maDVT2)
	SELECT 1
	ELSE
    SELECT 0
END

GO
/****** Object:  StoredProcedure [dbo].[kiemTraMaDVDGTrung]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[kiemTraMaDVDGTrung] 
@maDVDG CHAR(6)
AS
BEGIN
	IF EXISTS (SELECT * FROM dbo.DONVIDONGGOI WHERE ma = @maDVDG)
	SELECT 1
	ELSE
    SELECT 0
END

GO
/****** Object:  StoredProcedure [dbo].[kiemTraMaDVTTonTai]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[kiemTraMaDVTTonTai]
@maDVT CHAR(6)
AS
BEGIN
	IF EXISTS(SELECT * FROM dbo.DONVITINH WHERE ma = @maDVT)
		SELECT 1
	ELSE
		SELECT 0
END

GO
/****** Object:  StoredProcedure [dbo].[KiemTraMaKHTonTai]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[KiemTraMaKHTonTai]
@MaKH Char(6)
AS 
BEGIN
	IF EXISTS (SELECT * FROM dbo.KHACHHANG WHERE RTRIM(LTRIM(ma)) = @MaKH)
		SELECT 1
		ELSE
        SELECT 0
END
GO
/****** Object:  StoredProcedure [dbo].[KiemTraMaLoaiTonTai]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[KiemTraMaLoaiTonTai]
@MaLoai Char(6)
AS 
BEGIN
	IF EXISTS (SELECT * FROM dbo.LOAITHUOC WHERE RTRIM(LTRIM(ma)) = @MaLoai)
		SELECT 1
		ELSE
        SELECT 0
END
GO
/****** Object:  StoredProcedure [dbo].[kiemTraMaLoTonTai]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[kiemTraMaLoTonTai]
@maLo NVARCHAR(6)
AS
BEGIN
	IF EXISTS (SELECT *FROM dbo.LOTHUOC WHERE ma = @maLo)
		SELECT 1
	ELSE
		SELECT 0
END

GO
/****** Object:  StoredProcedure [dbo].[KiemTraMaThuocTonTai]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[KiemTraMaThuocTonTai]
@MaThuoc NVARCHAR(10)
AS 
BEGIN
	IF EXISTS (SELECT * FROM dbo.THUOC WHERE RTRIM(LTRIM(ma)) = @MaThuoc)
		SELECT 1
		ELSE
        SELECT 0
END

GO
/****** Object:  StoredProcedure [dbo].[layDanhSachDonViTinhThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[layDanhSachDonViTinhThuoc]
@maThuoc CHAR(6), @xapXep bit
AS
SET NOCOUNT ON;
BEGIN
	DECLARE @maDVTCB CHAR(6) = (SELECT ma_donvitinhCB FROM dbo.THUOC WHERE ma = @maThuoc)
	DECLARE @timXong BIT = 0
	DECLARE @cap int = 0
	DECLARE @soLuongChua INT = 1
	DECLARE @maDVTtruyNguoc CHAR(6)
	DECLARE @tenDVT NVARCHAR(150)

	DECLARE @tableKq TABLE(
	ma CHAR(6) NOT NULL,
	ten NVARCHAR(150) NOT NULL,
	soluong INT NOT NULL,
	cap INT not NULL
	)

	-- luu don vi dau tien
	SET @tenDVT = (SELECT ten FROM dbo.DONVITINH WHERE ma = @maDVTCB)
	INSERT INTO @tableKq(ma,ten,soluong,cap)VALUES(@maDVTCB,@tenDVT,@soLuongChua,@cap)

	-- tang cap
	SET @cap += 1

	SET @maDVTtruyNguoc = @maDVTCB

WHILE @timXong = 0
BEGIN
	-- truy nguoc tu don vi co ban den don vi tim kiem
	IF EXISTS (SELECT ma_donvitinh1 FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi WHERE ma_thuoc = @maThuoc AND ma_donvitinh2 = @maDVTtruyNguoc) -- co don vi chua
	BEGIN
			-- lay ma don vi tiep theo
			SET @maDVTtruyNguoc = (SELECT ma_donvitinh1 FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi WHERE ma_thuoc = @maThuoc AND ma_donvitinh2 = @maDVTtruyNguoc)
			-- lay ten
			SET @tenDVT = (SELECT ten FROM dbo.DONVITINH WHERE ma = @maDVTtruyNguoc)
			-- lay so luong chua
			SET @soLuongChua *= (SELECT SoLuong FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi WHERE ma_thuoc = @maThuoc AND @maDVTtruyNguoc = ma_donvitinh1)
			-- luu don vi
			INSERT INTO @tableKq(ma,ten,soluong,cap)VALUES(@maDVTtruyNguoc,@tenDVT,@soLuongChua,@cap)
			-- tang cap
			SET @cap += 1
	END
	ELSE 
		SET @timXong = 1
END
	IF	@xapXep = 0
	SELECT ma, ten,soluong FROM  @tableKq ORDER BY cap ASC
	ELSE
    SELECT ma, ten,soluong FROM  @tableKq ORDER BY cap DESC

END

GO
/****** Object:  StoredProcedure [dbo].[layDongGoiThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[layDongGoiThuoc]
@maThuoc nvarchar(6)
AS
BEGIN
	SELECT dbo.DONGGOI.ID,dvt1.ten AS dvt1,dvt2.ten AS dvt2, dbo.DONGGOI.SoLuong FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi JOIN dbo.DONVITINH dvt1 ON dvt1.ma = DONVIDONGGOI.ma_donvitinh1 JOIN dbo.DONVITINH dvt2 ON dvt2.ma = dbo.DONVIDONGGOI.ma_donvitinh2
		WHERE dbo.DONGGOI.ma_thuoc = @maThuoc
END


GO
/****** Object:  StoredProcedure [dbo].[layLoaiThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[layLoaiThuoc]
@maLoai CHAR(6)
AS
BEGIN
SELECT * FROM LOAITHUOC WHERE ma = @maLoai
END
GO
/****** Object:  StoredProcedure [dbo].[layLoThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[layLoThuoc]
@maThuoc CHAR(6)
AS
BEGIN
SELECT lo.ma,DATEDIFF(DAY,GETDATE(),ngayhethan) AS HSD, SoLuong FROM dbo.LOTHUOC lo LEFT JOIN dbo.NHACUNGCAP ON NHACUNGCAP.ma = lo.ma_nhacungcap WHERE lo.ma_thuoc = @maThuoc
END
GO
/****** Object:  StoredProcedure [dbo].[layMaDonHangMoi]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[layMaDonHangMoi]
@tienTo NVARCHAR(6) , @loaibang int
as BEGIN

DECLARE @soDuoiFormat NVARCHAR(12)
DECLARE @max INT
DECLARE @columnLength TINYINT

IF @loaibang = 0
BEGIN
set @max = (SELECT MAX(CAST(SUBSTRING(ma,LEN(@tienTo)+1,LEN(ma)) AS INT)) FROM dbo.HOADONBAN WHERE  ma LIKE CONCAT(@tienTo,'%') AND ISNUMERIC(SUBSTRING(ma,LEN(@tienTo)+1,LEN(ma))) = 1)
set @columnLength = (select MAX(DATALENGTH(ma)) from dbo.HOADONBAN )
END
ELSE
BEGIN
set @max = (SELECT MAX(CAST(SUBSTRING(ma,LEN(@tienTo)+1,LEN(ma)) AS INT)) FROM dbo.HOADONNHAP WHERE  ma LIKE CONCAT(@tienTo,'%') AND ISNUMERIC(SUBSTRING(ma,LEN(@tienTo)+1,LEN(ma))) = 1)
set @columnLength = (select MAX(DATALENGTH(ma)) from dbo.HOADONNHAP )
end

IF @max is NULL
SET @max = 0

SET @max += 1
SET @soDuoiFormat = RIGHT('0000000' + CAST(@max AS NVARCHAR(7)), 7);
SELECT CONCAT(@tienTo,@soDuoiFormat)
END

GO
/****** Object:  StoredProcedure [dbo].[laySoLuongChiaTheoDonViTinh]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[laySoLuongChiaTheoDonViTinh]
@maThuoc CHAR(6),
@maDVTTimKiem CHAR(6)
AS
SET NOCOUNT ON;
BEGIN

DECLARE @maDVTCB CHAR(6) = (SELECT ma_donvitinhCB FROM dbo.THUOC WHERE ma = @maThuoc)
DECLARE @khopDonVi BIT = 0
DECLARE @tongChia INT = 1
DECLARE @maDVTtruyNguoc CHAR(6) = @maDVTCB

IF @maDVTtruyNguoc != @maDVTTimKiem
WHILE @khopDonVi = 0
BEGIN
	
	-- truy nguoc tu don vi co ban den don vi tim kiem
	IF EXISTS (SELECT ma_donvitinh1 FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi WHERE ma_thuoc = @maThuoc AND ma_donvitinh2 = @maDVTtruyNguoc) -- co don vi chua
	BEGIN
			-- lay ma don vi chua
			SET @maDVTtruyNguoc = (SELECT ma_donvitinh1 FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi WHERE ma_thuoc = @maThuoc AND ma_donvitinh2 = @maDVTtruyNguoc)
			-- lay so luong chua
			SET @tongChia *= (SELECT SoLuong FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi WHERE ma_thuoc = @maThuoc AND @maDVTtruyNguoc = ma_donvitinh1)
			IF @maDVTtruyNguoc = @maDVTTimKiem -- neu trung voi don vi dang tim thi dung loop
			SET @khopDonVi = 1
	END
	ELSE 
		SET @khopDonVi = 1
END

SELECT @tongChia AS soluong

END

GO
/****** Object:  StoredProcedure [dbo].[LaySoTrangTableThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[LaySoTrangTableThuoc]
 @ItemsPerPage int

 as
 begin
	declare @ItemCount int
	declare @SoLe int
	declare @SoTrang int

	set @ItemCount = (select Count(*) from thuoc)
	set @SoLe = @ItemCount%@ItemsPerPage
	set @SoTrang = @ItemCount/@ItemsPerPage

	if @SoLe > 0
		select @SoTrang + 1
		else
		select @SoTrang

 end

GO
/****** Object:  StoredProcedure [dbo].[LaySoTrangTimKiemKhachHang]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[LaySoTrangTimKiemKhachHang]
@Search nvarchar(150), @ItemsPerPage int
as 
begin
if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	declare @ItemCount int
	declare @SoLe int
	declare @SoTrang int = 0

	set @ItemCount = (SELECT count(*) from dbo.KHACHHANG where
	ten like @Search)

	set @SoLe = @ItemCount%@ItemsPerPage
	set @SoTrang = @ItemCount/@ItemsPerPage

	if @SoLe > 0
		select @SoTrang + 1 as SoTrang
		else
		select @SoTrang as SoTrang
END
GO
/****** Object:  StoredProcedure [dbo].[LaySoTrangTimKiemLoaiThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[LaySoTrangTimKiemLoaiThuoc]
@Search nvarchar(150), @ItemsPerPage int
as 
begin
if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	declare @ItemCount int
	declare @SoLe int
	declare @SoTrang int = 0

	set @ItemCount = (SELECT count(*) from dbo.LOAITHUOC where
	ten like @Search)

	set @SoLe = @ItemCount%@ItemsPerPage
	set @SoTrang = @ItemCount/@ItemsPerPage

	if @SoLe > 0
		select @SoTrang + 1 as SoTrang
		else
		select @SoTrang as SoTrang
end
GO
/****** Object:  StoredProcedure [dbo].[laySoTrangTimKiemLoThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[laySoTrangTimKiemLoThuoc]
@search NVARCHAR(150),@maKho NVARCHAR(6), @trangthai  INT, @ItemsPerPage  INT
AS
SET NOCOUNT ON;
BEGIN

	if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	if len(rtrim((ltrim(@maKho)))) = 0
		set @maKho = '%%'

	SET @search =  dbo.non_unicode_convert(@search)


	
	declare @ItemCount int
	declare @SoLe int
	declare @SoTrang int = 0

	IF @trangthai < 3
		set @ItemCount = (SELECT COUNT(*) FROM dbo.LOTHUOC JOIN dbo.THUOC ON THUOC.ma = LOTHUOC.ma_thuoc
							WHERE dbo.THUOC.ten LIKE @search AND ma_kho LIKE @maKho)
	ELSE
		set @ItemCount = (SELECT COUNT(*) FROM dbo.LOTHUOC JOIN dbo.THUOC ON THUOC.ma_kho = LOTHUOC.ma_thuoc
							WHERE dbo.THUOC.ten LIKE @search AND ma_kho LIKE @maKho AND DATEDIFF(DAY,GETDATE(),ngayhethan) <= 0)


	set @SoLe = @ItemCount%@ItemsPerPage
	set @SoTrang = @ItemCount/@ItemsPerPage

	if @SoLe > 0
		select @SoTrang + 1 as SoTrang
		else
		select @SoTrang as SoTrang
END

GO
/****** Object:  StoredProcedure [dbo].[LaySoTrangTimKiemNhaCungCap]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[LaySoTrangTimKiemNhaCungCap]
@Search nvarchar(150), @ItemsPerPage int
as 
begin
if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	declare @ItemCount int
	declare @SoLe int
	declare @SoTrang int = 0

	set @ItemCount = (SELECT count(*) from dbo.NHACUNGCAP where
	ten like @Search)

	set @SoLe = @ItemCount%@ItemsPerPage
	set @SoTrang = @ItemCount/@ItemsPerPage

	if @SoLe > 0
		select @SoTrang + 1 as SoTrang
		else
		select @SoTrang as SoTrang
END
GO
/****** Object:  StoredProcedure [dbo].[LaySoTrangTimKiemThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[LaySoTrangTimKiemThuoc]
@Search nvarchar(150), @MaLoai nvarchar(10), @MaKho nvarchar(10),  @ItemsPerPage int
as 
begin
if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	if len(rtrim((ltrim(@MaLoai)))) = 0
		set @MaLoai = '%%'

	if len(rtrim((ltrim(@MaKho)))) = 0
		set @MaKho = '%%'


	declare @ItemCount int
	declare @SoLe int
	declare @SoTrang int = 0

	set @ItemCount = (SELECT count(*) from thuoc where
	ten like @Search  
	and ma_loaithuoc like @MaLoai
	and ma_kho like @MaKho)

	set @SoLe = @ItemCount%@ItemsPerPage
	set @SoTrang = @ItemCount/@ItemsPerPage

	if @SoLe > 0
		select @SoTrang + 1 as SoTrang
		else
		select @SoTrang as SoTrang
end

GO
/****** Object:  StoredProcedure [dbo].[layTatCaDonViTinhThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[layTatCaDonViTinhThuoc]
@maThuoc char(6)
AS
BEGIN
	SELECT ma_donvitinh1 AS maDVT, ten AS tenDVT  FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi JOIN dbo.DONVITINH ON DONVITINH.ma = DONVIDONGGOI.ma_donvitinh1 WHERE ma_thuoc = @maThuoc
	UNION SELECT ma_donvitinh2, ten  FROM dbo.DONGGOI JOIN dbo.DONVIDONGGOI ON DONVIDONGGOI.ma = DONGGOI.ma_donvidonggoi JOIN dbo.DONVITINH ON DONVITINH.ma = DONVIDONGGOI.ma_donvitinh2 WHERE ma_thuoc = @maThuoc

END

GO
/****** Object:  StoredProcedure [dbo].[timDanhSachDonHang]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[timDanhSachDonHang]
@ngayBatDau CHAR(23), @ngayKetThuc CHAR(23) , @search NVARCHAR(50), @trangThai INT , @loaiBang int
AS
BEGIN
	IF @loaiBang = 0
	BEGIN
		SET @search =  dbo.non_unicode_convert(@search)
		SELECT dbo.HOADONBAN.ma, khachhang.ten AS tenkhachhang,nhanvien.ten AS tennhanvien,dbo.HOADONBAN.TongTien AS tongtien,dbo.HOADONBAN.GhiChu,dbo.HOADONBAN.TrangThai,dbo.HOADONBAN.ngaythang FROM dbo.HOADONBAN JOIN dbo.KHACHHANG ON khachhang.ma = dbo.HOADONBAN.ma_khachhang JOIN dbo.NHANVIEN ON dbo.HOADONBAN.ma_nhanvien = dbo.NHANVIEN.ma
		WHERE ( dbo.HOADONBAN.ngaythang BETWEEN CONVERT(DATETIME,@ngayBatDau) AND CONVERT(datetime,@ngayKetThuc)) AND ( dbo.non_unicode_convert (dbo.KHACHHANG.ten) LIKE @search or  dbo.non_unicode_convert(dbo.NHANVIEN.ten) LIKE @search) AND 
		dbo.HOADONBAN.TrangThai = CASE WHEN @trangThai < 0 THEN dbo.HOADONBAN.trangthai ELSE @trangThai END
	END
	ELSE
    BEGIN
	SET @search =  dbo.non_unicode_convert(@search)
		SELECT dbo.HOADONNHAP.ma, dbo.NHACUNGCAP.ten AS tennhacungcap,nhanvien.ten AS tennhanvien,dbo.HOADONNHAP.TongTien AS tongtien,dbo.HOADONNHAP.GhiChu,dbo.HOADONNHAP.ngaythang FROM dbo.HOADONNHAP JOIN dbo.NHACUNGCAP ON dbo.NHACUNGCAP.ma = dbo.HOADONNHAP.ma_nhacungcap JOIN dbo.NHANVIEN ON dbo.HOADONNHAP.ma_nhanvien = dbo.NHANVIEN.ma
		WHERE ( dbo.HOADONNHAP.ngaythang BETWEEN CONVERT(DATETIME,@ngayBatDau) AND CONVERT(datetime,@ngayKetThuc)) AND ( dbo.non_unicode_convert (dbo.NHACUNGCAP.ten) LIKE @search or  dbo.non_unicode_convert(dbo.NHANVIEN.ten) LIKE @search) 
    end
END

GO
/****** Object:  StoredProcedure [dbo].[TimKiemKhachHang]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[TimKiemKhachHang]
@Search nvarchar(150),
@PageNumber int, @ItemsPerPage int
as
BEGIN
	if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	SET @search =  dbo.non_unicode_convert(@Search)

	DECLARE @PageNumberSet AS INT
	DECLARE @ItemsPerPageSet AS INT
	SET @PageNumberSet = @PageNumber
	SET @ItemsPerPageSet = @ItemsPerPage

	SELECT * FROM dbo.KHACHHANG WHERE dbo.non_unicode_convert(ten) like @Search OR dbo.non_unicode_convert(diachi) LIKE @Search
	ORDER BY (SELECT NULL)
	OFFSET (@PageNumberSet-1)*@ItemsPerPageSet ROWS
	FETCH NEXT @ItemsPerPageSet ROWS ONLY

END
GO
/****** Object:  StoredProcedure [dbo].[TimKiemLoaiThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[TimKiemLoaiThuoc]
@Search nvarchar(150),
@PageNumber int, @ItemsPerPage int
as
BEGIN
	if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	SET @Search =  dbo.non_unicode_convert(@Search)

	DECLARE @PageNumberSet AS INT
	DECLARE @ItemsPerPageSet AS INT
	SET @PageNumberSet = @PageNumber
	SET @ItemsPerPageSet = @ItemsPerPage

	SELECT * FROM dbo.LOAITHUOC WHERE dbo.non_unicode_convert(ten) like @Search
	ORDER BY (SELECT NULL)
	OFFSET (@PageNumberSet-1)*@ItemsPerPageSet ROWS
	FETCH NEXT @ItemsPerPageSet ROWS ONLY

END
GO
/****** Object:  StoredProcedure [dbo].[timKiemLoThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[timKiemLoThuoc]
@search NVARCHAR(150),@maKho NVARCHAR(6), @trangthai  INT,@PageNumber  int, @ItemsPerPage  int
AS
SET NOCOUNT ON
BEGIN

	DECLARE @tableKq TABLE(
	MaLo CHAR(6) NOT NULL,
	MaSP CHAR(6) NOT NULL,
	TenSP NVARCHAR(150) NOT NULL,
	NgaySX DATE NOT NULL,
	HanSuDung DATE NOT NULL,
	hethan int NOT NULL,
	soluong INT NOT NULL,
	TenNCC NVARCHAR(150) NULL,
	GhiChu NVARCHAR(150) NULL
	)

	if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	if len(rtrim((ltrim(@maKho)))) = 0
		set @maKho = '%%'
	SET @search =  dbo.non_unicode_convert(@search)

	INSERT INTO @tableKq SELECT LOTHUOC.ma,LOTHUOC.ma_thuoc, dbo.THUOC.ten, ngaysanxuat, ngayhethan,DATEDIFF(DAY,GETDATE(),ngayhethan) AS hethan, SoLuong,dbo.LOTHUOC.ma_nhacungcap AS tenNCC, LOTHUOC.ghichu 
	FROM dbo.LOTHUOC JOIN dbo.THUOC ON THUOC.ma = LOTHUOC.ma_thuoc 
	WHERE dbo.non_unicode_convert(dbo.THUOC.ten) LIKE @search AND dbo.non_unicode_convert(ma_kho) LIKE @maKho

	ORDER BY (SELECT null)
	OFFSET (@PageNumber-1)*@ItemsPerPage ROWS
	FETCH NEXT @ItemsPerPage ROWS ONLY

	UPDATE @tableKq SET TenNCC = (SELECT ten FROM dbo.NHACUNGCAP WHERE [@tableKq].TenNCC = ma)

	IF @trangthai = 0
	SELECT * from @tableKq
	ELSE
		BEGIN
			IF @trangthai = 1
				SELECT * FROM @tableKq ORDER BY hethan DESC
			ELSE
				BEGIN
					IF @trangthai = 2
						SELECT * FROM @tableKq ORDER BY hethan ASC
					ELSE
						SELECT * FROM @tableKq WHERE hethan <= 0
				END
		END
END
GO
/****** Object:  StoredProcedure [dbo].[TimKiemNhaCungCap]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[TimKiemNhaCungCap]
@Search nvarchar(150),
@PageNumber int, @ItemsPerPage int
as
BEGIN
	if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	SET @search =  dbo.non_unicode_convert(@Search)

	DECLARE @PageNumberSet AS INT
	DECLARE @ItemsPerPageSet AS INT
	SET @PageNumberSet = @PageNumber
	SET @ItemsPerPageSet = @ItemsPerPage

	SELECT * FROM dbo.NHACUNGCAP WHERE dbo.non_unicode_convert(ten) like @Search
	ORDER BY (SELECT NULL)
	OFFSET (@PageNumberSet-1)*@ItemsPerPageSet ROWS
	FETCH NEXT @ItemsPerPageSet ROWS ONLY

END
GO
/****** Object:  StoredProcedure [dbo].[TimKiemThuoc]    Script Date: 12/7/2020 4:12:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[TimKiemThuoc]
@Search nvarchar(150), @MaLoai nvarchar(10), @MaKho nvarchar(10),
@PageNumber int, @ItemsPerPage int
as
BEGIN
	if len(rtrim((ltrim(@Search)))) = 0
		set @Search = '%%'

	if len(rtrim((ltrim(@MaLoai)))) = 0
		set @MaLoai = '%%'

	if len(rtrim((ltrim(@MaKho)))) = 0
		set @MaKho = '%%'
	SET @search =  dbo.non_unicode_convert(@search)

	DECLARE @PageNumberSet AS INT
	DECLARE @ItemsPerPageSet AS INT
	SET @PageNumberSet = @PageNumber
	SET @ItemsPerPageSet = @ItemsPerPage

	SELECT thuoc.ma,dbo.THUOC.ten,(SELECT SUM(SoLuong) FROM dbo.LOTHUOC WHERE ma = dbo.THUOC.ma) AS SoLuong, LOAITHUOC.ten AS TenLoai, DONVITINH.ten AS TenDVT ,thuoc.GiaBan, thuoc.GiaNhap 
	FROM thuoc JOIN dbo.LOAITHUOC ON LOAITHUOC.ma = THUOC.ma_loaithuoc JOIN dbo.DONVITINH ON DONVITINH.ma = THUOC.ma_donvitinhCB where
	dbo.non_unicode_convert(thuoc.ten) like @Search  
	and dbo.non_unicode_convert(thuoc.ma_loaithuoc) like @MaLoai
	and dbo.non_unicode_convert(thuoc.ma_kho) like @MaKho

	ORDER BY (SELECT NULL)
	OFFSET (@PageNumberSet-1)*@ItemsPerPageSet ROWS
	FETCH NEXT @ItemsPerPageSet ROWS ONLY

end
GO
