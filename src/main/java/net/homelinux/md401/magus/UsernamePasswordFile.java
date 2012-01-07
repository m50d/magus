package net.homelinux.md401.magus;

import java.io.File;

public class UsernamePasswordFile {
	public final CharSequence username, password;
	public final File file;
	public UsernamePasswordFile(CharSequence username, CharSequence password, File file) {
		this.username = username;
		this.password = password;
		this.file = file;
	}

}
