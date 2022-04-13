import java.time.LocalDate;
import java.util.Objects;

public class Booking {
        private final int RoomId;
        private final LocalDate fromDate;
        private final LocalDate toDate;
        private String name;

        public Booking(LocalDate fromDate, LocalDate toDate) {
            this.RoomId = generateRandomId();
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

    public Booking(String name,int RoomId, LocalDate fromDate, LocalDate toDate) {
            this.name = name;
            this.RoomId = RoomId;
            this.fromDate = fromDate;
            this.toDate = toDate;
    }

    public String getName() {
        return name;
    }

    public int getRoomId() {
            return RoomId;
        }

        public LocalDate getFromDate() {
            return fromDate;
        }

        public LocalDate getToDate() {
            return toDate;
        }

        private int generateRandomId() {
            return (int) (Math.random() * 10000000);
        }

    @Override
    public String toString() {
        return "Booking{" +
                "RoomId=" + RoomId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return RoomId == booking.RoomId && Objects.equals(fromDate, booking.fromDate) && Objects.equals(toDate, booking.toDate) && Objects.equals(name, booking.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RoomId, fromDate, toDate, name);
    }
}

