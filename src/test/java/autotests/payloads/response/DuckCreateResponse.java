package autotests.payloads.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors (fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DuckCreateResponse {

    @JsonInclude
    private String color;
    @JsonInclude
    private Double height;
    @JsonInclude
    private Long id;
    @JsonInclude
    private String material;
    @JsonInclude
    private String sound;
    @JsonInclude
    private String wingsState;

}
