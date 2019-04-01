package ro.nila.ra.exceptions;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.nila.ra.model.view.Views;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceName = message;
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
        this.resourceName = message;

    }

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {

        super(String.format("%s not fould with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


    @JsonView(Views.WithoutRole.class)
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
