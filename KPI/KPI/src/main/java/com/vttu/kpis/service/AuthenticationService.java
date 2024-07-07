package com.vttu.kpis.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vttu.kpis.dto.request.AuthenticationRequest;
import com.vttu.kpis.dto.request.IntrospectRequest;
import com.vttu.kpis.dto.response.AuthenticationResponse;
import com.vttu.kpis.dto.response.IntrospectResponse;
import com.vttu.kpis.entity.Permission;
import com.vttu.kpis.entity.Role;
import com.vttu.kpis.entity.TaiKhoan;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.responsitory.TaiKhoanRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    TaiKhoanRepository taiKhoanRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();

        boolean isValid = true;
        if(token == null){
            isValid = false;

            return IntrospectResponse.builder()
                    .valid(isValid)
                    .build();
        }
        try{
            verifytoken(token);
        }catch (AppException e){
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
}

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        var user = taiKhoanRepository.findByTentaikhoan(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getMatkhau());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

       // var token = generateToken(user);

        return AuthenticationResponse.builder()
              //  .token(token)
                .authenticated(true)
                .build();

    }

    public AuthenticationResponse generateTokenTheoChucVu(AuthenticationRequest request, int machucvu){

        var user = taiKhoanRepository.findByTentaikhoan(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getMatkhau());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

         var token = generateToken(user,machucvu);

        return AuthenticationResponse.builder()
                 .token(token)
                .authenticated(true)
                .build();
    }

    private String  generateToken(TaiKhoan taiKhoan, int machucvu){

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(taiKhoan.getTentaikhoan())
                .issuer("KPI.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(taiKhoan))
                .claim("machuvu", machucvu)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return  jwsObject.serialize();
        }catch (JOSEException e){
            log.error("Cannot create token",e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(TaiKhoan taiKhoan){
        StringJoiner stringJoiner = new StringJoiner("");

        if(!CollectionUtils.isEmpty(taiKhoan.getRoles()))
            taiKhoan.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());

                if(!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });
        return stringJoiner.toString();

    }

    private SignedJWT verifytoken(String token) throws JOSEException, ParseException {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verufied = signedJWT.verify(verifier);
            if(!(verufied && expityTime.after(new Date())))
                throw new AppException(ErrorCode.UNAUTHENTICATED);

            return signedJWT;
    }
}
