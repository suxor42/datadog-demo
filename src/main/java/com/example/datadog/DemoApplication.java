package com.example.datadog;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.RequestWrapper;

@SpringBootApplication
@RestController
public class DemoApplication {


    private static final StatsDClient statsd = new NonBlockingStatsDClient(
            /* prefix to any stats; may be null or empty string */
            "mip.test",
            /* common case: localhost */
            "localhost",
            /* port */
            8125,
            /* Datadog extension: Constant tags, always applied */
            "isMipTest:true");

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping(value = "/{whatEver}", method = RequestMethod.GET)
    public String hello(@PathVariable String whatEver) {
        statsd.incrementCounter("demo", "path:" + whatEver);
	    return "Hello World!";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        statsd.incrementCounter("demo");
        return "Hello World!";
    }
}
