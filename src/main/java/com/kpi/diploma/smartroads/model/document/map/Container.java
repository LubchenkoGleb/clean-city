package com.kpi.diploma.smartroads.model.document.map;

import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@Document(collection = "mapObject")
public class Container extends MapObject {

    private List<MapObjectDetail> details;

    public Container() {
        details = new ArrayList<>();
    }
}
