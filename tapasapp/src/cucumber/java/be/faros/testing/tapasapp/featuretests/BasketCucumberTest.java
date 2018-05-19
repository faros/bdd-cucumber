package be.faros.testing.tapasapp.featuretests;

import static org.assertj.core.api.Assertions.assertThat;

import be.faros.testing.tapasapp.BaseCucumberTest;
import be.faros.testing.tapasapp.store.domain.usecases.dto.TapasOrder;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.math.BigDecimal;

/**
 * TODO 03 (Let's read: Our Steps Definitions File)
 * A steps definition file is used to give a technical implementation to the steps that Cucumber will be doing during our tests.
 * Methods will be added here so when using the correct annotations, we can bind them to Cucumber steps.
 */
public class BasketCucumberTest extends BaseCucumberTest {

  /**
   * TODO 06 Uncomment the following methods, so they can be used as Step Definitions. Try running your test again after.
   */
  @When("^the user creates a new Basket$")
  public void theUserCreatesANewBasket() {
    userBasketManagement.createNewBasket();
  }

  @When("^the user adds (-?\\d+) Tapas with id (\\d+) to the Basket with id (\\d+)$")
  public void theUserAddsTapasWithIdToTheBasketWithId(int numberTapas, String tapasId, int basketId) {
    userBasketManagement.changeTapasOrderInBasket(basketId, new TapasOrder(tapasId, numberTapas));
  }

  @Then("^the total number of items in the Basket with id (\\d+) equals (\\d+)$")
  public void theTotalNumberOfItemsInTheBasketWithIdEquals(int basketId, int totalNumber) {
    assertThat(userBasketManagement.retrieveListOfAllTapasOrdersInBasket(basketId).stream().map(to -> to.getAmount()).reduce(0L, Long::sum))
        .isEqualTo(totalNumber);
  }

  @Then("^the number of items with id (\\d+) in the Basket with id (\\d+) equals (\\d+)$")
  public void theNumberOfDifferentItemsInTheBasketWithIdEquals(String tapasId, int basketId, int amount) {
    assertThat(
        userBasketManagement.retrieveListOfAllTapasOrdersInBasket(basketId)
            .stream()
            .filter(to -> to.getTapasId().equals(tapasId))
            .map(to -> to.getAmount()).findFirst().orElse(0L)
    ).isEqualTo(amount);
  }

  @Then("^the Total Cost for all the Tapas in the Basket with id (\\d+) equals (\\d+)$")
  public void theTotalCostForAllTheTapasInTheBasketEquals(int basketId, int totalCost) {
    assertThat(userBasketManagement.calculateCostForBasket(basketId)).isEqualTo(BigDecimal.valueOf(totalCost));
  }

  @After
  public void clear() {
    super.clearDatabase();
  }
}
