
import java.util.ArrayList;
import java.util.List;

public class DtoEntitie {
    private final String name;
    private Integer population;
    private List<DtoPropertyDefinition> propertyDefinitions;
    private  List<DtoRule> rules;
    private DtoTermination termination;

    public DtoEntitie(String name, Integer population, List<DtoPropertyDefinition> propertyDefinitions, List<DtoRule> rules, DtoTermination termination) {
        this.name = name;
        this.population = population;
        this.termination = termination;
        this.propertyDefinitions = propertyDefinitions;
        this.rules = rules;

    }

    public String getName() {
        return name;
    }

    public Integer getPopulation() {
        return population;
    }

    public List<DtoPropertyDefinition> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public List<DtoRule> getRules() {
        return rules;
    }

    public DtoTermination getTermination() {
        return termination;
    }
}
