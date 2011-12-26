package net.homelinux.md401.magus.anidb;

import net.anidb.udp.UdpConnection;
import net.anidb.udp.UdpConnectionFactory;

import static org.easymock.EasyMock.*;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.base.Function;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UdpConnectionFactory.class)
public class ConnectionWrapperTest {
	private static interface MockFunction extends Function<UdpConnection, Object>{}
	private UdpConnectionFactory factory;
	private UdpConnection connection;
	private MockFunction function;
	private ConnectionWrapper wrapper;

	@Before
	public void setUp() {
		PowerMock.mockStatic(UdpConnectionFactory.class);
		factory = createMock(UdpConnectionFactory.class);
		expect(UdpConnectionFactory.getInstance()).andReturn(factory);
		connection = createMock(UdpConnection.class);
		function = createMock(MockFunction.class);
		wrapper = new ConnectionWrapper();
	}
	
	@Test 
	public void invokesSuppliedFunction() throws Exception {
		Object expected = new Object();
		expect(factory.connect(anyInt())).andReturn(connection);
		expect(function.apply(connection)).andReturn(expected);
		PowerMock.replayAll();
		assertThat(wrapper.perform(function)).isEqualTo(expected);
		PowerMock.verifyAll();
	}
}
