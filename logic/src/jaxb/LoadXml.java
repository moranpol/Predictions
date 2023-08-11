package jaxb;

import entity.EntityDefinition;
import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import jaxb.schema.generated.PRDAction;
import jaxb.schema.generated.PRDEntity;
import jaxb.schema.generated.PRDRule;
import jaxb.schema.generated.PRDWorld;
import rule.Rule;
import world.WorldDefinition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadXml {
    private final static String JAXB_XML_PACKAGE_NAME = "jaxb.schema.generated";
    private final String filePath;
    private PRDWorld prdWorld = null;

    public LoadXml(String filePath){
        if(validateFileName(filePath)){
            this.filePath = filePath;
        }
        else{
            throw new InvalidNameException("file name must end with .xml");
        }
    }

    public WorldDefinition loadAndValidateXml() throws IOException, JAXBException {
        InputStream inputStream = Files.newInputStream(new File(this.filePath).toPath());
        this.prdWorld = deserializeFrom(inputStream);
        //todo - create world WorldDefinition with prdWorld
        WorldDefinition worldDefinition = new WorldDefinition();
        return worldDefinition;
    }

    private PRDWorld deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (PRDWorld) u.unmarshal(in);
    }

    private boolean validateFileName(String fileName){
        return (fileName.endsWith(".xml"));
    }

    private WorldDefinition validatePrdWorld(){
        EnvironmentDefinition environment = new EnvironmentDefinition(this.prdWorld.getPRDEvironment());
        Map<String,EntityDefinition> entities = new HashMap<>();
        List<Rule> rules = new ArrayList<>();

        for (PRDEntity entity : this.prdWorld.getPRDEntities().getPRDEntity()) {
            entities.put(entity.getName(), new EntityDefinition(entity));
            validateRuleActions(entities);
            for (PRDRule rule : this.prdWorld.getPRDRules().getPRDRule()) {
                rules.add(new Rule(rule));
            }
            //todo - validate termination
        }

        return null;
    }

    private void validateRuleActions(Map<String,EntityDefinition> entities){
        for (PRDRule rule : this.prdWorld.getPRDRules().getPRDRule()) {
            for (PRDAction action : rule.getPRDActions().getPRDAction()) {
                if(!entities.containsKey(action.getEntity())) {
                    throw new InvalidNameException(action.getEntity() + "not exist for action.");
                }
                if(!entities.get(action.getEntity()).getPropertiesOfAllPopulation().containsKey(action.getProperty())){
                    throw new InvalidNameException(action.getProperty() + "not exist in " + action.getEntity() + "entity for action.");
                }
            }
        }
    }
}
