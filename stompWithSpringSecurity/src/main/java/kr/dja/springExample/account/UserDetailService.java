package kr.dja.springExample.account;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailService implements UserDetailsService
{
	private final AccountRepo userRepo;

	private final ModelMapper modelMapper;

	private String adminPW;
	

	
	public UserDetailService(AccountRepo userRepo, ModelMapper modelMapper, PasswordEncoder encoder, @Value("${steelerp.admin-password}") String rawAdminPW)
	{
		this.userRepo = userRepo;
		this.modelMapper = modelMapper;
		this.adminPW = encoder.encode(rawAdminPW);

	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		User info;
		if (userName.equals("root"))
		{
			info = new LoginInfo("root", this.adminPW, true, UserType.ROOT.getRoles())
			{
				private static final long serialVersionUID = 1L;

				@Override
				public AccountData getInfo()
				{
					return AccountData.builder().isEnabled(true)
							.id(-1L)
							.userType(UserType.ROOT)
							.userId("root")
							.build();
				}

			};
			return info;
		}
		AccountEntity user = this.userRepo.findByUserId(userName);
		if(user == null)
		{
			throw new UsernameNotFoundException(userName);
		}

		info = new LoginInfo(user.getUserId(), user.getPassword(), user.isEnabled(),
				user.getUserType().getRoles())
		{
			private static final long serialVersionUID=-8665650748726351529L;

			@Override
			public AccountData getInfo()
			{
				return UserDetailService.this.projectionAccountData(this.getUsername());
			}
		};
		return info;
	}
	
	@Transactional
	public AccountData projectionAccountData(String userId)
	{
		AccountEntity accountEntity = UserDetailService.this.userRepo.findByUserId(userId);
		return UserDetailService.this.modelMapper.map(accountEntity, AccountData.class);
	}
}