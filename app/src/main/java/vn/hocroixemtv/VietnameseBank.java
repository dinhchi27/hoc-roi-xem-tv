package vn.hocroixemtv;
import java.util.*;

/** Original short Vietnamese exercises; columns: prompt, correct, distractor, distractor. */
final class VietnameseBank {
 static final String[][] DATA={
 {
 "Tiếng nào có vần ‘an’?|bàn|bút|bông",
 "Tiếng nào có vần ‘ong’?|ong|anh|em",
 "Tiếng nào có dấu sắc?|lá|là|lạ",
 "Tiếng nào có dấu huyền?|bà|bá|bạ",
 "Tiếng nào có dấu hỏi?|cỏ|có|cò",
 "Tiếng nào có dấu ngã?|gỗ|gố|gộ",
 "Tiếng nào có dấu nặng?|mẹ|mé|mè",
 "Chữ nào đứng đầu tiếng ‘mèo’?|m|n|b",
 "Chữ nào đứng đầu tiếng ‘bàn’?|b|d|p",
 "Tiếng nào bắt đầu bằng ‘ch’?|chim|tim|sim",
 "Tiếng nào bắt đầu bằng ‘th’?|thỏ|bò|cá",
 "Từ nào gọi tên một con vật?|mèo|bàn|bút",
 "Từ nào gọi tên đồ dùng học tập?|bút|cam|gà",
 "Từ nào gọi tên một loại quả?|cam|cá|cốc",
 "Điền từ: Bé uống …|sữa|bút|ghế",
 "Điền từ: Con cá bơi dưới …|nước|mây|lá",
 "Đọc: ‘Mẹ bế bé.’ Ai bế bé?|Mẹ|Bố|Bà",
 "Đọc: ‘Bé có mũ đỏ.’ Mũ của bé màu gì?|Đỏ|Xanh|Vàng",
 "Đọc: ‘Nam đi học.’ Ai đi học?|Nam|Lan|Mai",
 "Đọc: ‘Bà cho bé quả cam.’ Bà cho bé quả gì?|Cam|Táo|Lê"
 },
 {
 "Từ nào chỉ hoạt động?|chạy|bàn|xanh",
 "Từ nào chỉ màu sắc?|đỏ|đọc|đèn",
 "Từ nào chỉ đồ vật?|ghế|ngủ|ngoan",
 "Từ nào chỉ người?|cô giáo|cây bàng|con mèo",
 "Từ nào chỉ đặc điểm?|cao|đi|bút",
 "Điền từ: Em … bài trong vở.|viết|bay|bơi",
 "Điền từ: Chim bay trên …|trời|vở|bàn",
 "Cuối câu ‘Bạn tên là gì’ cần dấu nào?|Dấu hỏi chấm|Dấu phẩy|Dấu hai chấm",
 "Cuối câu kể ‘Em đi học’ thường dùng dấu nào?|Dấu chấm|Dấu hỏi chấm|Dấu phẩy",
 "Cách viết tên bạn nào đúng?|Ngọc|ngọc|nGọc",
 "Từ nào trái nghĩa với ‘cao’?|thấp|dài|rộng",
 "Từ nào trái nghĩa với ‘nóng’?|lạnh|đỏ|sáng",
 "Từ nào trái nghĩa với ‘nhanh’?|chậm|vui|tròn",
 "Từ nào trái nghĩa với ‘sạch’?|bẩn|đẹp|mới",
 "Trong ‘Bé đọc sách’, từ nào chỉ hoạt động?|đọc|bé|sách",
 "Trong ‘Bông hoa đỏ’, từ nào chỉ màu sắc?|đỏ|hoa|bông",
 "Đọc: ‘Lan tưới cây mỗi sáng.’ Lan làm gì?|Tưới cây|Quét nhà|Đọc sách",
 "Đọc: ‘Minh để sách trên bàn.’ Sách ở đâu?|Trên bàn|Dưới ghế|Trong cặp",
 "Đọc: ‘Trời mưa, An mang ô.’ An mang gì?|Ô|Diều|Bóng",
 "Khi được bạn giúp đỡ, em nên nói gì?|Cảm ơn bạn!|Bạn tên là gì?|Hẹn gặp lại!"
 },
 {
 "Trong ‘Mẹ nấu cơm’, ai nấu cơm?|Mẹ|Cơm|Bếp",
 "Trong ‘Lan đọc sách ở thư viện’, Lan đọc sách ở đâu?|Ở thư viện|Ở sân trường|Ở nhà bếp",
 "Trong ‘Sáng nay, Nam đi học’, từ ngữ nào chỉ thời gian?|Sáng nay|Nam|Đi học",
 "Trong ‘Chú mèo rất nhanh nhẹn’, mèo có đặc điểm gì?|Nhanh nhẹn|Chậm chạp|Ồn ào",
 "Câu nào có hình ảnh so sánh?|Mây trắng như bông.|Mây bay trên trời.|Bầu trời có mây.",
 "Trong ‘Mắt bé sáng như sao’, mắt bé được so sánh với gì?|Sao|Mây|Nắng",
 "Trong ‘Tóc bà trắng như mây’, từ nào dùng để so sánh?|như|tóc|bà",
 "Từ nào cùng nghĩa với ‘siêng năng’?|chăm chỉ|lười biếng|vội vã",
 "Từ nào trái nghĩa với ‘dũng cảm’?|nhút nhát|gan dạ|can đảm",
 "Nhóm nào chỉ gồm từ về trường học?|thầy giáo, học sinh|cá, tôm|nồi, chảo",
 "Điền dấu phù hợp: ‘Em có bút… thước và vở.’|Dấu phẩy|Dấu hỏi chấm|Dấu chấm than",
 "Câu nào dùng để hỏi?|Bạn đi đâu?|Bạn đang đi học.|Bạn đi học rồi.",
 "Câu nào dùng để đề nghị?|Bạn đóng cửa giúp mình nhé!|Cửa đã đóng.|Cửa màu xanh.",
 "Điền từ: ‘Vì trời mưa … em mang áo mưa.’|nên|nhưng|hoặc",
 "Đọc: ‘Hà nhặt rác bỏ vào thùng.’ Hà bỏ rác ở đâu?|Vào thùng|Xuống ao|Ra đường",
 "Đọc: ‘Bình dậy sớm để tập thể dục.’ Bình dậy sớm để làm gì?|Tập thể dục|Xem phim|Đi ngủ",
 "Đọc: ‘Mai cho bạn mượn bút vì bạn quên bút.’ Vì sao Mai cho mượn bút?|Bạn quên bút|Mai mất bút|Bạn làm gãy bút",
 "Cụm từ chỉ hoạt động nào viết đúng chính tả?|nghỉ ngơi|ngỉ ngơi|nghĩ ngơi",
 "Cụm từ chỉ đồ vật nào viết đúng chính tả?|ghế gỗ|gế gỗ|ghế gổ",
 "Trong ‘Các bạn đang đá bóng’, từ ngữ nào chỉ hoạt động?|đá bóng|các bạn|đang"
 },
 {
 "Từ nào là danh từ chỉ đồ vật?|quyển vở|chạy|xanh",
 "Từ nào là động từ?|nhảy|bàn|đẹp",
 "Từ nào là tính từ?|dịu dàng|quyển sách|đọc",
 "Trong ‘Lan tưới cây’, chủ ngữ là gì?|Lan|tưới cây|cây",
 "Trong ‘Chim hót líu lo’, vị ngữ là gì?|hót líu lo|Chim|líu lo",
 "Trong ‘Buổi sáng, em tập thể dục’, trạng ngữ chỉ gì?|Thời gian|Nơi chốn|Nguyên nhân",
 "Trong ‘Ngoài sân, các bạn đá bóng’, trạng ngữ là gì?|Ngoài sân|các bạn|đá bóng",
 "Câu nào có biện pháp nhân hóa?|Ông mặt trời mỉm cười.|Mặt trời rất sáng.|Mặt trời mọc ở phía đông.",
 "Trong ‘Chị gió thì thầm’, sự vật nào được nhân hóa?|Gió|Mây|Mưa",
 "Câu nào có biện pháp so sánh?|Dòng sông như dải lụa.|Dòng sông chảy qua làng.|Nước sông trong xanh.",
 "Từ nào là từ láy?|lung linh|bàn ghế|sách vở",
 "Từ nào là từ ghép?|bàn ghế|long lanh|lấp lánh",
 "Từ nào trái nghĩa với ‘trung thực’?|gian dối|thật thà|ngay thẳng",
 "Từ nào gần nghĩa với ‘nhân hậu’?|tốt bụng|ích kỉ|độc ác",
 "Câu ‘Ôi, bông hoa đẹp quá!’ biểu lộ điều gì?|Cảm xúc|Câu hỏi|Mệnh lệnh",
 "Câu ‘Em hãy giữ trật tự!’ dùng để làm gì?|Yêu cầu|Kể chuyện|Hỏi thời gian",
 "Trong ‘Mẹ nói: “Con hãy chăm học.”’, dấu hai chấm báo hiệu gì?|Lời nói trực tiếp|Kết thúc đoạn văn|Tên riêng",
 "Điền từ: ‘Bạn nhỏ … trả lại ví cho người mất.’|trung thực|gian dối|lười biếng",
 "Đọc: ‘Cây thiếu nước nên lá héo. Nam liền tưới cây.’ Vì sao lá héo?|Thiếu nước|Thừa ánh sáng|Nam hái lá",
 "Đọc: ‘Hà kiên trì luyện viết mỗi ngày. Chữ Hà dần đẹp hơn.’ Điều gì giúp Hà tiến bộ?|Kiên trì luyện tập|Bỏ luyện viết|Đổi chỗ ngồi"
 },
 {
 "Từ nào đồng nghĩa với ‘bảo vệ’?|giữ gìn|phá hoại|bỏ mặc",
 "Từ nào trái nghĩa với ‘hòa bình’?|chiến tranh|yên ổn|thanh bình",
 "Từ nào đồng nghĩa với ‘cần cù’?|siêng năng|lười nhác|cẩu thả",
 "Trong ‘Lan chăm học. Bạn ấy luôn làm bài đầy đủ’, ‘bạn ấy’ chỉ ai?|Lan|Cô giáo|Mẹ",
 "Trong ‘Tôi thích đọc sách’, từ nào là đại từ xưng hô?|Tôi|thích|sách",
 "Điền từ: ‘Tuy trời mưa … chúng em vẫn đến lớp.’|nhưng|nên|vì",
 "Điền từ: ‘Nếu chăm luyện tập … em sẽ tiến bộ.’|thì|nhưng|vì",
 "Cặp từ ‘vì … nên …’ biểu thị quan hệ gì?|Nguyên nhân – kết quả|Tương phản|Tăng tiến",
 "Cặp từ ‘tuy … nhưng …’ biểu thị quan hệ gì?|Tương phản|Nguyên nhân – kết quả|Tăng tiến",
 "Câu nào là câu ghép?|Mưa tạnh, nắng lên.|Lan đang đọc sách.|Bông hoa rất đẹp.",
 "Trong ‘Mẹ nấu cơm, bố rửa bát’, có mấy vế câu?|Hai|Một|Ba",
 "Trong ‘Trời mưa nên đường trơn’, nguyên nhân là gì?|Trời mưa|Đường trơn|Trời nắng",
 "‘Chân’ trong ‘chân bàn’ được dùng theo nghĩa nào?|Nghĩa chuyển|Nghĩa gốc chỉ bộ phận cơ thể|Tên riêng",
 "‘Mắt’ trong ‘mắt em bé’ được dùng theo nghĩa nào?|Nghĩa gốc|Nghĩa chuyển|Tên địa danh",
 "Trong ‘Bé đá bóng’ và ‘Hòn đá nhỏ’, hai tiếng ‘đá’ là gì?|Từ đồng âm|Từ trái nghĩa|Từ đồng nghĩa",
 "‘Lan có một chú mèo. Nó rất ngoan.’ Từ ‘nó’ thay cho gì?|Chú mèo|Lan|Một đồ chơi",
 "‘Trời mưa to. Vì vậy, đường bị ngập.’ ‘Vì vậy’ giúp làm gì?|Nối hai câu|Đặt tên sự vật|Chỉ màu sắc",
 "‘Cây bàng đã lớn. Cây bàng tỏa bóng mát.’ Hai câu liên kết bằng cách nào?|Lặp từ ngữ|Dùng từ trái nghĩa|Dùng dấu hỏi",
 "Đọc: ‘Dù bài khó, An vẫn thử nhiều cách và tìm ra đáp án.’ An có đức tính gì?|Kiên trì|Lười biếng|Cẩu thả",
 "Đọc: ‘Rừng giữ đất, giữ nước và là nhà của nhiều loài vật.’ Ý chính là gì?|Lợi ích của rừng|Cách làm đồ gỗ|Các màu của lá"
 }
 };
 static List<QuestionBank.Question> build(int grade){
  List<QuestionBank.Question> bank=new ArrayList<>();
  for(String row:DATA[QuestionBank.grade(grade)-1]){String[] v=row.split("\\|",-1);if(v.length!=4)throw new IllegalStateException("Invalid Vietnamese question");bank.add(new QuestionBank.Question(v[0],v[1],v[2],v[3]));}
  return bank;
 }
}
