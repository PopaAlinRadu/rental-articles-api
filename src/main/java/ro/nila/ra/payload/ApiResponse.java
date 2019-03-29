package ro.nila.ra.payload;

import com.fasterxml.jackson.annotation.JsonView;
import ro.nila.ra.model.view.Views;

public class ApiResponse<T extends Object> {

    private Boolean success;
    private T data;
    private String resourceLocation;

    public ApiResponse(Boolean success, T data, String resourceLocation) {
        this.success = success;
        this.data = data;
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
    public T getData() {
        return data;
    }

    public void setData(T t) {
        this.data = t;
    }

    @JsonView(Views.WithoutRole.class)
    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
}
