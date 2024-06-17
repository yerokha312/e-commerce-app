package dev.yerokha.ecommerce.email;

import dev.yerokha.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static dev.yerokha.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static dev.yerokha.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String emailDestination,
                                        String customerName,
                                        BigDecimal amount,
                                        String orderReference) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("no-reply@yerbo-dev.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> model = Map.of(
                "customerName", customerName,
                "amount", amount,
                "orderReference", orderReference
        );

        Context context = new Context();
        context.setVariables(model);
        mimeMessageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setTo(emailDestination);
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to '{}' with template '{}'", emailDestination, templateName);
        } catch (MessagingException | MailException e) {
            log.warn("Failed to send payment confirmation email to '{}'", emailDestination, e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(String emailDestination,
                                           String customerName,
                                           BigDecimal amount,
                                           String orderReference,
                                           List<Product> products) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom("yerbolatt312@gmail.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> model = Map.of(
                "customerName", customerName,
                "totalAmount", amount,
                "orderReference", orderReference,
                "products", products
        );

        Context context = new Context();
        context.setVariables(model);
        mimeMessageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String html = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setTo(emailDestination);
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to '{}' with template {}", emailDestination, templateName);
        } catch (MessagingException | MailException e) {
            log.warn("Failed to send order confirmation email to '{}'", emailDestination, e);
        }
    }
}



















