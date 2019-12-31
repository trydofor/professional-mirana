package pro.fessional.mirana.jaxb;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 只把顶层元素变成key-value的map，用来做参数签名
 *
 * @author trydofor
 * @since 2019-12-31
 */
public class StringMapXmlWriter implements XMLStreamWriter {

    private final Map<String, String> resultTree;
    private String currentKey;

    /**
     * 按key的ascii（unicode）的值排序
     *
     * @return key值排序
     */
    public static StringMapXmlWriter treeMap() {
        return new StringMapXmlWriter(new TreeMap<>());
    }

    /**
     * 按key的顺序排序
     *
     * @return key顺序排序
     */
    public static StringMapXmlWriter linkMap() {
        return new StringMapXmlWriter(new LinkedHashMap<>());
    }

    public static StringMapXmlWriter userMap(Map<String, String> map) {
        return new StringMapXmlWriter(map);
    }

    public Map<String, String> getResultTree() {
        return resultTree;
    }

    private StringMapXmlWriter(Map<String, String> map) {
        this.resultTree = map;
    }

    private void putStringValue(String text) {
        resultTree.put(currentKey, text);
    }

    @Override
    public void writeStartElement(String localName) throws XMLStreamException {
        currentKey = localName;
    }

    @Override
    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        writeStartElement(localName);
    }

    @Override
    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        writeStartElement(localName);
    }

    @Override
    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        writeEmptyElement(localName);
    }

    @Override
    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        writeEmptyElement(localName);
    }

    @Override
    public void writeEmptyElement(String localName) throws XMLStreamException {
        writeStartElement(localName);
        putStringValue("");
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        // ignore
    }

    @Override
    public void close() throws XMLStreamException {
        // ignore

    }

    @Override
    public void flush() throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeAttribute(String localName, String value) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeComment(String data) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeCData(String data) throws XMLStreamException {
        putStringValue(data);
    }

    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        // ignore
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        putStringValue(text);
    }

    @Override
    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
        writeCharacters(new String(text, start, len));
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return null;
    }

    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        // ignore
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        // ignore
    }

    @Override
    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        // ignore
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return null;
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return null;
    }
}
