package kr.dja.springExample.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import kr.dja.springExample.account.AccountData;
import lombok.extern.slf4j.Slf4j;

/**
 * 웹소켓 서비스(pub/sub)
 */
@Slf4j
public class WebSocketService implements IWebSocketService
{
	private Map<String, UserSocket> socketMap=new HashMap<>();

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	public synchronized IUserSocket connect(String userID, String sessionID, MessageChannel ch, AccountData e)
	{
		UserSocket socket=this.socketMap.get(userID);
		if(socket==null)
		{
			socket=new UserSocket(this.messagingTemplate, userID, e);
			this.socketMap.put(userID, socket);
		}
		socket.addMessageCh(sessionID, ch);
		log.info("create websocket session user:"+userID+" remain:"+socket.getSessionCnt());
		return socket;
	}

	public synchronized void disconnect(String userID, String sessionID)
	{
		UserSocket socket=this.socketMap.get(userID);
		if(socket==null)
		{
			log.warn("session not managed");
			return;
		}
		socket.removeMessageCh(sessionID);
		if(socket.getSessionCnt()==0)
		{
			this.socketMap.remove(userID);
		}
		log.info("close websocket session user:"+userID+" remain:"+socket.getSessionCnt());
	}

	@Override
	public synchronized IUserSocket getSessionByID(String userID)
	{
		UserSocket socket=this.socketMap.get(userID);
		return socket;
	}

	@Override
	public void publishTopic(String key, Object value)
	{
		this.messagingTemplate.convertAndSend("/topic/"+key, value);
	}

}
