package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.response.DuckUpdateResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-controller")
@Feature("Обновление свойств уточки")
public class DuckUpdateTest extends DuckActionClient {

    @Test(description = "Проверка успешного обновления цвета и высоты утки (color/height)")
    @CitrusTest
    public void successfulUpdateColorHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1234");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckUpdate(runner, "${duckId}", "green", 10, "rubber", "quack", "ACTIVE");

        DuckUpdateResponse expectedResponse = new DuckUpdateResponse()
                .message("Duck with id = ${duckId} is updated");

        validateResponsePayloads(runner, HttpStatus.OK, expectedResponse);
        validateDuckInDatabase(runner, "${duckId}", "green", "10.0", "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Проверка успешного обновления цвета и звука утки (color/sound)")
    @CitrusTest
    public void successfulUpdateColorSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1234");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckUpdate(runner, "${duckId}", "green", 5, "rubber", "quuaack", "ACTIVE");

        DuckUpdateResponse expectedResponse = new DuckUpdateResponse()
                .message("Duck with id = ${duckId} is updated");

        validateResponsePayloads(runner, HttpStatus.OK, expectedResponse);
        validateDuckInDatabase(runner, "${duckId}", "green", "5.0", "rubber", "quuaack", "ACTIVE");
    }
}