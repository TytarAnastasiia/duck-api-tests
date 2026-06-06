package autotests.payloads.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors (fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DuckSwimResponse {

    @JsonInclude
    private String message;

}
