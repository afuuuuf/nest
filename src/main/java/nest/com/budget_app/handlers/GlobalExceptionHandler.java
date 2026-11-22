package nest.com.budget_app.handlers;

import java.util.stream.Collectors;
import nest.com.budget_app.exceptions.ApiError;
import nest.com.budget_app.exceptions.NotFoundException;
import nest.com.budget_app.exceptions.UnauthorizedException;
import nest.com.budget_app.exceptions.UnsupportedActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
    String detail =
        ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining("; "));
    return ResponseEntity.badRequest().body(new ApiError(detail));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleUnreadableBody(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest().body(new ApiError("Malformed or invalid request body."));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(ex.getMessage()));
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(ex.getMessage()));
  }

  @ExceptionHandler(UnsupportedActionException.class)
  public ResponseEntity<ApiError> handleUnsupportedAction(UnsupportedActionException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(ex.getMessage()));
  }
}
