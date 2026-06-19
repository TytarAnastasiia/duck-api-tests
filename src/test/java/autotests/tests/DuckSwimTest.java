package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.response.DuckSwimResponse;
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
@Feature("Плавание уточки")
public class DuckSwimTest extends DuckActionClient {

    //код статуса: 404, в сообщении указано, что не найдены лапки
    @Test(description = "Проверка плавания утки")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1234");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckSwim(runner, "${duckId}");

        DuckSwimResponse expectedResponse = new DuckSwimResponse()
                .message("Paws are not found ((((");

        validateResponsePayloads(runner, duckService, HttpStatus.NOT_FOUND, expectedResponse);
    }

    //код статуса: 404, в сообщении указано, что не найдены лапки
    //(должно выводиться сообщение о том, что утки с таким ID не существует)
    @Test(description = "Проверка плавания несуществующей утки")
    @CitrusTest
    public void swimNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String nonExistentId = "9999";
        duckSwim(runner, nonExistentId);

        DuckSwimResponse expectedResponse = new DuckSwimResponse()
                .message("Paws are not found ((((");

        validateResponsePayloads(runner, duckService, HttpStatus.NOT_FOUND, expectedResponse);
    }
}