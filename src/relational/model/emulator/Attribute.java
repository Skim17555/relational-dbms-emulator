
package relational.model.emulator;

public class Attribute {
    
    //attribute name
    private String name;
    //specifies the domain of the attribute
    private Class type;
    
    //constructor for an attribute
    public Attribute(String name, Class type) {
        //sets the attribute name
        this.name = name;
        //specifies that the domain of an attribute are restricted only to string and integer data types
        if(type.equals(String.class) | type.equals(Integer.class)) {
            this.type = type;
        }
    }
    //returns attribute name
    public String getName() {
    	return name;
    }
    //returns attribute domain data type
    public Class getType() {
    	return this.type;
    }
    //returns string format of attribute information
    public String toString() {
    	return String.format("Attribute %s <{%s}>", this.name, this.type.toString());
    }
}
