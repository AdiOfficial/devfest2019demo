package experiment

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("experiment")
                .mainClass(Application.javaClass)
                .start()
    }
}