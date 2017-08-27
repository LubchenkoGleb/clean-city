package com.kpi.diploma.smartroads.config.db;

import com.kpi.diploma.smartroads.model.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class UserCascadeSaveEL extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if ((source instanceof User) &&
                (((User) source).getRoles() != null &&
                        !((User) source).getRoles().isEmpty())) {
            ((User) source).getRoles().forEach(mongoOperations::save);
        }
    }
}