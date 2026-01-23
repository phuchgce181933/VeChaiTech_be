package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.model.ContactRequest;
import com.ra.base_spring_boot.model.constants.ContactStatus;
import com.ra.base_spring_boot.repository.IContactRepository;
import com.ra.base_spring_boot.services.IContactService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.time.LocalDateTime;
import java.util.Hashtable;

@Service
public class ContactServiceImpl implements IContactService {
    @Autowired
    private IContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void submitContact(ContactRequest request) {
        // 1. Validate email format
        if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }

        // 2. Check MX domain
        if (!isEmailDomainValid(request.getEmail())) {
            throw new IllegalArgumentException("Domain email không tồn tại");
        }

        // 3. Save DB
        request.setStatus(ContactStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        contactRepository.save(request);

        // 4. Send email


        sendSupportEmail(request);

        request.setStatus(ContactStatus.SENT);
        contactRepository.save(request);
    }

    @Override
    public boolean isEmailDomainValid(String email) {
        try {
            String domain = email.substring(email.indexOf("@") + 1);
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial",
                    "com.sun.jndi.dns.DnsContextFactory");

            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});
            return attrs.get("MX") != null;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Gửi email tới bộ phận support
     */
    private void sendSupportEmail(ContactRequest request) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("hgphuchgce181933@gmail.com");   // email nhận
        mail.setReplyTo(request.getEmail());    // reply về user
        mail.setSubject("[CONTACT] " + request.getSubject());

        mail.setText(
                "Tên: " + request.getFullName() + "\n" +
                        "Email: " + request.getEmail() + "\n\n" +
                        "Nội dung:\n" +
                        request.getMessage()
        );

        mailSender.send(mail);
    }
}
