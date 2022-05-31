package com.extrawest.core;

import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.stereotype.Component;

@Component
class NoTypeHintMongoTypeConverter extends DefaultMongoTypeMapper {

    public NoTypeHintMongoTypeConverter() {
        super(null);
    }
}

