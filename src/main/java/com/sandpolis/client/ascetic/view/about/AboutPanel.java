//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.client.ascetic.view.about;

import static com.googlecode.lanterna.SGR.BOLD;
import static com.googlecode.lanterna.gui2.GridLayout.Alignment.BEGINNING;
import static com.googlecode.lanterna.gui2.GridLayout.Alignment.CENTER;

import java.util.Date;

import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.sandpolis.core.foundation.util.TextUtil;
import com.sandpolis.core.instance.Entrypoint;

public class AboutPanel extends Panel {

	public AboutPanel() {
		super(new GridLayout(2));
		init();
	}

	private void init() {

		Label art = new Label(TextUtil.getSandpolisArt());
		addComponent(art, GridLayout.createLayoutData(CENTER, BEGINNING, true, false, 2, 1));

		Label lbl_version = new Label("Sandpolis Version").addStyle(BOLD);
		addComponent(lbl_version);

		Label val_version = new Label(Entrypoint.data().so_build().getProperty("instance.version"));
		addComponent(val_version);

		Label lbl_timestamp = new Label("Build Timestamp").addStyle(BOLD);
		addComponent(lbl_timestamp);

		Label val_timestamp = new Label(
				new Date(Long.parseLong(Entrypoint.data().so_build().getProperty("build.timestamp"))).toString());
		addComponent(val_timestamp);

		Label lbl_platform = new Label("Build Platform").addStyle(BOLD);
		addComponent(lbl_platform);

		Label val_platform = new Label(Entrypoint.data().so_build().getProperty("build.platform"));
		addComponent(val_platform);

		Label lbl_java_version = new Label("Java Version").addStyle(BOLD);
		addComponent(lbl_java_version);

		Label val_java_version = new Label(
				System.getProperty("java.vendor") + " " + System.getProperty("java.version"));
		addComponent(val_java_version);
	}

}
