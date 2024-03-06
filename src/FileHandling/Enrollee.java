package FileHandling;

public class Enrollee {
    String userId;
    String firstName;
    String lastName;
    int version;
    String insuranceCompany;

    public Enrollee(String userId, String firstName, String lastName, int version, String insuranceCompany) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.version = version;
        this.insuranceCompany = insuranceCompany;
    }
}
