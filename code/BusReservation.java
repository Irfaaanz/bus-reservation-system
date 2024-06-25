import java.io.*;
import java.util.*;
import javax.swing.JOptionPane; 
import java.text.DecimalFormat;
public class BusReservation
{
    public static void main(String[] args) throws IOException
    {    
        try
        {
            DecimalFormat df = new DecimalFormat("0.00");
            DecimalFormat dt = new DecimalFormat("0.00");

            FileReader fr = new FileReader("reservation.txt");
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            Ticket [] t = new Ticket[100];
            Customer [] cs = new Customer[100];
            int [] array = new int[100];
            int i = 0;

            while((line = br.readLine())!=null)
            {       
                StringTokenizer st = new StringTokenizer(line,",");
                String choice = st.nextToken();
                String cName = st.nextToken();
                int cAge = Integer.parseInt(st.nextToken());
                String cPhone = st.nextToken();
            
                Customer c = new Customer(cName, cPhone, cAge);
                if(choice.equalsIgnoreCase("L"))
                {
                    String tDestination = st.nextToken();
                    int tQty = Integer.parseInt(st.nextToken());
                    String tInsurance = st.nextToken();
                    array[i] = tQty;
                    t[i] = new Local(tQty, c, tDestination, tInsurance);
                }
                else
                {
                    String tDestination = st.nextToken();
                    int tQty = Integer.parseInt(st.nextToken());
                    String tMem = st.nextToken();
                    boolean tMember = false;
                    if(tMem.equalsIgnoreCase("Yes"))
                    tMember = true;
                    array[i] = tQty;
                    t[i] = new Island(tQty, c, tDestination, tMember);
                }
                i++;
            }

            PrintWriter pw = new PrintWriter("bus.txt");
            pw.println("                                         LIST OF ALL CUSTOMERS [MANAGEMENT PURPOSE]                                        ");
            pw.println("------------------------------------------------------------------------------------------------------------------------------------------|---------------|");
            pw.println(String.format("%-25s%-12s%-20s%-30s%-20s%-20s%-20s","NAME","AGE","PHONE NO","DESTINATION","QUANTITY","INSURANCE","MEMBERSHIP"));
            pw.println("------------------------------------------------------------------------------------------------------------------------------------------|---------------|");
            for(int a=0;a<i;a++)
            {
                if(t[a] instanceof Local)
                {
                    Local l = (Local)t[a];
                    pw.println(String.format("%-25s%-12s%-20s%-30s%-20s%-20s%-20s",l.customer.getName(),l.customer.getAge(),l.customer.getPhoneNum(),l.getDestination(),l.getQuantity(),l.getInsurance(),"NA"));
                }
                else if(t[a] instanceof Island)
                {
                    Island is = (Island)t[a];
                    pw.println(String.format("%-25s%-12s%-20s%-30s%-20s%-20s%-20s",is.customer.getName(),is.customer.getAge(),is.customer.getPhoneNum(),is.getDestination(),is.getQuantity(),"NA",is.boolString()));
                }
            }

            //total & average of ticket price (all customer)
            double total = 0.0, totalLo = 0.0, totalIs = 0.0, average = 0.0;
            for(int a=0;a<i;a++)
            {
                if(t[a] instanceof Local)//calculate total
                {
                    Local l = (Local)t[a];
                    totalLo += l.calculatePrice();
                }
                else if(t[a] instanceof Island)
                {
                    Island is = (Island)t[a];
                    totalIs += is.calculatePrice();
                }
                total = totalLo + totalIs;
                average = total/i;
            }

            //max quantity of ticket 
            int max = -9999;
            for(int a=0;a<i;a++)
            {
                if(t[a] instanceof Local)
                {
                    Local l = (Local)t[a];
                    if(l.getQuantity() > max)
                    max = l.getQuantity();
                }
                else if(t[a] instanceof Island)
                {
                    Island is = (Island)t[a];
                    if(is.getQuantity() > max)
                    max = is.getQuantity();
                }
                if(t[a] instanceof Local && t[a] instanceof Island)
                {
                    Local l = (Local)t[a];
                    Island is = (Island)t[a];
                    if(l.getQuantity()>is.getQuantity())
                    max = l.getQuantity();
                    else
                    max = is.getQuantity();
                }
            }

            //min quantity of ticket 
            int min = 9999;
            for(int a=0;a<i;a++)
            {
                if(t[a] instanceof Local)
                {
                    Local l = (Local)t[a];
                    if(l.getQuantity() < min)
                    min = l.getQuantity();
                }
                else if(t[a] instanceof Island)
                {
                    Island is = (Island)t[a];
                    if(is.getQuantity() < min)
                    min = is.getQuantity();
                }
                if(t[a] instanceof Local && t[a] instanceof Island)
                {
                    Local l = (Local)t[a];
                    Island is = (Island)t[a];
                    if(l.getQuantity()<is.getQuantity())
                    min = l.getQuantity();
                    else
                    min = is.getQuantity();
                }
            }
            pw.println("------------------------------------------------------------------------------------------------------------------------------------------|---------------|");
            pw.println(String.format("%-25s%-12s%-20s%-30s%-20s%-36s%-20s","TOTAL(RM)","","","","","",df.format(total)));
            pw.println(String.format("%-25s%-12s%-20s%-30s%-20s%-36s%-20s","Average(RM)","","","","","",dt.format(average)));
            pw.println("------------------------------------------------------------------------------------------------------------------------------------------|---------------|");
            pw.println(String.format("%-25s%-12s%-20s%-30s%-20s%-39s%-20s","Maximum number of Ticket","","","","","",max));
            pw.println(String.format("%-25s%-12s%-20s%-30s%-20s%-39s%-20s","Minimum number of Ticket","","","","","",min));
            pw.println("------------------------------------------------------------------------------------------------------------------------------------------|---------------|");
            
            //search customer
            String search = JOptionPane.showInputDialog("Search customer's name : ");
            boolean found = false;
            int indexFound = 0;
            for(int a=0;a<i;a++)
            {
                if(t[a] instanceof Local)
                {
                    Local l = (Local)t[a];
                    if(l.customer.getName().equals(search))
                    {
                        found = true;
                        indexFound = a;
                    }
                }
                else if(t[a] instanceof Island)
                {
                    Island is = (Island)t[a];
                    if(is.customer.getName().equals(search))
                    {
                        found = true;
                        indexFound = a;
                    }
                }
            }
            if(found)
            {
                System.out.println("THE NAME YOU SEARCHED FOR WAS FOUND!");
                String update = JOptionPane.showInputDialog("DO YOU WANT TO UPDATE YOUR PHONE NUMBER? [YES | NO] : ");
                cs[indexFound].setPhoneNum(update);
                
                //updated customer receipt
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("\t\tUpdated Customer Receipt");
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(cs[indexFound].toString());
            }    
            else
                System.out.println("THE NAME YOU SEARCHED FOR IS NOT EXIST!");

            //sort based on quantity
            int swap = 0, n = i;
            for(int w=0;w<(n-1);w++)
            {
                for(int x=0;x<(n-w-1);x++) //descending order
                {
                    if(array[x]>array[x+1])
                    {
                       swap = array[x];
                       array[x] = array[x+1];
                       array[x+1] = swap;
                    }
                }
            }

            //count for ticket quantity
            int count = 0;
            for(int x = 0; x < t.length; x++)
            {
                if(array[x] > 0)
                {
                    count += t[x].getQuantity();
                }
            }

            

            System.out.println("\n                                         LIST OF ALL CUSTOMERS [SORTING PURPOSE]                                        ");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
            for(int c=0;c<n;c++)
            {
                if(c==0)
                {
                    for(int j=0;j<n;j++)
                    {
                        if(array[c] == t[j].getQuantity())
                        System.out.println(t[j].toString());
                    }
                }
                else if(array[c] != array[c-1])
                {
                    for(int j=0;j<n;j++)
                    {
                        if(array[c] == t[j].getQuantity())
                        System.out.println(t[j].toString());  
                    }
                }
            }

            System.out.println("\nThe total ticket of this bus reservation ticket is " + count + " tickets.");

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
            pw.close();
            br.close();
        }
            catch(FileNotFoundException e){System.out.println("File cannot be found!");}
            catch(Exception e){System.out.println("Cannot read the Data!");}
    }
}