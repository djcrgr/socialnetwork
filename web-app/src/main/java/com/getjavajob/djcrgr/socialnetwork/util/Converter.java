package com.getjavajob.djcrgr.socialnetwork.util;

import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Converter {
	private static final String FILE_NAME = "info.xml";
	private Account account;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public void saveCustomerInfo() throws IOException {
		FileOutputStream os = null;
		try {
			System.out.println("--- Marshaller ---");
			os = new FileOutputStream(FILE_NAME);
			account.setName("John");
			account.setId(123);
			this.marshaller.marshal(account, new StreamResult(os));
			System.out.println("CustomerInfo " + account + " saved to info.xml file. ");
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	public void loadCustomerInfo() throws IOException {
		FileInputStream is = null;
		try {
			System.out.println("--- Unmarshaller ---");
			is = new FileInputStream(FILE_NAME);
			this.account = (Account) this.unmarshaller
					.unmarshal(new StreamSource(is));
			System.out.println("Info loaded from xml : " + account);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
}
