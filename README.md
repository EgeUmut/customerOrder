<h1>Customer Order App</h1>
Bu proje, Galaksiya Bilişim Teknolojileri firması tarafından, 26/03/2024 ile 30/03/2024 tarihleri arasında verilen Java mülakatı için geliştirilmiştir.
<br>
<h2>Projenin kurulumu</h2>
<br>
Projenin kurulumu için proje içinde "docker-compose up -d"  yazılması gereklidir.
<br>
<h2>Database diagram ve nesnelerimiz</h2>
<img src="https://github.com/EgeUmut/customerOrder/assets/45629714/017dea2e-fdbb-4ea4-ae86-3c63eed32f34"/>
<br>
<h2>Projenin Kullanımı</h2>
Projeyi tamamen swagger üzerinden test edeceğiz. Projeyi ayağa kaldırdıktan sonra <a href="http://localhost:8080/swagger-ui/index.html">swagger</a> bağlantısını açıyoruz.
projede basit bir yetkilendirme sistemi bulunmakta ancak hiç atama yapılmadı. Projedeki komutlara erişim için authenticate olmak yeterlidir. Giriş yapılmadığı sürece /Auth altında olmayan hiçbir komuta erişim iznimiz bulunmamaktadır.
<br>
<h4>Kayıt Olma / Giriş Yapma</h4>
Auth-Controller kısmına gelip /Register komutunu açıyoruz. karşımıza şu şekilde bir yer gelmeli:
<br>
<img src="https://github.com/EgeUmut/customerOrder/assets/45629714/7b36f8db-bb16-4b5e-ad7f-59dfa12f6b62"/>
<br>
Burada önemli olan kısımlar sadece Email ve Password alanlarıdır. Diğer alanları varsayılan değerde bırakabilirsiniz. Aynı mail ile kayıtlı biri var ise hata fırlatır.
Şifreyi girdikten sonra unutmamak için basit bir şey girin.(örnek: 12345)
Başarılı bir şekilde kayıt olunca bize StatusCode 200 ve bir token dönüyor.
Kayıt olduktan sonra giriş yapabiliriz. Giriş yaparken mail kontrolü ve şifre kontrolü yapılmaktadır. ikisinden biri yanlış girilirse gerekli hatalar fırlatılır.
Giriş yapmak için /Register ın hemen altındaki /authenticate komutu çalıştırılmalı:
<br>
<img src="https://github.com/EgeUmut/customerOrder/assets/45629714/f1c8198a-3840-4a2f-ba88-16410cd3b612">
<br>
Girişimiz başarılı ise bize aynı şekilde bir token gelmeli. Bu token ı kopyalıyoruz(tırnaklar olmadan).
Swagger'ın en üstüne gidiyoruz ve oradaki Authorize butonuna basıyoruz. Açılan pencerede Value kısmına kopyaladığımız token ı koyuyoruz.
Not: Token ı direkt yapıştırıyoruz. Başına "Bearer" yazılmasına gerek yok.
<br>
<img src="https://github.com/EgeUmut/customerOrder/assets/45629714/403a72c6-ded7-415a-8c0e-1d79f0f91449">
<br>
Giriş yaptıktan sonra projeye tam erişim sahibi oluyoruz.
<br>
<br>
<h4>Örnek Demo</h4>
1- İlk olarak Category-Controller a gelip post işlemi ile bir ya da daha fazla category ekliyoruz.
<br>
<br>
2- Kategori eklediğimize göre product-controller a gidip post işlemi ile product ekleyelim.
<br>
<br>
3- Eklediğimiz productları category name lerine göre arayıp filtreleyebiliriz veya fiyat aralığı verip o fiyat aralığına göre filtreleyebiliriz.
<br>
<br>
4- Order oluşturmak için gerekli her şeye sahibiz. Order-Controller a gidip post işlemi ile sipariş verebiliriz. girilen bilgilerin doğru olduğundan emin olun yoksa gerekli hatalar fırlatılacaktır.
<br>
<br>
5- Order oluşturunca OrderState otomatik olarak 1 yani "Received" olarak kaydedilir. Eğer Order'ı iptal etmek istersen Order-Controller içinde /cancelOrder komutu çalıştırılmalı ve gerekli id verilmeli. Order İptal edilirse OrderState 4 yani "cancelled" olarak.
atanır. bu işlemleri yaparken product-controller a gidip stock sayısı üzerindeki değişimleri görebilirsiniz.(sipariş verince stok azalması , siparişi iptal edince stoğun eski haline gelmesi)
<br>
<br>
6- tek bir kullanıcıya ait Orderları görmek istersek Order-Controller içinde getOrderByUserId komutunu çalıştırabilirsiniz.
<br>
<br>
Demo bu kadar.
<br>
<br>
<h4>Ek Bilgiler</h4>
<br>
Herhangi bir ekleme , silme ve güncelleme metodu çalıştığında sistem loglanmaktadır. Loglar direkt terminale yazdırılıyor. Get metotlarında Log alınmamaktadır. Alınmasını isterseniz istediğiniz metodun üstüne @Loggable annotation u ekleyebilirsiniz.
<br>
örnek log:
<br>
<img src="https://github.com/EgeUmut/customerOrder/assets/45629714/f9062c21-7a20-4fdf-849f-018c8c6ad485">
<br>

