package kr.dja.springExample.websocket;

import java.security.Principal;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import kr.dja.springExample.account.AccountData;
import kr.dja.springExample.account.LoginInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class WSChannelInterceptor implements ChannelInterceptor
{
	private WebSocketService mgr;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel)
	{
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		StompCommand cmd = accessor.getCommand();
		if (cmd == StompCommand.CONNECT)
		{
			Principal p = accessor.getUser();
			if (p == null || !(p instanceof UsernamePasswordAuthenticationToken))
			{
				return null;
			}
			UsernamePasswordAuthenticationToken t = (UsernamePasswordAuthenticationToken) p;
			AccountData e = getAccountDataLogin(t.getPrincipal());
			if (e == null)
			{
				return null;
			}
			this.mgr.connect(e.getUserId(), accessor.getSessionId(), channel, e);
		} else if (cmd == StompCommand.DISCONNECT)
		{
			AccountData e = getAccountDataLogout(accessor.getUser());
			if (e == null)
			{
				return null;
			}
			this.mgr.disconnect(e.getUserId(), accessor.getSessionId());
		}

		return message;
	}


	private static AccountData getAccountDataLogin(Object o)
	{
		if (o == null || !(o instanceof LoginInfo))
		{
			return null;
		}
		
		AccountData accountData = ((LoginInfo)o).getInfo();
		
		
		return accountData;
	}
	
	private static AccountData getAccountDataLogout(Object o)
	{
		if(o == null || !(o instanceof UsernamePasswordAuthenticationToken))
		{
			return null;
		}
		o = ((UsernamePasswordAuthenticationToken)o).getPrincipal();
		if(o == null || !(o instanceof LoginInfo))
		{
			return null;
		}
		
		AccountData accountData = ((LoginInfo)o).getInfo();
		
		return accountData;
	}
}
