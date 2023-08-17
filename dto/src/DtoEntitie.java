import property.PropertyDefinition;
import rule.Rule;
import termination.Termination;

import java.util.ArrayList;
import java.util.List;

public class DtoEntitie {
    private final String name;
    private Integer population;
    private List<DtoPropertyDefinition> propertyDefinitions;
    private  List<DtoRule> rules;
    private DtoTermination termination;

    public DtoEntitie(String name, Integer population, List<PropertyDefinition> propertyDefinitions, List<Rule> rules, Termination termination) {
        this.name = name;
        this.population = population;
        //this.termination = termination;
        this.propertyDefinitions = new ArrayList<DtoPropertyDefinition>();
        for(PropertyDefinition prop : propertyDefinitions){
            this.propertyDefinitions.add(new DtoPropertyDefinition(prop.getName(), prop.getType(), prop.getRange(), prop.isRandomInit()));
        }


        this.rules = new ArrayList<>(DtoRule);
        //this.rules.addAll(rules);
    }

    public String getName() {
        return this.name;
    }

    public Integer getPopulation() {
        return this.population;
    }

    public List<PropertyDefinition> getPropertyDefinitions() {
        return this.propertyDefinitions;
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    public Termination getTermination() {
        return this.termination;
    }
}
