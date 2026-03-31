package dev.boog.moneyloverdatamanager.exceptions.customexceptions;

import dev.boog.moneyloverdatamanager.utils.Constants;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super(Constants.Messages.RESOURCE_NOT_FOUND);
    }
}
