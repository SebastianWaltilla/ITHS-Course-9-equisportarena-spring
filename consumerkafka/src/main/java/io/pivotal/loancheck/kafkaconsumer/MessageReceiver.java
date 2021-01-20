package io.pivotal.loancheck.kafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

  public static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);
  private static final Long MAX_AMOUNT = 10000L;
  private ConsumerProcessor processor;

  @Autowired
  public MessageReceiver(ConsumerProcessor processor) {
    this.processor = processor;
  }

  @StreamListener(ConsumerProcessor.APPLICATIONS_IN)
  public void receiveMessage(String user) {

    if (!user.isEmpty()) {
      log.info(user);
    } else {
      log.info("does not work");
    }
  }

  private static final <T> Message<T> message(T val) {
    return MessageBuilder.withPayload(val).build();
  }
}
