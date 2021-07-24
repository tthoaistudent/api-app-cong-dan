package vn.vnpt.digo.basepad.document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.ArrayList;
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
import vn.vnpt.digo.basepad.pojo.TranslateName;

/**
 *
 * @author ChiThien
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "form")
public class Form {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    @FieldDescription(logKey = "lang.word.id")
    private ObjectId id;

    @FieldDescription(logKey = "lang.word.code")
    private String code;

    @NotNull
    @FieldDescription(logKey = "lang.word.name")
    private ArrayList<TranslateName> name = new ArrayList<>();

    @NotNull
    @FieldDescription(logKey = "lang.word.status")
    private int status;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @FieldDescription(logKey = "lang.word.created-date")
    private Date createdDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @FieldDescription(logKey = "lang.word.updated-date")
    private Date updatedDate;
}
