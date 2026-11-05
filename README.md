# 📺 Học Rồi Xem TV — Phiên bản 1.0.1

*Quản lý giờ xem, mở khóa bằng kiến thức.*

- 🛠️ App Android TV mình tự phát triển, giúp phụ huynh quản lý bé xem YouTube.
- ⏱️ Tự đếm thời gian khi mở YouTube sau khi thiết lập lần đầu; đến giờ sẽ hiện câu hỏi.
- 🔓 Bé trả lời đúng đủ **1–20 câu** để xem tiếp. Trả lời sai đổi câu, giữ số câu đã đúng.
- 📚 **888 câu Toán + 100 câu Tiếng Việt**, chia theo lớp **1–5**, dùng ngoại tuyến. Có thể chọn từng môn hoặc kết hợp.
- 🔐 Quản trị bằng **PIN**: chọn thời gian, số câu, lớp học, môn học và tạm nghỉ.
- 🎮 Điều khiển bằng remote, lưu tiến độ làm bài khi về Home hoặc khởi động lại TV.
- 📦 Cài bằng APK; đã thử các bản trước trên **Sony Android TV 10**, chưa có trên Play Store.
- ℹ️ Không khóa nút Home hay cài đặt hệ thống; khả năng chạy nền phụ thuộc từng TV.

## Tải APK

**[⬇ Tải Học Rồi Xem TV v1.0.1](https://github.com/dinhchi27/hoc-roi-xem-tv/releases/download/v1.0.1/Hoc-Roi-Xem-TV-v1.0.1.apk)**

Bản **1.0.1** sửa lỗi hiển thị PIN: che ngay mọi chữ số khi nhập, kể cả ký tự vừa gõ. Bản vá đã được kiểm thử phần xử lý PIN và chữ ký APK; cần kiểm tra trực tiếp trên TV.

Chép APK vào USB và mở bằng trình quản lý tệp trên TV để cài. Có thể cài đè bản cũ, giữ PIN và cài đặt. Sau khi cài, mở app để thiết lập PIN và cấp hai quyền cần thiết.

- [Hướng dẫn cài đặt và sử dụng](HUONG-DAN.md)
- [Ngân hàng Tiếng Việt và đáp án](NGAN-HANG-TIENG-VIET.md)

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

Chạy `./build.sh`; kết quả `out/Hoc-Roi-Xem-TV-v1.0.1.apk`.

SDK, file build và khóa ký **không được đưa lên Git**. Script tạo khóa thử nghiệm cục bộ nếu chưa có; APK ký bằng khóa mới không thể cài đè bản đã ký bằng khóa khác. Để cập nhật bản đang cài trên TV phải sử dụng khóa gốc được giữ riêng trên máy phát triển.

## Giới hạn

Thời gian tính khi ứng dụng YouTube đang ở phía trước, kể cả menu/tạm dừng. Nút Home và cài đặt hệ thống không bị khóa. Khả năng chạy nền và nhận lệnh tạm dừng phụ thuộc TV. Nội dung học tập là ôn tập cơ bản, không cam kết khớp từng bộ sách. Chưa phát hành trên Play Store.
