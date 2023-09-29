package jaxb;

import exceptions.InvalidNameException;
import factory.FactoryDefinition;
import jaxb.schema.generated.PRDWorld;
import world.WorldDefinition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;

public class LoadXml {
    private final static String JAXB_XML_PACKAGE_NAME = "jaxb.schema.generated";

    public WorldDefinition loadAndValidateXml(InputStream xmlFile){
        try {
            PRDWorld prdWorld = deserializeFrom(xmlFile);
            return FactoryDefinition.createWorldDefinition(prdWorld);
        } catch (JAXBException e) {
            throw new InvalidNameException("file path not found.");
        }
    }

    private PRDWorld deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (PRDWorld) u.unmarshal(in);
    }
}