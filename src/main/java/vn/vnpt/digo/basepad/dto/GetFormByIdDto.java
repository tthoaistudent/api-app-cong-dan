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
import java.util.ArrayList;
import java.util.List;
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
public class GetFormByIdDto implements Serializable{

    @JsonSerialize(using = ToStringSerializer.class)
    ObjectId id;

    String code;

    ArrayList<TranslateName> name;

    int status;


}
