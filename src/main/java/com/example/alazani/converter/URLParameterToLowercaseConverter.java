package com.example.alazani.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class URLParameterToLowercaseConverter implements Converter<String, String> {
    public String convert(String source) {
        if (source == null) return null;
        return source.toLowerCase();
    }
}
