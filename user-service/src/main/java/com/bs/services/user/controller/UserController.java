package com.bs.services.user.controller;


import com.bs.services.user.dto.UserDTO;
import com.bs.services.user.dto.response.PageInfo;
import com.bs.services.user.dto.response.Response;
import com.bs.services.user.service.UserService;
import com.bs.services.user.util.HeaderUtil;
import com.bs.services.user.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import kafka.UserProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {


  private final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;
  private final UserProducer userProducer;

  public UserController(UserService userService, UserProducer userProducer) {
    this.userProducer = userProducer;
    this.userService = userService;
  }



  @PostMapping("")
  public ResponseEntity<Response<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO)
      throws URISyntaxException, ExecutionException, InterruptedException {
    log.info("User create request {}", userDTO);

    if (userDTO.getId() != null) {
      log.error("User create request with ID ");
      throw new ValidationException("A new User cannot already have an ID");
    }
    UserDTO user = userService.createUser(userDTO);
    userProducer.sendCreateOrderEvent(userDTO);
    return ResponseEntity
        .created(new URI("/api/v1/users/" + user.getId()))
        .headers(HeaderUtil.createEntityCreationAlert("User", user.getId().toString()))
        .body(new Response(user, PageInfo.of(null)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<UserDTO>> updateUser(
      @PathVariable(value = "id", required = false) final Long id,
      @Valid @RequestBody UserDTO userDTO
  ) throws URISyntaxException {
    log.debug("REST request to update ProviderCompany : {}, {}", id, userDTO);
    if (userDTO.getId() == null) {
      throw new ValidationException("Invalid id");
    }
    if (!Objects.equals(id, userDTO.getId())) {
      throw new ValidationException("Invalid ID");
    }


    UserDTO result = userService.updateUser(userDTO, id);
    return ResponseEntity
        .ok()
        .headers(HeaderUtil.createEntityUpdateAlert("User", result.getId().toString()))
        .body(new Response(result, PageInfo.of(null)));
  }


  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
    log.debug("REST request to get User : {}", id);
    Optional<UserDTO> providerCompanyDTO = userService.findByUserId(id);
    return ResponseUtil.wrapOrNotFound(providerCompanyDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
    log.debug("REST request to delete User : {}", id);
    userService.deleteUser(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert("User",  id.toString()))
        .build();
  }

  @GetMapping
  public String getUser() {
    return "working";
  }
}
