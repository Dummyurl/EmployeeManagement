package app.zingo.employeemanagements.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by ZingoHotels Tech on 26-09-2018.
 */

public class Employee implements Serializable {

    @SerializedName("EmployeeId")
    private int EmployeeId;

    @SerializedName("EmployeeName")
    private String EmployeeName;

    @SerializedName("Address")
    private String Address;

    @SerializedName("Gender")
    private String Gender;

    @SerializedName("DateOfBirth")
    private String DateOfBirth;

    @SerializedName("PrimaryEmailAddress")
    private String PrimaryEmailAddress;

    @SerializedName("AlternateEmailAddress")
    private String AlternateEmailAddress;

    @SerializedName("PhoneNumber")
    private String PhoneNumber;

    @SerializedName("Password")
    private String Password;

    @SerializedName("DateOfJoining")
    private String DateOfJoining;

    @SerializedName("department")
    private Departments department;

    @SerializedName("DepartmentId")
    private int DepartmentId;

    @SerializedName("designation")
    private Designations designation;

    @SerializedName("DesignationId")
    private int DesignationId;

    @SerializedName("ManagerId")
    private int ManagerId;

    @SerializedName("Status")
    private String Status;

    @SerializedName("employeeDocument")
    private ArrayList<EmployeeDocuments> employeeDocument;

    @SerializedName("Email")
    private String Email;

    @SerializedName("loginDetails")
    private ArrayList<LoginDetails> loginDetails;

    @SerializedName("meetings")
    private ArrayList<Meetings> meetings;

    @SerializedName("employeeImages")
    private ArrayList<EmployeeImages> employeeImages;

    @SerializedName("Salary")
    private double Salary;

    @SerializedName("UserRoleId")
    private int UserRoleId;

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getPrimaryEmailAddress() {
        return PrimaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        PrimaryEmailAddress = primaryEmailAddress;
    }

    public String getAlternateEmailAddress() {
        return AlternateEmailAddress;
    }

    public void setAlternateEmailAddress(String alternateEmailAddress) {
        AlternateEmailAddress = alternateEmailAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDateOfJoining() {
        return DateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        DateOfJoining = dateOfJoining;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    public Designations getDesignation() {
        return designation;
    }

    public void setDesignation(Designations designation) {
        this.designation = designation;
    }

    public int getDesignationId() {
        return DesignationId;
    }

    public void setDesignationId(int designationId) {
        DesignationId = designationId;
    }

    public int getManagerId() {
        return ManagerId;
    }

    public void setManagerId(int managerId) {
        ManagerId = managerId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public ArrayList<EmployeeImages> getEmployeeImages() {
        return employeeImages;
    }

    public void setEmployeeImages(ArrayList<EmployeeImages> employeeImages) {
        this.employeeImages = employeeImages;
    }

    public ArrayList<LoginDetails> getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(ArrayList<LoginDetails> loginDetails) {
        this.loginDetails = loginDetails;
    }

    public ArrayList<Meetings> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<Meetings> meetings) {
        this.meetings = meetings;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        UserRoleId = userRoleId;
    }

    public static Comparator getCompareEmployee() {
        return compareEmployee;
    }

    public ArrayList<EmployeeDocuments> getEmployeeDocument() {
        return employeeDocument;
    }

    public void setEmployeeDocument(ArrayList<EmployeeDocuments> employeeDocument) {
        this.employeeDocument = employeeDocument;
    }

    public static void setCompareEmployee(Comparator compareEmployee) {
        Employee.compareEmployee = compareEmployee;
    }

    public static Comparator compareEmployee = new Comparator() {
        @Override
        public int compare(Object o, Object t1) {
            Employee profile = (Employee) o;
            Employee profile1 = (Employee) t1;
            return profile.getEmployeeName().compareTo(profile1.getEmployeeName());
        }
    };
}
