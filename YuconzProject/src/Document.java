import java.util.Date;

/**
 * Created by bramreth on 2/21/17.
 */
public class Document {
    private String username, surname, name, dob, address, townCity, county, postcode,
            telephoneNumber, mobileNumber, emergencyContact, emergencyContactNumber;
    private int staffNo;

    public Document(String userIn)
    {
        username = userIn;
    }
    public String getUsername()
    {
        return username;
    }
    public String getSurname()
    {
        return surname;
    }
    public String getName()
    {
        return name;
    }
    public String getDob()
    {
        return dob;
    }
    public String getAddress()
    {
        return address;
    }
    public String getCounty()
    {
        return county;
    }
    public String getTownCity()
    {
        return townCity;
    }
    public String getEmergencyContact()
    {
        return emergencyContact;
    }
    public String getEmergencyContactNumber()
    {
        return emergencyContactNumber;
    }
    public String getMobileNumber()
    {
        return mobileNumber;
    }
    public String getPostcode()
    {
        return postcode;
    }
    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }

    public int getStaffNo(){
        return  staffNo;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }
    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    public void populateDocument(int staffNo, String name, String surname, String dob, String address, String townCity, String county, String postcode, String telephoneNumber, String mobileNumber, String emergencyContact, String emergencyContactNumber){
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

    public String read(){

        return          "<html>Staff No: " + staffNo + "<br>" +
                        "Name: " + name + "<br>" +
                        "Surname: " + surname + "<br>" +
                        "Date Of Birth: " + dob + "<br>" +
                        "Address: " + address + "<br>" +
                        "Town/City: " + townCity + "<br>" +
                        "County: " + county + "<br>" +
                        "Postcode: " + postcode + "<br>" +
                        "telephone Number: " + telephoneNumber + "<br>" +
                        "Mobile Number: " + mobileNumber + "<br>" +
                        "Emergency Contact Name: " + emergencyContact + "<br>" +
                        "Emergency Contact Number: " + emergencyContactNumber + "<br><html>";
    }
}
