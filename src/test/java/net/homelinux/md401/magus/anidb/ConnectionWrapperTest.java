package net.homelinux.md401.magus.anidb;

import net.anidb.udp.UdpConnectionFactory;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UdpConnectionFactory.class)
public class ConnectionWrapperTest {
	private UdpConnectionFactory factory;

	@Before
	public void setUp() {
		PowerMock.mockStatic(UdpConnectionFactory.class);
		factory = EasyMock.createMock(UdpConnectionFactory.class);
		EasyMock.expect(UdpConnectionFactory.getInstance()).andReturn(factory);
	}
}
