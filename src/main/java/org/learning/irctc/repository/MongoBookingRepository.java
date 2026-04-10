package org.learning.irctc.repository;

import org.learning.di.annotation.Component;
import org.learning.di.annotation.Primary;
import org.learning.di.annotation.Property;

@Component
@Primary
public class MongoBookingRepository implements BookingRepository {
    private final String connectionString;

    public MongoBookingRepository(@Property("mongo-connection-uri") String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public void saveBooking() {
        System.out.println( "MongoBookingRepository: connectionString: " + connectionString);
        System.out.println("MongoBookingRepository: saving booking");
    }
}
