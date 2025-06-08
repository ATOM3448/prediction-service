package ru.tusur.prediction.service.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ErrorMessageResolver;
import ru.tusur.prediction.service.core.error.ServiceException;

/**
 * Базовый класс для обработки исключений API.
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception exception) {
        log.error("Ошибка обработки запроса", exception);

        return ResponseEntity.status(getHttpStatus(exception))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(getError(exception));
    }

    private HttpStatus getHttpStatus(Exception exception) {
        if (exception instanceof NoResourceFoundException) {
            return HttpStatus.NOT_FOUND;
        }
        if (exception instanceof NoHandlerFoundException) {
            return HttpStatus.NOT_FOUND;
        }
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        }
        if (exception instanceof HttpMediaTypeNotAcceptableException) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
        if (exception instanceof HttpMediaTypeNotSupportedException) {
            return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        }
        if (exception instanceof ServletRequestBindingException) {
            return HttpStatus.BAD_REQUEST;
        }
        if (exception instanceof BadCredentialsException) {
            return HttpStatus.UNAUTHORIZED;
        }
        if (exception instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN;
        }
        if (exception instanceof InternalAuthenticationServiceException) {
            return HttpStatus.FORBIDDEN;
        }
        if (exception instanceof AuthenticationException) {
            return HttpStatus.FORBIDDEN;
        }
        if (exception instanceof MethodArgumentTypeMismatchException) {
            return HttpStatus.BAD_REQUEST;
        }
        if (exception instanceof MethodArgumentNotValidException) {
            return HttpStatus.BAD_REQUEST;
        }
        if (exception instanceof ServiceException serviceException) {
            return getHttpStatus(serviceException);
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private HttpStatus getHttpStatus(ServiceException exception) {
        return switch (exception.getErrorCode()) {
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case OBJECT_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case ACCESS_DENIED -> HttpStatus.FORBIDDEN;
            case DUPLICATE -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    private Error getError(Exception exception) {
        if (exception instanceof NoHandlerFoundException
                || exception instanceof NoResourceFoundException
                || exception instanceof HttpRequestMethodNotSupportedException
                || exception instanceof HttpMediaTypeNotAcceptableException
                || exception instanceof HttpMediaTypeNotSupportedException
                || exception instanceof HttpMessageNotReadableException) {
            return null;
        }
        if (exception instanceof BadCredentialsException) {
            return buildError(ErrorCode.UNAUTHORIZED);
        }
        if (exception instanceof AccessDeniedException
                || exception instanceof AuthenticationException) {
            return buildError(ErrorCode.ACCESS_DENIED);
        }
        if (exception instanceof MissingServletRequestParameterException
                || exception instanceof MethodArgumentTypeMismatchException
                || exception instanceof MethodArgumentNotValidException) {
            return buildError(ErrorCode.INVALID_ARGUMENT);
        }
        if (exception instanceof ServletRequestBindingException) {
            return null;
        }
        if (exception instanceof ServiceException serviceException) {
            return buildError(serviceException.getErrorCode());
        }

        return buildError(ErrorCode.INTERNAL_ERROR);
    }

    private Error buildError(ErrorCode errorCode) {
        return new Error(
                ErrorType.ERROR,
                errorCode.getCode(),
                ErrorMessageResolver.getErrorMessage(errorCode));
    }
}
