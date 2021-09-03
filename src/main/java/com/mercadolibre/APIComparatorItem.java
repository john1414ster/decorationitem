package com.mercadolibre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class APIComparatorItem {

    private final Logger logger = LoggerFactory.getLogger(APIComparatorItem.class);


    @Test
    public void whenItemDecoratedAsSuccessfullThenOK() throws IOException {
        System.out.println("Iniciando test...");
        try (LoggerOutputStream outputStream = new LoggerOutputStream(logger)) {
            RestAssured.config = config().logConfig(logConfig()
                    .defaultStream(new PrintStream(outputStream))
                    .enableLoggingOfRequestAndResponseIfValidationFails());
            Root searchAPI = RestAssured.given().baseUri("https://internal-api.mercadolibre.com/sites/MHN/search")
                    .and()
                    .queryParam("internal", true)
                    .queryParam("isPublicPagination", true)
                    .queryParam("productads_limit", 6)
                    .queryParam("backend_info", true)
                    .queryParam("allow_pdp_filters", "on")
                    .queryParam("visual-id", "STD")
                    .queryParam("public", false)
                    .queryParam("limit", 50)
                    .queryParam("action", "zero")
                    .queryParam("loyal_level", 0)
                    .queryParam("forced-layout", false)
                    .queryParam("lang", "es_HN")
                    .queryParam("platformSource", "desktop")
                    .queryParam("unified-items-and-products", true)
                    .queryParam("debug", "api-call")
                    .queryParam("offset", 0)
                    .queryParam("profile", true)
                    .queryParam("d2Id", "03171910-367b-4c71-baad-4df757b89766")
                    .queryParam("x-webview-client", false)
                    .queryParam("platform-id", "ML")
                    .queryParam("sort", "relevance")
                    .queryParam("has_reviews", true)
                    .queryParam("noRedirect", false)
                    .queryParam("layout", "stack")
                    .queryParam("q", "celulares")
                    .queryParam("sourceIp", "190.191.172.62")
                    .queryParam("fs_calculator", true)
                    .queryParam("allowPdpWithNoBuyBoxWinner", true)
                    .queryParam("mclicsDynamicSearchMigrated", true)
                    .queryParam("mclicsPadsScoreOn", true)
                    .queryParam("device", "desktop")
                    .log().all()
                    .when()
                    .get("/")
                    .then()
                    .statusCode(is(equalTo(200)))
            .extract().body().as(Root.class);
            outputStream.flush();
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            String results = mapper.writeValueAsString(searchAPI.results);
            List<Result> copyResults = searchAPI.results;
            copyResults.get(0).title = "Prueba";
            System.out.println("Results: " + results);
            String copyResult = mapper.writeValueAsString(copyResults);
            System.out.println("Copy Result: " + copyResult);
            assertEquals(mapper.readTree(results), mapper.readTree(copyResult));
        }




    }
}
