package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class DuckPropertiesTest extends DuckPropertiesClient {

    @Test(description = "Проверка свойств утки (нечётный ID)")
    @CitrusTest
    public void getOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "1";
        duckProperties(runner, duckId);
        validateResponse(runner, HttpStatus.OK, "{\"color\":\"yellow\",\"height\":500.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"ACTIVE\"}");
    }

    //для утки с чётным ID запрос возвращает пустое тело
    @Test(description = "Проверка свойств утки (чётный ID)")
    @CitrusTest
    public void getEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {
        // указать ID заранее созданной утки
        String duckId = "6";
        duckProperties(runner, duckId);
        validateResponse(runner, HttpStatus.OK, "{}");
    }
}


