package com.mkyong;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParseResult;

@SpringBootApplication
public class Application implements CommandLineRunner {

	// https://docs.spring.io/spring/docs/5.1.6.RELEASE/spring-framework-reference/integration.html#mail
	@Autowired
	private JavaMailSender javaMailSender;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	class Params {

		@Option(required = true, names = { "-t",
				"--template" }, paramLabel = "mail-template.txt", description = "The email template file")
		File template;

		@Option(required = true, names = { "-l",
				"--mail-list" }, paramLabel = "mail-list.csv", description = "The list of target emails")
		File emails;

		@Option(required = true, names = { "-s",
				"--subject" }, paramLabel = "Subject", description = "The subject of the emails")
		String subject;
	}

	@Override
	public void run(String... args) throws IOException {

		System.out.println("Sending Email...");
		Params params = new Params();
		ParseResult result = new CommandLine(params).parseArgs(args);
		if (result.errors().size() > 0)
			throw new IllegalArgumentException("Missing arguments");

		String content = readFile(params.template);

		InputStream inputStream = new FileInputStream(params.emails);
		Reader isr = new InputStreamReader(inputStream);

		/// (/CSVParser csvParser = CSVFormat.DEFAULT.parse(isr);
		CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(isr);
		List<CSVRecord> records = csvParser.getRecords();
//		CSVRecord headers = records.get(0);

		RECORD: for (CSVRecord record : records) {
			if (record.getRecordNumber() == 0)
				continue RECORD;

			String c = content;
			for (String h : csvParser.getHeaderNames()) {
				c = c.replaceAll("\\$\\{" + h + "\\}", record.get(h));
			}
			String to = record.get("email");
			System.out.println("Enviando: " + to);
			sendEmail(params.subject, c, to);
		}

		System.out.println("Done");

	}

	void sendEmail(String subject, String text, String to) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);

		msg.setSubject(subject);
		msg.setText(text);

		javaMailSender.send(msg);

	}

	static String readFile(File file) throws IOException {

		byte[] encoded = Files.readAllBytes(file.toPath());
		return new String(encoded, StandardCharsets.UTF_8);
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
