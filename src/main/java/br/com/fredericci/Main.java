package br.com.fredericci;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import java.util.concurrent.atomic.AtomicLong;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {


        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        MyGlobalFilter globalFilter = new MyGlobalFilter();
        config.addFilter(globalFilter);

        LoggerConfig rootLogger = config.getRootLogger();
        LoggerFilter loggerFilter = new LoggerFilter();
        rootLogger.addFilter(loggerFilter);

        ctx.updateLoggers(config);

        log.info("Just a random log...");


        System.out.printf("Global Filter: %s %n", globalFilter.getCounter());
        System.out.printf("Logger Filter: %s %n", loggerFilter.getCounter());

    }

    public static class LoggerFilter extends AbstractFilter {

        private final AtomicLong counter = new AtomicLong();

        @Override
        public Result filter(LogEvent event) {
            counter.incrementAndGet();
            return Result.NEUTRAL;
        }

        public AtomicLong getCounter() {
            return counter;
        }
    }

    public static class MyGlobalFilter extends AbstractFilter {

        private final AtomicLong counter = new AtomicLong();

        @Override
        public Result filter(LogEvent event) {
            counter.incrementAndGet();
            return Result.NEUTRAL;
        }

        public AtomicLong getCounter() {
            return counter;
        }
    }

}
