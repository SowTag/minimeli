# Products service

This microservice handles product data,
mainly [products](src/main/java/dev/maddock/minimeli/productsservice/entity/Product.java) and
their [categories](src/main/java/dev/maddock/minimeli/productsservice/entity/Category.java)

## Technical details

### Category handling

Categories are handled in a tree. For this, the internal implementation uses both a closure table implemented in pure
Java/JPA and an adjacency list. This provides a good compromise by showcasing my technical skills, proving I can
leverage data structures and design patterns. Other alternatives would've been using Postgres' ltree (which imposes a
vendor lock-in, makes unit testing harder and just proves I can leverage Postgres) or using native queries (recursive
CTEs), which are a "simpler" solution that's not as interesting.
