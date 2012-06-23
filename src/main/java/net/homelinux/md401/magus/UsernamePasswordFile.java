package net.homelinux.md401.magus;

import java.io.File;
import java.io.Serializable;

public class UsernamePasswordFile implements Serializable {
	public static final String USERNAME_PASSWORD_FILE = "UsernamePasswordFile";
	private static final long serialVersionUID = 1L;
	public final String username, password;
	public final File file;
	public UsernamePasswordFile(String username, String password, File file) {
		this.username = username;
		this.password = password;
		this.file = file;
	}

}
