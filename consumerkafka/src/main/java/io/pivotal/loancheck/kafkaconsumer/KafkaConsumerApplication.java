package io.pivotal.loancheck.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(ConsumerProcessor.class)
public class KafkaConsumerApplication {

  public static final Logger log = LoggerFactory.getLogger(KafkaConsumerApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(KafkaConsumerApplication.class, args);
    log.info("Application has started...");
  }
}
