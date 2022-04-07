import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {

        SQLiteTest test = new SQLiteTest();
        ResultSet rs;

        try {
            rs = test.displayUsers();

            while (rs.next()) {
                System.out.println(rs.getString("fname") + " " + rs.getString("lname"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
