
package relational.model.emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tuple {
    
    //each tuple(row) will be repesented using a map:
    //Map<attribute type(string name), type of mapped values> 
    private Map<String, Object> tValues;
    
    //this constructor creates a tuple object by initializing tValues with input map in parameter
    public Tuple(Map<String, Object> attrValues) {
        this.tValues = new HashMap<>(attrValues);
    }
        
    //returns the value that is mapped to the key attrName
    public Object getAttributeValue(String attrName) {
        return tValues.get(attrName);
    }
    //returns hashmap of tuple values
    public Map<String, Object> getTupleHashMap(){
        return tValues;
    }
    //returns set of all attribute names
    public Set<String> getAttributeNames(){
        return tValues.keySet();
    }
    //returns arraylist of values conatined in the map
    public ArrayList<Object> getTupleValueList(){
        return new ArrayList<Object>(tValues.values());
    }   
    //returns string format of tuple values
    public String toString() {
        String str = "";
        for (Map.Entry<String, Object> entry : tValues.entrySet()) {
            str += entry.getKey()+" : "+entry.getValue() + '\t';
        }
        return str;
    } 
}
