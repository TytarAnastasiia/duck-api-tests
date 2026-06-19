package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionClient extends DuckClient {

    @Step("Эндпоинт для команды \"Летать\" уточки")
    public void duckFly(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/fly?id=" + id;
        sendGetMethod(runner, path, duckService);
    }

    @Step("Эндпоинт для команды \"Крякать\" уточки")
    public void duckQuack(TestCaseRunner runner, String id, int repetitionCount, int soundCount) {
        String path = "/api/duck/action/quack?id=" + id + "&repetitionCount=" + repetitionCount + "&soundCount=" + soundCount;
        sendGetMethod(runner, path, duckService);
    }

    @Step("Эндпоинт для команды \"Плавать\" уточки")
    public void duckSwim(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/swim?id=" + id;
        sendGetMethod(runner, path, duckService);
    }

    @Step("Эндпоинт для команды \"Создать\" уточки")
    public void createDuck(TestCaseRunner runner, Object userData) {
        String path = "/api/duck/create";
        sendPostMethod(runner, path, userData, duckService);
    }

    @Step("Эндпоинт для команды \"Удалить\" уточки")
    public void duckDelete(TestCaseRunner runner, String id) {
        String path = "/api/duck/delete?id=" + id;
        sendDeleteMethod(runner, path, duckService);
    }

    @Step("Эндпоинт для команды \"Свойства\" уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/properties?id=" + id;
        sendGetMethod(runner, path, duckService);
    }

    @Step("Эндпоинт для команды \"Обновить\" уточки")
    public void duckUpdate(TestCaseRunner runner, String id, String color, double height, String material, String sound, String wingsState) {
        String path = "/api/duck/update?color=" + color + "&height=" + height + "&id=" + id + "&material=" + material + "&sound=" + sound + "&wingsState=" + wingsState;
        sendPutMethod(runner, path, duckService);

    }
}
