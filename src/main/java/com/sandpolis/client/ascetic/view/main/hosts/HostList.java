//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//
package com.sandpolis.client.ascetic.view.main.hosts;

import static com.sandpolis.core.net.connection.ConnectionStore.ConnectionStore;
import static com.sandpolis.core.net.network.NetworkStore.NetworkStore;

import com.googlecode.lanterna.gui2.table.Table;
import com.sandpolis.core.instance.state.ConnectionOid;

public class HostList extends Table<String> {

//	private STCollection collection = STStore.root().get(InstanceOid().profile);

	private String serverUuid;

	public HostList() {
		super("UUID", "Hostname", "IP Address", "Platform");

		NetworkStore.getPreferredServer().ifPresentOrElse(cvid -> {
			serverUuid = ConnectionStore.getByCvid(cvid).get().get(ConnectionOid.REMOTE_UUID);

			// Attach the local collection
//			STCmd.async().sync(collection, InstanceOid().profile);
		}, () -> {
			serverUuid = null;
		});
	}
}
