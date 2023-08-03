package dev.vanderblom.inventorybackenddemo

import dev.vanderblom.inventorybackenddemo.data.model.Product
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryBackendDemoApplicationTests() {
    @Value(value = "\${local.server.port}")
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    val baseUrl get() = "http://localhost:$port/api/inventory/"

    @Test
    fun `list endpoint should return a list of products`() {
        val response = restTemplate.getForObject(
            baseUrl,
            Array<Product>::class.java
        )

        assertThat(response)
            .extracting("name", "inventory")
            .contains(
                Tuple("Nails", 1337L),
                Tuple("Screws", 42L)
            )
    }

    @Test
    fun `create endpoint should add a product`() {
        val sizeBefore = restTemplate.getForObject(
            baseUrl,
            Array<Product>::class.java
        ).size

        val newProduct = Product("Glue", 420)
        val createResponse = restTemplate
            .postForEntity(baseUrl, newProduct, Void::class.java)

        assertThat(createResponse.statusCode.value())
            .isEqualTo(HttpStatus.OK.value())

        val sizeAfter = restTemplate.getForObject(
            baseUrl,
            Array<Product>::class.java
        ).size

        assertThat(sizeAfter)
            .isEqualTo(sizeBefore + 1)
    }
}
