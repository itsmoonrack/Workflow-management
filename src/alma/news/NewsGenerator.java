package alma.news;

import javax.jms.JMSException;

import alma.common.models.vo.NewsVO;
import alma.common.services.QueueMessageSender;

public class NewsGenerator {
	
	public static void main(String[] args) {
		QueueMessageSender newsPool = new QueueMessageSender("newsPoolQueue");
		
		NewsVO[] news = DummyNews.news;
		try {
			for (NewsVO newsVO : news) {
				newsPool.sendObjectMessage(newsVO);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
