package ro.nila.ra.payload;

import com.fasterxml.jackson.annotation.JsonView;
import ro.nila.ra.model.view.Views;

public class ApiResponse<T extends Object> {

    private Boolean success;
    private T t;
    private String resourceLocation;

    public ApiResponse(Boolean success, T t, String resourceLocation) {
        this.success = success;
        this.t = t;
        this.resourceLocation = resourceLocation;
    }

    @JsonView(Views.WithoutRole.class)
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonView(Views.WithoutRole.class)
    public T getObject() {
        return t;
    }

    public void setObject(T t) {
        this.t = t;
    }

    @JsonView(Views.WithoutRole.class)
    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
}
