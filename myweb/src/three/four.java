package three;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import one.two;;

public class four {

	public boolean send(two mail) {
		// ����email
		HtmlEmail email = new HtmlEmail();
		try {
			// ������SMTP���ͷ�����������
			email.setHostName(mail.getHost());
			// QQ��SMTP��������˿�Ϊ465
			email.setSslSmtpPort("465");
			// ����SSL֤��
			email.setSSL(true);
			// �ַ����뼯������
			email.setCharset(two.ENCODEING);
			// �ռ��˵�����
			email.addTo(mail.getReceiver());
			// �����˵�����
			email.setFrom(mail.getSender(), mail.getName());
			// �����Ҫ��֤��Ϣ�Ļ���������֤���û���-���롣�ֱ�Ϊ���������ʼ��������ϵ�ע�����ƺ�����
			email.setAuthentication(mail.getUsername(), mail.getPassword());
			// Ҫ���͵��ʼ�����
			email.setSubject(mail.getSubject());
			// Ҫ���͵���Ϣ������ʹ����HtmlEmail���������ʼ�������ʹ��HTML��ǩ
			email.setMsg(mail.getMessage());
			// ����
			email.send();
			System.out.println(mail.getSender() + " �����ʼ��� " + mail.getReceiver());
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			System.out.println(mail.getSender() + " �����ʼ��� " + mail.getReceiver() + " ʧ��");
			return false;
		}
	}

	public static void main(String[] args) {
		two mail = new two();
		mail.setHost("smtp.qq.com"); // �����ʼ�������,�������163��,�Լ����ҿ���ص�
		mail.setSender("2098711732@qq.com");
		mail.setReceiver("2098711732@qq.com"); // ������
		mail.setUsername("2098711732@qq.com"); // ��¼�˺�,һ�㶼�Ǻ�������һ����
		mail.setPassword("hwzzdyluxlnedjdb"); // ����������ĵ�¼����
		mail.setSubject("aaaaaaaaa");
		mail.setMessage("bbbbbbbbbbbbbbbbb");
		new four().send(mail);
	}
}