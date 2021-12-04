package io.github.swahid.northend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class NorthendExcelParseApplicationTests {

	@Test
	void contextLoads() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020,06,01);
		System.out.println("Cal Set Date: " + calendar.getTime());
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy EEEE");
		System.out.println("format date: " + dateFormat.format(calendar.getTime()) + ".xlsx");

		calendar.add(Calendar.DATE, 1);

		System.out.println("plus date: " + dateFormat.format(calendar.getTime()) + ".xlsx");

		String formate = "1 (ground)";
		formate = formate.replaceAll("[^\\d]","");
		System.out.println("replcae val: " +formate);
		System.out.println(Integer.valueOf(formate));


	}

}
