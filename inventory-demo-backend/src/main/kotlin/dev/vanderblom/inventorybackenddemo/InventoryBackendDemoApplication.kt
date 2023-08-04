package dev.vanderblom.inventorybackenddemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class InventoryBackendDemoApplication

fun main(args: Array<String>) {
	runApplication<InventoryBackendDemoApplication>(*args)
}
