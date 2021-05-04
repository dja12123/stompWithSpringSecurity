package kr.dja.springExample.websocket;

import org.springframework.messaging.MessageChannel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class StompMessageChannel
{
	private String sessionID;
	private MessageChannel channel;
}
