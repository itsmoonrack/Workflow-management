package alma.edition;

import alma.common.services.QueueSessionMessageSenderNewNews;

public class SendNewsFromEditor extends QueueSessionMessageSenderNewNews{
	
	public SendNewsFromEditor(String destination){
		super(destination);
	}

}
