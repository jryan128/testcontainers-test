import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.startupcheck.IsRunningStartupCheckStrategy;
import org.testcontainers.containers.startupcheck.StartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

public class TestContainersTest {
    @Test
    void test() throws InterruptedException {
//        try (GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis")).withExposedPorts(6379)) {
        GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis"))
                .withNetworkMode("concourse")
//                .withCreateContainerCmdModifier(cmd -> cmd.withName("redis"))
                .withExposedPorts(6379);
            redis.start();
            try (var redisClient = RedisClient.create("redis://localhost: " + redis.getFirstMappedPort())) {
                StatefulRedisConnection<String, String> connection = redisClient.connect();
                System.out.println(connection.sync().info());
            }
//        }
    }
}
