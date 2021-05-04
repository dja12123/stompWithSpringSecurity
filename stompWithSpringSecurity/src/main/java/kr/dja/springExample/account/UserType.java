package kr.dja.springExample.account;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public enum UserType
{
	NONE(Role.ANONYMOUS),
	ADMIN(Role.ADMIN, Role.WEBSOCKET),
	USER(Role.USER, Role.WEBSOCKET),
	ROOT(Role.values());


	private Set<GrantedAuthority> roles;

	private UserType(Role... rolearr)
	{
		this.roles = new HashSet<>(rolearr.length);
		for (Role role : rolearr)
			this.roles.add(role);
	}

	public Set<GrantedAuthority> getRoles()
	{
		Set<GrantedAuthority> l = new HashSet<>(this.roles.size());
		l.addAll(this.roles);
		return l;
	}
}
