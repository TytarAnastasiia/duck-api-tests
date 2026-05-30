package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckUpdateTest extends DuckUtils {

    public void duckUpdate(TestCaseRunner runner, String id, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .put("/api/duck/update")
                        .queryParam("id", id)
                        .queryParam("color", color)
                        .queryParam("height", String.valueOf(height))
                        .queryParam("material", material)
                        .queryParam("sound", sound)
                        .queryParam("wingsState", wingsState)
        );
    }

    @Test(description = "Проверка успешного обновления утки")
    @CitrusTest
    public void successfulUpdateDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckUpdate(runner, "${duckId}", "green", 10, "wood", "QUACK", "FIXED");
        validateResponse(runner, "{\"message\": \"Duck with id = ${duckId} is updated\"}");
    }

    @Test(description = "Попытка обновления несуществующей утки")
    @CitrusTest
    public void updateNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String nonExistentId = "9999";
        duckUpdate(runner, nonExistentId, "red", 10, "wood", "QUACK", "FIXED");
        validateErrorResponse(runner, HttpStatus.INTERNAL_SERVER_ERROR, "Duck with id = " + nonExistentId + " is not found");
    }
}