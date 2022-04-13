import java.time.LocalDate;
import java.util.List;

public interface HotelManager {


    void setNumberOfRooms(int numRooms);


    boolean makeBooking(Booking booking);


    Booking getBooking(int id);


    int getNumberAvailableRooms(LocalDate dateToCheck);


    List<Booking> getAllBookingSortedByDate(LocalDate from, LocalDate to);

    List<Booking> getAllBookingByName(String name);

}