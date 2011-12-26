package net.homelinux.md401.magus.anidb;

import net.anidb.udp.AniDbException;
import net.anidb.udp.UdpConnection;
import net.anidb.udp.UdpConnectionException;
import net.anidb.udp.UdpConnectionFactory;

import com.google.common.base.Function;

public class ConnectionWrapper {
	private final UdpConnectionFactory factory;

	public ConnectionWrapper() {
		factory = UdpConnectionFactory.getInstance();
	}
	
	public <T> T perform(Function<UdpConnection, T> toInvoke) throws UdpConnectionException, AniDbException {
		UdpConnection connection = factory.connect(1074);
		T ret = toInvoke.apply(connection);
		connection.close();
		return ret;
	}
}
