/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.repository;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.vnpt.digo.basepad.dto.GetFormDto;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import vn.vnpt.digo.basepad.document.Form;

;

/**
 *
 * @author thiennc
 */
@Repository
public interface FormRepository extends MongoRepository<Form, ObjectId> {

    @Query(value = "{$and: [{'code': {$regex : :#{#keyword}, $options: 'i'} },{'status' : 1}]}")
    ArrayList<Form> search(@Param("keyword") String keyword);

    @Query(value = "{$or: [{'code': {$regex : :#{#keyword}, $options: 'i'} },{'name.name': {$regex : :#{#keyword}, $options: 'i'} } ]}")
    Page<Form> searchPage(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "{$or: [{'code': {$regex : :#{#keyword}, $options: 'i'} },{'name.name': {$regex : :#{#keyword}, $options: 'i'} } ]}")
    Slice<Form> searchSlice(@Param("keyword") String keyword, Pageable pageable);

    int deleteFormById(@Param("id") ObjectId id);

}
