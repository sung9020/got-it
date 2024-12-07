package com.snow.gotit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GotItApplication

fun main(args: Array<String>) {
    runApplication<GotItApplication>(*args)
}
