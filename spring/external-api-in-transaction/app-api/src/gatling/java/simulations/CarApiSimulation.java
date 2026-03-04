package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class CarApiSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    ScenarioBuilder scn = scenario("Car API Load Test")
            .exec(http("test")
                    .post("/v2/car")
                    .check(status().is(200)));

    {
        setUp(
                scn.injectOpen(atOnceUsers(50))
        ).protocols(httpProtocol);
    }
}