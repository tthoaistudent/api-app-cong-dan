/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vnpt.digo.basepad.document.Form;
import vn.vnpt.digo.basepad.dto.AffectedRowsDto;
import vn.vnpt.digo.basepad.dto.GetFormByIdDto;
import vn.vnpt.digo.basepad.dto.GetFormDto;
import vn.vnpt.digo.basepad.dto.PostFormDto;
import vn.vnpt.digo.basepad.exception.DigoHttpException;
import vn.vnpt.digo.basepad.pojo.IdResponse;
import vn.vnpt.digo.basepad.repository.FormRepository;
import vn.vnpt.digo.basepad.util.GsonHelper;
import vn.vnpt.digo.basepad.util.Translator;

/**
 *
 * @author ChiThien
 */
@Service
public class FormService {

    final String CONST_PAGEABLE_TYPE = "page";

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private Translator translator;

    public GetFormByIdDto findById(ObjectId id) {
        Optional<Form> search = formRepository.findById(id);
        if (search != null) {
            Form form = search.get();
            return GsonHelper.copyObject(form, GetFormByIdDto.class);
        } else {
            throw new DigoHttpException(11000, new String[]{translator.toLocale("lang.word.form")}, HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public ArrayList<GetFormDto> getFormForOption(String keyword) {
        return (ArrayList<GetFormDto>) formRepository.search(keyword).stream().map(GetFormDto::fromDocument).collect(Collectors.toList());
    }

    @Cacheable("formSearch")
    @Transactional
    public Slice<GetFormDto> findAll(String keyword, String spec, Pageable pageable, Short langId) {

        if (CONST_PAGEABLE_TYPE.equals(spec)) {
            return formRepository.searchPage(keyword, pageable).map(GetFormDto::fromDocument);
        }
        return formRepository.searchSlice(keyword, pageable).map(GetFormDto::fromDocument);
    }

    @CacheEvict(value = {"formSearch"}, allEntries = true)
    public AffectedRowsDto delete(ObjectId id) {
        try {
            int affected = formRepository.deleteFormById(id);
            return new AffectedRowsDto(affected);
        } catch (Exception e) {
            System.out.println("Delete form failed! " + e.getMessage());
        }
        return new AffectedRowsDto(0);
    }

    @CacheEvict(value = {"formSearch"}, allEntries = true)
    @Transactional
    public IdResponse add(PostFormDto formPayload) {
        try {
            Form form = new Form();
            form.setCode(formPayload.getCode());
            form.setName(formPayload.getName());
            form.setStatus(formPayload.getStatus());
            form.setCreatedDate(new Date());
            form.setUpdatedDate(new Date());
            formRepository.save(form);
            return new IdResponse(form.getId());
        } catch (Exception ex) {
            if (ex.getMessage().contains("E11000 duplicate key error collection")) {
                throw new DigoHttpException(10007, new String[]{translator.toLocale("lang.word.form")}, HttpServletResponse.SC_CONFLICT);
            }
        }
        return null;
    }

    @CacheEvict(value = {"formSearch"}, allEntries = true)
    @Transactional
    public AffectedRowsDto update(ObjectId id, PostFormDto formPayload) {
        try {
            Optional<Form> search = formRepository.findById(id);
            if (search != null) {
                Form form = search.get();
                Form oldForm = GsonHelper.copyObject(form);
                form = GsonHelper.copyObject(formPayload, Form.class);
                form.setId(oldForm.getId());
                form.setCreatedDate(oldForm.getCreatedDate());
                form.setUpdatedDate(new Date());
                formRepository.save(form);
                return new AffectedRowsDto(1);
            }
        } catch (Exception e) {
            if (e.getMessage().contains("E11000 duplicate key error collection")) {
                throw new DigoHttpException(10007, new String[]{translator.toLocale("lang.word.form")}, HttpServletResponse.SC_CONFLICT);
            }
            System.out.println("Update form failed! " + e.getMessage());
        }
        return new AffectedRowsDto(0);
    }

}
