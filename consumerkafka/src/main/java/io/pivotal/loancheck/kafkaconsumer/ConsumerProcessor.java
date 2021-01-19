package io.pivotal.loancheck.kafkaconsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface ConsumerProcessor {

  String APPLICATIONS_IN = "output";
  String APPROVED_OUT = "approved";
  String DECLINED_OUT = "declined";

  @Input(APPLICATIONS_IN)
  SubscribableChannel sourceOfLoanApplications();

  @Output(APPROVED_OUT)
  MessageChannel approved();

  @Output(DECLINED_OUT)
  MessageChannel declined();

}
