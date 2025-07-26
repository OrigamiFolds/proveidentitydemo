/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draft.dev.proveidentitydemo;

/**
 *
 * @author Mdu Sibisi
 */
public class Identity {

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String phoneNumber;



    private String flowType;
    
    

    public Identity() {

    }

    public String getFirstName() {

        return firstName;

    }

    public void setFirstName(String firstname) {

        this.firstName = firstname;

    }

    public String getLastName() {

        return lastName;

    }

    public void setLastName(String lastName) {

        this.lastName = lastName;

    }

    public String getEmailAddress() {

        return emailAddress;

    }

    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;

    }

 

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getFlowType() {
        return flowType;
    }
    
    

}
