package com.ticketingtool.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BindException.class)
	ResponseEntity<List<ErrorResponse>> handleBindException(BindException ex, WebRequest request) {
		// Build custom error response with validation errors
		List<ErrorResponse> list = ex.getFieldErrors().stream()
				.map(err -> new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.getReasonPhrase(), err.getField() + " " + err.getDefaultMessage(),
						request.getDescription(false))

				).collect(Collectors.toList());
		return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {

		Map<String, String> fieldMsg = ex.getConstraintViolations().stream().collect(Collectors
				.toMap(violation -> violation.getPropertyPath().toString(), violation -> violation.getMessage()));
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), "Validation Failed!", request.getDescription(false),
				fieldMsg);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handlingBadCredentialsException(BadCredentialsException ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), "Credentials Invalid !", request.getDescription(false));

		

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<ErrorResponse> handleResourceAlreadyExistException(ResourceAlreadyExistException ex,
			WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
			WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

}
