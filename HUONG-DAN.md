# Học Rồi Xem TV 1.0.1 — chạy nền và quản trị phụ huynh

APK: `out/Hoc-Roi-Xem-TV-v1.0.1.apk`. Cài đè bản cũ để giữ quyền đã cấp. Package `vn.tvquiz`, giữ khóa ký của các bản trước. Không cần gỡ app.

## Thiết lập một lần

1. Cài APK từ USB bằng trình quản lý tệp trên TV, rồi mở Học Rồi Xem TV.
2. Chọn **Thiết lập PIN phụ huynh**. Nhập PIN 4–8 chữ số và nhập lại để xác nhận. Không có PIN mặc định; phụ huynh cần thực hiện bước này trước khi giao remote cho bé.
3. Trong quản trị, cấp quyền **Hiển thị câu hỏi** và **Nhận biết YouTube** nếu chưa có. Rời app để cấp quyền sẽ khóa trang quản trị; nhập PIN lại khi quay về.
4. Đặt **Tự động theo dõi: BẬT**. Nếu bản cũ đã tắt thì cài đè giữ trạng thái tắt, cần bật lại ở đây.
5. Chọn thời gian rồi nhấn Home. Mở YouTube trực tiếp bằng remote, không cần mở Học Rồi Xem TV trước mỗi lần xem.

Dịch vụ chạy nền có thông báo hệ thống. App đăng ký khởi động dịch vụ khi TV khởi động thật sự và sau cập nhật APK; hệ thống có thể khởi động lại dịch vụ khi thiếu bộ nhớ. Không cam kết hoạt động sau **Buộc dừng**, tắt quyền hoặc hạn chế nền riêng của Sony: khi đó mở Học Rồi Xem TV lại. TV tắt màn hình/ngủ không tích lũy thời gian ngủ.

## Trang quản trị có PIN

- Chọn nhanh: 5, 10, 30 giây; 1 phút; 1 phút 30 giây; 2, 5, 10, 15, 30 phút.
- Tùy chỉnh: 5–7200 giây (ví dụ 90 = 1 phút 30 giây).
- Tạm nghỉ: 5, 15, 30 phút; 1 hoặc 2 giờ; tùy chỉnh 1–1440 phút.
- Hết tạm nghỉ tự theo dõi lại nếu chế độ tự động đang bật; hạn tạm nghỉ lưu qua khởi động lại. Dùng đồng hồ hệ thống nên đổi giờ TV có thể làm thay đổi thời hạn.
- Có nút kết thúc tạm nghỉ ngay, bật/tắt hẳn theo dõi, thử câu hỏi và đổi PIN.
- Đổi thời gian, đổi bật/tắt hoặc tạm nghỉ xóa bài đang chờ và bắt đầu chu kỳ mới. Đó là thao tác có PIN của phụ huynh.
- Rời ứng dụng hoặc chọn khóa quản trị sẽ yêu cầu PIN lần sau. PIN được băm PBKDF2 với salt ngẫu nhiên, không lưu trực tiếp; sai 5 lần khóa nhập 30 giây.

Nếu quên PIN, không có mã mở khóa mặc định. Xóa dữ liệu app hoặc gỡ/cài lại sẽ xóa PIN và toàn bộ thiết lập. Cơ chế PIN chỉ bảo vệ cài đặt bên trong Học Rồi Xem TV, không khóa trang Cài đặt Android hoặc ngăn gỡ app.

## Lớp học, môn học và số câu

Trong **Quản trị phụ huynh → nhập PIN**, chọn **Lớp học**, **Môn học** và **Số câu đúng mỗi lượt** (1–20). Đổi các lựa chọn này sẽ bắt đầu lượt mới.

App có 888 câu Toán và 100 câu Tiếng Việt, chia theo lớp 1–5. Chế độ kết hợp xen kẽ hai môn trong khi cả hai bộ còn câu chưa dùng. Nội dung là ôn tập cơ bản, không cam kết khớp tiến độ từng bộ sách. Xem [ngân hàng Tiếng Việt và đáp án](NGAN-HANG-TIENG-VIET.md).

Tiến độ hiển thị **Đã đúng X/Y**. Sai đổi câu, giữ số câu đúng; đủ Y câu mới xem tiếp, không yêu cầu đúng liên tiếp. Tiến độ được lưu qua Home và khởi động lại TV.

## Khi bé xem

App tính thời gian YouTube ở phía trước, kể cả menu hoặc video tạm dừng, chưa đo thời gian video thực sự phát. Đủ thời gian thì gửi PAUSE và hiện câu hỏi; dùng trái/phải và OK để chọn. Sai đổi câu, đúng đủ số câu đã đặt mới đóng. Bấm Play/OK nếu YouTube chưa phát lại. Back không đóng bài; Home vẫn do Android quản lý. Mở lại YouTube sẽ gặp bài đang chờ.

## Checklist thử trên Sony

1. Đặt 5 giây, ra Home rồi mở YouTube trực tiếp: có câu hỏi sau khoảng 5 giây.
2. Đổi 10 giây, 90 giây: kiểm tra chu kỳ mới áp dụng.
3. Đặt tạm nghỉ tùy chỉnh 1 phút, mở YouTube: không có bài trong phút đó; hết nghỉ bắt đầu đếm mới.
4. Trong lúc tạm nghỉ, khởi động lại TV: hạn nghỉ được giữ và hết hạn sẽ tự đếm.
5. Khởi động lại TV khi tự động bật, vào YouTube trực tiếp: không cần mở Học Rồi Xem TV.
6. Tắt tự động, mở YouTube hoặc khởi động lại: không xuất hiện bài.
7. Rời trang quản trị, vào lại: yêu cầu PIN. Nhập sai 5 lần: đợi 30 giây.
8. Nút mở YouTube ở trang chính không tự bật lại chế độ phụ huynh đã tắt và không hủy tạm nghỉ.

## ADB (tùy chọn nếu máy đã có ADB)

```sh
adb connect DIA_CHI_IP_TV:5555
adb install -r out/Hoc-Roi-Xem-TV-v1.0.1.apk
adb shell appops set vn.tvquiz SYSTEM_ALERT_WINDOW allow
adb shell appops set vn.tvquiz GET_USAGE_STATS allow
```

## Build và xác minh

`./build.sh` dùng Java 21, Android API 30 / target API 29, Build Tools 35.0.0. Công cụ nằm trong `.tools`. Đã biên dịch, xác minh chữ ký APK và zip alignment. Test `tests/PinCodecTest.java` kiểm tra PIN đúng/sai, số 0 đầu và salt. Chưa có thiết bị/emulator kết nối để chạy kiểm thử tích hợp; cần checklist Sony phía trên. Logo trong `assets/`.

## Lịch sử cập nhật

### v1.0 — phát hành chính thức

- Tự theo dõi thời gian xem YouTube, hỗ trợ tạm nghỉ và tự bật lại.
- Quản trị có PIN, tùy chỉnh thời gian và yêu cầu 1–20 câu đúng mỗi lượt.
- Chọn lớp 1–5 và môn Toán, Tiếng Việt hoặc kết hợp hai môn.
- Ngân hàng ngoại tuyến gồm 888 câu Toán và 100 câu Tiếng Việt.
- Điều khiển bằng remote, lưu tiến độ làm bài khi về Home hoặc khởi động lại TV.
- Tên ứng dụng **Học Rồi Xem TV**, dòng mô tả **Quản lý giờ xem, mở khóa bằng kiến thức.**

### v1.0.1 — che mã PIN

Ô đăng nhập/tạo/xác nhận/đổi PIN luôn hiển thị dấu chấm ngay khi gõ, không lộ ký tự cuối. Tắt chế độ nhập toàn màn hình của bàn phím và xóa ô nhập khi đóng. Cài đè APK 1.0.1 để giữ PIN và thiết lập. Đã kiểm thử lớp che ký tự và xác minh PIN. Nếu PIN trước đó đã bị bé nhìn thấy, hãy đổi PIN sau cập nhật.
