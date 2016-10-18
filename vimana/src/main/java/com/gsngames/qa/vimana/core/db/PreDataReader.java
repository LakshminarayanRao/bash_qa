package com.gsngames.qa.vimana.core.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.gsngames.qa.vimana.core.Resource;
import com.gsngames.qa.vimana.core.VimanaUtil;


/**
 * First level parser which uses native parser with Entity resolver
 * 
 * @author lnr
 * 
 */

public class PreDataReader {
    private String xmlFilePath;
    private String product;
    private String intl;
    private String dataDir;

    
    public PreDataReader(String xmlFile, String product, String intl) {
        this.intl = intl;
        this.product = product;
        this.dataDir = Resource.dataPath+File.separator+product;
        this.xmlFilePath = VimanaUtil.join(new String[] { this.dataDir, xmlFile }, "/");
    }

    /**
     * This is a pre xml processor which feeds the processed xml to xstream.
     * This function is responsible for resolving entity references
     * 
     * @return
     * @throws Exception
     */
    public String getPreProcessedXML() throws Exception {
        StringWriter stringWriter = new StringWriter();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder dom = dbf.newDocumentBuilder();
        dom.setEntityResolver(new DTDEntityResolver(this.product,this.intl));
        Document xmlDocument = dom.parse(new InputSource(new FileInputStream(new File(xmlFilePath))));
        xmlDocument.getDocumentElement().normalize();

       // logger.info("xmlDocument.getTextContent():" + xmlDocument.getTextContent());

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DOMSource source = new DOMSource(xmlDocument);

        StreamResult result = new StreamResult(stringWriter);
        transformer.transform(source, result);

        String xml = stringWriter.toString();
        xml = xml.replaceAll("xml:base=\".*\"", "");

        return xml;
    }

}
