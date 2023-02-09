import java.text.DecimalFormat;

public class decimalformat {
    public static void main(String[] args) {
        int intValue;
        String toString;
        
        DecimalFormat df = new DecimalFormat("000");
        
        for (int i = 0; i < 100; i++) {
            intValue = i;
            toString = df.format(intValue);
            System.out.println(toString);
        }
    }
}