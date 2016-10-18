package com.gsngames.bashwebframe.data;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;


public class NameValueMap {
    public Map<String, Object> map = new HashMap<String, Object>();
    public Map<String, Object> getMap() {
        return map;
    }

}
