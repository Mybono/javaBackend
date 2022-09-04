import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HW3 {
    @BeforeAll
    static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }
    @Test
    void getComplexSearch() {
        given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(200);
    }

    @Test
    void getComplexSearchBurger() {
        JsonPath response = given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .queryParam("query", "burger")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("totalResults"), equalTo(54));

    }

    @Test
    void getComplexSearchSugar() {
        JsonPath response = given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .queryParam("maxSugar", "100")
                .queryParam("number", "5")
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .body()
                .jsonPath();
        assertThat(response.get("number"), equalTo(5));
    }

    @Test
    void getComplexSearchVitaminB12() {
        given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .queryParam("maxVitaminB12", "100")
                .expect()
                .body("results[0].nutrition.nutrients[0].name", equalTo("Vitamin B12"))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(200);
    }
    @Test
    void getComplexSearchCaffeine() {
        given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .queryParam("minCaffeine", "0")
                .expect()
                .body("results[0].nutrition.nutrients[0].name", equalTo("Caffeine"))
                .body("results[0].nutrition.nutrients[0].amount", equalTo(0.0F))
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(200);
    }
    @Test
    void postPorkRoastWithGreenBeans() {
        given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Pork roast with green beans")
                .expect()
                .body("cuisine", equalTo("Mediterranean"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void postCauliflowerBrownRiceAndVegetableFriedRice() {
        given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Cauliflower, Brown Rice, and Vegetable Fried Rice")
                .expect()
                .body("cuisine", equalTo("Chinese"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void postSpanishStyleSalmonFillets() {
        given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Spanish style salmon fillets")
                .expect()
                .body("cuisine", equalTo("European"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void postAfricanChickenPeanutStew() {
        given()
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","African Chicken Peanut Stew")
                .expect()
                .body("cuisine", equalTo("African"))
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .statusCode(200);
    }
    @Test
    void addMealTest() {
        String id = given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .expect()
                .body("status", equalTo("success"))
                .when()
                .post("https://api.spoonacular.com/mealplanner/geekbrains/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", "02f3d8ab85d54af69db9e18220c414ba")
                .delete("https://api.spoonacular.com/mealplanner/geekbrains/items/" + id)
                .then()
                .statusCode(200);
    }
}
