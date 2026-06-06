package autotests.payloads.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors (fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DuckCreate {

    @JsonInclude
    private String color;
    @JsonInclude
    private Double height;
    @JsonInclude
    private String material;
    @JsonInclude
    private String sound;
    @JsonInclude
    private String wingsState;

}
