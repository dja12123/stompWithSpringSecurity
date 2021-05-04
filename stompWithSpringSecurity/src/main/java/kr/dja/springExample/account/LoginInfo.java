package kr.dja.springExample.account;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public abstract class LoginInfo extends User
{
	
	private static final long serialVersionUID = 3563768926961773982L;

	public LoginInfo(String name, String pw, boolean enable, Set<GrantedAuthority> set)
	{
		super(name, pw, enable, true, true, true, set);
	}
	
	public abstract AccountData getInfo();
}
