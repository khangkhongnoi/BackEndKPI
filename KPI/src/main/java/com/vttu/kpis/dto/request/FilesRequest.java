package com.vttu.kpis.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilesRequest {

    Long id;
    String file_name;
    String file_path;
    @NotNull(message = "Mã công việc không được phép trống")
    String ma_congviec;
}
