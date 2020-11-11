package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    static void start() throws InvalidStateException {
        final Supplier<ApplicationContext> applicationContextSupplier = NassaContext::getInstance; // todo
        final NassaContext nassaContext = NassaContext.getInstance();
        final ApplicationMenu appMenu = applicationContextSupplier::get;

        nassaContext.init();
        appMenu.printAvailableOptions();
        appMenu.handleUserInput();
    }
}
