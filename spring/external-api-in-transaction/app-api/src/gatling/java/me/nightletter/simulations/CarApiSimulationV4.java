package me.nightletter.simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static java.time.Duration.ofSeconds;

public class CarApiSimulationV4 extends Simulation {
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    ScenarioBuilder scn = scenario("Car Registration")
            .exec(http("Create Car Task")
                    .post("/v4/car")
                    .check(jsonPath("$.taskId").saveAs("taskId"))
            )
            .exec(session -> session.set("status", "PENDING"))
            .pause(ofSeconds(2))
            .asLongAs(session -> !session.getString("status").equals("SUCCESS"))
            .on(
                    exec(http("Check Task Status")
                            .get(session -> "/v4/car/" + session.getString("taskId") + "/status")
                            .check(jsonPath("$.status").saveAs("status"))
                    )
                            .pause(ofSeconds(5))
            );

    {
        setUp(scn.injectOpen(atOnceUsers(1)))
                .protocols(httpProtocol);
    }
}