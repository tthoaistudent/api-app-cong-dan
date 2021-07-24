/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.dto;

import vn.vnpt.digo.basepad.util.Translator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author DELL
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigoHttpExceptionDto {
    
    public static String MESSAGE_SOURCE_PREFIX = "digo.http.response.error.";
    
    private int code;
    private String message;
    
    public DigoHttpExceptionDto(int code) {
        setCode(code);
    }
    
    public DigoHttpExceptionDto bindMessageSource(Translator translator) {
        setMessage(translator.toLocale(MESSAGE_SOURCE_PREFIX + getCode()));
        return this;
    }
    
    public DigoHttpExceptionDto bindMessageSource(Translator translator, String[] args) {
        setMessage(translator.toLocale(MESSAGE_SOURCE_PREFIX + getCode(), args));
        return this;
    }
    
    @Override
    public String toString() {
        return "DIGO-" + getCode() + ": " + getMessage();
    }
}
