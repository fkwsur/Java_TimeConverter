import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeConverter {

    // 시간차 구하기
    public long GetGapDate(LocalDateTime start, LocalDateTime end) {

        long diff = ChronoUnit.SECONDS.between(start, end);
        LocalTime before_lunch = LocalTime.of(12, 01);
        LocalTime after_lunch = LocalTime.of(12, 59);

        // 장기 휴가일 때
        if (diff > 32400) {
            System.out.println(start.toLocalTime());

            LocalTime go_home = LocalTime.of(18, 00);
            LocalTime come_office = LocalTime.of(9, 00);
            // 첫날 시긴
            long getStartTime = ChronoUnit.SECONDS.between(start.toLocalTime(), go_home);
            System.out.println(getStartTime);

            // 마지막날 시간
            long getEndTime = ChronoUnit.SECONDS.between(come_office, end.toLocalTime());
            System.out.println(getEndTime);

            // 나머지 날들 수 구하기
            long days = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());

            // 8시간으로 변환
            double total_days = (days - 1) * 28800;

            // 첫날 점심시간 제외
            double start_lunch;
            if (start.toLocalTime().isBefore(before_lunch)) {
                start_lunch = 3600;
            } else {
                start_lunch = 0;
            }

            // 마지막날 점심시간 제외
            double end_lunch;
            if (end.toLocalTime().isAfter(after_lunch)) {
                end_lunch = 3600;
            } else {
                end_lunch = 0;
            }

            return (long) (total_days + getStartTime + getEndTime - start_lunch - end_lunch);

        } else {

            if (start.toLocalTime().isBefore(before_lunch) && end.toLocalTime().isAfter(after_lunch)) {
                long total_diff = diff - 3600;
                return total_diff;
            }

            return diff;
        }

    }

    // 일,시,분,초 단위로 변환
    public String GetDateFormat(int total_second) {

        Integer sec = total_second;
        DecimalFormat df = new DecimalFormat("00");

        int remain = sec.intValue();

        String dayStr = "일 ";
        String hourStr = "시간 ";
        String minStr = "분 ";
        String secStr = "초";

        // 기존 하루 단위
        // int day = remain / 86400;
        // remain %= 86400;

        // 휴가용 하루 단위
        int day = remain / 28800;
        remain %= 28800;

        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(df.format(day));
            sb.append(dayStr);
        }

        int hour = remain / 3600;
        remain %= 3600;
        if (hour > 0) {
            sb.append(df.format(hour));
            sb.append(hourStr);
        }

        int minute = remain / 60;
        remain %= 60;
        if (minute > 0) {
            sb.append(df.format(minute));
            sb.append(minStr);
        }

        int second = remain;
        if (second > 0) {
            sb.append(df.format(second));
            sb.append(secStr);
        }
        System.out.println(sb.toString());
        return sb.toString();
        // 1428일 21시간 33분 09초
    }

}
