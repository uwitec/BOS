package cn.guwei.sms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.guwei.test.sendmsg.SmsSystem;
@Service("smsConsumer")
public class SmsConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		MapMessage map = (MapMessage) message;
		try {
			SendSmsResponse sendSms = SmsSystem.sendSms(map.getString("code"), map.getString("telephone"));
			System.out.println("消息发送成功-----"+sendSms.toString());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	
}
