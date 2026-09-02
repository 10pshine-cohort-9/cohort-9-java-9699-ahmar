package com.tenpshine.contacts.exception;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler{
 private static final Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
 @ExceptionHandler(ApiException.class) ResponseEntity<?> api(ApiException e){return response(e.getStatus(),e.getMessage());}
 @ExceptionHandler(BadCredentialsException.class) ResponseEntity<?> credentials(){return response(HttpStatus.UNAUTHORIZED,"Invalid email/phone or password");}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){String msg=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).findFirst().orElse("Validation failed");return response(HttpStatus.BAD_REQUEST,msg);}
 @ExceptionHandler(Exception.class) ResponseEntity<?> unknown(Exception e){log.error("Unhandled request failure",e);return response(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred");}
 private ResponseEntity<?> response(HttpStatus s,String m){return ResponseEntity.status(s).body(Map.of("timestamp",Instant.now(),"status",s.value(),"message",m));}
}
