package example;

import com.schedule.simulator.Scheduler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Scheduler scheduler = new Scheduler();
        scheduler.loadConfiguration("input.xml", new MultiplyQueuesDymanic(20, 2), null);
        scheduler.schedule();
    }
}
