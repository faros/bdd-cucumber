package be.faros.testing.tapasapp.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.faros.testing.tapasapp.store.domain.internal.entity.BasketEntity;
import be.faros.testing.tapasapp.store.domain.internal.repository.BasketRepository;
import be.faros.testing.tapasapp.store.domain.internal.service.BasketService;
import be.faros.testing.tapasapp.store.domain.internal.service.impl.BasketServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class BasketServiceImplTest {

  private BasketService t;
  private BasketRepository basketRepositoryMock;

  @BeforeEach
  public void init() {
    basketRepositoryMock = mock(BasketRepository.class);

    t = new BasketServiceImpl(basketRepositoryMock);
  }

  @Nested
  @DisplayName("BasketRetrievalFeature")
  class BasketRetrievalFeature {

    @Test
    void whenBasketNotFoundInRepositoryAnIllegalArgumenExceptionIsThrown() {
      final int basketId = 123;

      when(basketRepositoryMock.findById(eq(basketId))).thenReturn(Optional.empty());

      assertThrows(IllegalArgumentException.class, () -> t.getExpectedBasket(basketId));
    }

    @Test
    public void basketCanBeRetrievedFromRepository() {
      final int basketId = 123;
      final BasketEntity basketEntity = new BasketEntity();

      when(basketRepositoryMock.findById(eq(basketId))).thenReturn(Optional.of(basketEntity));

      assertNotNull(t.getExpectedBasket(basketId));
    }
  }

  @Nested
  @DisplayName("Generic Tests")
  class GenericTests {

    @DisplayName("Times two multiplying")
    @ParameterizedTest(name = "{0} times 2 should be {1}")
    @CsvSource({"1, 2", "2, 4", "4, 8"})
    void numbersShouldBeMultipliedByTwo(int firstNumber, int secondNumber) {
      assertEquals(secondNumber, firstNumber * 2);
    }

    @DisplayName("Multiple assertions")
    @Test
    void multiAssertions() {
      int[] numbers = {0, 1, 2, 3, 4};
      Assertions.assertAll("Assumed Numbers",
          () -> assertEquals(numbers[0], 0),
          () -> assertEquals(numbers[2], 2),
          () -> assertEquals(numbers[4], 4)
      );
    }
  }
}
