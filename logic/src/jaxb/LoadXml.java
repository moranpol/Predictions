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

    public WorldDefinition loadAndValidateXml(String filePath) throws IOException, JAXBException {
        if(!validateFileName(filePath)){
            throw new InvalidNameException("file name must end with .xml");
        }
        InputStream inputStream = Files.newInputStream(new File(filePath).toPath());
        PRDWorld prdWorld = deserializeFrom(inputStream);

        return FactoryDefinition.createWorldDefinition(prdWorld);
    }

    private PRDWorld deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (PRDWorld) u.unmarshal(in);
    }

    private boolean validateFileName(String fileName){
        return (fileName.endsWith(".xml"));
    }

}