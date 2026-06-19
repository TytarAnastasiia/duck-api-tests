package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.response.DuckDeleteResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты duck-controller")
@Feature("Удаление уточки")
public class DuckDeleteTest extends DuckActionClient {

    @Test(description = "Проверка успешного удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1234");

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckDelete(runner, "${duckId}");

        DuckDeleteResponse expectedResponse = new DuckDeleteResponse()
                .message("Duck is deleted");

        validateResponsePayloads(runner, HttpStatus.OK, expectedResponse);
        validateDuckIsDeletedInDb(runner);
    }
}