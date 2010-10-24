package alma.common.services;

import javax.jms.Connection;
import javax.jms.Session;
import javax.naming.Context;

public abstract class MessageSender {
	protected Context context = null;
	protected Session session = null;
	protected Connection connection = null;
	protected String factoryName = "ConnectionFactory";
	protected String destName = null;
	
	protected abstract void start();
	protected abstract void close();
}
