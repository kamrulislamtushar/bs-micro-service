package com.bs.services.user.exception;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ValidationFailureException extends AbstractThrowableProblem {
  public ValidationFailureException(String message) {
    super(URI.create("/v1/failure"), message, Status.BAD_REQUEST);
  }
}
