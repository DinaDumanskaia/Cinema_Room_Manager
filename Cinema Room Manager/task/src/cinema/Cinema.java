package cinema;

import java.util.Scanner;

public class Cinema {

    public static final int SEAT = Integer.MAX_VALUE;
    public static final int SPACE = Integer.MIN_VALUE;

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        Scanner scanner = new Scanner(System.in);
        int rowsY = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsX = scanner.nextInt();
        scanner.close();
        int[][] theHall = createTheHall(rowsY, seatsX);
        int seatsAmountInHall = rowsY * seatsX;
        int input;
        int ticketCounter = 0;
        int currentIncome = 0;
        do {
                System.out.println();
                System.out.println("1. Show the seats" + "\n" + "2. Buy a ticket" + "\n" + "3. Statistics" + "\n" + "0. Exit");
                Scanner scanner2 = new Scanner(System.in);
                input = scanner2.nextInt();

                switch (input) {
                    case (1):
                        printMassive(theHall);
                        break;
                    case (2):
                        boolean isAvailable;
                        int x, y;
                        do {
                            System.out.println("Enter a row number:");
                            Scanner scanner1 = new Scanner(System.in);
                            y = scanner1.nextInt();
                            System.out.println("Enter a seat number in that row:");
                            x = scanner1.nextInt();
                            isAvailable = checkIsAvailable(theHall, x, y);
                        } while (!isAvailable);

                        //считаем стоимость билета по координатам и распечатываем
                        System.out.println("Ticket price: $" + countOneTicketPrice(rowsY, seatsX, y));
                        ticketCounter++;
                        currentIncome += countOneTicketPrice(rowsY, seatsX, y);
                        //вносим изменения в массив
                        modifyTheMassive(theHall, y, x);
                        break;

                    case 3 :
                        System.out.println("Number of purchased tickets: " + ticketCounter);
                        double percent = (double) ticketCounter * 100 / seatsAmountInHall;
                        String percentString =  String.format("%4.2f", percent).replace(',', '.');
                        System.out.println("Percentage: " + percentString + "%");
                        System.out.println("Current income: $" + currentIncome);
                        System.out.println("Total income: $" + countPriceForHall(rowsY, seatsX));
                    case (0):
                        break;
                    default:
                        System.out.println("Incorrect input");
                }
        } while (input != 0);
    }

    private static boolean checkIsAvailable(int[][] theHall, int x, int y) {
        if (y < 0 || y > theHall.length - 1 || x > theHall[0].length - 1 || x < 0) {
            System.out.println("Wrong input!");
            return false;
        } else if (theHall[y][x] == -1) {
             System.out.println("That ticket has already been purchased!");
             return false;
        }
      return true;
    }

    private static void modifyTheMassive(int[][] theHall, int y, int x) {
        theHall[y][x] = -1;
    }

    private static int countOneTicketPrice(int rowsY, int seatsX, int y) {
        int oneSeatPrice;
        int totalHallPrice = rowsY * seatsX;
        if (totalHallPrice < 60) {
            oneSeatPrice = 10;
        } else if (y <= rowsY / 2) {
            oneSeatPrice = 10;
        } else {
            oneSeatPrice = 8;
        }
        return oneSeatPrice;
    }

    private static int[][] createTheHall(int rowsY, int seatsX) {
        int[][] theHall = new int[rowsY + 1][seatsX + 1];
        theHall[0][0] = SPACE;
        for (int i = 1; i <= rowsY; i++) {
            theHall[i][0] = i;
        }
        for (int i = 1; i <= seatsX; i++) {
            theHall[0][i] = i;
        }
        for (int y = 1; y < theHall.length; y++) {
            for (int x = 1; x < theHall[0].length; x++) {
                theHall[y][x] = SEAT;
            }
        }
        return theHall;
    }

    private static int countPriceForHall(int rowsY, int seatsX) {
        int totalSeats = rowsY * seatsX;
        int hallPrice;
        if (totalSeats < 60) {
            hallPrice = totalSeats * 10;
        } else {
            int frontHalfPrise = rowsY / 2 * seatsX * 10;
            int backHalfPrice = (rowsY - rowsY / 2) * seatsX * 8;
            hallPrice = frontHalfPrise + backHalfPrice;
        }
        return hallPrice;
    }

    private static void printMassive(int[][] theHall) {
        System.out.println();
        System.out.println("Cinema:");
        for (int[] chars : theHall) {
            for (int x = 0; x < chars.length; x++) {
                if (x == chars.length - 1) {
                    printCell(chars[x]);
                } else {
                    printCellWithSpace(chars[x]);
                }
            }
            System.out.println();
        }
    }

    private static void printCell(int cell) {
        if (cell == Integer.MAX_VALUE) {
            System.out.print("S");
        } else if (cell == Integer.MIN_VALUE) {
            System.out.print(" ");
        } else if (cell == -1){
            System.out.print("B");
        } else {
            System.out.print(cell);
        }
    }

    private static void printCellWithSpace(int cell) {
        printCell(cell);
        System.out.print(' ');
    }
}