package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    //валидация данных
    public void validateResponse(TestCaseRunner runner, HttpStatus status, String responseMessage) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(status)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage)
        );
    }
}