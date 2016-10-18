package com.gsngames.qa.vimana.core.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.gsngames.qa.vimana.core.Resource;


/**
 * Custom Entity resolver. Provides the functions to resolves the DTD system
 * references
 * 
 * @author lnr
 * 
 */
public class DTDEntityResolver implements EntityResolver {

    private String intl;
    private String product;

    public DTDEntityResolver(String product, String intl) {
        this.intl = intl;
        this.product = product;
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

        String dtdPath = "";

        dtdPath = Resource.dataPath + File.separator + this.product;

        if (systemId.contains("intl.view.dtd")) {
            dtdPath = dtdPath + File.separator + intl + ".view.dtd";
            return new InputSource(dtdPath);
        } else if (systemId.contains("intl.testdata.dtd")) {
            dtdPath = dtdPath + File.separator + intl + ".testdata.dtd";
            return new InputSource(dtdPath);

        } else if (systemId.contains(".xml")) {
            try {
                return new InputSource(new FileInputStream(new File(new URI(systemId).getPath())));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else {
            try {
                return new InputSource(new FileInputStream(new File(new URI(systemId).getPath())));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
