package org.itstep.safetywork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Period;

@Data
@NoArgsConstructor
public class ExpiringTerms {

    private String name;
    private Period period;
    private String message;
    private String link;

    public ExpiringTerms(String name, Period period, String message, String link) {
        this.name = name;
        this.period = period;
        this.message = message;
        this.link = link;
    }
}