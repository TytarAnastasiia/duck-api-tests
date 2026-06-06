package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.request.DuckCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckFlyTest extends DuckActionClient {

    @Test(description = "Проверка полёта утки с wingsState=FIXED")
    @CitrusTest
    public void successfulFlyFixed(@Optional @CitrusResource TestCaseRunner runner) {

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, properties);
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"I can not fly :C\"}");
    }

    @Test(description = "Проверка полёта утки с wingsState=UNDEFINED")
    @CitrusTest
    public void successfulFlyUndefined(@Optional @CitrusResource TestCaseRunner runner) {

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("rubber")
                .sound("quack")
                .wingsState("UNDEFINED");

        createDuck(runner, properties);
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Wings are not detected :(\"}");
    }

    @Test(description = "Проверка полёта утки с wingsState=ACTIVE")
    @CitrusTest
    public void successfulFlyActive(@Optional @CitrusResource TestCaseRunner runner) {

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, properties);
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"I am flying :)\"}");
    }
}