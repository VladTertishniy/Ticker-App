package com.extrawest.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private final String mongoDatabaseName;

    private final MongoTypeMapper mongoTypeMapper;

    public MongoConfiguration(
            @Value("${spring.data.mongodb.database}") String mongoDatabaseName,
            MongoTypeMapper mongoTypeMapper
    ) {
        this.mongoDatabaseName = mongoDatabaseName;
        this.mongoTypeMapper = mongoTypeMapper;
    }

    @Override
    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory databaseFactory,
            MongoCustomConversions customConversions,
            MongoMappingContext mappingContext
    ) {
        MappingMongoConverter converter = super.mappingMongoConverter(
                databaseFactory,
                customConversions,
                mappingContext
        );
        converter.setTypeMapper(mongoTypeMapper);
        return converter;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    protected String getDatabaseName() {
        return mongoDatabaseName;
    }
}
