package click.dobel.micronaut

import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ScheduledConsumer(
  private val producer: CachedProducer
) {

  companion object {
    val LOGGER: Logger = LoggerFactory.getLogger(ScheduledConsumer::class.java)
  }

  @Scheduled(fixedRate = "5s")
  fun consume() {
    val number = producer.randomNumber()

    LOGGER.info("Consumed random number: {}", number)
  }
}
