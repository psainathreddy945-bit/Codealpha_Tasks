# Hotel Reservation System

A simple Java console application for managing hotel room reservations.

## Features

- View available rooms by category
- Book a room for a customer
- Cancel an existing reservation
- View booking details
- Save and load bookings using `bookings.txt`

## Project Structure

```text
Hotel-Reservation-System/
|-- HotelReservationSystem.java
|-- bookings.txt
|-- README.md
`-- screenshots/
    |-- output1.png
    `-- output2.png
```

## Room Categories

- Standard: Rooms 101, 102
- Deluxe: Rooms 201, 202
- Suite: Rooms 301, 302

## How to Run

Compile the Java file:

```bash
javac HotelReservationSystem.java
```

Run the application:

```bash
java HotelReservationSystem
```

## Data Storage

Bookings are saved automatically in `bookings.txt` using this format:

```text
CustomerName,RoomNumber,Category
```

## Screenshots

Add output screenshots inside the `screenshots` folder:

- `screenshots/output1.png`
- `screenshots/output2.png`
