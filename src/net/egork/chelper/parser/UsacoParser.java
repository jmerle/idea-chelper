package net.egork.chelper.parser;

import net.egork.chelper.checkers.TokenChecker;
import net.egork.chelper.task.StreamConfiguration;
import net.egork.chelper.task.Task;
import net.egork.chelper.task.Test;
import net.egork.chelper.task.TestType;

import javax.swing.*;
import java.text.ParseException;

/**
 * @author egorku@yandex-team.ru
 */
public class UsacoParser implements Parser {
	public Icon getIcon() {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return "USACO";
	}

	public void getContests(DescriptionReceiver receiver) {
		throw new UnsupportedOperationException();
	}

	public void parseContest(String id, DescriptionReceiver receiver) {
		throw new UnsupportedOperationException();
	}

	public Task parseTask(Description description) {
		throw new UnsupportedOperationException();
	}

	public TestType defaultTestType() {
		return TestType.SINGLE;
	}

	public Task parseTaskFromHTML(String html) {
		StringParser parser = new StringParser(html);
		try {
			parser.advance(true, "<h2>");
			String contestName = parser.advance(false, "</h2>").trim();
			parser.advance(true, "<h2>");
			String taskName = parser.advance(false, "</h2>").trim();
			parser.advance(true, "PROBLEM NAME:");
			String taskId = parser.advance(false, "\n").trim();
			String taskClass = Character.toUpperCase(taskId.charAt(0)) + taskId.substring(1);
			StreamConfiguration	input = new StreamConfiguration(StreamConfiguration.StreamType.CUSTOM, taskId + ".in");
			StreamConfiguration output = new StreamConfiguration(StreamConfiguration.StreamType.CUSTOM, taskId + ".out");
			parser.advance(true, "SAMPLE INPUT");
			parser.advance(true, "\n");
			String testInput = parser.advance(false, "INPUT DETAILS", "OUTPUT FORMAT").trim() + "\n";
			parser.advance(true, "SAMPLE OUTPUT");
			parser.advance(true, "\n");
			String testOutput = parser.advance(false, "OUTPUT DETAILS", "</span>").trim() + "\n";
			return new Task(taskName, defaultTestType(), input, output, new Test[]{new Test(testInput, testOutput)},
				null, "-Xmx1024M", "Main", taskClass, TokenChecker.class.getCanonicalName(), "",
				new String[0], null, contestName, true, null, null, false, false);
		} catch (ParseException e) {
			return null;
		}
	}
}
