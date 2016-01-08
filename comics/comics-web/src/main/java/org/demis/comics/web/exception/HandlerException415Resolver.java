package org.demis.comics.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.Priority;

@Component
@Priority(value = 0)
public class HandlerException415Resolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
            return new ModelAndView(new MappingJackson2JsonView(), "error", new RestError("Unsupported Media Type"));
        }
        else {
            return null;
        }
    }

}
