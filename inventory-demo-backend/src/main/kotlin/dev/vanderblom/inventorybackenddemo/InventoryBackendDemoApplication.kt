package dev.vanderblom.inventorybackenddemo

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InventoryBackendDemoApplication

fun main(args: Array<String>) {
	runApplication<InventoryBackendDemoApplication>(*args)
}
