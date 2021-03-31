package de.dennismaas.osbdemo.web;

import de.dennismaas.osbdemo.web.model.Place;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PlacesMongoDao extends PagingAndSortingRepository<Place,String> {
    List<Place> findAll();
}
