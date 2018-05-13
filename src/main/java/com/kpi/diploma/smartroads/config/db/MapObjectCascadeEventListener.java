//package com.kpi.diploma.smartroads.config.db;
//
//import com.kpi.diploma.smartroads.model.document.map.MapObject;
//import com.kpi.diploma.smartroads.repository.map.MapObjectRepository;
//import com.mongodb.DBObject;
//import org.mockito.verification.After;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
//import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
//import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
//
//public class MapObjectCascadeEventListener extends AbstractMongoEventListener<MapObject> {
//
//    @Autowired
//    private MongoOperations mongoOperations;
//
//    @Autowired
//    private MapObjectRepository mapObjectRepository;
//
//
//
//    @Override
//    public void onAfterDelete(AfterDeleteEvent<MapObject> event) {
//        DBObject dbObject = event.getSource();
//        dbObject
//
//        MapObject source = (MapObject) dbObject;
//
//
//        if ((source instanceof User) && (((User) source).getEmailAddress() != null)) {
//            mongoOperations.save(((User) source).getEmailAddress());
//        }
//    }
//}
