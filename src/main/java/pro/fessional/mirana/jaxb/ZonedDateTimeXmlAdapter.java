package pro.fessional.mirana.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;

/**
 * @author trydofor
 * @since 2021-04-19
 */
public class ZonedDateTimeXmlAdapter extends XmlAdapter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime unmarshal(String stringValue) {
        return stringValue != null ? ZonedDateTime.parse(stringValue) : null;
    }

    @Override
    public String marshal(ZonedDateTime value) {
        return value != null ? value.toString() : null;
    }
}
