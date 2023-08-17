import factory.FactoryInstance;
import jaxb.LoadXml;
import simulation.Simulation;
import world.WorldDefinition;
import world.WorldInstance;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            LoadXml xml = new LoadXml();
            WorldDefinition world = xml.loadAndValidateXml("C:\\Users\\moran\\Documents\\Education\\Second year\\Java\\ex1-cigarets2.xml");
            WorldInstance worldInstance = FactoryInstance.createWorldInstance(world);
            worldInstance.getEnvironmentVariables().getProperties().forEach((name, environment)->{
                if(name.equals("cigarets-critical")) {
                    environment.setValue(1900);
                }
                if(name.equals("cigarets-decrease-already-smoker")) {
                    environment.setValue(55);
                }
                if(name.equals("cigarets-increase-non-smoker")) {
                    environment.setValue(3);
                }
                if(name.equals("cigarets-increase-already-smoker")) {
                    environment.setValue(55);
                }
            });
            Simulation simulation = new Simulation(1, worldInstance);
            simulation.runSimulation();
            System.out.println();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
