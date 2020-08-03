
/* AirSimulation class
 *
 * TP1 of SE
 *
 * AM
 */

import java.util.Random;


public class AirSimulation {
    // Data
    public int nAgent1;
    public int nAgent2;
    public int nAgent3;
    public int nAgent4;
    public Aircraft a;

    // Constructor
    public AirSimulation() {
        this.nAgent1 = 0;
        this.nAgent2 = 0;
        this.nAgent3 = 0;
        this.nAgent4 = 0;
        this.a = new Aircraft();
    }

    // Agent1 : takes single reservations
    public void agent1() {
        // generates a new Customer
        Customer c = new Customer(this.nAgent1 + 1);

        // picks randomly a seat
        Random r = new Random(this.nAgent1 + 111);
        int row = r.nextInt(this.a.getNumberOfRows());
        int col = r.nextInt(this.a.getSeatsPerRow());

        // if the random seat is busy, a free seat is searched
        if (!this.a.isSeatEmpty(row, col)) {
            boolean found = false;
            int i = 0;
            int j = 0;
            while (!found && i < this.a.getNumberOfRows()) {
                j = 0;
                while (!found && j < this.a.getSeatsPerRow()) {
                    found = this.a.isSeatEmpty(i, j);
                    j++;
                }
                i++;
            }
            row = i - 1;
            col = j - 1;
        }

        // the Customer is placed at the given seat
        this.a.add(c, row, col);

        // updating counter
        this.nAgent1++;
    }

    // Agent2: takes couple reservations, and is a little lazy
    public void agent2() {
        // generates two new Customers
        Customer c1 = new Customer(this.nAgent2 + 1);
        Customer c2 = new Customer(this.nAgent2 + 1);

        // finds two near seats, starting from the back
        int rows = a.getNumberOfRows();
        int seatsPerRow = a.getSeatsPerRow();

        boolean found = false;
        for (int i = rows - 1; i >= 0; i--) {
            if (found)
                break;
            for (int j = 0; j < seatsPerRow - 1; j++) {
                if (found)
                    break;
                if (a.isSeatEmpty(i, j) && a.isSeatEmpty(i, j + 1)) {
                    // the two Customers are placed in the two near seats
                    a.add(c1, i, j);
                    a.add(c2, i, j + 1);
                    found = true;
                    System.out.println("Agent 2 : Found and added " + c1.getId() + " at " + i + ":" + j + " and " + c2.getId() + " at " + i + ":" + j + 1);

                }
            }
        }

        // if two such seats are not found, the agent ignores the request
        if (found)
            this.nAgent2++;
        else
            System.out.println("Agent 2 : I'm being lazy");
    }

    // Agent3 : moves customers with high frequency in upper rows
    public void agent3() {
        // looking for the most frequent flyer in the map
        Customer freq = a.mostFrequentFlyer();
        // getting the seat coordinates for this customer
        int row = a.getRowNumber(freq);
        int seatN = a.getSeatNumberInTheRow(freq);


        boolean moved = false;
        // verifying whether upper-row seats are available
        for (int i = 0; i < row; i++) {
            if (moved)
                break;
            for (int j = 0; j < a.getSeatsPerRow(); j++) {
                if (moved)
                    break;
                // moving the Customer
                if (a.isSeatEmpty(i, j)) {
                    System.out.println("Agent 3 : Moved " + freq.getId() + " from " + row + ":" + seatN + " to " + i + ":" + j);
                    a.freeSeat(row, seatN);
                    a.add(freq, i, j);
                    moved = true;
                }
            }
        }
        // nothing is done if no upper seat is available
        if (!moved)
            System.out.println("Agent 3 : Could not move most frequent client (already at row " + row);
        // updating counter
        this.nAgent3++;
    }

    public void agent4() {
        boolean found = false;
        Customer old = null;
        for (int i = a.getNumberOfRows()-1; i >= 1; i--) {
            if (found)
                break;
            for (int j = 0; j < a.getSeatsPerRow(); j++) {
                if (found)
                    break;
                if (!a.isSeatEmpty(i,j) && a.getCustomer(i, j).isOver60()) {
                    found = true;
                    old = a.getCustomer(i, j);
                }

            }
        }

        if (!found) {
            System.out.println("Agent 4 : Did not find old client ");
            nAgent4++;
            return;
        }

        // getting the seat coordinates for this customer
        int row = a.getRowNumber(old);
        int seatN = a.getSeatNumberInTheRow(old);


        boolean moved = false;
        // verifying whether upper-row seats are available
        for (int i = 0; i < row; i++) {
            if (moved)
                break;
            for (int j = 0; j < a.getSeatsPerRow(); j++) {
                if (moved)
                    break;
                // moving the Customer
                if (a.isSeatEmpty(i, j)) {
                    System.out.println("Agent 4 : Moved " + old.getId() + " from " + row + ":" + seatN + " to " + i + ":" + j);
                    a.freeSeat(row, seatN);
                    a.add(old, i, j);
                    moved = true;
                }
            }
        }
        // nothing is done if no upper seat is available
        if (!moved)
            System.out.println("Agent 4 : Could not move most old client (already at row " + row);
        // updating counter
        this.nAgent4++;
    }


    // Resetting
    public void reset() {
        this.nAgent1 = 0;
        this.nAgent2 = 0;
        this.nAgent3 = 0;
        this.nAgent4 = 0;
        this.a.reset();
    }

    // Printing
    public String toString() {
        String print = "AirSimulation (agent1 calls " + this.nAgent1 + ", agent2 calls " + this.nAgent2 + ", " +
                "agent3 calls " + this.nAgent3 + ", agent4 calls " + this.nAgent4 + ")\n";
        print = print + a.toString();
        return print;
    }

    // Simulation (main)
    public static void main(String[] args) throws InterruptedException {
        // sequential execution
        System.out.println("\n** Sequential execution **\n");
        AirSimulation s = new AirSimulation();
        while (!s.a.isFlightFull()) {
            s.agent1();
            s.agent2();
            s.agent3();
            s.agent4();
            System.out.println(s);
        }
        System.out.println(s);
        s.reset();
    }
}

