package com.mercadolibre;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class JsonParserFile {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("/Users/jalvarado/Documents/Proyectos/itemsMHN.json"));
            JSONArray jsonArray = (JSONArray) obj;
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject object = iterator.next();
                String response = RestAssured.given().baseUri("https://testrelease_search-api-go.furyapps.io/consumer/message")
                        .and()
                        .body("{\"message_id\":\"fake-message-id2\", \"publish_time\":123123, \"msg\": {\"id\":\""+object.get("id")+"\"}}")
                        .header("x-auth-token", "0c0e0faefd8956254702492e4d999aa242aede25e0043cdeb515ce0b362211ea")
                        .header("Content-Type", "application/json")
                        .header("X-Caller-Id", "1")
                        .header("X-Client-Id", "1")
                        .post().then().statusCode(is(equalTo(200)))
                        .extract().body().as(String.class);

                System.out.println("ID: "+object.get("id") + " Response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
