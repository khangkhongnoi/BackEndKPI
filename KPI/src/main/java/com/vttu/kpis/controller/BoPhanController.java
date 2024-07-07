package com.vttu.kpis.controller;

import com.vttu.kpis.dto.response.ApiResponse;
import com.vttu.kpis.dto.response.BoPhanResponse;
import com.vttu.kpis.entity.BoPhan;
import com.vttu.kpis.service.BoPhanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/bophan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BoPhanController {

    BoPhanService boPhanService;

    @GetMapping
    ApiResponse<List<BoPhanResponse>> getBoPhanByMaDonVi(@RequestParam("madonvi") int madonvi){

        return ApiResponse.<List<BoPhanResponse>>builder()
                .result(boPhanService.getBoPhanByMaDonVi(madonvi))
                .build();
    }


}
