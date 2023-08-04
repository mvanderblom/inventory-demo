package dev.vanderblom.inventorybackenddemo.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.result.view.RedirectView

//@Controller
//@RequestMapping("/")
class RootController {
    @GetMapping
    fun redirectToSwagger() = RedirectView("swagger-ui/index.html");
}
