package com.mangofactory.swagger.configuration;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.mangofactory.swagger.core.DefaultSwaggerPathProvider;
import com.mangofactory.swagger.core.ResourceGroupingStrategy;
import com.mangofactory.swagger.core.SpringGroupingStrategy;
import com.mangofactory.swagger.core.SwaggerCache;
import com.mangofactory.swagger.core.SwaggerPathProvider;
import com.wordnik.swagger.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.*;
import static com.google.common.collect.Sets.*;
import static com.mangofactory.swagger.ScalaUtils.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Configuration
@ComponentScan(basePackages = { "com.mangofactory.swagger.controllers" })
public class SpringSwaggerConfig {

    @Autowired
    private List<RequestMappingHandlerMapping> handlerMappings;

    @Bean
    public List<RequestMappingHandlerMapping> swaggerRequestMappingHandlerMappings() {
        return handlerMappings;
    }

    @Bean
    public ResourceGroupingStrategy defaultResourceGroupingStrategy() {
        return new SpringGroupingStrategy();
    }

    @Bean
    public List<Class<? extends Annotation>> defaultExcludeAnnotations() {
        List<Class<? extends Annotation>> annotations = new ArrayList<Class<? extends Annotation>>();
        annotations.add(ApiIgnore.class);
        return annotations;
    }

    @Bean
    public SwaggerPathProvider defaultSwaggerPathProvider() {
        return new DefaultSwaggerPathProvider();
    }

    @Bean
    public SwaggerCache swaggerCache() {
        return new SwaggerCache();
    }

    @Bean
    public Set<Class> defaultIgnorableParameterTypes() {
        HashSet<Class> ignored = newHashSet();
        ignored.add(ServletRequest.class);
        ignored.add(HttpHeaders.class);
        ignored.add(ServletResponse.class);
        ignored.add(HttpServletRequest.class);
        ignored.add(HttpServletResponse.class);
        ignored.add(HttpHeaders.class);
        ignored.add(BindingResult.class);
        ignored.add(ServletContext.class);
        ignored.add(UriComponentsBuilder.class);
        return ignored;
    }

    /**
     * Default response messages set on all api operations
     */
    @Bean
    public Map<RequestMethod, List<ResponseMessage>> defaultResponseMessages() {
        LinkedHashMap<RequestMethod, List<ResponseMessage>> responses = newLinkedHashMap();
        responses.put(GET, asList(
                new ResponseMessage(OK.value(), OK.getReasonPhrase(), toOption(null)),
                new ResponseMessage(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));

        responses.put(PUT, asList(
                new ResponseMessage(CREATED.value(), CREATED.getReasonPhrase(), toOption(null)),
                new ResponseMessage(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));

        responses.put(POST, asList(
                new ResponseMessage(CREATED.value(), CREATED.getReasonPhrase(), toOption(null)),
                new ResponseMessage(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));

        responses.put(DELETE, asList(
                new ResponseMessage(NO_CONTENT.value(), CREATED.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));

        responses.put(PATCH, asList(
                new ResponseMessage(NO_CONTENT.value(), CREATED.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));

        responses.put(TRACE, asList(
                new ResponseMessage(NO_CONTENT.value(), CREATED.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));

        responses.put(OPTIONS, asList(
                new ResponseMessage(NO_CONTENT.value(), CREATED.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));
        responses.put(HEAD, asList(
                new ResponseMessage(NO_CONTENT.value(), CREATED.getReasonPhrase(), toOption(null)),
                new ResponseMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), toOption(null)),
                new ResponseMessage(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), toOption(null))
        ));
        return responses;
    }

    private List<ResponseMessage> asList(ResponseMessage... responseMessages) {
        List<ResponseMessage> list = new ArrayList();
        for (ResponseMessage responseMessage : responseMessages) {
            list.add(responseMessage);
        }
        return list;
    }

    public List<RequestMappingHandlerMapping> getHandlerMappings() {
        return handlerMappings;
    }
}
