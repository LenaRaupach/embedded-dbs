package de.lraupach.embeddeddbs.main;

import de.lraupach.embeddeddbs.core.SQLiteTest;
import de.lraupach.embeddeddbs.model.Drug;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger("Main");
    public static void main(String[] args) {
        Drug warfarin = new Drug("Warfarin", "81-81-2");

        SQLiteTest sqlite = new SQLiteTest();
        sqlite.addDrug(warfarin);
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
