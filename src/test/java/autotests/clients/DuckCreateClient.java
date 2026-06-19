package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckCreateClient extends DuckActionClient {

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http()
                .client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "    \"color\": \"" + color + "\",\n" +
                        "    \"height\": " + height + ",\n" +
                        "    \"material\": \"" + material + "\",\n" +
                        "    \"sound\": \"" + sound + "\",\n" +
                        "    \"wingsState\": \"" + wingsState + "\"\n" +
                        "}"));
    }
}
