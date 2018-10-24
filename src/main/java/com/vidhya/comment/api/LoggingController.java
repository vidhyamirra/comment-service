package com.vidhya.comment.api;

import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log/v1")
public class LoggingController {
	private static final org.apache.logging.log4j.Logger loggerNative = LogManager.getLogger(LoggingController.class);
    /** 
     * This method is to log messages of different levels.
     * @return
     */
	@GetMapping("/native")
    public String nativeLogging() {
    	loggerNative.trace("This TRACE message has been printed by Log4j2 without passing through SLF4J");
	    loggerNative.debug("This DEBUG message has been printed by Log4j2 without passing through SLF4J");
	    loggerNative.info("This INFO message has been printed by Log4j2 without passing through SLF4J");
	    loggerNative.warn("This WARN message been printed by Log4j2 without passing through SLF4J");
	    loggerNative.error("This ERROR message been printed by Log4j2 without passing through SLF4J");
	    loggerNative.fatal("This FATAL message been printed by Log4j2 without passing through SLF4J");
	    return "Howdy! Check out the Logs to see the output printed directly throguh Log4j2...";
	}

}
