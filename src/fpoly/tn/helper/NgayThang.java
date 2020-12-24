package fpoly.tn.helper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class NgayThang {
    
    public static int Ngay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }
    
    public static int Thang(){
         Calendar calendar = Calendar.getInstance();
         return calendar.get(Calendar.MONTH) + 1;
    }
    
    public static int Nam(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
    
    public static String ThuTrongTuan(){
        Calendar calendar = Calendar.getInstance();
        int n_thu = calendar.get(Calendar.DAY_OF_WEEK);
        String s_thu = "";
        switch(n_thu){
            
            case 1: s_thu = "Chủ Nhật"; 
                break;
            case 2: s_thu = "Thứ Hai"; 
                break;
                case 3: s_thu = "Thứ Ba"; 
                break;
                case 4: s_thu = "Thứ Tư"; 
                break;
                case 5: s_thu = "Thứ Năm"; 
                break;
                case 6: s_thu = "Thứ Sáu"; 
                break;
                case 7: s_thu = "Thứ Bảy"; 
                break;
            default:
                break;
        }
        return s_thu;
    }

    public static long layKhoangCachNgay(String ngayA, String ngayB, String formatNgay) {
        long soNgay;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatNgay);
        LocalDate date1 = LocalDate.parse(ngayA, dtf);
        LocalDate date2 = LocalDate.parse(ngayB, dtf);
        soNgay = ChronoUnit.DAYS.between(date1, date2);
        return soNgay;
    }

    public static String doiDinhDangNgay(String date, String FormatHienTai, String FormatMoi) {
        SimpleDateFormat formatmoi = new SimpleDateFormat(FormatMoi);
        SimpleDateFormat formatcu = new SimpleDateFormat(FormatHienTai);
        String ngay = date;
        try {
            ngay = formatmoi.format(formatcu.parse(date));
        } catch (ParseException e) {
            System.out.println("L\u1ed7i \u0111\u1ed5i ng\u00e0y : " + e.getMessage());
        }
        return ngay;
    }

    public static String layNgayThangNamFormat(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = new Date();
        return df.format(date);
    }
    
    public static String layNgayThangNamFormat(Date date,String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date2 = new Date();
        date2.setTime(date.getTime());
        return df.format(date2);
    }
    
    public static Date layNgayTuTimestamp(Timestamp stamp){
        Date date = new Date(stamp.getTime());
        return date;
    }
    
    
}
