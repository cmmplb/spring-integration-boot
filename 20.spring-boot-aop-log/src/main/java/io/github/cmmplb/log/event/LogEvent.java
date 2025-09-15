package io.github.cmmplb.log.event;

import io.github.cmmplb.log.entity.Log;
import org.springframework.context.ApplicationEvent;


public class LogEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6227862916554900064L;

	public LogEvent(Log source) {
		super(source);
	}

}
