package autotests.tests;

import autotests.clients.DuckActionClient;
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
@Feature("Получение свойств уточки")
public class DuckPropertiesTest extends DuckActionClient {

    @Test(description = "Проверка свойств утки (нечётный ID)")
    @CitrusTest
    public void getOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckProperties(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"color\":\"yellow\",\"height\":500.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"ACTIVE\"}");
    }

    @Test(description = "Проверка свойств утки (чётный ID)")
    @CitrusTest
    public void getEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "6");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckProperties(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\"color\":\"yellow\",\"height\":500.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"ACTIVE\"}");
    }
}


