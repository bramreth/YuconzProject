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

    public void populateDocument(int staffNo, String name, String surname, String dob, String address,String townCity, String county, String postcode, String telephoneNumber, String mobileNumber, String emergencyContact, String emergencyContactNumber){
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

        System.out.println(
                        "Staff No: " + staffNo + "\n" +
                        "Name: " + name + "\n" +
                        "Surname: " + surname + "\n" +
                        "Date Of Birth: " + dob + "\n" +
                        "Address: " + address + "\n" +
                        "Town/City: " + townCity + "\n" +
                        "County: " + county + "\n" +
                        "Postcode: " + postcode + "\n" +
                        "telephone Number: " + telephoneNumber + "\n" +
                        "Mobile Number: " + mobileNumber + "\n" +
                        "Emergency Contact Name: " + emergencyContact + "\n" +
                        "Emergency Contact Number: " + emergencyContactNumber + "\n"
        );
    }
}
