package dev.maddock.minimeli.pricingservice;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
class PricingService {

    private final Map<UUID, BigDecimal> prices = new HashMap<>();

    public BigDecimal getFinalPrice(UUID productId) {
        if (prices.containsKey(productId)) return prices.get(productId);

        throw new PriceNotFoundException(productId);
    }

    public void setBasePrice(UUID productId, BigDecimal basePrice) {
        prices.put(productId, basePrice);
    }

}
