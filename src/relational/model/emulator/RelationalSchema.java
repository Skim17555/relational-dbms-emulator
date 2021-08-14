
package relational.model.emulator;

import java.util.ArrayList;

//This relational schema class contains a set of all its relations and it's constraints 
public class RelationalSchema {
    
    //our relation objects in this 'sales' relational schema
    private Relation agents;
    private Relation customers;
    private Relation orders;
    
    //primary key constraints
    private Attribute agentsPrimaryKey;
    private Attribute customersPrimaryKey;
    private Attribute ordersPrimaryKey;
    
    //foreign key constraints
    private Attribute ordersFK1;
    private Attribute ordersFK2;
    
    RelationalSchema(){
        //arrayLists containing lists of attributes
        ArrayList<Attribute> agentAttributes  = getAgentAttributes();
        ArrayList<Attribute> customerAttributes  = getCustomersAttributes();
        ArrayList<Attribute> orderAttributes  = getOrdersAttributes();
        
        //relation objects initialized with their name and its list of attributes
        agents = new Relation("agents", agentAttributes, this);
        customers = new Relation("customers", customerAttributes, this);
        orders = new Relation("orders", orderAttributes, this);          
              
        //adds primary/foreign key attributes to corresponding relation's arraylist of key attributes
        agents.setPK(agentsPrimaryKey);
        customers.setPK(customersPrimaryKey);
        orders.setPK(ordersPrimaryKey);     
        
        orders.setFK(ordersFK1);
        orders.setFK(ordersFK2);
    }
    
    //getter methods to retrieve our relation objects
    public Relation getAgentsRelation(){                
        return agents;
    }
    public Relation getCustomersRelation(){             
        return customers;
    }
    public Relation getOrdersRelation(){             
        return orders;
    } 
    //returns our initialized list of attributes 
    public ArrayList<Attribute> getAgentAttributes() {
        ArrayList<Attribute> agentsAttributes = new ArrayList<>();
        
        //defines our primary key constraint
        agentsPrimaryKey = new Attribute("agentCode", String.class);
        
        agentsAttributes.add(agentsPrimaryKey);
        agentsAttributes.add( new Attribute("agentName", String.class));
        agentsAttributes.add( new Attribute("workingArea", String.class));
        agentsAttributes.add( new Attribute("commissionPer", Integer.class));
        agentsAttributes.add( new Attribute("phoneNo", Integer.class));
              
        //returns arraylist of attribute objects, which contain attribute name and its domain type
        return agentsAttributes;
    }   
    public ArrayList<Attribute> getCustomersAttributes() {
        ArrayList<Attribute> customersAttributes = new ArrayList<>();
        
        //defines our primary key constraint
        customersPrimaryKey = new Attribute("custCode", String.class);
        
        customersAttributes.add(customersPrimaryKey);
        customersAttributes.add( new Attribute("custName", String.class));
        customersAttributes.add( new Attribute("custCity", String.class));
        customersAttributes.add( new Attribute("custCountry", String.class));
        customersAttributes.add( new Attribute("grade", Integer.class));
        customersAttributes.add( new Attribute("balance", Integer.class));
        
        //returns arraylist of attribute objects, which contain attribute name and its domain type
        return customersAttributes;
    }   
    public ArrayList<Attribute> getOrdersAttributes() {
        ArrayList<Attribute> ordersAttributes = new ArrayList<>();
        
        //defines our primary key constraint
        ordersPrimaryKey = new Attribute("orderNumber", Integer.class);
        //defines our referenced foreign key constraints
        ordersFK1 = customersPrimaryKey;
        ordersFK2 = agentsPrimaryKey;
        
        ordersAttributes.add(ordersPrimaryKey);
        ordersAttributes.add( new Attribute("orderAmount", Integer.class));
        ordersAttributes.add( new Attribute("advanceAmount", Integer.class));
        ordersAttributes.add( new Attribute("orderDate", String.class));
        ordersAttributes.add(ordersFK1);
        ordersAttributes.add(ordersFK2);
                   
        //returns arraylist of attribute objects, which contain attribute name and its domain type
        return ordersAttributes;
    }
}

    
   

    


