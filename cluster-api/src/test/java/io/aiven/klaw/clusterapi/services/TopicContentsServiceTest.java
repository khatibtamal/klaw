package io.aiven.klaw.clusterapi.services;

import static org.mockito.Mockito.when;

import io.aiven.klaw.clusterapi.constants.TestConstants;
import io.aiven.klaw.clusterapi.utils.ClusterApiUtils;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
//@ExtendWith(MockitoExtension.class)
class TopicContentsServiceTest {

  @Autowired
  private KafkaProducer producer;
  @Mock private ClusterApiUtils clusterApiUtils;
  private TopicContentsService topicContentsService;

  @BeforeEach
  void setup() {
    topicContentsService = new TopicContentsService(clusterApiUtils);
  }

  @Test
  void readEventsWhenLastOffsetsSelected() {
    String protocol = "SSL";
    int offsetPosition = 0;
    String readMessagesType = "OFFSET_ID";
    String bootstrapServers = "localhost:9092";
    Properties properties = new Properties();

    when(clusterApiUtils.getSslConfig(TestConstants.CLUSTER_IDENTIFICATION)).thenReturn(properties);

    //EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, 1, TestConstants.TOPIC_NAME);

  }

  @Test
  void readEventsWhenCustomLastOffsetsSelected() {

  }

  @Test
  void readEventsWhenRangeOffsetsSelected() {

  }

  @Test
  void readEvents() {
    String protocol = "SSL";
    int offsetPosition = 0;
    String readMessagesType = "OFFSET_ID";
    String bootstrapServers = "localhost:9092";

    Mockito.when(clusterApiUtils.getSslConfig(TestConstants.CLUSTER_IDENTIFICATION))
        .thenReturn(new Properties());

    Map<Long, String> actual =
        topicContentsService.readEvents(
            bootstrapServers,
            protocol,
            TestConstants.CONSUMER_GROUP_ID,
            TestConstants.TOPIC_NAME,
            offsetPosition + "",
            0,
            0,
            0,
            0,
            readMessagesType,
            TestConstants.CLUSTER_IDENTIFICATION);

    Map<Long, String> expected = Collections.emptyMap();
    Assertions.assertThat(actual).isEqualTo(expected);
  }
}
