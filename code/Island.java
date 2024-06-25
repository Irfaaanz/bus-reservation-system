import java.text.DecimalFormat;
public class Island extends Ticket
{
    private boolean membership;
    DecimalFormat df = new DecimalFormat("0.00");

    public Island(int a,Customer b, String c, boolean d)
    {
        super(a, b, c);
        membership = d;
    }

    public void setIsland(int a,Customer b, String c, boolean d)
    {
        super.setTicket(a, b, c);
        membership = d;
    }

    public boolean getMembership()
    {
        return membership;
    }

    public double calculatePrice()
    {
        double charge = 0.0;
        double member;

        //base ticket price
        if(super.getDestination().equals("Pulau Kapas"))
        {
            charge = 100 * super.getQuantity();
        }
        else if(super.getDestination().equals("Pulau Perhentian")) 
        {
            charge = 150 * super.getQuantity();
        }
        else if(super.getDestination().equals("Pulau Tioman"))
        {
            charge = 200 * super.getQuantity();
        }
        else //PULAU LANGKAWI
        {
            charge = 300 * super.getQuantity();
        }

        //ticket with membership 
        if(membership)
        {
            //discount
            member = charge * 0.15;
        }
        else
        {
            member = charge;
        }
          
        return member;
    }

    public String boolString()
    {
        if(membership)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }

    public String toString() //toString for Island
    {
        return super.toString() + 
            "\nMemberShip: " + membership +
            "\nTotal Price: RM " + df.format(calculatePrice());
    }
}