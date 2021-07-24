/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.vnpt.digo.basepad.document.Form;
import vn.vnpt.digo.basepad.pojo.TranslateName;
import vn.vnpt.digo.basepad.util.Translator;

/**
 *
 * @author Candy-PC
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GetFormDto implements Serializable{

    @JsonSerialize(using = ToStringSerializer.class)
    ObjectId id;

    String code;

    String name;

    int status;

    @Autowired
    @JsonIgnore
    private Translator trans;

    private static Translator translator;

    @PostConstruct
    private void initStaticDao() {
        translator = this.trans;
    }

    public static GetFormDto fromDocument(Form form) {
        Short langId = translator.getCurrentLocaleId();
        GetFormDto getFormDto = new GetFormDto();
        getFormDto.setId(form.getId());
        getFormDto.setCode(form.getCode());
        for (TranslateName name : form.getName()) {
            if (Objects.equals(langId,name.getLanguageId())) {
                getFormDto.setName(name.getName());
                break;
            }
        }
        getFormDto.setStatus(form.getStatus());
        return getFormDto;
    }

}
