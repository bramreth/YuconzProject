import java.util.Date;

/**
 * Created by bramreth on 2/21/17.
 */
public class Document {
    private String username, surname, name, dob, address, townCity, county, postcode,
            telephoneNumber, mobileNumber, emergencyContact, emergencyContactNumber;
    private int staffNo, documentID;

    /**
     * Constructor
     * @param userIn username
     */
    public Document(String userIn)
    {
        username = userIn;
    }

    /**
     * Gets the username
     * @return username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Gets the surname
     * @return surname
     */
    public String getSurname()
    {
        return surname;
    }

    /**
     * Gets the name
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the Date of Birth
     * @return dob
     */
    public String getDob()
    {
        return dob;
    }

    /**
     * Gets the address
     * @return address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Gets the county
     * @return country
     */
    public String getCounty()
    {
        return county;
    }

    /**
     * Gets the town or city
     * @return townCity
     */
    public String getTownCity()
    {
        return townCity;
    }

    /**
     * Gets the emergency contact
     * @return emergencyContact
     */
    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    /**
     * Gets the emergency contact number
     * @return emergencyContactNumber
     */
    public String getEmergencyContactNumber()
    {
        return emergencyContactNumber;
    }

    /**
     * Gets the mobile number
     * @return mobileNumber
     */
    public String getMobileNumber()
    {
        return mobileNumber;
    }

    /**
     * Gets the postcode
     * @return postcode
     */
    public String getPostcode()
    {
        return postcode;
    }

    /**
     * Gets the telephone number
     * @return telephoneNumber
     */
    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }

    /**
     * Gets the staff number
     * @return staffNo
     */
    public int getStaffNo(){
        return  staffNo;
    }

    /**
     * Gets the document id
     * @return documnetID
     */
    public int getDocumentID() {return  documentID;}

    /**
     * Sets the name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the surname
     * @param surname surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets the address
     * @param address address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the date of birth
     * @param dob date of birth
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * Sets the county
     * @param county country
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * Sets the postcode
     * @param postcode postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Sets the emergency contact
     * @param emergencyContact emergency contact
     */
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    /**
     * Sets the town or city
     * @param townCity town or city
     */
    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    /**
     * Sets the emergency contact number
     * @param emergencyContactNumber emergency contact number
     */
    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    /**
     * Sets the mobile number
     * @param mobileNumber mobile number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Sets the telephone number
     * @param telephoneNumber telephone number
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Populates the document with data
     * @param documentID document ID
     * @param staffNo Staff number
     * @param name First name
     * @param surname surname
     * @param dob date of birth
     * @param address address
     * @param townCity town or city
     * @param county county
     * @param postcode postcode
     * @param telephoneNumber telephone number
     * @param mobileNumber mobile number
     * @param emergencyContact emergency contact
     * @param emergencyContactNumber emergency  contact number
     */
    public void populateDocument(int documentID, int staffNo, String name, String surname, String dob, String address, String townCity, String county, String postcode, String telephoneNumber, String mobileNumber, String emergencyContact, String emergencyContactNumber){
        this.documentID = documentID;
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

    /**
     * Prints the information stored in a formatted way
     */
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

    /**
     * Returns a formatted string of the information
     * @return a formatted string
     */
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
