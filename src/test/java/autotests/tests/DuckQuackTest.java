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
@Feature("Кряканье уточки")
public class DuckQuackTest extends DuckActionClient {

    //перепутана логика параметров repetitionCount и soundCount
    //(repetitionCount отвечает за количество звуков, а soundCount за количество повторений)
    @Test(description = "Проверка кряканья утки (нечётный ID, repetitionCount=3, soundCount=2)")
    @CitrusTest
    public void successfulOddDuckQuack(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponse(runner, HttpStatus.OK, "{\"sound\": \"quack-quack-quack, quack-quack-quack\"}");
    }

    //перепутана логика параметров repetitionCount и soundCount
    //(repetitionCount отвечает за количество звуков, а soundCount за количество повторений)
    //для утки с чётным ID заменяет указанный при создании звук на "moo"
    @Test(description = "Проверка кряканья утки (чётный ID, repetitionCount=3, soundCount=2)")
    @CitrusTest
    public void successfulEvenDuckQuack(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "6");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));

        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId}, 'yellow', 5.0, 'rubber', 'quack', 'ACTIVE');");

        duckQuack(runner, "${duckId}", 3, 2);
        validateResponse(runner, HttpStatus.OK, "{\"sound\": \"moo-moo-moo, moo-moo-moo\"}");
    }
}