package vn.vnpt.digo.basepad.config;

import vn.vnpt.digo.basepad.properties.DigoProperties;
import vn.vnpt.digo.basepad.util.Translator;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 *
 * @author Nghia
 */
@Configuration("localeResolver")
public class LocaleResolverConfig extends AcceptHeaderLocaleResolver {
    
    @Autowired
    private DigoProperties digoProperties;
    
    @Autowired
    private Translator translator;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String localeCode = digoProperties.getLocaleCode();
        Map<String, Short> supportedLocales = digoProperties.parseSupportedLocales();
        Locale rsLocale = Locale.forLanguageTag(localeCode);
        if (request.getHeader("Accept-Language") != null) {
            Locale locale = super.resolveLocale(request);
            String languageTag = locale.toLanguageTag();
            if (supportedLocales.containsKey(languageTag)) {
                localeCode = languageTag;
                rsLocale = locale;
            }
        }
        translator.setCurrentLocaleId(null);
        if (supportedLocales.containsKey(localeCode)) {
            translator.setCurrentLocaleId(supportedLocales.get(localeCode));
        }
        LocaleContextHolder.setLocale(rsLocale);
        return rsLocale;
    }

}
