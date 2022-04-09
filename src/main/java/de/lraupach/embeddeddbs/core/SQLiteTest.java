package de.lraupach.embeddeddbs.core;

import java.sql.*;
import java.util.logging.Logger;

public class SQLiteTest {
    private static final Logger LOGGER = Logger.getLogger("SQLiteTest");
    static final String QUERY_CREATE_TABLE_DRUG = "CREATE TABLE drug(id integer, name varchar(60), cas varchar(60), " +
            "primary key (id));";
    static final String QUERY_DROP_TABLE_DRUG = "DROP TABLE drug;";
    static final String QUERY_SELECT_TABLE_DRUG = "SELECT * FROM drug";
    static final String QUERY_INSERT_TABLE_DRUG = "insert into drug values(?,?,?);";
    private static Connection con;
    private static boolean hasData = false;

    public SQLiteTest() {
        getConnection();
        addDrug("Warfarin", "81-81-2");
    }

    public static ResultSet displayUsers() {
        ResultSet res = null;
        try {
            Statement state = con.createStatement();
           res = state.executeQuery(QUERY_SELECT_TABLE_DRUG);
           return res;
        } catch (Exception e) {
            LOGGER.severe("Failed to get drugs from drug table.");
        }
        return res;
    }

    public static void addDrug(String drugName, String cas) {
        try (PreparedStatement prep = con
                .prepareStatement(QUERY_INSERT_TABLE_DRUG)) {
            prep.setString(2, drugName);
            prep.setString(3, cas);
            prep.execute();
        } catch (SQLException e) {
            LOGGER.severe("Failed to execute SQLite statement. " + e);
        }
    }

    private static void getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:test.db");
            initialise();
        } catch (SQLException e) {
            LOGGER.severe("Failed to get SQLite connection. " + e);
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Failed to find SQLite class org.sqlite.JDBC. " + e);
        }
    }

    private static void initialise() throws SQLException {
        if( !hasData ) {
            hasData = true;
            try (Statement state = con.createStatement()) {
                state.execute(QUERY_DROP_TABLE_DRUG);
                LOGGER.info("Building the Drug table.");
            }
            try (Statement state = con.createStatement();) {
                state.executeUpdate(QUERY_CREATE_TABLE_DRUG);
            } catch (Exception e) {
                LOGGER.severe("Failed to create table 'drug'. " + e);
            }
        }
    }
}
