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

import com.sandpolis.client.ascetic.init.AsceticLoadUserInterface;
import com.sandpolis.core.instance.Entrypoint;
import com.sandpolis.core.instance.Metatypes.InstanceFlavor;
import com.sandpolis.core.instance.Metatypes.InstanceType;
import com.sandpolis.core.instance.init.InstanceLoadEnvironment;

public final class Main extends Entrypoint {

	private Main(String[] args) {
		super(Main.class, InstanceType.CLIENT, InstanceFlavor.CLIENT_ASCETIC);

		register(new InstanceLoadEnvironment());
		// register(new InstanceLoadStores());
		register(new AsceticLoadUserInterface());

		start("Sandpolis Terminal Client", args);
	}

	public static void main(String[] args) {
		new Main(args);
	}

}
