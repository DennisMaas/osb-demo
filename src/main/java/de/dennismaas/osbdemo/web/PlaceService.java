package de.dennismaas.osbdemo.web;

import de.dennismaas.osbdemo.web.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    private final PlacesMongoDao placesMongoDao;

    @Autowired
    public PlaceService(PlacesMongoDao placesMongoDao) {
        this.placesMongoDao = placesMongoDao;
    }

    public List<Place> getPlaces() {
        return placesMongoDao.findAll();

    }
}
