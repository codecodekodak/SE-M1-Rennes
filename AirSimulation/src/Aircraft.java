
/* Aircraft class
 *
 * TP1 of SE
 *
 * Antonio Mucherino
 */


public class Aircraft
{
   // Data
   private int nRows;
   private int nSeatsPerRow;
   private int nAisles;
   private int [] aisleSeat;
   private int nEmergencyRows;
   private int [] emergencyRow;
   private volatile Customer[][] seatMap;

   // Constructor 1 (explicit definition of all attributes, except the seatMap)
   public Aircraft(int nRows,int nSeatsPerRow,int nAisles,int[] aisleSeat,int nEmergencyRows,int[] emergencyRow)
   {
      // number of rows
      if (nRows < 3)
      {
         System.out.println("Aircraft: the aircraft needs at least 3 rows!");
         System.exit(1);
      }
      this.nRows = nRows;

      // number of seats per row
      if (nSeatsPerRow < 2)
      {
         System.out.println("Aircraft: the aircraft needs to have at least 2 seats per row!");
         System.exit(1);
      }
      this.nSeatsPerRow = nSeatsPerRow;

      // number of aisles
      if (nAisles < 2)  // 1 aisle, 2 corresponding seats
      {
         System.out.println("Aircraft: the aircraft needs to have at least 1 aisle!");
         System.exit(1);
      }
      this.nAisles = nAisles;

      // location of every aisle
      this.aisleSeat = new int [this.nAisles];
      for (int i = 0; i < this.nAisles; i++)
      {
         if (aisleSeat[i] == 1 || aisleSeat[i] == nAisles - 1)
         {
            System.out.println("Aircraft: aisles cannot be located next to the windows!");
            System.exit(1);
         }
         this.aisleSeat[i] = aisleSeat[i];
      }

      // number of emergency rows
      if (nEmergencyRows < 2)
      {
         System.out.println("Aircraft: the aircraft needs at least 2 security exists!\n");
         System.exit(1);
      }
      this.nEmergencyRows = nEmergencyRows;

      // location of emergency rows
      this.emergencyRow = new int [this.nEmergencyRows];
      for (int i = 0; i < this.nEmergencyRows; i++)  this.emergencyRow[i] = emergencyRow[i];

      // seat map (Null Customer)
      this.seatMap = new Customer[nRows][nSeatsPerRow];
      for (int i = 0; i < nRows; i++)
      {
         for (int j = 0; j < nSeatsPerRow; j++)
         {
            this.seatMap[i][j] = new Customer();
         }
      }
   }

   // Constructor 2 (loads a predefined model)
   public Aircraft()
   {
      this(32,6,2,new int[] {3,4},3,new int[] {0,12,31});
      //this(8,4,2,new int[] {2,3},2,new int[] {0,7});
   }

   // Get the Number of Rows
   public int getNumberOfRows()
   {
      return this.nRows;
   }

   // Get the Number of Seats per Row
   public int getSeatsPerRow()
   {
      return this.nSeatsPerRow;
   }

   // Get the Customer in a specific seat
   public Customer getCustomer(int row, int col)
   {
      Customer c = new Customer();
      c.clone(this.seatMap[row][col]);
      return c;
   }

   // Looking for the Customer in the Aircraft with higher flyer frequency
   public Customer mostFrequentFlyer()
   {
      int max = 0;
      Customer c = new Customer();
      for (int i = 0; i < this.nRows; i++)
      {
         for (int j = 0; j < this.nSeatsPerRow; j++)
         {
            if (max < this.seatMap[i][j].getFlyerLevel())
            {
               c.clone(this.seatMap[i][j]);
               max = this.seatMap[i][j].getFlyerLevel();
            }
         }
      }
      return c;
   }

   // Get the Row Number of a given Customer
   public int getRowNumber(Customer c)
   {
      int row = -1;
      for (int i = 0; i < this.nRows; i++)
      {
         for (int j = 0; j < this.nSeatsPerRow; j++)
         {
            if (this.seatMap[i][j].equals(c))  row = i;
         }
      }
      return row;
   }

   // Get the Seat Number in the Row of a given Customer
   public int getSeatNumberInTheRow(Customer c)
   {
      int col = -1;
      for (int i = 0; i < this.nRows; i++)
      {
         for (int j = 0; j < this.nSeatsPerRow; j++)
         {
            if (this.seatMap[i][j].equals(c))  col = j;
         }
      }
      return col;
   }
 
   // Checking whether a seat is busy or not
   public boolean isSeatEmpty(int row,int col)
   {
      if (row >= this.nRows)
      {
         System.out.println("Aircraft: (" + row + "," + col + ") does not belong to the Aircraft map");
         System.exit(1);
      }
      if (col >= this.nSeatsPerRow)
      {
         System.out.println("Aircract: (" + row + "," + col + ") does not belong to the Aircraft map");
         System.exit(1);
      }
      return this.seatMap[row][col].isNullCustomer();
   }

   // Checking whether the flight is full
   public boolean isFlightFull()
   {
      boolean full = true;
      int i = 0;
      while (full && i < this.nRows)
      {
         int j = 0;
         while (full && j < this.nSeatsPerRow)
         {
            full = full && !this.seatMap[i][j].isNullCustomer();
            j++;
         }
         i++;
      }
      return full;
   }

   // Freeing a seat of the Aircraft map
   public void freeSeat(int row,int col)
   {
      this.seatMap[row][col].clone(new Customer());
   }

   // Placing a Customer in a given Aircraft seat
   public void add(Customer c, int row, int col)
   {
      if (!isSeatEmpty(row,col))
      {
         System.out.println("Aircraft: the seat (" + row + "," + col + ") is not empty");
         System.out.println(this.seatMap[row][col]);
         System.exit(1);
      }

      this.seatMap[row][col].clone(c);
   }

   // Resetting an empty Aircraft
   public void reset()
   {
      for (int i = 0; i < this.nRows; i++)
      {
         for (int j = 0; j < this.nSeatsPerRow; j++)
         {
            this.seatMap[i][j].reset();
         }
      }
   }

   // Printing
   public String toString()
   {
      int exit = 0;
      int aisle = 0;
      String print = "Aircraft:\n";

      for (int i = 0; i < this.nRows; i++)
      {
         if (this.emergencyRow[exit] == i)
         {
            print = print + "-|";
            if (exit < this.nEmergencyRows - 1)  exit++;
         }
         else
         {
            print = print + "--";
         }
      }
      print = print + "-\n";

      for (int j = 0; j < this.nSeatsPerRow; j++)
      {
         if (aisle < this.nAisles - 1)
         {
            if (this.aisleSeat[aisle] == j && this.aisleSeat[aisle+1] == j + 1)
            {
               for (int k = 0; k < this.nRows; k++)  print = print + "--";
               print = print + "-\n";
               aisle++;
            }
         }

         for (int i = 0; i < this.nRows; i++)
         {
            if (this.seatMap[i][j].isNullCustomer())
               print = print + " x";
            else
               print = print + " o";
         }
         print = print + "\n";
      }

      exit = 0;
      for (int i = 0; i < this.nRows; i++)
      {
         if (this.emergencyRow[exit] == i)
         {
            print = print + "-|";
            if (exit < this.nEmergencyRows - 1)  exit++;
         }
         else
         {
            print = print + "--";
         }
      }
      print = print + "-\n";
      
      return print;
   }
   
   // main
   public static void main(String [] args)
   {
      //Aircraft a = new Aircraft(6,4,2,aisles,2,emergencyRow);
      Aircraft a = new Aircraft();
      a.add(new Customer(1),1,1); 
      System.out.println(a);
      a.freeSeat(1,1);
      System.out.println(a.isSeatEmpty(1,1));
   }
}

