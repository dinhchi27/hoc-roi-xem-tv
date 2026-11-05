# Học Rồi Xem TV

**Quản lý giờ xem, mở khóa bằng kiến thức.**

Ứng dụng Android TV 1.0 giúp phụ huynh đặt thời gian xem YouTube, sau đó yêu cầu bé trả lời câu hỏi để xem tiếp.

## Tải APK

**[⬇ Tải Học Rồi Xem TV v1.0](https://github.com/dinhchi27/hoc-roi-xem-tv/releases/download/v1.0/Hoc-Roi-Xem-TV-v1.0.apk)**

[Xem bản phát hành v1.0](https://github.com/dinhchi27/hoc-roi-xem-tv/releases/tag/v1.0). Repo đang riêng tư: cần đăng nhập GitHub bằng tài khoản có quyền truy cập để tải.

Chép APK vào USB và mở bằng trình quản lý tệp trên TV để cài. Có thể cài đè bản cũ, giữ PIN và cài đặt. Xem [hướng dẫn chi tiết](HUONG-DAN.md).

## Tính năng

- Theo dõi YouTube bằng dịch vụ nền sau khi thiết lập lần đầu.
- Quản trị có PIN: thời gian xem, tạm nghỉ, lớp học, môn học và 1–20 câu đúng mỗi lượt.
- 888 câu Toán và 100 câu Tiếng Việt cho lớp 1–5, dùng ngoại tuyến.
- Điều khiển bằng remote; lưu tiến độ làm bài.
- Logo và giao diện dành cho TV.

Xem [hướng dẫn cài đặt](HUONG-DAN.md) và [ngân hàng Tiếng Việt](NGAN-HANG-TIENG-VIET.md).

## Cấu trúc

- `app/src/main/`: mã Java, manifest, tài nguyên Android.
- `assets/`: logo, banner.
- `tests/`: kiểm thử ngân hàng câu hỏi, tiến độ và xác minh PIN.
- `build.sh`: biên dịch, đóng gói và ký APK.

## Build

Script hiện cấu hình cho macOS, JDK 21 và Android SDK tải riêng:

- JDK: `/opt/homebrew/opt/openjdk@21`, hoặc đặt `TVQUIZ_JAVA_HOME`.
- Android API 30: `.tools/platform/android-11/android.jar`.
- Android Build Tools 35.0.0: `.tools/build-tools35/android-15/`.

Chạy `./build.sh`; kết quả `out/Hoc-Roi-Xem-TV-v1.0.apk`.

SDK, file build và khóa ký **không được đưa lên Git**. Script tạo khóa thử nghiệm cục bộ nếu chưa có; APK ký bằng khóa mới không thể cài đè bản đã ký bằng khóa khác. Để cập nhật bản đang cài trên TV phải sử dụng khóa gốc được giữ riêng trên máy phát triển.

## Giới hạn

Thời gian tính khi ứng dụng YouTube đang ở phía trước, kể cả menu/tạm dừng. Nút Home và cài đặt hệ thống không bị khóa. Khả năng chạy nền và nhận lệnh tạm dừng phụ thuộc TV. Nội dung học tập là ôn tập cơ bản, không cam kết khớp từng bộ sách. Chưa phát hành trên Play Store.
