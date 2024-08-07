package com.vttu.kpis.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    MucTieu_EXISTED(1001, "Tên mục tiêu trùng lặp",HttpStatus.BAD_REQUEST),
    NhomMucTieu_EXISTED(1002, "Tên nhóm trùng lặp",HttpStatus.BAD_REQUEST),
    Nhom_NOT_EXISTED(1003, "Mã nhóm không tồn tại",HttpStatus.BAD_REQUEST),
    NhanVien_NOT_EXISTED(1004, "Nhân viên không tồn tại",HttpStatus.BAD_REQUEST),
    DonVi_NOT_EXISTED(1005, "Đon vị không tồn tại",HttpStatus.BAD_REQUEST),
    Quyen_NOT_EXISTED(1006, "Quyền không tồn tại",HttpStatus.BAD_REQUEST),
    MucTieu_NOT_EXISTED(1007, "Mục tiêu không tồn tại",HttpStatus.BAD_REQUEST),
    CongViec_NOT_EXISTED(1008, "Mã công việc không hợp lệ", HttpStatus.BAD_REQUEST),
    DanhCho_NOT_EXISTED(1009, "Dành cho không tồn tại", HttpStatus.BAD_REQUEST),
    PhanCongNhanVienIsEmpty(1010, "Phân công nhân viên không không được rỗng", HttpStatus.BAD_REQUEST),
    PhanCongDonViIsEmpty(1011, "Phân công đơn vị không được rỗng", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1012, "Tên User Name không tồn tại",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1013, "Unauthenticated",HttpStatus.BAD_REQUEST),
    Exception_Token(1014, "Token không hợp lệ",HttpStatus.BAD_REQUEST),
    Exception_IsEmpty_Token(1015, "Đầu vào token không hợp lệ",HttpStatus.BAD_REQUEST),
    MucTieu_NOT_FOUND(1016, "Mã mục tiêu không tồn tại", HttpStatus.BAD_REQUEST),
    NhanVien_ChucVu_NOT_FOUND(1017, "Mã nhân viên chức vụ không tồn tại", HttpStatus.BAD_REQUEST),
    NhanVien_NOT_FOUND(1018, "Mã nhân viên không hợp lệ", HttpStatus.BAD_REQUEST),
    XoaNhanVien_BanLanhDao(1019, "Không thể xóa do nhân viên đã tạo công việc con", HttpStatus.BAD_REQUEST),
    ErrorNgayBDConViecCon(1020, "Ngày bắt đầu công việc con không hợp lệ", HttpStatus.BAD_REQUEST),
    ErrorNgayKTConViecCon(1021, "Ngày kết thúc công việc con không hợp lệ", HttpStatus.BAD_REQUEST),
    XoaDonVi_PhanCong(1022, "Không thể xóa do đơn vị đã tạo công việc con", HttpStatus.BAD_REQUEST),
    TrangThai_NOT_EXISTED(1023, "Trạng thái không tồn tại",HttpStatus.BAD_REQUEST),
    PhanCongBoPhanIsEmpty(1024, "Phân công bộ phận không được rỗng", HttpStatus.BAD_REQUEST),
    BoPhan_NOT_EXISTED(1025, "Bộ phận không tồn tại",HttpStatus.BAD_REQUEST),
    KetQua_NOT_EXISTED(1026, "Cách tính kết quả không tồn tại",HttpStatus.BAD_REQUEST),
    CongViec_Nhan_DaXacNhan(1027, "Công việc được nhận đã xác nhận không thể chỉnh sửa",HttpStatus.BAD_REQUEST),
    CongViec_Dang_ThucHien_ERROR(1028, "Công việc đang tiến hành không thể xóa",HttpStatus.BAD_REQUEST),
    CongViec_Da_ThucHien_ERROR(1028, "Công việc đã hoàn thành không thể xóa",HttpStatus.BAD_REQUEST),
    TenDonVi_EXISTED(1029, "Tên đơn vị trùng lặp",HttpStatus.BAD_REQUEST),
    TenBoPhan_EXISTED(1030, "Tên bộ phận trùng lặp",HttpStatus.BAD_REQUEST),
    ThoiGian_Is_Empty(1031, "Thời gian không được rỗng",HttpStatus.BAD_REQUEST),
   LyDo_Is_Empty(1032, "Lý do không được rỗng",HttpStatus.BAD_REQUEST),
    PhanCongLanhDaoIsEmpty(1033, "Phân công lãnh đạo không được rỗng", HttpStatus.BAD_REQUEST),

    ;


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
