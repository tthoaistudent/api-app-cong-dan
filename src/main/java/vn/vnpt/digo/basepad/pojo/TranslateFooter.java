/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mit
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateFooter implements Serializable{
    
    private Short languageId;
    private String content;
}
