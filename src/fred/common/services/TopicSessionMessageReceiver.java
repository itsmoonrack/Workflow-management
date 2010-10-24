package alma.common.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.models.Categorie;
import alma.common.models.vo.NewsVO;

public class TopicSessionMessageReceiver implements MessageListener {

	public String destination;
	public Categorie categorie;
	public String name;

	public TopicSessionMessageReceiver() {

	}

	public TopicSessionMessageReceiver(String destName, Categorie categorie,
			String name) {
		this.destination = destName;
		this.categorie = categorie;
		this.name = name;
	}

	public void run() {

		Context context = null;
		TopicConnectionFactory topicConnectionFactory = null;
		TopicConnection topicConnection = null;
		String factoryName = "ConnectionFactory";

		Topic topic = null;
		TopicSession topicSession = null;
		TopicSubscriber topicSubscriber = null;

		try {

			// create the JNDI initial context
			context = new InitialContext();

			// look up the ConnectionFactory
			topicConnectionFactory = (TopicConnectionFactory) context
					.lookup(factoryName);

			// look up the Destination
			topic = (Topic) context.lookup(destination);

			// create the connection
			topicConnection = topicConnectionFactory.createTopicConnection();

			// create the session
			topicSession = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// create the subscriber
			topicSubscriber = topicSession.createSubscriber(topic);

			topicSubscriber.setMessageListener(this);

			// start the connection, to enable message receipt
			topicConnection.start();
			
			while(true);

		} catch (JMSException exception) {
			exception.printStackTrace();
		} catch (NamingException exception) {
			exception.printStackTrace();
		} finally {
			// close the context
			if (context != null) {
				try {
					context.close();
				} catch (NamingException exception) {
					exception.printStackTrace();
				}
			}

			// close the connection
			if (topicConnection != null) {
				try {
					topicConnection.close();
					System.out
							.println("Connection to the topic (receiver) closed");
				} catch (JMSException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onMessage(Message message) {
		try {

			if (message instanceof ObjectMessageImpl) {

				NewsVO news = (NewsVO) ((ObjectMessageImpl) message)
						.getObject();

				if (news.categories.contains(this.categorie)) {
					System.out.println("--------------------------------------");
					System.out.println(this.name + " can edited the news");
					this.treat(news);
					System.out.println("--------------------------------------");
				} else {
					System.out.println("--------------------------------------");
					System.out.println(this.name + " can't edited the news");
					System.out.println("--------------------------------------");
				}
			}
 
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
	}
	public void treat(NewsVO news){}
}
