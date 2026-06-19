package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.response.DuckFlyResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-action-controller")
@Feature("Полёт уточки")
public class DuckFlyTest extends DuckActionClient {

    @Test(description = "Проверка полёта утки с wingsState=FIXED")
    @CitrusTest
    public void successfulFlyFixed(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1234");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'FIXED');");

        duckFly(runner, "${duckId}");

        DuckFlyResponse expectedResponse = new DuckFlyResponse()
                .message("I can not fly :C");

        validateResponsePayloads(runner, duckService, HttpStatus.OK, expectedResponse);
    }

    @Test(description = "Проверка полёта утки с wingsState=UNDEFINED")
    @CitrusTest
    public void successfulFlyUndefined(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1234");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'UNDEFINED');");

        duckFly(runner, "${duckId}");

        DuckFlyResponse expectedResponse = new DuckFlyResponse()
                .message("Wings are not detected :(");

        validateResponsePayloads(runner, duckService, HttpStatus.OK, expectedResponse);
    }

    @Test(description = "Проверка полёта утки с wingsState=ACTIVE")
    @CitrusTest
    public void successfulFlyActive(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1234");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckFly(runner, "${duckId}");

        DuckFlyResponse expectedResponse = new DuckFlyResponse()
                .message("I am flying :)");

        validateResponsePayloads(runner, duckService, HttpStatus.OK, expectedResponse);
    }
}