
package fpoly.tn.dto;

public class MyComboBox {

    private String display;
    private String value;

    public MyComboBox(String value, String display) {
        this.display = display;
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public String getValue() {
        return value;
    }

    // Ghi đè hàm toString, combobox sẽ hiển thị thông tin trả về
    @Override
    public String toString() {
        return display;
    }
}
