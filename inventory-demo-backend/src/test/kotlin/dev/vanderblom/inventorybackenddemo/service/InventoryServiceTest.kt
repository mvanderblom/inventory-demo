package dev.vanderblom.inventorybackenddemo.service

import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.service.model.ReservationRequestModel
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class InventoryServiceTest {

    @Autowired
    private lateinit var service: InventoryService

    @Test
    fun `a product can be added`() {
        val numProductsBefore = service.list().size
        service.create(Product("Product a", 42L))
        assertThat(service.list())
            .hasSize(numProductsBefore + 1)
            .extracting("name", "inventory")
            .contains(Tuple("Product a", 42L))
    }

    @Test
    fun `a product can be reserved for a while`() {
        val product = service.create(Product("Product a", 42L))

        service.reserve(product.id, ReservationRequestModel(11L, 10L))

        assertThat(service.read(product.id).inventory)
            .isEqualTo(31L)
    }

    @Test
    fun `you cant reserve more products than the amount in stock`() {
        val product = service.create(Product("Product a", 42L))

        assertThatThrownBy {
            service.reserve(product.id, ReservationRequestModel(43L, 1L))
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("You cannot reserve more than 42 products")
    }
}