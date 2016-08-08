package com.proizvo.pkg.util;

import static java.net.NetworkInterface.getNetworkInterfaces;
import static java.util.Collections.list;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.LinkedHashSet;
import java.util.Set;

public class NetHelper {

	public static Set<InetAddress> getAddresses() throws SocketException {
		Set<InetAddress> results = new LinkedHashSet<>();
		for (NetworkInterface ni : list(getNetworkInterfaces()))
			for (InetAddress ia : list(ni.getInetAddresses())) {
				if (ia.isLoopbackAddress() || ia.isLinkLocalAddress())
					continue;
				results.add(ia);
			}
		return results;
	}
}