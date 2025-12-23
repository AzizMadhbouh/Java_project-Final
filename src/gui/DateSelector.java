package src.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.stream.IntStream;

public class DateSelector extends JPanel {
    private JComboBox<Integer> dayCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<Integer> yearCombo;

    private static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    public DateSelector() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder("Date"));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        

        Integer[] days = IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
        dayCombo = new JComboBox<>(days);

        

        monthCombo = new JComboBox<>(MONTHS);

        

        int currentYear = Year.now().getValue();
        Integer[] years = IntStream.rangeClosed(currentYear - 1, currentYear + 10).boxed().toArray(Integer[]::new);
        yearCombo = new JComboBox<>(years);
        yearCombo.setSelectedItem(currentYear);

        add(dayCombo);
        add(monthCombo);
        add(yearCombo);
    }

    public String getSelectedDate() {
        int day = (int) dayCombo.getSelectedItem();
        int monthIndex = monthCombo.getSelectedIndex() + 1;
        int year = (int) yearCombo.getSelectedItem();

        

        return String.format("%04d-%02d-%02d", year, monthIndex, day);
    }
}
