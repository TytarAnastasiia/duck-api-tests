package autotests.tests;

import autotests.clients.DuckActionClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckQuackTest extends DuckActionClient {

    //перепутана логика параметров repetitionCount и soundCount
    //(repetitionCount отвечает за количество звуков, а soundCount за количество повторений)
    @Test(description = "Проверка кряканья утки (нечётный ID, repetitionCount=3, soundCount=2)")
    @CitrusTest
    public void successfulOddDuckQuack(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "1";
        duckQuack(runner, duckId, 3, 2);
        validateResponse(runner, HttpStatus.OK, "{\"sound\": \"quack-quack-quack, quack-quack-quack\"}");
    }

    //перепутана логика параметров repetitionCount и soundCount
    //(repetitionCount отвечает за количество звуков, а soundCount за количество повторений)
    //для утки с чётным ID заменяет указанный при создании звук на "moo"
    @Test(description = "Проверка кряканья утки (чётный ID, repetitionCount=3, soundCount=2)")
    @CitrusTest
    public void successfulEvenDuckQuack(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "6";
        duckQuack(runner, duckId, 3, 2);
        validateResponse(runner, HttpStatus.OK, "{\"sound\": \"moo-moo-moo, moo-moo-moo\"}");
    }
}