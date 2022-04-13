import java.time.LocalDate;
import java.util.*;

public class Hotel implements HotelManager {

    private HashMap<Integer, Booking> reservations;
    private int numberOfRooms;
    private HashMap<String,Booking> reservations2;
    public Hotel () {
        reservations = new HashMap<>();
        reservations2 = new HashMap<>();
        numberOfRooms = 0;
    }

    //设置房间总量
    @Override
    public void setNumberOfRooms (int numRooms) {
        this.numberOfRooms = numRooms;
    }

    //预定
    @Override
    public synchronized boolean  makeBooking (Booking booking) {
        int nonAvailableRooms = 0;
        for (Map.Entry<Integer, Booking> entry : reservations.entrySet()) {
            if (reservationsAreOverlapping(booking, entry.getValue())) {
                nonAvailableRooms++;
            }
        }
        if (nonAvailableRooms == this.numberOfRooms) {
            return false;
        } else {
            reservations2.put(booking.getName(),booking);
            reservations.put(booking.getRoomId(), booking);
            return true;
        }
    }

    private boolean reservationsAreOverlapping (Booking newReservation, Booking existedReservation) {
        if (newReservation.getFromDate().isAfter(existedReservation.getToDate()) ||
                newReservation.getToDate().isBefore(existedReservation.getFromDate())) {
            return false;
        }
        return true;
    }


//通过房间号查找房
    @Override
    public Booking getBooking (int RoomId ) {
        return reservations.get(RoomId);
    }

    @Override
    public int getNumberAvailableRooms (LocalDate dateToCheck) {
        int availableRooms = this.numberOfRooms;
        for (Booking res : reservations.values()) {
            if (dateToCheck.isAfter(res.getFromDate()) && dateToCheck.isBefore(res.getToDate())) {
                availableRooms--;
            }
        }
        return availableRooms;
    }


//通过日期获得预定信息
    private List<Booking> getReservationBetweenDates (LocalDate from, LocalDate to) {
        List<Booking> out = new ArrayList<>();
        for (Booking res : reservations.values()) {
            if ((res.getFromDate().equals(from) || res.getFromDate().isAfter(from)) &&
                    (res.getToDate().equals(to) || res.getToDate().isBefore(to))) {
                out.add(res);
            }
        }
        return out;
    }

    //所有预定房间日期排序
    @Override
    public List<Booking> getAllBookingSortedByDate (LocalDate from, LocalDate to) {
        List<Booking> out = getReservationBetweenDates(from, to);
        Collections.sort(out, Comparator.comparing(Booking::getFromDate).thenComparing(Booking::getToDate));
        return out;
    }

    //通过名字显示预定
    @Override
    public List<Booking> getAllBookingByName(String name) {
        List<Booking> out = new ArrayList<>();
        for (Booking res : reservations2.values()){
            if (res.getName().equals(name)) {
                out.add(res);
            }
        }
        return out;
    }


    public static void main(String args[]) {

        Hotel hilton = new Hotel();
        hilton.numberOfRooms = 99;

        Booking r1 = new Booking("jason",55,LocalDate.of(2022, 4, 7),
                LocalDate.of(2022, 4, 10));
        Booking r2 = new Booking("jack",49,LocalDate.of(2022, 4,8),
                LocalDate.of(2022, 4, 10));
        Booking r3 = new Booking("sam",47,LocalDate.of(2022, 4, 7),
                LocalDate.of(2022, 4, 10));
        Booking r4 = new Booking("bob",46,LocalDate.of(2022, 4, 7),
                LocalDate.of(2020, 4, 10));
        Booking r5 = new Booking("zara",45,LocalDate.of(2022, 4, 9),
                LocalDate.of(2022, 4, 10));
        Booking r6 = new Booking("jacky",99,LocalDate.of(2022, 4, 9),
                LocalDate.of(2022, 4, 14));
        Booking r7 = new Booking("jacky",44,LocalDate.of(2022,4,15),
                LocalDate.of(2022,5,14));



        hilton.makeBooking(r2);
        hilton.makeBooking(r3);
        hilton.makeBooking(r4);
        hilton.makeBooking(r5);
        hilton.makeBooking(r6);
        hilton.makeBooking(r7);
//多线程测试预定安全
        Thread thread1 = new Thread(() -> {
            try {
                hilton.makeBooking(r1);
            } catch ( Exception e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                hilton.makeBooking(r1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        List<Booking> allBookingSortedByDate = hilton.getAllBookingSortedByDate(LocalDate.of(2022, 4, 7),
                LocalDate.of(2022, 4, 10));
//打印所有预定房间
        System.out.println(allBookingSortedByDate);
        //显示该日期可预定房间
        System.out.println(hilton.getNumberAvailableRooms(LocalDate.of(2022, 4, 16)));

//通过房间号显示订单历史
        System.out.println(hilton.getBooking(47));
        //通过名字显示预定
        System.out.println(hilton.getAllBookingByName("jacky"));


    }
}