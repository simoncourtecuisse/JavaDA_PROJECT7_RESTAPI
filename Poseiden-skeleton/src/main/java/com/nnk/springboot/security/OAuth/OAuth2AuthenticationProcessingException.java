package com.nnk.springboot.security.OAuth;

import org.springframework.security.core.AuthenticationException;

//////////////////////////////////////////////// callicoder.com ////////////////////////////////////////////////
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}