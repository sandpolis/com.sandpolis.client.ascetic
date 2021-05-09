//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.client.ascetic;

import static com.sandpolis.client.ascetic.store.window.WindowStore.WindowStore;
import static com.sandpolis.core.instance.Environment.printEnvironment;
import static com.sandpolis.core.instance.MainDispatch.register;
import static com.sandpolis.core.instance.plugin.PluginStore.PluginStore;
import static com.sandpolis.core.instance.state.InstanceOid.InstanceOid;
import static com.sandpolis.core.instance.state.STStore.STStore;
import static com.sandpolis.core.instance.thread.ThreadStore.ThreadStore;
import static com.sandpolis.core.net.connection.ConnectionStore.ConnectionStore;
import static com.sandpolis.core.net.network.NetworkStore.NetworkStore;
import static com.sandpolis.core.net.stream.StreamStore.StreamStore;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.lanterna.gui2.AsynchronousTextGUIThread;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.SeparateTextGUIThread;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.sandpolis.client.ascetic.view.login.LoginWindow;
import com.sandpolis.core.instance.Core;
import com.sandpolis.core.instance.Environment;
import com.sandpolis.core.instance.MainDispatch;
import com.sandpolis.core.instance.MainDispatch.InitializationTask;
import com.sandpolis.core.instance.MainDispatch.Task;
import com.sandpolis.core.instance.config.CfgInstance;
import com.sandpolis.core.instance.state.oid.Oid;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

/**
 * {@link Client} is responsible for initializing the instance.
 *
 * @since 5.0.0
 */
public final class Client {

	public static final Logger log = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		printEnvironment(log, "Sandpolis Client");

		register(Client.loadEnvironment);
		register(Client.loadStores);
		register(Client.loadUserInterface);
	}

	/**
	 * Load the runtime environment.
	 */
	@InitializationTask(name = "Load runtime environment", fatal = true)
	private static final Task loadEnvironment = new Task(outcome -> {

		CfgInstance.PATH_CFG.register();
		CfgInstance.PATH_LOG.register();
		CfgInstance.PATH_LIB.register();
		CfgInstance.PATH_PLUGIN.register();

		Environment.CFG.requireReadable();
		Environment.LIB.requireReadable();
		Environment.LOG.requireWritable();
		Environment.PLUGIN.requireWritable();
		return outcome.success();
	});

	/**
	 * Load static stores.
	 */
	@InitializationTask(name = "Load static stores", fatal = true)
	private static final Task loadStores = new Task(outcome -> {

		STStore.init(config -> {
			config.concurrency = 2;
			config.root = Oid.of("/").get();
		});

		ThreadStore.init(config -> {
			config.defaults.put("net.exelet", new NioEventLoopGroup(2));
			config.defaults.put("net.connection.outgoing", new NioEventLoopGroup(2));
			config.defaults.put("net.message.incoming", new UnorderedThreadPoolEventExecutor(2));
			config.defaults.put("store.event_bus", Executors.newSingleThreadExecutor());
		});

		NetworkStore.init(config -> {
			config.collection = Oid.of("/network_connection").get();
		});

		ConnectionStore.init(config -> {
			config.collection = Oid.of("/connection").get();
		});

		PluginStore.init(config -> {
			config.collection = Oid.of("/profile//plugin", Core.UUID).get();
		});

		WindowStore.init(config -> {
		});

		StreamStore.init(config -> {
		});

		return outcome.success();
	});

	/**
	 * Load and show the user interface.
	 */
	@InitializationTask(name = "Load user interface")
	private static final Task loadUserInterface = new Task(outcome -> {

		TerminalScreen screen = new DefaultTerminalFactory().setForceTextTerminal(true).createScreen();
		WindowBasedTextGUI textGUI = new MultiWindowTextGUI(new SeparateTextGUIThread.Factory(), screen);
		((AsynchronousTextGUIThread) textGUI.getGUIThread()).start();
		screen.startScreen();
		WindowStore.gui = textGUI;

		WindowStore.create(LoginWindow::new);

		return outcome.success();
	});

	private Client() {
	}

	static {
		MainDispatch.register(Client.class);
	}

}
