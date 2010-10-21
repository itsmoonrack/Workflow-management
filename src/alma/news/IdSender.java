package alma.news;

import alma.common.services.QueueMessageSender;

public class IdSender extends QueueMessageSender {

	public IdSender(String destination) {
		super(destination);
	}

}
