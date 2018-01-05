package arkthepro.com.sqlitedemo;

/**
 * Created by rajesharumugam on 23-11-2016.
 */

public class Contact {
    //Private Variable
    int id;
    String name;
    String phone_number;
    //Empty Constructor
    public Contact(){

    }
    //Constructor
    public Contact(int id,String name,String phone_number){
        this.id=id;
        this.name=name;
        this.phone_number=phone_number;
         }
    public Contact(String name,String phone_number){
        this.name=name;
        this.phone_number=phone_number;
    }

    //Get id
    public int getID(){
        return this.id;
    }
    //Set id
    public void setId(int id){
        this.id=id;
    }

    //Get Name
    public String getName(){
        return this.name;
    }
    //Set Name
    public void setName(String name){
        this.name=name;
    }

    //Get Number

    public String getPhone_number(){
        return this.phone_number;
    }

    //Set Phone Number
    public void setPhone_number(String phone_number){
        this.phone_number=phone_number;
    }

}

