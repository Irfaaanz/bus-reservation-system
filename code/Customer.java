public class Customer
{
    private String name, phoneNum;
    private int age;

    public Customer(String name, String phoneNum, int age) //parameterized constructor
    {
        this.name = name;
        this.phoneNum = phoneNum;
        this.age = age;
    }

    public void setCustomer(String a, String b, int c) //parameterized constructor
    {
        this.name = a;
        this.phoneNum = b;
        this.age = c;
    }

    //accessor
    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public String toString() //toString for Customer
    {
        return "\nCustomer Name : " + name + 
                "\nCustomer age : " + age +
                "\nPhone Number : " + phoneNum;
    }
}