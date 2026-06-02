package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;

import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

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

    @Test(description = "Проверка кряканья утки (нечётный ID, repetitionCount=3, soundCount=2)")
    @CitrusTest
    public void successfulOddDuckQuack(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "1";
        duckQuack(runner, duckId, 3, 2);
        validateResponse(runner, "{\"sound\": \"quack-quack-quack, quack-quack-quack\"}");
    }

    @Test(description = "Проверка кряканья утки (чётный ID, repetitionCount=3, soundCount=2)")
    @CitrusTest
    public void successfulEvenDuckQuack(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "6";
        duckQuack(runner, duckId, 3, 2);
        validateResponse(runner, "{\"sound\": \"moo-moo-moo, moo-moo-moo\"}");
    }
}