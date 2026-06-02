package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesTest extends DuckUtils {

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id)
        );
    }

    @Test(description = "Проверка свойств утки (нечётный ID)")
    @CitrusTest
    public void getOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "1";
        duckProperties(runner, duckId);
        validateResponse(runner, "{\"color\":\"yellow\",\"height\":500.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"ACTIVE\"}");
    }

    @Test(description = "Проверка свойств утки (чётный ID)")
    @CitrusTest
    public void getEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "6";
        duckProperties(runner, duckId);
        validateResponse(runner, "{}");
    }
}


