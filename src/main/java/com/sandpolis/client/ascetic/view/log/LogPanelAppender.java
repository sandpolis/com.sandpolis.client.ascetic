//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.client.ascetic.view.log;

import com.googlecode.lanterna.gui2.TextBox;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;

public class LogPanelAppender extends AppenderBase<ILoggingEvent> {

	public static LogPanelAppender instance;

	private TextBox textbox;

	private Encoder<ILoggingEvent> encoder;

	public LogPanelAppender() {
		if (instance != null)
			throw new IllegalStateException();

		instance = this;
	}

	public void setOutput(TextBox textbox) {
		this.textbox = textbox;
	}

	public void setEncoder(Encoder<ILoggingEvent> encoder) {
		this.encoder = encoder;
	}

	@Override
	protected void append(ILoggingEvent eventObject) {
		if (textbox != null) {
			for (String line : new String(encoder.encode(eventObject)).split("\n"))
				textbox.addLine(line);
		}
	}

}
