package alma.news;

import alma.common.services.QueueSessionMessageSenderId;

public class SendId extends QueueSessionMessageSenderId {
	
	public SendId(String destination){
		super(destination);
	}
}
