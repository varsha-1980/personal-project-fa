package com.mindlease.fa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailTemplate {

    String mailTo;

    String subject;

    String emailBody;

    String status;

    public EmailTemplate() {

    }
}
