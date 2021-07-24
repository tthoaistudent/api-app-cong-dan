/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import vn.vnpt.digo.basepad.pojo.TranslateName;

/**
 *
 * @author Candy-PC
 */
@Data
@AllArgsConstructor
public class PostFormDto {

    String code;
    
    @NotNull
    ArrayList<TranslateName> name;
    
    int status;

}
