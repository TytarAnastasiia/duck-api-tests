package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.request.DuckCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckActionClient {

    @Test(description = "Проверка успешного создания утки material=rubber")
    @CitrusTest
    public void successfulCreateRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, properties);
        validateResponseResources(runner, HttpStatus.OK, "createTest/DuckPropertiesResponse1.json");
    }

    @Test(description = "Проверка успешного создания утки material=wood")
    @CitrusTest
    public void successfulCreateWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, properties);
        validateResponseResources(runner, HttpStatus.OK, "createTest/DuckPropertiesResponse2.json");
    }
}