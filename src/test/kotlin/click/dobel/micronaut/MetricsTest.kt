package click.dobel.micronaut
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Tags

@MicronautTest
class MetricsTest (
  private val producer: CachedProducer,
  private val registry: MeterRegistry
): StringSpec({

  fun tagForCache() = Tag.of("cache", "mycache")
  fun tagsForCache() = Tags.of(
    tagForCache()
  )
  fun tagsForCacheGets(type: String) = Tags.of(
    tagForCache(),
    Tag.of("result", type)
  )

  "the cache contains at least one element" {
    producer.randomNumber()

    val cacheSize = registry
      .get("cache.size")
      .tags(tagsForCache())
      .gauge()
      .value()

    print("cache size: ${cacheSize}.")
    cacheSize shouldBeGreaterThanOrEqual 1.0
  }

  "the cache has at least one miss or hit" {
    producer.randomNumber()

    val hits = registry
      .get("cache.gets")
      .tags(tagsForCacheGets("hit"))
      .functionCounter()
      .count()

    val misses = registry
      .get("cache.gets")
      .tags(tagsForCacheGets("miss"))
      .functionCounter()
      .count()

    print("hits: ${hits}, misses: ${misses}.")
    (hits + misses) shouldBeGreaterThanOrEqual 1.0
  }
})
