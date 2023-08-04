package dev.vanderblom.inventorybackenddemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InventoryBackendDemoApplication

fun main(args: Array<String>) {
	runApplication<InventoryBackendDemoApplication>(*args)
}
