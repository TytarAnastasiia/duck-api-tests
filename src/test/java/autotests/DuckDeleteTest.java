package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckDeleteTest extends DuckUtils {

    public void duckDelete(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id)
        );
    }

    @Test(description = "Проверка успешного удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckDelete(runner, "${duckId}");
        validateResponse(runner, "{\"message\": \"Duck is deleted\"}");
    }

    @Test(description = "Попытка удаления несуществующей утки")
    @CitrusTest
    public void deleteNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String nonExistentId = "9999";
        duckDelete(runner, nonExistentId);
        validateErrorResponse(runner, HttpStatus.INTERNAL_SERVER_ERROR, "No class ru.cft.shift.qa.duck.model.entity.Duck entity with id " + nonExistentId + " exists!");
    }

    @Test(description = "Попытка повторного удаления уже удалённой утки")
    @CitrusTest
    public void deleteAlreadyDeletedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "ACTIVE");
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckDelete(runner, "${duckId}");
        validateResponse(runner, "{\"message\": \"Duck is deleted\"}");
        duckDelete(runner, "${duckId}");
        validateErrorResponse(runner, HttpStatus.INTERNAL_SERVER_ERROR, "No class ru.cft.shift.qa.duck.model.entity.Duck entity with id ${duckId} exists!");
    }
}