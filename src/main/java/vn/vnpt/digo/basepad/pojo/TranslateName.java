package vn.vnpt.digo.basepad.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ChiThien
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateName implements Serializable{

    private Short languageId;
    
    private String name;
}
