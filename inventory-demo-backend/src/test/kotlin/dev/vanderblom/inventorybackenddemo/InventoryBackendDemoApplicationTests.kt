package dev.vanderblom.inventorybackenddemo

import dev.vanderblom.inventorybackenddemo.data.model.Product
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryBackendDemoApplicationTests {

    @Value(value = "\${local.server.port}")
    private val port = 0
    private val baseUrl get() = "http://localhost:$port/api/v1/inventory/"

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setUp() {
        client = WebTestClient.bindToServer().baseUrl(baseUrl).build()
    }

    @Test
    fun `list endpoint should return a list of products`() {
        client.get()
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Product::class.java)
            .contains(
                Product("Nails", 1337L, id=1L),
                Product("Screws", 42L, id=2L)
            )
    }

    @Test
    fun `create endpoint should add a product`() {
        val sizeBefore = client.get()
            .exchange()
            .expectStatus().isOk()
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
            .expectStatus().isOk()
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
            .expectStatus().isNotFound()
    }

}
