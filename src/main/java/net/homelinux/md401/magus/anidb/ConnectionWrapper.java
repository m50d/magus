package net.homelinux.md401.magus.anidb;

import net.anidb.udp.UdpConnection;

import com.google.common.base.Function;

public class ConnectionWrapper {
	public <T> T perform(Function<UdpConnection, T> toInvoke) {
		return null;
	}
}
