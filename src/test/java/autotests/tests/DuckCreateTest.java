package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.request.DuckCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class DuckCreateTest extends DuckActionClient {

    @Test(description = "Проверка успешного создания утки material=rubber")
    @CitrusTest
    public void successfulCreateRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, properties);
        validateResponseResources(runner, "createdTest/DuckPropertiesResponse.json");
        runner.$(
                http()
                        .client(duckService)
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

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, properties);
        runner.$(
                http()
                        .client(duckService)
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