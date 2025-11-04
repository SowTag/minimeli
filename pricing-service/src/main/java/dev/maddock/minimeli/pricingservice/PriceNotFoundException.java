package dev.maddock.minimeli.pricingservice;

import java.util.UUID;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(UUID productId) {
        super(String.format("Price not found for product UUID %s", productId));
    }
}
