package vn.vnpt.digo.basepad.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 *
 * @author Nghia
 */
public class Translator implements Serializable{

    private Short currentLocaleId;

    private MessageSource source;

    public void setMessageSource(MessageSource source) {
        this.source = source;
    }

    public String toLocale(String name) {
        return source.getMessage(name, null, getCurrentLocale());
    }

    public String toLocale(String name, String[] arguments) {
        return source.getMessage(name, arguments, getCurrentLocale());
    }

    public void setCurrentLocaleId(Short currentLocaleId) {
        this.currentLocaleId = currentLocaleId;
    }

    public Short getCurrentLocaleId() {
        LocaleContextHolder.getLocale();
        return currentLocaleId;
    }

    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }
     }
