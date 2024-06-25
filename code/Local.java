import java.text.DecimalFormat;
public class Local extends Ticket
{
    private String insurance;
    DecimalFormat df = new DecimalFormat("0.00");

    public Local(int qty, Customer cust, String desti, String ins)
    {
        super(qty, cust, desti);
        insurance = ins;
    }

    public void setLocal(int qty, Customer cust, String desti, String newIns)
    {
        super.setTicket(qty, cust, desti);
        insurance = newIns;
    }

    public String getInsurance()
    {
        return insurance;
    }

    public double calculatePrice()
    {
        double charge = 0.0, total = 0.0, insuredCharge = 0;

        //base ticket price
        if(super.getDestination().equals("TBS"))
          charge = 20 * super.getQuantity();
        else if(super.getDestination().equals("Terminal Melaka Sentral")) 
          charge = 15 * super.getQuantity();
        else if(super.getDestination().equals("Kuala Perlis bus Terminal"))
          charge = 70 * super.getQuantity();
        else //Kuala Terengganu Bus Station
          charge = 30 * super.getQuantity();

        //ticket with insurance
        if(getInsurance().equalsIgnoreCase("Yes"))
          insuredCharge = charge + (1.5 * super.getQuantity());
        else 
          insuredCharge = charge; 

        //ticket with discount + insurance
        if(customer.getAge() <= 7)
          total = insuredCharge - (insuredCharge * 0.20);
        else if(customer.getAge() >= 60)
          total = insuredCharge - (insuredCharge * 0.25);
        else
          total = insuredCharge;
    
        return total;
    }

    public String toString() //toString for Local
    {
        return super.toString() + 
              "\nInsurance:" + getInsurance() + 
              "\nTotal Price: RM " + df.format(calculatePrice());
    }
        
}
