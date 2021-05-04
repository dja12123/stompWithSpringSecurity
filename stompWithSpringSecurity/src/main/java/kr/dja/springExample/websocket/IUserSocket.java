package kr.dja.springExample.websocket;

public interface IUserSocket
{
	/**
	 * 메세지 전송
	 */
	void publishTopic(String key, Object value);
}
