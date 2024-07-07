package com.vttu.kpis.utils;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.request.IntrospectRequest;
import com.vttu.kpis.exception.AppException;
import com.vttu.kpis.exception.ErrorCode;
import com.vttu.kpis.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@RequiredArgsConstructor
@Slf4j
public class CheckToken {

    public static boolean CheckHanToKen(HttpServletRequest request, AuthenticationService authenticationService) throws ParseException, JOSEException {
        String authorizationHeader = request.getHeader("Authorization");
        boolean check = true;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String jwt = authorizationHeader.substring(7);
            log.info(jwt);
            IntrospectRequest intro = new IntrospectRequest();
            intro.setToken(jwt);
            var result = authenticationService.introspect(intro);
            if(result.isValid())
                check = true;
            else{
                check = false;
                throw new AppException(ErrorCode.Exception_Token);
            }

        }
        else{
            check = false;
            throw new AppException(ErrorCode.Exception_IsEmpty_Token);
        }

        return  check;
    }
}
