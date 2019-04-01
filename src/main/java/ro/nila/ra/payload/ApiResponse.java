package ro.nila.ra.payload;

import com.fasterxml.jackson.annotation.JsonView;
import ro.nila.ra.model.view.Views;

public class ApiResponse<T> {

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

    @JsonView(Views.WithoutRole.class)
    public T getData() {
        return data;
    }
    
    @JsonView(Views.WithoutRole.class)
    public String getResourceLocation() {
        return resourceLocation;
    }

}
