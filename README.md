# 📡 FileBeam

Android telefonlar ve Android TV arasında aynı yerel Wi-Fi ağı üzerinden dosya aktarımı ve ekran yansıtma. Kotlin + Jetpack Compose + TCP/UDP.

## Özellikler
- Ham TCP üzerinden doğrudan cihazdan cihaza dosya aktarımı
- Android TV / Leanback başlatıcısı, TV banner'ı ve uzaktan kumanda desteği
- QR kodu ve 6 haneli PIN ile eşleştirme; eşleşme telefonda hatırlanır
- UDP broadcast ile yerel ağdaki alıcı cihazları keşfetme (PIN yayınlanmaz)
- İlerleme, aktarım hızı, tamamlanma bildirimi ve aktarım geçmişi
- Ekran yansıtma: H.264 + uygulama sesi, 30/60/90 FPS profilleri
- Uygulama içi güncelleme: GitHub Releases'ten SHA-256 doğrulamalı APK
- Koyu/açık tema, ayarlar ekranı ve dinamik renkler

## Kullanım
1. TV'de FileBeam'i açıp **Al** ekranını başlatın.
2. Telefonda **Gönder** ekranında TV'yi seçin, QR kodunu okutun veya IP + PIN girin.
3. Dosyaları seçip gönderin. Her iki cihaz da aynı yerel ağda olmalıdır.
4. **Yansıt** ekranından telefon ekranını TV'ye aktarabilirsiniz (TV'de yansıtma alıcısı açık olmalı).
5. Yeni sürümler ana ekrandaki güncelleme kartından veya **Ayarlar → Uygulama güncellemesi** ekranından kurulur.

## Depo düzeni
- `main` tek geliştirme dalıdır; kararlı sürümler buradan çıkar.
- `main`'e yapılan her push, imzalı release APK üretir ve GitHub Releases'e yayımlar. Uygulama içi güncelleme bu yayını takip eder.
- Pull request'ler ve feature dalları yalnızca doğrulama/APK artifact üretir; bu dallardan hiçbir sürüm kullanıcıya yayımlanmaz.

## Kalıcı imzalı APK (önemli)
GitHub Actions her zaman debug APK üretir. Kalıcı release APK yalnızca dört imzalama secret'ı tanımlandığında üretilir. Geçici imza anahtarı kullanılmaz; böylece her derlemede değişen imzayla yanlış bir güncelleme beklentisi oluşturulmaz.

Repository Settings → Secrets and variables → Actions bölümüne aşağıdaki dört secret eklenmelidir:
- `SIGNING_KEYSTORE_BASE64`: aynı release keystore dosyasının Base64 içeriği
- `SIGNING_KEYSTORE_PASSWORD`: keystore parolası
- `SIGNING_KEY_ALIAS`: anahtar takma adı
- `SIGNING_KEY_PASSWORD`: anahtar parolası

**Mevcut kullanıcıların cihazlarında kurulu APK'nın imzasıyla aynı keystore kullanılmalıdır.** Eski keystore kaybolduysa farklı imzalı APK, Android tarafından mevcut uygulamanın üzerine güncelleme olarak kurulamaz; eski uygulamayı kaldırmak veri kaybına yol açabilir.

Secret'lar tanımlı değilse `FileBeam-debug-apk` artifact'i kullanılabilir (mevcut release kurulumunun üzerine yüklenemez). Dört secret doğru tanımlandığında `FileBeam-release-apk` artifact'i içinde APK ve SHA-256 checksum bulunur; workflow imzayı doğrular.

## Teknik protokol
- UDP 47812: PIN içermeyen cihaz keşif beacon'ı
- TCP 47813: dosya aktarımı
- TCP 47814: ekran yansıtma (H.264 paket akışı + isteğe bağlı PCM ses)
- Keşif mesajı: `FILEBEAM2|device|ip|port` (PIN yayınlanmaz)
- Aktarım başlığı: `BEAM2|pin|size|base64url(filename)`
- `size=-1`: sağlayıcı dosya boyutunu bildirmediğinde akış EOF'a kadar aktarılır.
- Başlık, ikili dosya içeriğini önceden okumamak için tamponlamasız okunur.
- Sabit uzunluklu dosyalarda eksik aktarım algılanır; başarısız aktarımın geçici kaydı silinir.
- Alınan dosyalar Android 10+ cihazlarda `İndirilenler/`, daha eski sürümlerde uygulamanın kendi İndirilenler klasörüne kaydedilir.

## Ağ notları
Misafir Wi-Fi ağları veya router üzerindeki AP/client isolation, cihazların birbirini görmesini engelleyebilir. UDP keşfi çalışmazsa QR veya manuel IP bağlantısı denenebilir.
