package de.lraupach.embeddeddbs.main;

import de.lraupach.embeddeddbs.core.Neo4jExample;
import de.lraupach.embeddeddbs.core.SQLiteExample;
import de.lraupach.embeddeddbs.model.Drug;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger("Main");
    public static void main(String[] args) {
        Drug warfarin = new Drug("Warfarin", "81-81-2");


        printSQLiteEntries(warfarin);
        Neo4jExample neo4jTest = new Neo4jExample();
        neo4jTest.addDrug(warfarin);
        neo4jTest.displayDrugs();

    }

    private static void printSQLiteEntries(Drug drug) {
        SQLiteExample sqlite = new SQLiteExample();
        sqlite.addDrug(drug);
        ResultSet rs;
        try {
            rs = sqlite.displayUsers();
            if (rs != null) {
                while (rs.next()) {
                    LOGGER.info(rs.getString("name") + " " + rs.getString("cas"));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Failed to find drug entries. " + e);
        }
    }
}
