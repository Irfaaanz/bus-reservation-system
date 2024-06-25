public abstract class Ticket 
{
   protected int quantity;
   protected Customer customer;
   protected String destination;
   
   public Ticket(int qty, Customer cust, String desti)
   {
      quantity = qty;
      customer = cust;
      destination = desti;
    
   }

   public void setTicket(int a, Customer b, String c)
   {
      quantity = a;
      customer = b;
      destination = c;
   }

   public int getQuantity()
   {
      return quantity;
   }

   public Customer getDetails()
   {
      return customer;
   }

   public String getDestination()
   {
      return destination;
   }

   public abstract double calculatePrice();
   
   public String toString() //toString for Ticket
   {
      return "\nCustomer Details >> " + 
               customer.toString() +
               "\nDestination : " + destination;
   }
}