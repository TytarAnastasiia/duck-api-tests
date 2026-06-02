package autotests.tests;

import autotests.clients.DuckActionClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckDeleteTest extends DuckActionClient {

    @Test(description = "Проверка успешного удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckDelete(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"message\": \"Duck is deleted\"}");
    }
}