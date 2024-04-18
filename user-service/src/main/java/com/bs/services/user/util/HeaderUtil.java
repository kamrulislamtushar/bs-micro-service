package com.bs.services.user.util;



import com.bs.services.user.constants.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public final class HeaderUtil {
  private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

  private HeaderUtil() {
  }

  public static HttpHeaders createAlert( String message, String param) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + Constants.APPLICATION_NAME + "-alert", message);

    try {
      headers.add("X-" + Constants.APPLICATION_NAME + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
    } catch (UnsupportedEncodingException var5) {
    }

    return headers;
  }

  public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
    String message =  "A new " + entityName + " is created with identifier " + param;
    return createAlert( message, param);
  }

  public static HttpHeaders createEntityUpdateAlert( String entityName, String param) {
    String message = "A " + entityName + " is updated with identifier " + param;
    return createAlert(message, param);
  }

  public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
    String message =  "A " + entityName + " is deleted with identifier " + param;
    return createAlert (message, param);
  }

  public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
    log.error("Entity processing failed, {}", defaultMessage);
    String message =  defaultMessage;
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + Constants.APPLICATION_NAME + "-error", message);
    headers.add("X-" + Constants.APPLICATION_NAME + "-params", entityName);
    return headers;
  }
}
