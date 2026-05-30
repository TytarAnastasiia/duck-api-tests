package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckQuackTest extends DuckUtils {

    public void duckQuack(TestCaseRunner runner, String id, int repetitionCount, int soundCount) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/quack")
                        .queryParam("id", id)
                        .queryParam("repetitionCount", String.valueOf(repetitionCount))
                        .queryParam("soundCount", String.valueOf(soundCount))
        );
    }

    @Test(description = "Проверка кряканья утки (repetitionCount=3, soundCount=2)")
    @CitrusTest
    public void successfulQuack(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckQuack(runner, "${duckId}", 3, 2);
        validateResponse(runner, "{\"sound\": \"quack-quack, quack-quack, quack-quack\"}");
    }

    @Test(description = "Попытка кряканья несуществующей утки")
    @CitrusTest
    public void quackNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String nonExistentId = "9999";
        duckQuack(runner, nonExistentId, 3, 2);
        validateErrorResponse(runner, HttpStatus.INTERNAL_SERVER_ERROR, "Duck with id = " + nonExistentId + " is not found");
    }
}