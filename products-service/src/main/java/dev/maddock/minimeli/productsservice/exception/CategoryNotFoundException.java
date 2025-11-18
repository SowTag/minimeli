package dev.maddock.minimeli.productsservice.exception;

import dev.maddock.minimeli.productsservice.enumeration.ErrorCode;
import dev.maddock.minimeli.servicecommons.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class CategoryNotFoundException extends ApiException {
    public CategoryNotFoundException(UUID categoryId) {
        super(HttpStatus.NOT_FOUND, ErrorCode.CATEGORY_NOT_FOUND, String.format("Category with ID %s not found", categoryId));
    }
}
