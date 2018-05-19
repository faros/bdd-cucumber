package be.faros.testing.tapasapp;

import static org.junit.Assert.fail;

import be.faros.testing.tapasapp.catalogue.domain.usecases.CatalogueSearching;
import be.faros.testing.tapasapp.store.domain.usecases.UserBasketManagement;
import be.faros.testing.tapasapp.system.TestDatabaseManager;
import java.sql.SQLException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO 02 (Let's read: Setting up the Spring Application Context for our tests)
 * This is the base class used for our Step Definition files. It sets up our Spring Application Context, and injects the required dependencies
 * to be able to do the tests we want on the system. Also note the "clearDatabase" method that will be used to clean up the database between
 * tests, so they don't get into each other's way.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration // Magic. Don't ask :-)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DirtiesContext
public class BaseCucumberTest {

  @Autowired
  protected CatalogueSearching catalogueSearching;
  @Autowired
  protected UserBasketManagement userBasketManagement;

  @Autowired
  protected TestDatabaseManager databaseManager;

  protected void clearDatabase() {
    try {
      databaseManager.clearDatabase();
    } catch (SQLException e) {
      fail();
    }
  }
}


