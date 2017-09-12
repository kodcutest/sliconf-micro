# language: tr

#1 - etkinlik sahibi
#2 - soru soran katılımcı
#3 - oy veren katılımcı
#4 - oy veren katılımcı
#5 - etkinlik yöntercileri

Özellik: Yeni Etkinlik oluşturma web versiyonu

  Senaryo: F633 - Sistemle yeni tanışmış çiçeği burnunda etkinlik sahibi etkinlik oluşturuyor, heyecan dorukta
    Senaryo taslağı:
      Diyelim ki <Sisteme üye> bir kullanıcı yeni bir etkinlik oluşturmak istedi
      Eğer ki etkinliğin adı <Minimum> değerden küçük <Maksimum> değerden büyük değilse
      Ve <Etkinliğin tarihi> <Bugün> veya <Daha ileri bir tarih> olarak belirtlişse
      O zaman sistem etkinlik sahibini kayıt eder ve etkinlik oluşturulmuş olur
      Ve sistem etkinliğe özel ve eşşiz bir etkinlik kodu üretir.

    #yorum

    Örnekler:
      | Sisteme üye | Minimum | Maksimum | Etkinliğin tarihi | Bugün      | Daha ileri bir tarih |
      | true        | 4       | 20       | 07.05.2018        | 12.09.2017 | 12.09.2017           |


