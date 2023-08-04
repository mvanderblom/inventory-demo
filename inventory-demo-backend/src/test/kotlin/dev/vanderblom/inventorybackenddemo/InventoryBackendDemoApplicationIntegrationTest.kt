package dev.vanderblom.inventorybackenddemo

import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.service.model.ReservationRequestModel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication
import reactor.core.publisher.Mono


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class InventoryBackendDemoApplicationIntegrationTest {

    @Value(value = "\${local.server.port}")
    private val port = 0
    private val baseUrl get() = "http://localhost:$port/api/v1/product/"

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setUp() {
        client = WebTestClient.bindToServer()
            .baseUrl(baseUrl)
            .filter(basicAuthentication("admin", "admin"))
            .build()
    }

    @Test
    fun `list endpoint should return a list of products`() {
        client.get()
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Product::class.java)
            .contains(
                Product("Nails", 1337L, id = 1L),
                Product("Screws", 42L, id = 2L)
            )
    }

    @Test
    fun `create endpoint should add a product`() {
        val sizeBefore = client.get()
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Product::class.java)
            .returnResult()
            .responseBody!!
            .size

        val newProduct = Product("Glue", 420)
        client.post()
            .body(Mono.just(newProduct), Product::class.java)
            .exchange()
            .expectStatus().isOk()

        client.get()
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Product::class.java)
            .hasSize(sizeBefore + 1)
    }

    @Test
    fun `update endpoint returns 404 when trying to update a non-existing product`() {
        val product = Product("Glue", 420)
        client.put()
            .uri("4242")
            .body(Mono.just(product), Product::class.java)
            .exchange()
            .expectStatus()
            .isNotFound()
    }

    @Test
    fun `normal user cannot mutate a product`() {
        val client = WebTestClient.bindToServer()
            .baseUrl(baseUrl)
            .filter(basicAuthentication("user", "user"))
            .build()

        val product = Product("Glue", 420)
        client.put()
            .uri("1")
            .body(Mono.just(product), Product::class.java)
            .exchange()
            .expectStatus()
            .isForbidden()

    }

    @Test
    fun `no user gets a 401`() {
        val client = WebTestClient.bindToServer()
            .baseUrl(baseUrl)
            .build()

        client.put()
            .uri("1")
            .exchange()
            .expectStatus()
            .isUnauthorized()
    }

    @Test
    fun `providing invalid data leads to a 400`() {
        val reservationRequest = ReservationRequestModel(20L, 5 * 60 + 1L)
        client.put()
            .uri("1/reserve")
            .body(Mono.just(reservationRequest), ReservationRequestModel::class.java)
            .exchange()
            .expectStatus()
            .isBadRequest()
    }

}
