package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckFlyTest extends DuckUtils {

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id)
        );
    }

    @Test(description = "Проверка полёта утки с wingsState=FIXED")
    @CitrusTest
    public void successfulFlyFixed(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "FIXED");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\"message\": \"I can not fly :C\"}");
    }

    @Test(description = "Проверка полёта утки с wingsState=ACTIVE")
    @CitrusTest
    public void successfulFlyActive(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\"message\": \"I am flying :)\"}");
    }

    @Test(description = "Попытка полёта несуществующей утки")
    @CitrusTest
    public void flyNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String nonExistentId = "9999";
        duckFly(runner, nonExistentId);
        validateErrorResponse(runner, HttpStatus.INTERNAL_SERVER_ERROR, "Duck with id = " + nonExistentId + " is not found");;
    }
}