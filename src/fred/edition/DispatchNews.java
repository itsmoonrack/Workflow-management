package alma.edition;

import alma.common.services.TopicSessionMessageSender;

public class DispatchNews extends TopicSessionMessageSender{

	public String destination;
	
	public DispatchNews(String destination){
		super(destination);
		this.destination = destination;
	}
}
