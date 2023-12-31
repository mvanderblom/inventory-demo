package dev.vanderblom.inventorybackenddemo.data

import dev.vanderblom.inventorybackenddemo.data.model.Product
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DatabasePopulator(
    private val repo: ProductRepository
) {
    @PostConstruct
    fun init() {
        repo.saveAll(
            listOf(
                Product("Nails", 1337),
                Product("Screws", 42)
            )
        );
    }
}