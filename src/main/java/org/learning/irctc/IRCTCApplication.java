package org.learning.irctc;

import org.learning.di.ApplicationContext;
import org.learning.irctc.service.*;

public class IRCTCApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext();
        BookingService bookingService = applicationContext.get(BookingService.class);
        CancellationService cancellationService = applicationContext.get(CancellationService.class);

        if (bookingService.equals(cancellationService.getBookingService())) {
            System.out.println("Same booking service instance created");
        } else {
            System.out.println("Different booking service instance created");
        }

//        bookingService.bookTicket("user1", "Delhi", "Mumbai");
//        cancellationService.cancelTicket(123);
    }
}
