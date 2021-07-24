/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import vn.vnpt.digo.basepad.annotation.FieldDescription;

/**
 *
 * @author ThanhHoai
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Place")
public class Place {
    
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @FieldDescription(logKey = "lang.word.id")
    private ObjectId id;
    
    private Number Code;
    

    private String Name;
    

    private String Imgage;
    

    private String Description;
    

    private Double Popolation;
    

    private double Acreage;
    

    private Number Status;
    
    
    
    
}
