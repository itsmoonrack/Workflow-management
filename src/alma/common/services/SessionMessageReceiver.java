package alma.common.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.vo.NewsVO;

public class SessionMessageReceiver implements Runnable, MessageListener {

	public String destination;
	public boolean subscribe = true;

	public SessionMessageReceiver() {

	}

	public SessionMessageReceiver(String destName) {
		this.destination = destName;
	}

	@Override
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

			while (true);

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
				} catch (JMSException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage tempText = (TextMessage) ((ObjectMessageImpl) message).getObject();
				System.out.println("Message type : " + tempText.getClass());
			} 
			if(message instanceof ObjectMessageImpl){
				NewsVO tempNewsVO = (NewsVO) ((ObjectMessageImpl) message).getObject();
				System.out.println("Message type : " + tempNewsVO.getClass());
			}
			
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
		
	}
}
