import "/webjars/stomp-websocket/stomp.min.js";
import "/webjars/sockjs-client/sockjs.min.js";

export class WebSocketService
{
	socket;
	stompClient;
	isConnected;
	interest;
	
	constructor()
	{
		this.interest = new Array();
	}
	
	subscribeTopic(topic, callback)
	{
		this.interest.push({topic: '/topic/'+topic, callback: callback})
	}
	
	subscribeUserTopic(topic, callback)
	{
		this.interest.push({topic: '/user/'+username+'/'+topic, callback: callback})
	}
	
	sendDisconnect()
	{
		this.stompClient.deactivate();
	}
	
	publishTopic(key, value)
	{
		if (this.isConnected) {
			this.stompClient.send("/app/" + key, {}, value);
			return true;
		}
		else {
			console.log('error not connection')
		}
		return false
	}
	
	onconnect(frame)
	{
		this.isConnected = true;
		console.log("stomp connected" + frame);
		for(let e of this.interest)
		{
			this.stompClient.subscribe(e.topic, msg=>e.callback(JSON.parse(msg.body)));
		}
	}
	
	onerr(frame)
	{
		console.log("err " + frame);
		this.isConnected = false;
		this.socket.close();
	}
	
	connect()
	{
		this.socket = new SockJS('/websocket');
		this.stompClient = Stomp.over(this.socket);
		this.stompClient.connect({},f=>this.onconnect(f), f=>this.onerr(f));
	}
}