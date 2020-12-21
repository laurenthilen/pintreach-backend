package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.ValidationError;

import java.util.List;

public interface HelperFunctions
{
    /**
     * Searches to see if the exception has any constraint violations to report
     *
     * @param cause the exception to search
     * @return constraint violations formatted for sending to the client
     */
    List<ValidationError> getConstraintViolation(Throwable cause);

}
