package be.faros.testing.tapasapp.system;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

@Component
public class TestDatabaseManager {

  private DataSource dataSource;

  public TestDatabaseManager(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void clearDatabase() throws SQLException {
    dataSource.getConnection().prepareStatement("DROP ALL OBJECTS").execute();
  }
}
