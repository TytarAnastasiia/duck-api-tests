package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class DuckCreateTest extends DuckUtils {

    @Test(description = "Проверка успешного создания утки material=rubber")
    @CitrusTest
    public void successfulCreateRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .type(MessageType.JSON)
                        .validate(jsonPath().expression("$.color", "yellow"))
                        .validate(jsonPath().expression("$.height", "5.0"))
                        .validate(jsonPath().expression("$.material", "rubber"))
                        .validate(jsonPath().expression("$.sound", "quack"))
                        .validate(jsonPath().expression("$.wingsState", "ACTIVE"))
        );
    }

    @Test(description = "Проверка успешного создания утки material=wood")
    @CitrusTest
    public void successfulCreateWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "wood", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .type(MessageType.JSON)
                        .validate(jsonPath().expression("$.color", "yellow"))
                        .validate(jsonPath().expression("$.height", "5.0"))
                        .validate(jsonPath().expression("$.material", "wood"))
                        .validate(jsonPath().expression("$.sound", "quack"))
                        .validate(jsonPath().expression("$.wingsState", "ACTIVE"))
        );
    }
}