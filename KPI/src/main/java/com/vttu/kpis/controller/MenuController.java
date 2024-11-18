package com.vttu.kpis.controller;

import com.nimbusds.jose.JOSEException;
import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.MenuChaResponse;
import com.vttu.kpis.dto.response.NhanVienResponse;
import com.vttu.kpis.responsitory.MenuChaRespository;
import com.vttu.kpis.service.AuthenticationService;
import com.vttu.kpis.utils.CheckToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuController {

    HttpServletRequest request;
    AuthenticationService authenticationService;

    MenuChaRespository menuChaRespository;

    @GetMapping("/taikhoan/{id}")
    ApiResponse<List<Map<String,Object>>> getMenuByMaTK(@PathVariable int id) {
        try{
            if(CheckToken.CheckHanToKen(request,authenticationService)){

                    return ApiResponse.<List<Map<String,Object>>>builder()
                            .result(menuChaRespository.getMenuByMataikhoan(id))
                            .code(HttpStatus.OK.value())
                            .build();

            }else
            {
                return ApiResponse.<List<Map<String,Object>>>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())  // Using HTTP status as the code
                        .build();
            }
        } catch (ParseException | JOSEException e){
            e.printStackTrace();
            return ApiResponse.<List<Map<String,Object>>>builder()
                    .message("Internal Server Error")
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())  // Using HTTP status as the code
                    .build();
        }
    }
}
