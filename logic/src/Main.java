import exceptions.InvalidNameException;
import jaxb.LoadXml;
import world.WorldDefinition;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            LoadXml xml = new LoadXml();
            WorldDefinition world = xml.loadAndValidateXml("C:\\Users\\moran\\Documents\\Education\\Second year\\Java\\master-ex1.xml");
            System.out.println(world);
        } catch (JAXBException | IOException e) {
            System.out.println("File not found");
        } catch (InvalidNameException e) {
            System.out.println(e.getMessage());
        }
    }
}
