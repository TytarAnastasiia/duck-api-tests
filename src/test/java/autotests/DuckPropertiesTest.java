package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckPropertiesTest extends DuckUtils {

    public void duckProperties(TestCaseRunner runner, String id){
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id)
        );
    }

    @Test(description = "Проверка получения свойств утки")
    @CitrusTest

    public void successfulGetProperties(@Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, "yellow", 5, "rubber", "quack", "FIXED");

        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckProperties(runner, "${duckId}");
        validateResponse(runner, "{}");
    }

    @Test(description = "Попытка получения свойств несуществующей утки")
    @CitrusTest
    public void getPropertiesNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        String nonExistentId = "9999";
        duckProperties(runner, nonExistentId);
        validateErrorResponse(runner, HttpStatus.INTERNAL_SERVER_ERROR, "Duck with id = " + nonExistentId + " is not found");
    }
}


