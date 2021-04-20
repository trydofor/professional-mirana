package pro.fessional.mirana.jaxb;

import pro.fessional.mirana.time.DateParser;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * @author trydofor
 * @since 2021-04-19
 */
public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String stringValue) {
        return stringValue != null ? DateParser.parseDateTime(stringValue) : null;
    }

    @Override
    public String marshal(LocalDateTime value) {
        return value != null ? value.toString() : null;
    }
}
