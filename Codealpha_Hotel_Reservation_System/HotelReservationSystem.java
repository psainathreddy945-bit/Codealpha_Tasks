import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    boolean available;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.available = true;
    }
}

class Reservation {
    String customerName;
    int roomNumber;
    String category;

    Reservation(String customerName, int roomNumber, String category) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
    }

    @Override
    public String toString() {
        return customerName + "," + roomNumber + "," + category;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {

        initializeRooms();
        loadBookings();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAvailableRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    saveBookings();
                    System.out.println("Thank You!");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));

        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));

        rooms.add(new Room(301, "Suite"));
        rooms.add(new Room(302, "Suite"));
    }

    static void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");

        for (Room room : rooms) {
            if (room.available) {
                System.out.println("Room No: " + room.roomNumber +
                        " | Category: " + room.category);
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        viewAvailableRooms();

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        for (Room room : rooms) {
            if (room.roomNumber == roomNo && room.available) {

                System.out.println("Payment Successful!");

                room.available = false;

                Reservation reservation =
                        new Reservation(name, roomNo, room.category);

                reservations.add(reservation);

                saveBookings();

                System.out.println("Room Booked Successfully!");
                return;
            }
        }

        System.out.println("Room Not Available!");
    }

    static void cancelReservation() {

        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Reservation> iterator = reservations.iterator();

        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();

            if (reservation.roomNumber == roomNo) {

                iterator.remove();

                for (Room room : rooms) {
                    if (room.roomNumber == roomNo) {
                        room.available = true;
                    }
                }

                saveBookings();

                System.out.println("Reservation Cancelled!");
                return;
            }
        }

        System.out.println("Reservation Not Found!");
    }

    static void viewBookings() {

        if (reservations.isEmpty()) {
            System.out.println("No Bookings Found!");
            return;
        }

        System.out.println("\nBooking Details:");

        for (Reservation reservation : reservations) {
            System.out.println("Customer: " + reservation.customerName);
            System.out.println("Room No : " + reservation.roomNumber);
            System.out.println("Category: " + reservation.category);
            System.out.println("------------------------");
        }
    }

    static void saveBookings() {
        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Reservation reservation : reservations) {
                bw.write(reservation.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error Saving File!");
        }
    }

    static void loadBookings() {

        File file = new File(FILE_NAME);

        if (!file.exists())
            return;

        try (BufferedReader br =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String name = data[0];
                int roomNo = Integer.parseInt(data[1]);
                String category = data[2];

                reservations.add(
                        new Reservation(name, roomNo, category));

                for (Room room : rooms) {
                    if (room.roomNumber == roomNo) {
                        room.available = false;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error Loading File!");
        }
    }
}