/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.repository;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vn.vnpt.digo.basepad.document.Place;

/**
 *
 * @author ThanhHoai
 */
@Repository
public interface PlaceRepository extends MongoRepository<Place , ObjectId>{
    
}
