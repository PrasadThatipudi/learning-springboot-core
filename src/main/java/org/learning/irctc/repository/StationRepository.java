package org.learning.irctc.repository;

import org.learning.di.annotation.Component;

@Component
public class StationRepository {
    public void findStation(String name) {
        System.out.println("StationRepository: finding station " + name);
    }
}
