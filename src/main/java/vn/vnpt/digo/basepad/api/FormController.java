/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.api;

import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.digo.basepad.dto.AffectedRowsDto;
import vn.vnpt.digo.basepad.dto.GetFormByIdDto;

import vn.vnpt.digo.basepad.dto.GetFormDto;
import vn.vnpt.digo.basepad.dto.PostFormDto;
import vn.vnpt.digo.basepad.pojo.IdResponse;
import vn.vnpt.digo.basepad.service.FormService;
import vn.vnpt.digo.basepad.util.Translator;

/**
 *
 * @author ChiThien
 */
@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "http://localhost:4200")
public class FormController {

    @Autowired
    private FormService formService;
    
        @Autowired
    private Translator translator;

    @GetMapping("")
    public Slice<GetFormDto> getForms(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "spec", defaultValue = "slice") String spec,
            Pageable pageable) {
        return formService.findAll(keyword, spec, pageable, translator.getCurrentLocaleId());
    }


    @GetMapping("/{id}")
    public GetFormByIdDto getForm(@PathVariable ObjectId id) {
        return formService.findById(id);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public AffectedRowsDto updateForm(@PathVariable ObjectId id, @RequestBody PostFormDto formPayload) {
        return formService.update(id, formPayload);
    }

    @PostMapping(produces = "application/json")
    public IdResponse addForm(@RequestBody PostFormDto formPayload) {
        return formService.add(formPayload);
    }

    @DeleteMapping("/{id}")
    public AffectedRowsDto deleteForm(@PathVariable ObjectId id) {
        return formService.delete(id);
    }

}
