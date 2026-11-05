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

## Khi bé xem

App tính thời gian YouTube ở phía trước, kể cả menu hoặc video tạm dừng, chưa đo thời gian video thực sự phát. Đủ thời gian thì gửi PAUSE và hiện câu hỏi; dùng trái/phải và OK để chọn. Sai đổi câu, đúng mới đóng. Bấm Play/OK nếu YouTube chưa phát lại. Back không đóng bài; Home vẫn do Android quản lý. Mở lại YouTube sẽ gặp bài đang chờ.

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

## Cập nhật 0.6 — số câu đúng và chọn lớp

Trong Quản trị phụ huynh (PIN), chọn **Số câu đúng mỗi lượt** từ 1 đến 20 và **Ngân hàng Toán: Lớp** từ 1 đến 5. Mặc định giữ hành vi 1 câu, lớp 1. Đổi lớp hoặc số câu khởi tạo chu kỳ mới. Nút Thử một lượt câu hỏi dùng đúng số câu/lớp đã chọn.

- Lớp 1: 189 câu cộng trừ trong phạm vi 20.
- Lớp 2: 180 câu cộng trừ đến 100, nhân chia 2 và 5.
- Lớp 3: 192 câu bảng nhân chia, cộng trừ, chu vi hình vuông.
- Lớp 4: 180 câu nhân chia, cộng phân số cùng mẫu, diện tích hình chữ nhật.
- Lớp 5: 147 câu số thập phân, phần trăm, thể tích hình hộp chữ nhật.

Tổng 888 câu Toán do ứng dụng tạo theo mẫu và lưu trong mã nguồn, dùng ngoại tuyến. Đây là các bài ôn tập cơ bản theo mức lớp, không phải ngân hàng đủ mọi môn hay khớp từng tuần của sách giáo khoa. Phụ huynh chọn theo kiến thức bé đã học; đầu năm có thể cần chọn lớp thấp hơn.

Tiến độ hiển thị Đã đúng X/Y. Sai đổi câu và giữ số đúng; đúng đủ Y mới được xem tiếp. Không yêu cầu đúng liên tiếp. Thứ tự câu hỏi/đáp án được trộn; không lặp câu trước khi dùng hết bộ của lớp. Tiến độ, thứ tự và câu hiện tại được lưu qua Home/khởi động lại. Đổi cài đặt hoặc tạm nghỉ có PIN sẽ đặt lại lượt bài.

Đã kiểm tra tự động đáp án của cả 888 câu, lựa chọn không trùng, khả năng dựng lại thứ tự sau khôi phục, giữ tiến độ sau câu sai và điều kiện hoàn thành 1/3/20 câu. APK đã build và xác minh chữ ký; chưa kiểm thử tích hợp phiên bản này trên Sony thật.

Thử nhanh: chọn lớp 2, 3 câu đúng, 5 giây → mở YouTube → đúng 1 câu → sai 1 câu (vẫn 1/3) → Home rồi quay lại (vẫn 1/3) → đúng đủ 3 câu mới đóng bài. Thử khởi động lại giữa lượt để xác nhận giữ tiến độ trên TV.

## Cập nhật 0.7 — ngân hàng Tiếng Việt lớp 1–5

Cài đè APK v0.7. Vào **Quản trị phụ huynh → PIN → Lớp học → Môn học**, chọn **Tiếng Việt** hoặc **Kết hợp Toán + Tiếng Việt**. Giữ mặc định Toán cho bản cài nâng cấp để không tự thay đổi môn của bé. Đổi môn đặt lại lượt làm bài, giống đổi lớp.

Bổ sung 100 câu tự biên soạn, 20 câu mỗi lớp, dùng ngoại tuyến:

- Lớp 1: vần, dấu thanh, chữ đầu, từ quen thuộc và đọc hiểu câu ngắn.
- Lớp 2: từ chỉ sự vật/hoạt động/đặc điểm, dấu câu, trái nghĩa và đọc hiểu.
- Lớp 3: thành phần thông tin trong câu, so sánh, từ vựng, chính tả và đọc hiểu.
- Lớp 4: danh từ/động từ/tính từ, chủ ngữ/vị ngữ/trạng ngữ, nhân hóa, từ ghép/từ láy và đọc hiểu.
- Lớp 5: đồng nghĩa/trái nghĩa/đồng âm, nghĩa gốc/nghĩa chuyển, đại từ, câu ghép, liên kết câu và đọc hiểu.

Chế độ kết hợp bắt đầu bằng một câu Tiếng Việt, sau đó một câu Toán; xen kẽ trong khi cả hai bộ còn câu chưa dùng, rồi tiếp tục phần còn lại. Số câu đúng cần đạt tính chung hai môn. Tiến độ và quy tắc sai đổi câu giữ nguyên. Điều chỉnh chữ câu hỏi/đáp án dài để phù hợp remote và màn hình TV.

Đây là ngân hàng ôn tập cơ bản theo mức lớp, không bao phủ toàn bộ chương trình và không cam kết khớp từng tuần/sách. Phụ huynh có thể duyệt toàn bộ nội dung và đáp án trong `NGAN-HANG-TIENG-VIET.md`.

Kiểm tra: 100 mục tiếng Việt có 3 lựa chọn khác nhau, văn bản Unicode chuẩn, không trùng câu trong từng lớp; chọn môn, trộn môn và khôi phục thứ tự đạt kiểm thử. Đã đọc rà soát đáp án bằng tay; kiểm thử tự động không thay thế thẩm định sư phạm. Kiểm thử Toán/tiến độ cũ vẫn đạt. APK đã build, xác minh chữ ký và alignment; chưa thử bản mới trên TV thật.

## Cập nhật 0.8 — tên ứng dụng

Tên mới **Học Rồi Xem TV**, dòng mô tả **Quản lý giờ xem, mở khóa bằng kiến thức.** Đổi tên trên launcher, giao diện, thông báo và banner. Giữ package `vn.tvquiz` và khóa ký để cài đè, giữ PIN, tiến độ và cấu hình.

## Phiên bản 1.0

Bỏ nhãn thử nghiệm trong ứng dụng và thông báo. Phiên bản Android hiển thị 1.0 (versionCode 9). APK chính thức để cài trực tiếp: `out/Hoc-Roi-Xem-TV-v1.0.1.apk`; script build xuất đúng tên này. Giữ package và khóa ký để cài đè, bảo toàn PIN và cài đặt. Chưa phát hành lên Play Store. Các mục 0.x ở trên là lịch sử thay đổi.

## Bản vá 1.0.1 — che mã PIN

Ô đăng nhập/tạo/xác nhận/đổi PIN luôn hiển thị dấu chấm ngay khi gõ, không lộ ký tự cuối. Tắt chế độ nhập toàn màn hình của bàn phím và xóa ô nhập khi đóng. Cài đè APK 1.0.1 để giữ PIN và thiết lập. Đã kiểm thử lớp che ký tự và xác minh PIN; cần kiểm tra trực tiếp với bàn phím Sony. Nếu PIN trước đó đã bị bé nhìn thấy, hãy đổi PIN sau cập nhật.
