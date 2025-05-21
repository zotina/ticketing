package mg.itu.java.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    public static LocalDateTime  parser(String date){
        if (date != null && !date.isEmpty()) {
			try {
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				return  LocalDateTime.parse(date, formatter1);
			} catch (DateTimeParseException e) {
				try {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
					return  LocalDateTime.parse(date, formatter2);
				} catch (DateTimeParseException e2) {
                    return null;
				}
			}

		}
        return null;
    }
}
