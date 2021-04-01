package de.dennismaas.osbdemo.web.service;

import de.dennismaas.osbdemo.web.model.Place;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {


    public List<Place> getPlaces() {
        return List.of(
                new Place("id1", "place1"),
                new Place("id2", "place2"));

    }

}
