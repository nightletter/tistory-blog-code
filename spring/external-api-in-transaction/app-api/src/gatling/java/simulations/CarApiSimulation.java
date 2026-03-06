package simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static java.time.Duration.ofSeconds;

import io.gatling.core.session.package$;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

public class CarApiSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    ScenarioBuilder scn = scenario("Car Registration")
            .exec(http("Create Car Task")
//                    .post("/v3/car")
                            .post("/v4/car")
                            .check(jsonPath("$.taskId").saveAs("taskId"))
            )
            .exec(session -> session.set("status", "PENDING")) // 강제로 세션에 상태 주입, 없으면 종료됨
            .pause(ofSeconds(2))
            .asLongAs(session -> !session.getString("status").equals("SUCCESS"))
            .on(
                    exec(http("Check Task Status")
//                            .get(session -> "/v3/car/" + session.getString("taskId") + "/status")
                                    .get(session -> "/v4/car/" + session.getString("taskId") + "/status")
                                    .check(jsonPath("$.status").saveAs("status"))
                    )
                            .pause(ofSeconds(5)) // 5초 대기후 루프, 풀링 흉내
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(5000))
        ).protocols(httpProtocol);
    }
}