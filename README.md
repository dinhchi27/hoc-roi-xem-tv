# 📺 Học Rồi Xem TV — Phiên bản 1.2.1

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

**[⬇ Tải Học Rồi Xem TV v1.2.1](https://github.com/dinhchi27/hoc-roi-xem-tv/releases/download/v1.2.1/Hoc-Roi-Xem-TV-v1.2.1.apk)**

Bản **1.2.1** cải thiện kiểm tra cập nhật với nguồn thông tin tĩnh, API dự phòng và thông báo lỗi cụ thể. Trang phụ huynh có ba nhóm **Giờ xem / Bài học / Hệ thống**, các thẻ cài đặt lớn và điều hướng rõ ràng bằng remote. App hỗ trợ cập nhật từ GitHub Releases. Khi có bản mới, phụ huynh nhập PIN để tải và xác nhận cài đặt trên Android TV.

## Cập nhật ngay trên TV

- Cài bản **1.1.0** bằng USB hoặc LocalSend một lần để có chức năng cập nhật.
- Mỗi khi mở app, ứng dụng kiểm tra bản chính thức mới nhất trên GitHub (các lần mở cách nhau dưới 60 giây được gộp để hạn chế truy vấn).
- Khi có bản mới, chọn **Cập nhật → nhập PIN**. Có thể chọn **Để sau**.
- App tải APK và kiểm tra SHA-256, package, phiên bản tăng và cùng chữ ký trước khi mở trình cài đặt.
- Lần đầu, cấp quyền **Cài ứng dụng không rõ nguồn gốc** cho Học Rồi Xem TV. Quay lại app, xác nhận bằng PIN rồi chọn **Cài đặt** trên Android.
- Có nút **Kiểm tra cập nhật ứng dụng** trong trang phụ huynh. Mất mạng không ảnh hưởng câu hỏi ngoại tuyến.

Android yêu cầu xác nhận cài đặt; app không cài âm thầm. Chức năng cập nhật dùng Internet để kết nối GitHub, không gửi PIN hoặc tiến độ học tập.

## Cách cài trên Android TV

### Cách 1: Chép qua USB

1. Tải APK ở liên kết phía trên, chép vào USB và cắm USB vào TV.
2. Mở APK bằng trình quản lý tệp trên TV, chọn **Cài đặt**.
3. Nếu TV yêu cầu, cấp quyền **Cài ứng dụng không rõ nguồn gốc** cho trình quản lý tệp đang dùng, rồi mở APK lại.

### Cách 2: Gửi từ iPhone hoặc Android bằng LocalSend

[LocalSend](https://localsend.org/) hỗ trợ gửi tệp giữa các thiết bị trong cùng mạng nội bộ. Cài ứng dụng trên **cả điện thoại và TV**; xem các liên kết tải chính thức tại [localsend.org/download](https://localsend.org/download).

1. **Trên điện thoại:** cài LocalSend từ App Store (iPhone/iOS) hoặc Google Play (Android). Tải APK Học Rồi Xem TV ở phía trên. Trên iPhone, lưu APK vào ứng dụng **Tệp / Files**; chỉ gửi tệp sang TV, không cài APK trên iPhone.
2. **Trên TV:** tìm và cài LocalSend trong Google Play nếu có trên thiết bị. Nếu không tìm thấy, có thể cài APK LocalSend từ nguồn chính thức qua USB trước.
3. Kết nối điện thoại và TV vào **cùng mạng nhà** (cùng Wi-Fi; TV dùng dây LAN cùng router cũng được). Mở LocalSend trên cả hai thiết bị. Cho phép quyền **Mạng cục bộ / Local Network** trên iPhone khi được hỏi.
4. Trên điện thoại, vào **Gửi / Send → Tệp / Files**, chọn `Hoc-Roi-Xem-TV-v1.2.1.apk`, rồi chọn tên TV trong danh sách thiết bị.
5. Trên TV, **chấp nhận nhận tệp** và chờ gửi xong. Ghi nhớ thư mục lưu được LocalSend hiển thị hoặc cấu hình trên TV.
6. Mở APK vừa nhận; nếu LocalSend không mở được trình cài đặt, dùng trình quản lý tệp trên TV vào thư mục lưu để mở APK.
7. Khi TV hỏi, cấp quyền **Cài ứng dụng không rõ nguồn gốc** cho ứng dụng đang mở APK (LocalSend hoặc trình quản lý tệp), rồi chọn **Cài đặt**.

**Không thấy TV trong LocalSend?** Kiểm tra hai thiết bị cùng mạng, LocalSend đang mở, quyền Mạng cục bộ đã bật và không dùng mạng Wi-Fi khách có tính năng cách ly thiết bị.

Sau khi cài bằng một trong hai cách, mở **Học Rồi Xem TV** để thiết lập PIN và cấp hai quyền cần thiết. Nếu đã cài bản trước, cài đè để giữ PIN và cài đặt.

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

Chạy `./build.sh`; kết quả `out/Hoc-Roi-Xem-TV-v1.2.1.apk`.

SDK, file build và khóa ký **không được đưa lên Git**. Script tạo khóa thử nghiệm cục bộ nếu chưa có; APK ký bằng khóa mới không thể cài đè bản đã ký bằng khóa khác. Để cập nhật bản đang cài trên TV phải sử dụng khóa gốc được giữ riêng trên máy phát triển.

## Giới hạn

Thời gian tính khi ứng dụng YouTube đang ở phía trước, kể cả menu/tạm dừng. Nút Home và cài đặt hệ thống không bị khóa. Khả năng chạy nền và nhận lệnh tạm dừng phụ thuộc TV. Nội dung học tập là ôn tập cơ bản, không cam kết khớp từng bộ sách. Chưa phát hành trên Play Store.
