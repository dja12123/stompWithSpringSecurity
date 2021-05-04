package kr.dja.springExample.websocket;

public interface IWebSocketService
{
	/**
	 * 해당 유저의 웹소켓 채널을 가져옵니다.
	 */
	IUserSocket getSessionByID(String userID);

	
	/**
	 * 메세지 publish
	 */
	void publishTopic(String key, Object value);
	
	
}
