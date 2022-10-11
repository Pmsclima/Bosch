package org.acme;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
@TestHTTPEndpoint(SubscriptionResource.class)
public class SubscriptionResourceTest {

    @Test
    public void getAll() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void getById() {

        Subscription subscription = createSubscirption();
        Subscription savedSubscription = given()
                .contentType(ContentType.JSON)
                .body(subscription)
                .post()
                .then()
                .statusCode(201)
                .extract().as(Subscription.class);
        Subscription got = given()
                .when()
                .get("/{subscriptionId}", savedSubscription.getSubscriptionId())
                .then()
                .statusCode(200)
                .extract().as(Subscription.class);
        assertThat(savedSubscription).isEqualTo(got);
    }

}
