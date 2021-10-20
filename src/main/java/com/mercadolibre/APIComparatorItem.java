package com.mercadolibre;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class APIComparatorItem {

    private final Logger logger = LoggerFactory.getLogger(APIComparatorItem.class);


    @Test
    public void whenItemDecoratedAsSuccessfullThenOK() throws JsonProcessingException {
        System.out.println("Iniciando test...");
        List<String> itemsToDecorate = Arrays.asList("MHN400046500");
        List<Result> resultsSearchAPI = searchAPI();
        List<Result> itemDecoratedAPI = decorateItemsAPI(String.join(",", itemsToDecorate));
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        assertEquals(mapper.readTree(mapper.writeValueAsString(resultsSearchAPI.stream().filter(rs -> itemsToDecorate.contains(rs.id)).collect(Collectors.toList()))), mapper.readTree(mapper.writeValueAsString(itemDecoratedAPI)));
    }

    private List<Result> searchAPI() {
        try (LoggerOutputStream outputStream = new LoggerOutputStream(logger)) {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
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
            return searchAPI.results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<Result> decorateItemsAPI(String items) {
        try (LoggerOutputStream outputStream = new LoggerOutputStream(logger)) {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            RestAssured.config = config().logConfig(logConfig()
                    .defaultStream(new PrintStream(outputStream))
                    .enableLoggingOfRequestAndResponseIfValidationFails());
            List<Result> list = new ArrayList<>();
            list = RestAssured.given().baseUri("https://test-api_search-api-go.furyapps.io/items/decorate")
                    .header("x-auth-token", "b31e006aade2b5cb188ace8e6554ba0370ffd3cddc91474679c371b59181fb2a")
                    .header("Content-Type", "application/json")
                    .and()
                    .queryParam("items", items)
                    .queryParam("hasFilterSize", true)
                    .log().all()
                    .when()
                    .get("/")
                    .then()
                    .statusCode(is(equalTo(200)))
                    .extract().body().as(list.getClass());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
