//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.client.ascetic.logging;

import com.sandpolis.client.ascetic.view.log.LogPanelAppender;
import com.sandpolis.core.instance.logging.DefaultLogbackConfigurator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

public class LoggingConfigurator extends DefaultLogbackConfigurator {

	@Override
	public void configure(LoggerContext lc) {

		var appender = new LogPanelAppender();
		appender.setContext(lc);
		appender.setName("LogPanel");

		var encoder = new LayoutWrappingEncoder<ILoggingEvent>();
		encoder.setContext(lc);

		var layout = new PatternLayout();
		layout.setPattern(PLAIN);
		layout.setContext(lc);
		layout.start();

		encoder.setLayout(layout);

		appender.setEncoder(encoder);
		appender.start();

		Logger root = lc.getLogger(Logger.ROOT_LOGGER_NAME);
		root.addAppender(appender);

		configureLevels(lc);
	}
}
