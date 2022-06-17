package com.extrawest.core.service.impl;

import com.extrawest.core.model.UserSequence;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
@AllArgsConstructor
public class UserSequenceGeneratorService {
    private final MongoOperations mongoOperations;

    public int getSequenceNumber(String sequenceName) {
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);
        UserSequence counter = mongoOperations
                .findAndModify(query,
                        update,
                        options().returnNew(true).upsert(true),
                        UserSequence.class);
        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }
}
