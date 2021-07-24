/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.vnpt.digo.basepad.pojo.TranslateName;

/**
 *
 * @author ChiThien
 */
@Component
public class ArrayListHelper {

    @Autowired
    @JsonIgnore
    private Translator trans;

    private static Translator translator;

    @PostConstruct
    private void initStaticDao() {
        translator = this.trans;
    }

    public static ArrayList<TranslateName> mergeTranslate(ArrayList<TranslateName> origin, ArrayList<TranslateName> update) {
        //add new Language
        ArrayList<TranslateName> newNames = new ArrayList<TranslateName>();
        boolean exist = false;
        for (TranslateName newName : update) {
            exist = false;
            for (TranslateName oldName : origin) {
                if (oldName.getLanguageId().equals(newName.getLanguageId())) {
                    oldName.setName(newName.getName());
                    exist = true;
                    break;
                }
            }
            if (exist == false) {
                newNames.add(newName);
            }
        }
        origin.addAll(newNames);
        System.out.println(GsonHelper.getJson(origin));
        return origin;
    }

      public static String getName(ArrayList<TranslateName> names) {
        if (names == null) {
            return "";
        }
        for (TranslateName name : names) {
            if (Objects.equals(translator.getCurrentLocaleId(), name.getLanguageId())) {
                return name.getName();
            }
        }
        return "";
    }
}
