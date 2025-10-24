package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.FormLogin;
import com.ra.base_spring_boot.dto.req.FormRegister;
import com.ra.base_spring_boot.dto.resp.JwtResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.model.OtpData;
import com.ra.base_spring_boot.model.Role;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.model.constants.RoleName;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.security.jwt.JwtProvider;
import com.ra.base_spring_boot.security.principle.MyUserDetails;
import com.ra.base_spring_boot.services.IAuthService;
import com.ra.base_spring_boot.services.IEmailService;
import com.ra.base_spring_boot.services.IRoleService;
import com.ra.base_spring_boot.services.ISmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ra.base_spring_boot.configuration.OtpUtil.generateOtp;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService
{
    private final IEmailService emailService;
    private final IRoleService roleService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    // lưu tạm OTP và user pending trong RAM (có thể thay bằng Redis hoặc DB riêng)
    private final Map<String, FormRegister> pendingUsers = new HashMap<>();
    // Chỉ cần 1 biến otpStorage
    private final Map<String, OtpData> otpStorage = new ConcurrentHashMap<>();
    private final ISmsService smsService;

    @Override
    public void register(FormRegister formRegister) {
        if (userRepository.existsByUsername(formRegister.getUsername())) {
            throw new HttpBadRequest("Username already exists");
        }
        if (userRepository.existsByEmail(formRegister.getEmail())) {
            throw new HttpBadRequest("Email already exists");
        }

        String otp = generateOtp();
        String identifier;
        // gửi OTP và xác định identifier
        if ("SMS".equalsIgnoreCase(formRegister.getDeliveryMethod())) {
            identifier = formRegister.getPhone();
            smsService.sendOtp(identifier, otp);
        } else {
            identifier = formRegister.getEmail();
            emailService.sendOtp(identifier, otp);
        }

        // Lưu theo identifier (có thể là email hoặc phone)
        pendingUsers.put(identifier, formRegister);
        otpStorage.put(identifier, new OtpData(otp, System.currentTimeMillis() + 5 * 60 * 1000));

        System.out.println("OTP sent to " + formRegister.getEmail() + ": " + otp);
    }

    @Override
    public void confirmOtp(String email, String otp) {
        OtpData storedOtp = otpStorage.get(email);
        if (storedOtp == null || !storedOtp.getOtp().equals(otp) ||
                storedOtp.getExpiredAt() < System.currentTimeMillis()) {
            throw new HttpBadRequest("Invalid or expired OTP");
        }

        FormRegister formRegister = pendingUsers.get(email);
        if (formRegister == null) {
            throw new HttpBadRequest("User not found in pending list");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.ROLE_CUSTOMER));
        User user = User.builder()
                .email(formRegister.getEmail())
                .phone(formRegister.getPhone())
                .fullName(formRegister.getFullName())
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .status(true)
                .roles(roles)
                .build();

        userRepository.save(user);

        otpStorage.remove(email);
        pendingUsers.remove(email);
    }


    @Override
    public JwtResponse login(FormLogin formLogin)
    {
        Authentication authentication;
        try
        {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        }
        catch (AuthenticationException e)
        {
            throw new HttpBadRequest("Username or password is incorrect");
        }

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        if (!userDetails.getUser().getStatus())
        {
            throw new HttpBadRequest("your account is blocked");
        }





        return JwtResponse.builder()
                .accessToken(jwtProvider.generateToken(userDetails.getUsername()))
                .user(userDetails.getUser())
                .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }


}
