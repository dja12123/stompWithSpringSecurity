package kr.dja.springExample.websocket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import kr.dja.springExample.account.AccountData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class UserSocket implements IUserSocket
{
	private final SimpMessageSendingOperations messagingTemplate;
	private final String name;
	private final AccountData account;
	private int sessionCnt = 0;

	private Map<String, StompMessageChannel> messageCh = new HashMap<>();

	
	@Override
	public void publishTopic(String key, Object value)
	{
		this.messagingTemplate.convertAndSendToUser
		(this.account.getUserId()
				, key
				, value);
	}
	
	public synchronized void addMessageCh(String sessionID, MessageChannel ch)
	{
		StompMessageChannel pharmacyCh = new StompMessageChannel(sessionID, ch);
		this.messageCh.put(sessionID, pharmacyCh);
		this.sessionCnt = this.messageCh.size();
	}
	
	public synchronized void removeMessageCh(String sessionID)
	{
		this.messageCh.remove(sessionID);
		this.sessionCnt = this.messageCh.size();
	}
	
	public synchronized Set<StompMessageChannel> getMessageCh()
	{
		Set<StompMessageChannel> set = new HashSet<>(this.messageCh.size());
		set.addAll(this.messageCh.values());
		return set;
	}
	
	

}
