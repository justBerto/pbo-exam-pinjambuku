package pinjamnovel;
import java.sql.*;
import javax.swing.text.*;

public class init {

    public String host = "localhost";
    public String user = "root";
    public String pswd = "";
    public String db = "pinjambuku";
    public String url = "";
    
    Connection conn = null;
    
    public void Connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://" + host + "/" + db + "?user=" + user + "&password=" + pswd;
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver tidak ditemukan " + e.toString());
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal " + e.toString());
        }
    }
    
    public Object[][] getData(Object[][] object, String query, int col) {
        try {
            Connection();
            Statement st = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(query);
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            object = new Object[rowCount][col]; 
            int no = -1;
            while (rs.next()) {
                no = no + 1;
                Object[] row = new Object[col];
                for (int i = 0; i < row.length; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                object[no] = row;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        return object;
    }   
    
    public Boolean cek(String query, String jTexfield) {
        boolean cek = false;
        try {
            Connection();

            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setString(1, jTexfield);
            ResultSet rs = pStatement.executeQuery();

            if (rs.next()) {
                cek = false;
            } else {
                cek = true;
            }
            pStatement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error " + e.toString());
        }
        return cek;
    }

    static class JTextFieldLimit extends PlainDocument implements Document {
        private int limit;
        public JTextFieldLimit(int limit) {
           super();
           this.limit = limit; 
        }
        JTextFieldLimit(int limit, boolean upper) {
           super();
           this.limit = limit;
        }
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
           if (str == null)
              return;
           if ((getLength() + str.length()) <= limit) {
              super.insertString(offset, str, attr);
           }
        }
    }   
}


