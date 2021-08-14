
package relational.model.emulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public class Relation {
    
    private RelationalSchema schema;
    //name of relation
    private String name;
    //every relation contains a set of tuples(rows) and attributes(columns)
    //a relation will store tuples and attributes in an array list
    private ArrayList<Tuple> tuples;
    private ArrayList<Attribute> attributes;
       
    //primary/foreign key attributes for a relation
    private Attribute primaryKey;
    private ArrayList<Attribute> foreignKeys;
    
    //list of primary key values to be used to refer to when checking referential integrity constraints
    private static ArrayList<Object> primaryKeyValues = new ArrayList<>();
       
    //this constructor creates a relation object initialized with a list of attribute names
    //our tuples arraylist is left uninitialized to allow for insertion
    public Relation(String name, Collection<Attribute> attrs, RelationalSchema schema) {
        this.name = name;
        this.attributes = new ArrayList<>(attrs); 
        this.tuples = new ArrayList<>();
        this.primaryKey = null;
        this.foreignKeys = new ArrayList<>();
        this.schema = schema;
    }
    
    public Relation(String name, ArrayList<Attribute> attrs, ArrayList<Tuple> tuples, RelationalSchema schema){
        this.name = name;
        this.attributes= new ArrayList<>(attrs);
        this.tuples= new ArrayList<>(tuples);
        this.schema = schema;
    }
         
    public Tuple insertTuple(Tuple t) { 
                                
        //checks domain constraint
        for(Attribute attr : attributes){
            //if this tuple's attribute type is not equal to the relation's attribute type, return 
            if(t.getAttributeValue(attr.getName()).getClass()!= attr.getType()){
                System.out.println("Error, domain constraint violated: " + t.getTupleHashMap() + "\n");
                return t;
            }           
        }     
        for(Tuple tuple : tuples){
            //checks for duplicate tuples
            if(tuple.getTupleHashMap().equals(t.getTupleHashMap())){
                System.out.println("Error, relation cannot have duplicate tuple: " + t.getTupleHashMap().values() + "\n");
                return t;
            }
            //checks explicit key constraint: no duplicate primary key values
            if(tuple.getTupleValueList().contains(t.getAttributeValue(primaryKey.getName()))){
                System.out.println("Error, explicit key constraint violated; duplicate primary key value: " + t.getAttributeValue(primaryKey.getName())+ "\n");
                return t;         
            }             
        }        
        //checks entity integrity constraint: no null primary key values
        if(t.getAttributeValue(primaryKey.getName())== null){
            System.out.println("Error, entity integrity constraint violated: null primary key value " + t.getAttributeValue(primaryKey.getName())+ "\n");
            return t;         
        }        
        //If relation is agent or customer, add primary key values into list; 
        //used to refer to when checking referential integrity constraints
        if(this.getName().equals("agents")||this.getName().equals("customers")){
            primaryKeyValues.add(t.getAttributeValue(this.primaryKey.getName()));
        }
        //checks referential integrity constraint by referring to list of primary key values in database
        if(this.getName().equals("orders")){          
            for(int i=0;i<foreignKeys.size();i++){
                //if list of primary key values do not contain this foreign key value, return 
                if(!primaryKeyValues.contains(t.getAttributeValue(foreignKeys.get(i).getName()))){
                    System.out.println("Error, referential integrity constraint violated; No such primary key value: " + t.getAttributeValue(foreignKeys.get(i).getName())+ "\n");
                    return t;
                }              
            }                
        }      
        tuples.add(t);      
        return t;
    }
    
    
   
    public Object deleteTuple(Object... arr) {
        
        Object attributeName = arr[0];
        Object boolOperator = arr[1];
        Object attributeValue = arr[2];
              
        ArrayList<Tuple> ordersTuples = schema.getOrdersRelation().getTuples();
        
        if(boolOperator.equals("==")){          
            //updates list of primary key values
            for(int i =0; i<tuples.size();i++){
                if(tuples.get(i).getAttributeValue((String)attributeName) == attributeValue){          
                    primaryKeyValues.remove(tuples.get(i).getAttributeValue(this.getPK().getName()));   
                }                 
            }
            if(attributeValue.getClass().equals(Integer.class)){
                //removes all tuples in this relation's tuple list where the value at firstOperand(attribute name) is equal to value given by secondOperand
                tuples.removeIf(tuple -> (int)tuple.getAttributeValue((String)attributeName) == (int)(attributeValue)); 
            }
            else{
                //removes all tuples in this relation's tuple list where the value at firstOperand(attribute name) is equal to value given by secondOperand
                tuples.removeIf(tuple -> tuple.getAttributeValue((String)attributeName) == (attributeValue)); 
            }                                 
            //set null to foreign key values that reference a primary key value in another relation
            for(int i=0;i<ordersTuples.size();i++){
                if(!this.getName().equals("orders") && !primaryKeyValues.contains(ordersTuples.get(i).getAttributeValue(this.getPK().getName()))){
                   ordersTuples.get(i).getTupleHashMap().replace((this.getPK().getName()), null);
                }               
            }         
        }               
        else if(boolOperator.equals(">")){              
            //updates list of primary key values
            for(int i =0; i<tuples.size();i++){
                if((int)tuples.get(i).getAttributeValue((String)attributeName) > (int)attributeValue){                             
                    primaryKeyValues.remove(tuples.get(i).getAttributeValue(this.getPK().getName()));
                }                       
            }
            //removes all tuples that satisfy this condition
            tuples.removeIf(tuple -> (int)tuple.getAttributeValue((String)attributeName) > (int)attributeValue);
            //set null to foreign key values that reference a primary key value in another relation
            for(int i=0;i<ordersTuples.size();i++){
                //if this foreign key value is not found in list of primary key values, set that value to null
                if(!this.getName().equals("orders") && !primaryKeyValues.contains(ordersTuples.get(i).getAttributeValue(this.getPK().getName()))){
                   ordersTuples.get(i).getTupleHashMap().replace(this.getPK().getName(), null);
                }               
            }       
        }
        else if(boolOperator.equals("<")){                
            //updates list of primary key values
            for(int i =0; i<tuples.size();i++){
                if((int)tuples.get(i).getAttributeValue((String)attributeName) < (int)attributeValue){                             
                    primaryKeyValues.remove(tuples.get(i).getAttributeValue(this.getPK().getName()));
                }                       
            }
            //removes all tuples that satisfy this condition
            tuples.removeIf(tuple -> (int)tuple.getAttributeValue((String)attributeName) < (int)attributeValue);
            //set null to foreign key values that reference a primary key value in another relation
            for(int i=0;i<ordersTuples.size();i++){
                //if this foreign key value is not found in list of primary key values, set that value to null
                if(!this.getName().equals("orders") && !primaryKeyValues.contains(ordersTuples.get(i).getAttributeValue(this.getPK().getName()))){
                   ordersTuples.get(i).getTupleHashMap().replace(this.getPK().getName(), null);
                }               
            } 
        }              
        else{
            System.out.println("Error, invalid delete operation ");
        }
        System.out.print(primaryKeyValues); 
        return arr;
    }

    //"agentCode", "==", "A007, "agentCode", "A017"
    //"orderNumber", "==", 200222, "orderAmount", 3400
    public Object updateTuple(Object... arr) {
        
        Object attributeName = arr[0];
        Object boolOperator = arr[1];
        Object attributeValue = arr[2];
        Object updatedAttribute = arr[3];
        Object newValue = arr[4];
        
        ArrayList<Tuple> ordersTuples = schema.getOrdersRelation().getTuples();
                         
        //checks entity integrity constraint: no null primary key values
        if(updatedAttribute.equals(this.getPK().getName()) && newValue == null){
            System.out.println("Error, entity integrity constraint violated: null primary key value " + newValue + "\n");
            return arr;         
        }        
        for(Tuple tuple : tuples){   
            //checks domain constraint
            if(tuple.getAttributeValue((String)updatedAttribute).getClass() != newValue.getClass()){
                System.out.println("Error, domain constraint violated: " + newValue + "\n");
                return arr;
            }        
            //checks explicit key constraint: no duplicate primary key values
            if(updatedAttribute.equals(this.getPK().getName()) && tuple.getTupleValueList().contains(newValue)){
                System.out.println("Error, explicit key constraint violated; duplicate primary key value: " + newValue + "\n");
                return arr;         
            } 
            //checks referential integrity constraint by referring to list of primary key values in database
            if(this.getName().equals("orders")){          
                for(int i=0;i<foreignKeys.size();i++){               
                    if(!primaryKeyValues.contains(newValue) && updatedAttribute.equals(foreignKeys.get(i).getName())){
                        System.out.println("Error, referential integrity constraint violated; No such primary key value: " + newValue+ "\n");         
                        return arr;
                    }
                }                
            }
            if(boolOperator.equals("==")){
                //replaces value at given attribute name
                if(tuple.getAttributeValue((String) attributeName).equals(attributeValue)){
                    tuple.getTupleHashMap().replace((String) updatedAttribute, newValue); 
                }   
                if(!this.getName().equals("orders") && updatedAttribute.equals(this.getPK().getName())){
                    //updates primary key value list 
                    primaryKeyValues.remove(attributeValue);
                    primaryKeyValues.add(newValue);
                    System.out.print(primaryKeyValues);
                    //set null to foreign key values that reference a primary key value in another relation
                    for(int i=0;i<ordersTuples.size();i++){
                        if(ordersTuples.get(i).getAttributeValue((String)updatedAttribute) == (attributeValue)){
                           ordersTuples.get(i).getTupleHashMap().replace((String)updatedAttribute, newValue);
                        }               
                    }
                }                                       
            }       
            else if(boolOperator.equals(">")){ 
                //replaces value at given attribute name given satisfied condition
                if((int)tuple.getAttributeValue((String)attributeName) > (int)attributeValue){
                    tuple.getTupleHashMap().replace((String) updatedAttribute, newValue);  
                }        
            }
            else if(boolOperator.equals("<")){
                //replaces value at given attribute name given satisfied condition
                if((int)tuple.getAttributeValue((String)attributeName) < (int)attributeValue){
                    tuple.getTupleHashMap().replace((String) updatedAttribute, newValue);  
                }
            }                     
        }                 
        return arr; 
    }
    
    
    
    
    
    
    
    
    //will take a list of attributes as input
    //attributes must be a subset of a relation or will return an error 
    public Relation project(Object... arr){
        
        Relation result = null;
        
        ArrayList<Tuple> resultTuples = new ArrayList<>();
        ArrayList<Attribute> attrs = new ArrayList();
        //intializing list of attributes given from input parameter
        for(int i=0; i<arr.length;i++){           
            Attribute attr = new Attribute((String)arr[i], arr[i].getClass());
            attrs.add(attr);
        }
        //new hashmap to be used when creating and inserting new tuples 
        HashMap<String, Object> h = new HashMap<>();
        
        //for every tuple, get its value for every attribute name in our new attribute list
        //create new tuple with these values
        for(Tuple tuple: tuples){         
            for(int i=0;i<attrs.size();i++){
                h.put(attrs.get(i).getName(), tuple.getAttributeValue(attrs.get(i).getName()));         
            }
            Tuple t = new Tuple(h);
            resultTuples.add(t);
        }
        
        result = new Relation("Project result: ", attrs, resultTuples, schema);
        
        return result;
    }
    
    //returns new arraylist of tuples selected by given condition
    public Relation select(Object... arr){
        
        Object attributeName = arr[0];
        Object boolOperator = arr[1];
        Object attributeValue = arr[2];
        
        Relation result = null;
        
        ArrayList<Tuple> resultTuples = new ArrayList<>();
              
        //iterate through this relations list of tuples and add to new list when condition is satisfied
        for(Tuple tuple : tuples){     
            if(boolOperator.equals("==")){ 
                if(tuple.getAttributeValue((String)attributeName).equals(attributeValue)){
                   resultTuples.add(tuple);
                }             
            }
            else if(boolOperator.equals(">")){              
                if((int)tuple.getAttributeValue((String)attributeName) > (int)(attributeValue)){
                   resultTuples.add(tuple);
                }            
            }          
            else if(boolOperator.equals("<")){
                if((int)tuple.getAttributeValue((String)attributeName) < (int)(attributeValue)){           
                   resultTuples.add(tuple);
                }
            }
            else{
                System.out.println("Error, invalid selection ");
                return result;
            }                            
        }
        
        result = new Relation("select Result", attributes, resultTuples, schema);
        
        //return subset of tuples in form of array list
        return result;      
    }
    
    
    
    
    
    
    
    //returns union of two relations 
    public Relation union(Relation b){
        //resulting relation originally set to null             
        Relation result = null;
        
        //checks for same number of attributes
        if(this.getAttributes().size()!= b.getAttributes().size()){
            System.out.println("These relations are not type compatible");
            return result;
        }     
        else{//checks for compatible domain
            for(int i=0;i<this.getAttributes().size();i++){            
               if(!this.getAttributes().get(i).getType().equals(b.getAttributes().get(i).getType())){
                   System.out.println("These relations are not type compatible");
                   return result;
               }               
            }
        }
        ArrayList<Tuple> resultTuples = new ArrayList<>();
        ArrayList<Tuple> tuples2 = b.getTuples();
        
        for(int i=0;i<tuples2.size();i++){          
            if(!tuples.get(i).getTupleValueList().contains(tuples2.get(i).getAttributeValue(attributes.get(i).getName()))){
                tuples.add(tuples2.get(i));                      
            }
        }
        
         result = new Relation("Union result", attributes, tuples, schema);
      
        return result;
    }
    
    public Relation intersection(Relation b){
        
        Relation result = null;
        
        //checks for same number of attributes
        if(this.getAttributes().size()!= b.getAttributes().size()){
            System.out.println("These relations are not type compatible");
            return result;
        }     
        else{//checks for compatible domains
            for(int i=0;i<this.getAttributes().size();i++){            
               if(!this.getAttributes().get(i).getType().equals(b.getAttributes().get(i).getType())){
                   System.out.println("These relations are not type compatible");
                   return result;
               }               
            }
        }
                    
       ArrayList<Tuple> tuples2 = b.getTuples();
       
       ArrayList<Tuple> mutual = new ArrayList<>();
        
        for(int i=0;i<tuples.size();i++){          
            if(tuples.get(i).getTupleValueList().contains(tuples2.get(i).getAttributeValue(attributes.get(i).getName()))){
                mutual.add(tuples2.get(i));                      
            }
        }
         
         result = new Relation("Intersection result", attributes, mutual, schema);
        
        return result;
    }
    public Relation setDifference(Relation b){
        
        Relation result = null;
        
        //checks for same number of attributes
        if(this.getAttributes().size()!= b.getAttributes().size()){
            System.out.println("These relations are not type compatible");
            return result;
        }     
        else{//checks for compatible domains
            for(int i=0;i<this.getAttributes().size();i++){            
               if(!this.getAttributes().get(i).getType().equals(b.getAttributes().get(i).getType())){
                   System.out.println("These relations are not type compatible");
                   return result;
               }               
            }
        }
        
        ArrayList<Tuple> tuples2 = b.getTuples();
        
        for(int i=0;i<tuples.size();i++){            
            if(tuples.get(i).getTupleValueList().contains(tuples2.get(i).getAttributeValue(attributes.get(i).getName()))){
                tuples.remove(tuples.get(i));
            }             
        }
        
        result = new Relation("Set Difference result", attributes, tuples, schema);
        
        return result;
    }
    public Relation crossProduct(Relation r){          
        return this;
    }
    public Relation equiJoin(Relation r){
        return this;
    }
    public Relation naturalJoin(Relation r){
        return this;
    }
    
    public Relation min(String attr){
        
        int min = (int)tuples.get(0).getAttributeValue(attr);
        
        for(Tuple tuple:tuples){           
             if((int)tuple.getAttributeValue(attr)<min){               
                 min=(int)tuple.getAttributeValue(attr);
             }
        }  
        
        ArrayList<Attribute> attrs = new ArrayList<>();
        Attribute attr1 = new Attribute("min", Integer.class);
        attrs.add(attr1);
        
        HashMap<String, Object> h = new HashMap<>();
        h.put(attrs.get(0).getName(), min);
        Tuple t = new Tuple(h);
        
        ArrayList<Tuple> tuples = new ArrayList<>();
        tuples.add(t);
        
        Relation result = new Relation("Min Result", attrs,tuples, schema );
        
        return result;
    }
    public Relation max(String attr){
           
        int max = (int)tuples.get(0).getAttributeValue(attr);
        
        for(Tuple tuple:tuples){           
             if((int)tuple.getAttributeValue(attr)>max){               
                 max=(int)tuple.getAttributeValue(attr);
             }
        }  
        
        ArrayList<Attribute> attrs = new ArrayList<>();
        Attribute attr1 = new Attribute("max", Integer.class);
        attrs.add(attr1);
        
        HashMap<String, Object> h = new HashMap<>();
        h.put(attrs.get(0).getName(), max);
        Tuple t = new Tuple(h);
        
        ArrayList<Tuple> tuples = new ArrayList<>();
        tuples.add(t);
        
        Relation result = new Relation("Max Result", attrs,tuples, schema );
        
        return result;
    }
       
    public Relation average(String attr){
        
        int avg = 0;
        
        for(Tuple tuple:tuples){                      
            avg+=(int)tuple.getAttributeValue(attr);         
        }  
        
        avg/=tuples.size();
        
        ArrayList<Attribute> attrs = new ArrayList<>();
        Attribute attr1 = new Attribute("average", Integer.class);
        attrs.add(attr1);
        
        HashMap<String, Object> h = new HashMap<>();
        h.put(attrs.get(0).getName(), avg);
        Tuple t = new Tuple(h);
        
        ArrayList<Tuple> tuples = new ArrayList<>();
        tuples.add(t);
        
        Relation result = new Relation("Average Result", attrs,tuples, schema );
        
        return result;
    }
    
    public Relation sum(String attr){
        int sum = 0;
        
        for(Tuple tuple:tuples){                      
            sum+=(int)tuple.getAttributeValue(attr);         
        }  
              
        ArrayList<Attribute> attrs = new ArrayList<>();
        Attribute attr1 = new Attribute("sum", Integer.class);
        attrs.add(attr1);
        
        HashMap<String, Object> h = new HashMap<>();
        h.put(attrs.get(0).getName(), sum);
        Tuple t = new Tuple(h);
        
        ArrayList<Tuple> tuples = new ArrayList<>();
        tuples.add(t);
        
        Relation result = new Relation("Sum Result", attrs,tuples, schema );
        
        return result;
    }
      
    public Relation count(String attr){
        int count = 0;
        
        for(Tuple tuple:tuples){                                 
            if(tuple.getAttributeNames().contains(attr)){
                count++;
            }        
        }            
        ArrayList<Attribute> attrs = new ArrayList<>();
        Attribute attr1 = new Attribute("count", Integer.class);
        attrs.add(attr1);
        
        HashMap<String, Object> h = new HashMap<>();
        h.put(attrs.get(0).getName(), count);
        Tuple t = new Tuple(h);
        
        ArrayList<Tuple> tuples = new ArrayList<>();
        tuples.add(t);
        
        Relation result = new Relation("count Result", attrs,tuples, schema );
        
        return result;
    }
    public Relation group(Relation r){
        
        Relation result = null;
        
        ArrayList<Tuple> resultTuples = new ArrayList<>();
        
        ArrayList<Attribute> resultAttrs = new ArrayList();
        
        ArrayList<Attribute> attrR = r.getAttributes();
        attributes.addAll(attrR);
        
        ArrayList<Tuple> tuplesR = r.getTuples();
        tuples.addAll(tuplesR);
        
          
        //intializing list of attributes given from input parameter
        for(int i=0; i<attributes.size();i++){           
            Attribute attr = new Attribute(attributes.get(i).getName(), attributes.get(i).getType());
            resultAttrs.add(attr);
        }
        //new hashmap to be used when creating and inserting new tuples 
        HashMap<String, Object> h = new HashMap<>();
        
        //for every tuple, get its value for every attribute name in our new attribute list
        //create new tuple with these values
        for(Tuple tuple: tuples){         
            for(int i=0;i<attributes.size();i++){
                h.put(attributes.get(i).getName(), tuple.getAttributeValue(attributes.get(i).getName()));         
            }
            Tuple t = new Tuple(h);
            resultTuples.add(t);
        }
        
        result = new Relation("Project result: ", resultAttrs, resultTuples, schema);
        
        return result;        
    }
        
    public void setPK(Attribute pk){     
        this.primaryKey = pk;   
    }
    public void setFK(Attribute fk){     
        this.foreignKeys.add(fk);    
    }    
    public Attribute getPK(){
        return this.primaryKey;
    }
    public ArrayList<Attribute> getFK(){
        return this.foreignKeys;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Attribute> getAttributes(){
        return this.attributes;
    }
    public ArrayList<Tuple> getTuples(){
        return this.tuples;
    }
    public void printRelation() {
        System.out.println("RELATION: " + this.name);
        for( Attribute attr : this.attributes ) {
                System.out.printf("%-15s", attr.getName());
        }
        System.out.println();
        for (Tuple tuple : this.tuples) {
                for(Attribute attr: this.attributes ) {
                    Object val = tuple.getAttributeValue(attr.getName());
                    System.out.printf("%-15s", val);
                }
                System.out.println();
        }
        System.out.println();		
    } 
}
