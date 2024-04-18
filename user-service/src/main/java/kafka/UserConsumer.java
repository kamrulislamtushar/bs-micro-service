package kafka;

import com.bs.services.user.constants.Constants;
import com.bs.services.user.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {
  private static final Logger log = LoggerFactory.getLogger(UserConsumer.class);

  @KafkaListener(topics = Constants.USER_TOPIC)
  public void createOrderListener(@Payload UserDTO userDTO, Acknowledgment ack) {
    log.info("Notification service received user {} ", userDTO);
    ack.acknowledge();
  }
}
