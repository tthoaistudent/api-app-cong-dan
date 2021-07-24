/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.digo.basepad.document.Place;
import vn.vnpt.digo.basepad.exception.DigoHttpException;
import vn.vnpt.digo.basepad.repository.PlaceRepository;
import vn.vnpt.digo.basepad.util.Translator;

/**
 *
 * @author ThanhHoai
 */
@Service
public class PlaceService {
    
    @Autowired
    private PlaceRepository placeRepo;
    
    @Autowired
    private Translator translator;
    
    public List<Place> getAllPlace(){
        List<Place> place = placeRepo.findAll();
        if(place.size()>0){
            return place;
        }else{
            return new ArrayList<Place>();
        }
    }
    
    public Place getSinglePlace(ObjectId id) throws DigoHttpException{
        Optional<Place> place = placeRepo.findById(id);
        if(!place.isPresent()){
           throw new DigoHttpException(11000, new String[]{translator.toLocale("lang.word.form")}, HttpServletResponse.SC_NOT_FOUND);
        }else{
            return place.get();
        }
    }
    
    
    public void createPlace(Place place){
        try{
            Place placeNew = new Place();
            placeNew.setCode(place.getCode());
            placeNew.setName(place.getName());
            placeNew.setImgage(place.getImgage());
            placeNew.setDescription(place.getDescription());
            placeNew.setPopolation(place.getPopolation());
            placeNew.setAcreage(place.getAcreage());
            placeNew.setStatus(place.getStatus());
            System.out.println(place.getId());
            placeRepo.save(place);
        }catch(Exception e){
            if (e.getMessage().contains("E11000 duplicate key error collection")) {
                throw new DigoHttpException(10007, new String[]{translator.toLocale("lang.word.form")}, HttpServletResponse.SC_CONFLICT);
            }
        }
    }
    
    
    public void updatePlace(ObjectId id, Place place) throws DigoHttpException{
        Optional<Place> optional = placeRepo.findById(id);
        
        if(optional.isPresent()){
            Place placeUpdate = optional.get();
            placeUpdate.setCode(place.getCode());
            placeUpdate.setName(place.getName());
            placeUpdate.setImgage(place.getImgage());
            placeUpdate.setDescription(place.getDescription());
            placeUpdate.setPopolation(place.getPopolation());
            placeUpdate.setAcreage(place.getAcreage());
            placeUpdate.setStatus(place.getStatus());
            placeRepo.save(placeUpdate);
        }else{
           throw new DigoHttpException(11000, new String[]{translator.toLocale("lang.word.form")}, HttpServletResponse.SC_NOT_FOUND);
        }
        
        
        
    }
    
    
    public void deletePlace(ObjectId id) throws DigoHttpException{
        Optional<Place> place = placeRepo.findById(id);
        if(place.isPresent()){
            placeRepo.deleteById(id);
        }else{
            throw new DigoHttpException(11000, new String[]{translator.toLocale("lang.word.form")}, HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    
    
   
}
