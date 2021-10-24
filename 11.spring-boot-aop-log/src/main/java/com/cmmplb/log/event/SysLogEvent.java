package com.cmmplb.log.event;

import com.cmmplb.log.entity.Log;
import org.springframework.context.ApplicationEvent;


public class SysLogEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6227862916554900064L;

	public SysLogEvent(Log source) {
		super(source);
	}

}
