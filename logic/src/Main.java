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
            WorldDefinition world = xml.loadAndValidateXml("C:\\Users\\moran\\Documents\\Education\\Second year\\Java\\master-ex1.xml");
            WorldInstance worldInstance = FactoryInstance.createWorldInstance(world);
            Simulation simulation = new Simulation(1, worldInstance);
            simulation.runSimulation();
            System.out.println();
        } catch (JAXBException | IOException e) {
            System.out.println("File not found");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
