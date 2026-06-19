package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.request.DuckCreate;
import autotests.payloads.response.DuckCreateResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckCreateParametersTest extends DuckActionClient {

    DuckCreate properties1 = new DuckCreate()
            .color("red")
            .height(5.0)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckCreate properties2 = new DuckCreate()
            .color("yellow")
            .height(10.0)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckCreate properties3 = new DuckCreate()
            .color("yellow")
            .height(5.0)
            .material("wood")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckCreate properties4 = new DuckCreate()
            .color("yellow")
            .height(5.0)
            .material("rubber")
            .sound("quackquack")
            .wingsState("ACTIVE");

    DuckCreate properties5 = new DuckCreate()
            .color("yellow")
            .height(5.0)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");

    DuckCreateResponse expectedResponse1 = new DuckCreateResponse()
            .color("red")
            .height(5.0)
            .id(0L)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckCreateResponse expectedResponse2 = new DuckCreateResponse()
            .color("yellow")
            .height(10.0)
            .id(0L)
            .material("rubber")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckCreateResponse expectedResponse3 = new DuckCreateResponse()
            .color("yellow")
            .height(5.0)
            .id(0L)
            .material("wood")
            .sound("quack")
            .wingsState("ACTIVE");

    DuckCreateResponse expectedResponse4 = new DuckCreateResponse()
            .color("yellow")
            .height(5.0)
            .id(0L)
            .material("rubber")
            .sound("quackquack")
            .wingsState("ACTIVE");

    DuckCreateResponse expectedResponse5 = new DuckCreateResponse()
            .color("yellow")
            .height(5.0)
            .id(0L)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");

    @Test(description = "Проверка успешного создания утки (с использованием параметризации)", dataProvider = "ducksList")
    @CitrusTest
    @CitrusParameters({"properties", "expected", "runner"})
    public void successfulCreateDuck(DuckCreate properties, DuckCreateResponse expectedResponse, @Optional @CitrusResource TestCaseRunner runner) {

        createDuck(runner, properties);
        extractDuckId(runner);
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        validateDuckInDatabase(runner, "${duckId}", expectedResponse.color(), String.valueOf(expectedResponse.height()), expectedResponse.material(), expectedResponse.sound(), expectedResponse.wingsState());
    }

    @DataProvider(name = "ducksList")
    public Object[][] duckProvider() {

        return new Object[][] {
                {properties1, expectedResponse1, null},
                {properties2, expectedResponse2, null},
                {properties3, expectedResponse3, null},
                {properties4, expectedResponse4, null},
                {properties5, expectedResponse5, null}
        };
    }

}




