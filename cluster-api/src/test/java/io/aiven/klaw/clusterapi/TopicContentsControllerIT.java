package io.aiven.klaw.clusterapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.aiven.klaw.clusterapi.models.enums.ClusterStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.aiven.klaw.clusterapi.models.enums.ClusterStatus.ONLINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = KafkaClusterApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@Slf4j
public class TopicContentsControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Autowired
    private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaProducer<String, String> producer;

    private static final String topicName = "test-topic";

    private static final String bootStrapServers = "localhost:9092";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String KWCLUSTERAPIUSER = "kwclusterapiuser";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${klaw.clusterapi.access.base64.secret}")
    private String clusterAccessSecret;

    @Test
    void blah() throws Exception {
        String data = "Sending with our own simple KafkaProducer";

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, data);
        producer.send(producerRecord);

        Map<String, List<PartitionInfo>> f = consumer.listTopics();
        log.info("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        log.info(f.toString());
        assertThat(f).containsKey("aslkdjlkasjdlkasdj");
        assertThat(false).isTrue();
//        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
//        assertTrue(messageConsumed);
//        assertThat(consumer.getPayload(), containsString(data));
    }
}
