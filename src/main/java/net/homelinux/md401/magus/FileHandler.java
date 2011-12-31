package net.homelinux.md401.magus;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import net.anidb.udp.AniDbException;
import net.anidb.udp.UdpConnectionException;
import net.homelinux.md401.magus.anidb.ConnectionWrapper;

public class FileHandler {
	private final ConnectionWrapper connectionWrapper = new ConnectionWrapper();
	private final AbstractChecksum ed2kChecksum;

	public FileHandler() {
		try {
			ed2kChecksum = JacksumAPI.getChecksumInstance("ed2k");
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public void addFile(final String username, final String password, final boolean watched, final int length, final File data) {
		try {
			ed2kChecksum.reset();
			ed2kChecksum.readFile(data.getPath());
			final String hash = ed2kChecksum.getFormattedValue();
			connectionWrapper.add(username, password, length, hash, watched);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final UdpConnectionException e) {
			throw new RuntimeException(e);
		} catch (final AniDbException e) {
			throw new RuntimeException(e);
		}
	}
}
