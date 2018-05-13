package com.kpi.diploma.smartroads.config.db;

import com.kpi.diploma.smartroads.model.document.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

//public class UserCascadeSaveEL extends AbstractMongoEventListener<Object> {
//
//    @Autowired
//    private MongoOperations mongoOperations;
//
//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
//        Object source = event.getSource();
//        if ((source instanceof User) &&
//                (((User) source).getRoles() != null &&
//                        !((User) source).getRoles().isEmpty())) {
//            ((User) source).getRoles().forEach(mongoOperations::save);
//        }
//    }
//}


public class UserCascadeSaveEL extends AbstractMongoEventListener<User> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        User source = event.getSource();

        if (source.getRoles() != null && !source.getRoles().isEmpty()) {
            source.getRoles().forEach(mongoOperations::save);
        }
    }
}