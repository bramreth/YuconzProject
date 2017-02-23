import java.util.Date;

/**
 * Created by bramreth on 2/21/17.
 */
public class Document {
    private String username;
    private int staffNo;
    private String surname;
    private String name;
    private String dob ;//yyyy/mm/dd
    private String address;
    private String townCity;
    private String county;
    private String postcode;
    private String telephoneNumber;
    private String mobileNumber;
    private String emergencyContact;
    private String emergencyContactNumber;
    public Document(String userIn){
        username = userIn;
    }

    public void populateDocument(int staffNo, String name, String surname, String dob, String address,String townCity, String county, String postcode, String telephoneNumber, String mobileNumber, String emergencyContact, String EmergencyContactNumber){
        this.staffNo = staffNo;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.townCity = townCity;
        this.county = county;
        this.postcode = postcode;
        this.telephoneNumber = telephoneNumber;
        this.mobileNumber = mobileNumber;
        this.emergencyContact = emergencyContact;
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public void print(){
        System.out.println(staffNo + " "  + name + " "  + surname + " "  + dob + " "  + address + " "  + townCity + " "  + county + " " + postcode + " "  + telephoneNumber + " "  + mobileNumber + " "  + emergencyContact + " "  + emergencyContactNumber);
    }
}
