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

	public <T> T perform(final Function<UdpConnection, T> toInvoke)
			throws UdpConnectionException, AniDbException {
		final UdpConnection connection = factory.connect(1074);
		final T ret = toInvoke.apply(connection);
		connection.close();
		return ret;
	}

	public <T> T performWithAuthentication(final String username,
			final String password, final Function<UdpConnection, T> toInvoke)
			throws UdpConnectionException, AniDbException {
		return perform(new Function<UdpConnection, T>() {

			@Override
			public T apply(final UdpConnection arg0) {
				try {
					arg0.authenticate(username, password);
				} catch (final AniDbException e) {
					throw new RuntimeException(e);
				} catch (final UdpConnectionException e) {
					throw new RuntimeException(e);
				}
				return toInvoke.apply(arg0);
			}
		});
	}
}
