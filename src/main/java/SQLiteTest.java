import java.sql.*;

public class SQLiteTest {
        private static Connection con;
        private static boolean hasData = false;

    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if(con == null) {
            // get connection
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("select fname, lname from user");
        return res;
    }

    public void addUser(String firstname, String lastname) throws ClassNotFoundException, SQLException {
        if(con == null) {
            // get connection
            getConnection();
        }
        PreparedStatement prep = con
                .prepareStatement("insert into user values(?,?,?);");
        prep.setString(2, firstname);
        prep.setString(3, lastname);
        prep.execute();

    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        // sqlite driver
        Class.forName("org.sqlite.JDBC");
        // database path, if it's new database, it will be created in the project folder
        con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
        initialise();
    }

    private void initialise() throws SQLException {
        if( !hasData ) {
            hasData = true;
            // check for database table
            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
            if( !res.next()) {
                System.out.println("Building the User table with prepopulated values.");
                // need to build the table
                Statement state2 = con.createStatement();
                state2.executeUpdate("create table user(id integer,"
                        + "fName varchar(60)," + "lname varchar(60)," + "primary key (id));");

                // inserting some sample data
                PreparedStatement prep = con.prepareStatement("insert into user values(?,?,?);");
                prep.setString(2, "John");
                prep.setString(3, "McNeil");
                prep.execute();

                PreparedStatement prep2 = con.prepareStatement("insert into user values(?,?,?);");
                prep2.setString(2, "Paul");
                prep2.setString(3, "Smith");
                prep2.execute();
            }

        }
    }
}
