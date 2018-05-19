package be.faros.testing.tapaseater.gateway;

import be.faros.testing.tapaseater.gateway.dto.Tapas;
import be.faros.testing.tapaseater.gateway.dto.TapasOrder;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Component
public class TapasAppGatewayImpl implements TapasAppGateway {

  private static final String BASEPATH = "http://localhost:8080";
  private static final String BASKET = "/basket";

  private final RestTemplate restTemplate;

  public TapasAppGatewayImpl() {
    this.restTemplate = new RestTemplate();
    this.restTemplate.setErrorHandler(new ResponseErrorHandler() {
      @Override
      public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        boolean hasError = false;
        int rawStatusCode = clientHttpResponse.getRawStatusCode();
        if (rawStatusCode != 200) {
          hasError = true;
        }
        return hasError;
      }

      @Override
      public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        final String errorMessage = String.format("%s, %s",
            clientHttpResponse.getRawStatusCode(),
            StreamUtils.copyToString(clientHttpResponse.getBody(), Charset.defaultCharset())
        );

        throw new IllegalArgumentException(errorMessage);
      }
    });
  }

  @Override
  public List<Tapas> getAvailableTapas() {
    return restTemplate.exchange(BASEPATH + "/tapas", HttpMethod.GET, null, new ParameterizedTypeReference<List<Tapas>>() {
    }).getBody();
  }

  @Override
  public Integer createNewBasket() {
    return restTemplate.exchange(BASEPATH + BASKET, HttpMethod.POST, null, Integer.class).getBody();
  }

  @Override
  public List<TapasOrder> retrieveListOfAllTapasOrdersInBasket(int basketId) {
    return restTemplate.exchange(BASEPATH + BASKET + "/" + basketId, HttpMethod.GET, null, new ParameterizedTypeReference<List<TapasOrder>>() {
    }).getBody();
  }

  @Override
  public void addNewTapasOrderToBasket(int basketId, String tapasId, int amount) {
    restTemplate.put(BASEPATH + BASKET + "/" + basketId + "?tapasId=" + tapasId + "&amount=" + amount, null);
  }

  @Override
  public BigDecimal calculateCostForBasket(int basketId) {
    return restTemplate.exchange(BASEPATH + BASKET + "/" + basketId + "/totalCost", HttpMethod.GET, null, BigDecimal.class).getBody();
  }

}
