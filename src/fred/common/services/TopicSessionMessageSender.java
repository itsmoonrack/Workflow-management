package alma.common.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import alma.common.models.vo.NewsVO;

public abstract class TopicSessionMessageSender {

	public String destName;
	protected TopicSession session = null;
	public NewsVO news;

	public TopicSessionMessageSender(String destination) {
		this.destName = destination;
		
	}

	public void setNews(NewsVO news){
		this.news = news;
	}
	
	public void send(NewsVO news) {

		Context context = null;
		TopicConnectionFactory factory = null;
		TopicConnection connection = null;
		String factoryName = "ConnectionFactory";

		Topic dest = null;
		TopicPublisher topicPublisher = null;

		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (TopicConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Topic) context.lookup(destName);

			// create the connection
			connection = factory.createTopicConnection();

			// create the session
			session = connection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// create the sender
			topicPublisher = session.createPublisher(dest);

			// start the connection, to enable message sends
			connection.start();

			Message message = session.createObjectMessage(news);

			topicPublisher.publish(message);

			System.out.println("A news was sent in the topic, id : " + news.id
					+ " , categorie(s) : " + news.categories);

			// sleep
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			// close the context
			if (context != null) {
				try {
					context.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}

			// close the connection
			if (connection != null) {
				try {
					connection.close();
					System.out.println("Connection to the topic (sender) closed");
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
