/**
 *  Copyright 2011 VMware
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.vquery.samples;

import java.net.URL;

import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * An very simple test of the VI Java API
 * @author ishafer
 */
public class HelloVM
{
	/**
	 * List the hosts and VMs contained within the given folder
	 * @param root
	 * @throws Exception
	 */
	private static void listFolder(Folder root) throws Exception {
		InventoryNavigator nav = new InventoryNavigator(root);
		//find all clusters in the given folder
		ManagedEntity[] mes = nav.searchManagedEntities("ClusterComputeResource");

		//look through hosts and VMs in the first cluster
		ClusterComputeResource ccr = (ClusterComputeResource) mes[0];
		for (HostSystem hs : ccr.getHosts()) {
			System.out.println(hs);
			for (VirtualMachine vm : hs.getVms()) {
				System.out.println("\t" + vm + " @ IP " + vm.getGuest().ipAddress);
			}
		}
	}

	private static void usage() {
		System.err.println("Usage: java HelloVM URL USER PASS");
		System.err.println("\tURL like https://<vsphere url>/sdk");
	}

	public static void main(String[] args) throws Exception
	{
		if (args.length != 3) {
			usage();
		} else {
			ServiceInstance si = new ServiceInstance(new URL(args[0]), args[1], args[2], true);
			Folder rootFolder = si.getRootFolder();

			listFolder(rootFolder);
		}
	}

}
