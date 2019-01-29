package uk.ac.ebi.pride.tools.mzxml_parser.mzxml.xml.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AnySimpleTypeAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) {
        return v;
    }

    @Override
    public String marshal(String v) {
        if (v != null)
            return v;
        else
            return null;
    }
}
