package manager;

import jaxb.LoadXml;
import world.WorldDefinition;
import dto.DtoXmlPath;


import javax.xml.bind.JAXBException;
import java.io.IOException;

public class LogicManager {
    private WorldDefinition worldDefinition;

    private boolean ReadXmlFile(DtoXmlPath dtoXmlPath) throws JAXBException, IOException {
        LoadXml loadXmlfile = new LoadXml();
        this.worldDefinition =  loadXmlfile.loadAndValidateXml(dtoXmlPath.getPath());

        return true;//?
    }


}
