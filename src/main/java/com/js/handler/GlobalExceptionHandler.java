package com.js.handler;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.js.exception.ClientNotFoundException;
import com.js.exception.EmailAlreadyExistException;
import com.js.exception.PositionNotFoundException;
import com.js.validation.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
	// Default Handler

	@ExceptionHandler(
	{ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request)
	{
		logger.error("500 Status Code", ex);

		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// BindException

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		List<String> errors = new ArrayList<String>();

		for (FieldError error : ex.getBindingResult().getFieldErrors())
		{
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors())
		{
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	// MethodArgumentNotValidException

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		List<String> errors = new ArrayList<String>();

		for (FieldError error : ex.getBindingResult().getFieldErrors())
		{
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors())
		{
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	// MissingServletRequestParameterException

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		String error = ex.getParameterName() + " parameter is missing";

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// ConstrainViolationException

	@ExceptionHandler(
	{ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		List<String> errors = new ArrayList<String>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations())
		{
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// MethodArgumentTypeMismatchException

	@ExceptionHandler(
	{ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// NoHandlerFoundException

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		logger.error("404 Status Code", ex);

		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// HttpRequestMethodNotSupportedException

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		logger.error("405 Status Code", ex);

		StringBuilder builder = new StringBuilder();

		builder.append(ex.getMethod());

		builder.append(" method is not supported for this request. Supported methods are ");

		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// HttpMediaTypeNotSupportedException

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		logger.error("415 Status Code", ex);

		StringBuilder builder = new StringBuilder();

		builder.append(ex.getContentType());

		builder.append(" media type is not supported. Supported media types are ");

		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

		ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// --------------------

	// EmailAlreadyExistException

	@ExceptionHandler(
	{ EmailAlreadyExistException.class })
	public ResponseEntity<Object> handleEmailAlreadyExistException(RuntimeException ex, WebRequest request)
	{
		logger.error("409 Status Code", ex);

		String error = "The given email is already in use";

		ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(), error);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// BadCredentialsException

	@ExceptionHandler(
	{ BadCredentialsException.class })
	public ResponseEntity<Object> handleBadCredentialsException(RuntimeException ex, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		String error = "Bad credentials";

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// ClientNotFoundException

	@ExceptionHandler(
	{ ClientNotFoundException.class })
	public ResponseEntity<Object> handleClientNotFoundException(RuntimeException ex, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		String error = "There is no client with the given parameter";

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// PositionNotFoundException

	@ExceptionHandler(
	{ PositionNotFoundException.class })
	public ResponseEntity<Object> handlePositionNotFoundException(RuntimeException ex, WebRequest request)
	{
		logger.error("400 Status Code", ex);

		String error = "There is no position with the given parameter";

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
