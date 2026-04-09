package org.learning.irctc;

import org.learning.di.ApplicationContext;
import org.learning.irctc.service.*;

public class IRCTCApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        CancellationService cancellationService = applicationContext.getBean(CancellationService.class);

        System.out.println("======================================================================");
        if (cancellationService == null) {
            System.out.println("Cancellation service bean not found in the application context.");
        } else {
            System.out.println("Cancellation service bean found in the application context.");
        }
        System.out.println("======================================================================");
    }
}
