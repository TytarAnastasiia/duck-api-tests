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

    @Test(description = "Проверка успешного обновления цвета и высоты утки (color/height)")
    @CitrusTest
    public void successfulUpdateColorHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckUpdate(runner, "${duckId}", "green", 10, "rubber", "quack", "ACTIVE");
        validateResponse(runner, "{\"message\": \"Duck with id = ${duckId} is updated\"}");
    }

    @Test(description = "Проверка успешного обновления цвета и звука утки (color/sound)")
    @CitrusTest
    public void successfulUpdateColorSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckUpdate(runner, "${duckId}", "green", 5, "rubber", "quuaack", "ACTIVE");
        validateResponse(runner, "{\"message\": \"Duck with id = ${duckId} is updated\"}");
    }
}