package click.dobel.micronaut

import io.micronaut.cache.annotation.Cacheable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
open class CachedProducer {

  companion object {
    val LOGGER: Logger = LoggerFactory.getLogger(CachedProducer::class.java)
  }

  @Cacheable(cacheNames = ["mycache"])
  open fun randomNumber(): Int {
    val result = (Math.random() * 1000).toInt()
    LOGGER.info("Produced new random number: {}", result)
    return result
  }
}
