
package relational.model.emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RelationalModelEmulator {

  
    public static void main(String[] args) {
        
        //relational schema 'sales', containing set of 3 relations and its constraints defined
        RelationalSchema sales = new RelationalSchema();
        
        //instance of our 'agents' relation 
        Relation agents = sales.getAgentsRelation();    
              
        agents.insertTuple(createTuple(agents,"A001","Hugo","Paris",14,12346674));
        agents.insertTuple(createTuple(agents,"A002","Mukesh","Mumbai",11,12358964));
        agents.insertTuple(createTuple(agents,"A003","Alex","London",13,12458969));
        agents.insertTuple(createTuple(agents,"A004","Ivan","Toronto",15,22544166));
        agents.insertTuple(createTuple(agents,"A005","Anderson","Brisbane",13,21447739));
        agents.insertTuple(createTuple(agents,"A006","McDenny","London",15,22255588));
        agents.insertTuple(createTuple(agents,"A007","Ramasundar","Bangalore",15,25814763));
        agents.insertTuple(createTuple(agents,"A008","Alfred","New York",12,25874365));
        agents.insertTuple(createTuple(agents,"A009","Benjamin","Hampshire",11,22536178));
        agents.insertTuple(createTuple(agents,"A010","Sanchez","Madrid",14,22388644));
        agents.insertTuple(createTuple(agents,"A001","Stevens","Dublin",15,45625874)); //should be rejected for duplicate primary key value
        agents.insertTuple(createTuple(agents,"A011","Stevens","Dublin",15,45625874));
        agents.insertTuple(createTuple(agents,"A012","Lucida","San Jose",12,52981425));
        agents.insertTuple(createTuple(agents,"A005","Anderson","Brisbane",13,21447739)); //should be rejected for duplicate tuple
                    
                                     
        //instance of our 'customers' relation
        Relation customers = sales.getCustomersRelation();
        
        customers.insertTuple(createTuple(customers,"C00014","Victor","Paris","France",2,8000));
        customers.insertTuple(createTuple(customers,"C00005","Sasikant","Mumbai","India",1,7000));
        customers.insertTuple(createTuple(customers,"C00009","Ramesh","Mumbai","India",3,8000));
        customers.insertTuple(createTuple(customers,"C00022","Avinash","Mumbai","India",2,7000));
        customers.insertTuple(createTuple(customers,"C00013","Holmes","London","UK",2,6000));
        customers.insertTuple(createTuple(customers,"C00015","Stuart","London","UK",1,6000));
        customers.insertTuple(createTuple(customers,"C00003","Martin","Toronto","Canada",2,8000));
        customers.insertTuple(createTuple(customers,"C00006","Shilton","Toronto","Canada",1,10000));
        customers.insertTuple(createTuple(customers,"C00008","Karolina","Toronto","Canada",1,7000));
        customers.insertTuple(createTuple(customers,"C00004","Winston","Brisbane","Australia",1,5000));
        customers.insertTuple(createTuple(customers,"C00018","Fleming","Brisbane","Australia",2,7000));
        customers.insertTuple(createTuple(customers,"C01011","Salvador","Madrid",0,"Spain",1000));     //should be rejected for violating domain constraint
        customers.insertTuple(createTuple(customers,"C00021","Jacks","Brisbane","Australia",1,7000));
        customers.insertTuple(createTuple(customers,"C00023","Karl","London","UK",0,4000));
        customers.insertTuple(createTuple(customers,"C00024","Cook","London","UK",2,4000));
        customers.insertTuple(createTuple(customers,"C00016","Venkatpati","Bangalore","India",2,8000));
        customers.insertTuple(createTuple(customers,"C00017","Srinivas","Bangalore","India",2,8000));
        customers.insertTuple(createTuple(customers,"C00001","Micheal","New York","USA",2,3000));
        customers.insertTuple(createTuple(customers,"C00002","Bolt","New York","USA",3,5000));
        customers.insertTuple(createTuple(customers,"C00013","Erin","Los Angeles","USA",5,7000));  //should be rejected for duplicate primary key value
        customers.insertTuple(createTuple(customers,"C00020","Albert","New York","USA",3,5000));
        customers.insertTuple(createTuple(customers,"C00010","Charles","Hampshire","UK",3,6000));
        customers.insertTuple(createTuple(customers,"C00007","Oscar","Madrid","Spain",1,7000));
        customers.insertTuple(createTuple(customers,"C00011","Sergio","Madrid","Spain",3,7000));
        customers.insertTuple(createTuple(customers,"C00019","Alberto","Madrid","Spain",1,8000));
        customers.insertTuple(createTuple(customers,"C00011","Tara","London","UK",2,1000));    //should be rejected for duplicate primary key value
        customers.insertTuple(createTuple(customers,"C00025","Gary","Dublin","Ireland",2,5000));
        customers.insertTuple(createTuple(customers,"C00012","Steven","San Jose","USA",1,5000));
           
        
        //instance of our 'orders' relation
        Relation orders = sales.getOrdersRelation();
        
        orders.insertTuple(createTuple(orders,200117,800,200,"10/20/2008","C00014","A001"));
        orders.insertTuple(createTuple(orders,200106,2500,700,"04/20/2008","C00005","A002"));
        orders.insertTuple(createTuple(orders,200113,4000,600,"06/10/2008","C00022","A002"));
        orders.insertTuple(createTuple(orders,200120,500,100,"07/20/2008","C00009","A002"));
        orders.insertTuple(createTuple(orders,200123,500,100,"09/16/2008","C00022","A002"));
        orders.insertTuple(createTuple(orders,200126,500,100,"06/24/2008","C00022","A002"));
        orders.insertTuple(createTuple(orders,200128,3500,1500,"07/20/2008","C00009","A002"));
        orders.insertTuple(createTuple(orders,200133,1200,400,"06/29/2008","C00009","A002"));
        orders.insertTuple(createTuple(orders,200117,1200,400,"06/29/2008","C00009","A002")); //should be rejected for duplicate primary key value
        orders.insertTuple(createTuple(orders,200127,2500,400,"07/20/2008","C00015","A003"));
        orders.insertTuple(createTuple(orders,200104,1500,500,"03/13/2008","C00006","A004"));
        orders.insertTuple(createTuple(orders,200108,4000,600,"02/15/2008","C00008","A004"));
        orders.insertTuple(createTuple(orders,200121,1500,600,"09/23/2008","C00008","A004"));
        orders.insertTuple(createTuple(orders,200122,2500,400,"09/16/2008","C00003","A004"));
        orders.insertTuple(createTuple(orders,200222,2500,400,"09/16/2008","C00004","A004"));
        orders.insertTuple(createTuple(orders,200103,1500,700,"05/15/2008","C00021","A005"));
        orders.insertTuple(createTuple(orders,200125,2000,600,"10/10/2008","C00018","A005"));
        orders.insertTuple(createTuple(orders,200134,4200,1800,"09/25/2008","C00004","A005"));
        orders.insertTuple(createTuple(orders,200136,4200,1800,"09/25/2008","C40004","A005")); //should be rejected for violating referential integrity constraint. i.e no such custCode as C40004 in database 
        orders.insertTuple(createTuple(orders,200118,500,100,"07/20/2008","C00023","A006"));
        orders.insertTuple(createTuple(orders,200129,2500,500,"07/20/2008","C00024","A006"));
        orders.insertTuple(createTuple(orders,200112,2000,400,"05/30/2008","C00016","A007"));
        orders.insertTuple(createTuple(orders,200124,500,100,"06/20/2008","C00017","A007"));
        orders.insertTuple(createTuple(orders,200101,3000,1000,"07/15/2008","C00001","A008"));
        orders.insertTuple(createTuple(orders,200111,1000,300,"07/10/2008","C00020","A008"));
        orders.insertTuple(createTuple(orders,200114,3500,2000,"08/15/2008","C00002","A008"));
        orders.insertTuple(createTuple(orders,200116,500,100,"07/13/2008","C00010","A009"));
        orders.insertTuple(createTuple(orders,200107,4500,900,"08/30/2008","C00007","A010"));
        orders.insertTuple(createTuple(orders,200109,3500,800,"07/30/2008","C00011","A010"));
        orders.insertTuple(createTuple(orders,200110,3000,500,"04/15/2008","C00019","A010"));
        orders.insertTuple(createTuple(orders,200119,4000,700,"09/16/2008","C00007","A010"));
        orders.insertTuple(createTuple(orders,200135,2000,800,"09/16/2008","C00007","A010"));
        orders.insertTuple(createTuple(orders,200105,2500,500,"07/18/2008","C00025","A011"));
        orders.insertTuple(createTuple(orders,200130,2500,400,"07/30/2008","C00025","A011"));
        orders.insertTuple(createTuple(orders,200102,2000,300,"05/25/2008","C00012","A012"));
        orders.insertTuple(createTuple(orders,200131,900,150,"08/26/2008","C00012","A012"));
        orders.insertTuple(createTuple(orders,200137,2000,800,"09/16/2008","C00007","A110")); //should be rejected for violating referential integrity constraint. i.e no such agentCode as A110 in database 

                      
/*********DELETE/UPDATE****************************************************************************/
                 
        //agents.updateTuple("commissionPer", "<", 13, "commissionPer", 100);
        //agents.updateTuple("commissionPer", ">", 13, "commissionPer", 5);
        //orders.updateTuple("orderAmount", ">", 500, "orderAmount", 15);
        
        //agents.deleteTuple("agentCode", "==", "A010");   
        //agents.deleteTuple("commissionPer", "<", 13);   
        //agents.deleteTuple("commissionPer", "==", 13);
           
        //customers.deleteTuple("custCode", "==", "C00014");
        //customers.deleteTuple("balance", "<", 6000);
                         
        //orders.deleteTuple("agentCode", "==", "A002");
        //orders.deleteTuple("custCode", "==", "C00022");    
        //orders.deleteTuple("orderAmount", ">", 500);
        //orders.deleteTuple("orderAmount", "<", 1000);
              
        //agents.updateTuple("agentCode", "==", "A007", "agentCode", "A017"); //update tuple where agentCode=A007, set agentCode to A017
        //orders.updateTuple("orderNumber", "==", 200222, "orderAmount", 3400); //update tuple where orderNumber=200222, set orderAmount to 3400
        //orders.updateTuple("orderNumber", "==", 200222, "custCode", "C1000"); //should be rejected for violating referential integrity constraint: i.e no such custCode as C1000 in database 
     
        //agents.deleteTuple("agentCode", "==", "A017"); //delete tuple where agentCode=A017
        //orders.deleteTuple("orderNumber", "==", 200222);  //delete tuple where orderNumber=200222 
                    
             
        //agents.printRelation();
        //customers.printRelation();
        //orders.printRelation();       
        

    
/********SET OPERATIONS TEST****************************************************************/

        /*ArrayList<Attribute> meh = new ArrayList<>();    
        meh.add( new Attribute("test1", String.class));
        meh.add( new Attribute("test2", String.class));
        meh.add( new Attribute("test3", Integer.class));
        meh.add( new Attribute("test4", Integer.class));
               
        ArrayList<Attribute> meh1 = new ArrayList<>();
        meh1.add( new Attribute("test1", String.class));
        meh1.add( new Attribute("test2", String.class));
        meh1.add( new Attribute("test3", Integer.class));
        meh1.add( new Attribute("test4", Integer.class));
              
        Relation mehh = new Relation ("mehh", meh, sales);
        Relation mehh1 = new Relation ("mehh1", meh1, sales);      
        mehh.setPK(meh.get(0));
        mehh1.setPK(meh1.get(0));
        
        mehh.insertTuple(createTuple(mehh, "cheese", "milk", 44, 55));
        mehh.insertTuple(createTuple(mehh, "bread", "ches", 4, 5));
        mehh.insertTuple(createTuple(mehh, "hoi", "yea", 2, 7));
               
        mehh1.insertTuple(createTuple(mehh1, "cheese", "milk", 44, 55));
        mehh1.insertTuple(createTuple(mehh1, "bread", "ches", 4, 5));       
        mehh1.insertTuple(createTuple(mehh1, "blehh", "gahh", 8, 7));
        mehh1.insertTuple(createTuple(mehh1, "asddddd", "dddddd", 6, 12));
       
        mehh.union(mehh1).printRelation();*/
        //mehh.intersection(mehh1).printRelation();
        //mehh.setDifference(mehh1).printRelation();
               
        
/********AGGREGATE OPERATIONS TEST****************************************************************/

        /*agents.min("commissionPer").printRelation();
        agents.max("commissionPer").printRelation();*/
        //agents.average("commissionPer").printRelation();
        //agents.sum("commissionPer").printRelation();
        //agents.count("commissionPer").printRelation();
        
        
/********DEMO CASE QUERY OPERATIONS TEST****************************************************************/
        //customers.project("custName");   //Q1
        //agents.select("workingArea", "==","Bangalore").project("agentName", "phoneNo");     //Q2 
        //customers.project("custName").group(agents.project("agentName")).printRelation();   //Q3
       
        /*int size = customers.select("custCountry", "==", "USA").project("custCode").getTuples().size();      //Q4
        for(int i =0; i<size;i++){
        orders.select("custCode", "==",customers.select("custCountry", "==", "USA").project("custCode").getTuples().get(i).getAttributeValue("custCode")).printRelation();
        }      */
       
       
       //agents.group(orders).group(customers.count("custCode")).group(orders.sum("orderAmount")).printRelation();  //Q5
       //orders.sum("orderAmount").printRelation();      
       //agents.project("agentName", "phoneNo").printRelation();
    
        
    }   
     //this method returns a tuple object given a Relation and Object array of varying size
     public static Tuple createTuple(Relation r, Object... arr) {
        
        //hash map object used to create tuple object
        HashMap<String, Object> tupleHashMap = new HashMap<>();
        //attribute list from input relation
        ArrayList<Attribute> attrList = r.getAttributes();
        
        //initializing tuple hash map 
        for(int i=0;i<attrList.size();i++){
           tupleHashMap.put(attrList.get(i).getName(),arr[i]);
        }
        
        //declaration and initialization of tuple object
        Tuple newTuple = new Tuple(tupleHashMap);    
                
        return newTuple;		
    }      
}
