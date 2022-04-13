package de.lraupach.embeddeddbs.core;

import de.lraupach.embeddeddbs.model.Drug;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.logging.Logger;


public class Neo4jExample implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger("Neo4jExample");
    private static Driver driver;
    private static GraphDatabaseFactory graphDbFactory;
    private static GraphDatabaseService graphDb;

    public Neo4jExample() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.none());
        graphDbFactory = new GraphDatabaseFactory();
        graphDb = graphDbFactory.newEmbeddedDatabase(new File("neo4j.db"));

    }

    public static void addDrug(Drug drug) {
        graphDb.beginTx();
        Node node = graphDb.createNode(Label.label("Drug"));
        node.setProperty("cas", drug.getCas());
        node.setProperty("name", drug.getName());
    }

    public static void displayDrugs() {
        Result result = graphDb.execute(
                "MATCH (d:Drug) RETURN d.cas, d.name");
        while (result.hasNext()) {
            LOGGER.info(result.next().toString());
        }

    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}
