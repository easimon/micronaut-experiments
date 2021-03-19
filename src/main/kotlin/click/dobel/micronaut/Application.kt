package click.dobel.micronaut

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
    .args(*args)
    .packages("click.dobel.micronaut")
    .start()
}
