/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.api;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.digo.basepad.document.Place;
import vn.vnpt.digo.basepad.repository.PlaceRepository;
import vn.vnpt.digo.basepad.service.PlaceService;

/**
 *
 * @author ThanhHoai
 */

@RestController
@RequestMapping("/places")
@CrossOrigin(origins = "http://localhost:4200")
public class PlaceController {
    
    @Autowired
    private PlaceRepository placeRepo;
    
    @Autowired
    private PlaceService placeService;
    
    
    @GetMapping("")
    public ResponseEntity<?> getAllPlaces(){
        List<Place> places = placeService.getAllPlace();
        return new ResponseEntity<>(places, places.size() > 0?HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("")
    public ResponseEntity<?> createPlace(@RequestBody Place place){
        try{
            placeService.createPlace(place);
            return new ResponseEntity<Place>(place, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getSinglePlace(@PathVariable("id") ObjectId id){
        try{
            Place place = placeService.getSinglePlace(id);
            return new ResponseEntity<Place>(place, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") ObjectId id,@RequestBody Place place){
       try{
           placeService.updatePlace(id, place);
           return new ResponseEntity<>("Update todo successfully",HttpStatus.OK);
       } catch(Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable("id") ObjectId id){
        try{
            placeService.deletePlace(id);
            return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    
}
