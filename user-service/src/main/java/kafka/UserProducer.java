package kafka;

import com.bs.services.user.constants.Constants;
import com.bs.services.user.dto.UserDTO;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {

  private static final Logger log = LoggerFactory.getLogger(UserProducer.class);

  private final KafkaTemplate<String, UserDTO> createUserKafkaTemplate;


  public UserProducer(KafkaTemplate<String, UserDTO> createUserKafkaTemplate) {
    this.createUserKafkaTemplate = createUserKafkaTemplate;
  }

  public boolean sendCreateOrderEvent(UserDTO userDTO) throws ExecutionException, InterruptedException {
    SendResult<String, UserDTO> sendResult = createUserKafkaTemplate.send(Constants.USER_TOPIC, userDTO).get();
    log.info("Create user {} event sent via Kafka", userDTO);
    log.info(sendResult.toString());
    return true;
  }
}
