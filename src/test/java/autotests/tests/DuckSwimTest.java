package autotests.tests;

import autotests.clients.DuckActionClient;
import autotests.payloads.request.DuckCreate;
import autotests.payloads.response.DuckSwimResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckSwimTest extends DuckActionClient {

    //код статуса: 404, в сообщении указано, что не найдены лапки
    @Test(description = "Проверка плавания утки")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {

        DuckCreate properties = new DuckCreate()
                .color("yellow")
                .height(5.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, properties);
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
        duckSwim(runner, "${duckId}");

        DuckSwimResponse expectedResponse = new DuckSwimResponse()
                .message("Paws are not found ((((");

        validateResponsePayloads(runner, HttpStatus.NOT_FOUND, expectedResponse);
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

        validateResponsePayloads(runner, HttpStatus.NOT_FOUND, expectedResponse);
    }
}