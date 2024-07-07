package com.vttu.kpis.controller;


import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.DonViResponse;
import com.vttu.kpis.service.DonViService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/donvi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonViController {

    DonViService donViService;

    @GetMapping
    ApiResponse<List<DonViResponse>> getAll() {

        return ApiResponse.<List<DonViResponse>>builder()
                .result(donViService.getAllDonVi())
                .build();
    }

    @GetMapping("donviphoihop")
    ApiResponse<List<DonViResponse>> getDonViPhoiHopThucHien(@RequestParam("madonvi") int madonvi){

        return  ApiResponse.<List<DonViResponse>>builder()
                .result(donViService.getDonViPhoiHopThucHien(madonvi))
                .build();
    }
}
