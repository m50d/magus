package net.homelinux.md401.magus.anidb;

import java.util.List;

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
import com.google.common.collect.Lists;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UdpConnectionFactory.class)
public class ConnectionWrapperTest {
	private static interface MockFunction extends Function<UdpConnection, Object>{}
	private UdpConnectionFactory factory;
	private UdpConnection connection;
	private MockFunction function;
	private ConnectionWrapper wrapper;
	private final List<Object> mocks = Lists.newArrayList();
	
	private <T> T mock(Class<T> tclass) {
		T ret = createMock(tclass);
		mocks.add(ret);
		return ret;
	}

	@Before
	public void setUp() {
		PowerMock.mockStatic(UdpConnectionFactory.class);
		factory = mock(UdpConnectionFactory.class);
		expect(UdpConnectionFactory.getInstance()).andReturn(factory);
		connection = mock(UdpConnection.class);
		function = mock(MockFunction.class);
		PowerMock.replay(UdpConnectionFactory.class);
		wrapper = new ConnectionWrapper();
	}
	
	@Test 
	public void invokesSuppliedFunction() throws Exception {
		Object expected = new Object();
		expect(factory.connect(anyInt())).andReturn(connection);
		expect(function.apply(connection)).andReturn(expected);
		connection.close();
		replay();
		assertThat(wrapper.perform(function)).isEqualTo(expected);
		verify();
	}

	private void verify() {
		PowerMock.verifyAll();
	}

	private void replay() {
		PowerMock.replayAll(mocks.toArray());
	}
}
