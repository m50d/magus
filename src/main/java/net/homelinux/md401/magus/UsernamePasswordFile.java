package net.homelinux.md401.magus;

import java.io.File;
import java.io.Serializable;

public class UsernamePasswordFile implements Serializable {
	private static final long serialVersionUID = 1L;
	public final CharSequence username, password;
	public final File file;
	public UsernamePasswordFile(CharSequence username, CharSequence password, File file) {
		this.username = username;
		this.password = password;
		this.file = file;
	}

}
