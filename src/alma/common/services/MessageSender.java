package alma.common.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.Context;

public abstract class MessageSender {
	protected Context context = null;
	protected String destName = null;
	protected String factoryName = "ConnectionFactory";

	protected abstract Message createMessage() throws JMSException;
}
