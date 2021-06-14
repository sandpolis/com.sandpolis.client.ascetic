//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.client.ascetic.init;

import static com.sandpolis.client.ascetic.store.window.WindowStore.WindowStore;

import com.googlecode.lanterna.gui2.AsynchronousTextGUIThread;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.SeparateTextGUIThread;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.sandpolis.client.ascetic.store.window.WindowStore;
import com.sandpolis.client.ascetic.view.login.LoginWindow;
import com.sandpolis.core.instance.InitTask;
import com.sandpolis.core.instance.TaskOutcome;

public class AsceticLoadUserInterface extends InitTask {

	@Override
	public TaskOutcome run(TaskOutcome outcome) throws Exception {
		TerminalScreen screen = new DefaultTerminalFactory().setForceTextTerminal(true).createScreen();
		WindowBasedTextGUI textGUI = new MultiWindowTextGUI(new SeparateTextGUIThread.Factory(), screen);
		((AsynchronousTextGUIThread) textGUI.getGUIThread()).start();
		screen.startScreen();
		WindowStore.gui = textGUI;

		WindowStore.create(LoginWindow::new);

		return outcome.success();
	}

	@Override
	public String description() {
		return "Load terminal user interface";
	}

}
