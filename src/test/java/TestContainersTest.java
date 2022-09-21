import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class TestContainersTest {
    @Test
    void test() throws InterruptedException {
//        try (GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis")).withExposedPorts(6379)) {
        GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis"))
                .withNetworkMode("container:6566187da736")
                .withExposedPorts(6379);
            redis.start();
            redis.waitingFor(Wait.forListeningPort());
            try (var redisClient = RedisClient.create("redis://localhost:%d/0".formatted(redis.getMappedPort(6379)))) {
                StatefulRedisConnection<String, String> connection = redisClient.connect();
                System.out.println(connection.sync().info());
            }
//        }
    }
}
