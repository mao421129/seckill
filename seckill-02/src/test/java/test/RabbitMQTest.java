package test;

import org.junit.Test;
import com.mao.seckill_02.rabbitMq.RabbitMQSender;

public class RabbitMQTest {
	@Test
	public void testRabbitMq(){
		RabbitMQSender rs = new RabbitMQSender();
		rs.send("hello");
	}
}
