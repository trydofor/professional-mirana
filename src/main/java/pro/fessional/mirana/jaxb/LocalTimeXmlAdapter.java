package pro.fessional.mirana.jaxb;

import pro.fessional.mirana.time.DateParser;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author trydofor
 * @since 2021-04-19
 */
public class LocalTimeXmlAdapter extends XmlAdapter<String, LocalTime> {
    @Override
    public LocalTime unmarshal(String stringValue) {
        return stringValue != null ? DateParser.parseTime(stringValue) : null;
    }

    @Override
    public String marshal(LocalTime value) {
        return value != null ? value.format(DateTimeFormatter.ISO_TIME) : null;
    }
}
