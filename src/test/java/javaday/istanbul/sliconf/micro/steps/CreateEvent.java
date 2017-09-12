package javaday.istanbul.sliconf.micro.steps;
/*
 * Created by hakdogan on 12/09/2017
 */

import cucumber.api.java.tr.Diyelimki;
import cucumber.api.java.tr.Eğerki;
import cucumber.api.java.tr.Ozaman;

public class CreateEvent {


    @Diyelimki("^true bir kullanıcı yeni bir etkinlik oluşturmak istedi$")
    public void true_bir_kullanıcı_yeni_bir_etkinlik_oluşturmak_istedi() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Eğerki("^etkinliğin adı (\\d+) değerden küçük (\\d+) değerden büyük değilse$")
    public void etkinliğin_adı_değerden_küçük_değerden_büyük_değilse(int min, int max) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Eğerki("^(\\d+)\\.(\\d+)\\.(\\d+) (\\d+)\\.(\\d+)\\.(\\d+) veya (\\d+)\\.(\\d+)\\.(\\d+) olarak belirtlişse$")
    public void veya_olarak_belirtlişse(int etkinlik_tarihi_gun, int etkinlik_tarihi_ay, int etkinlik_tarihi_yil,
                                        int bugun_gun, int bugun_ay, int bugun_yil,
                                        int ileri_tarih_gun, int ileri_tarih_ay, int ileri_tarih_yil) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("*** " + ileri_tarih_gun + " ***");
    }

    @Ozaman("^sistem etkinlik sahibini kayıt eder ve etkinlik oluşturulmuş olur$")
    public void sistem_etkinlik_sahibini_kayıt_eder_ve_etkinlik_oluşturulmuş_olur() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Ozaman("^sistem etkinliğe özel ve eşşiz bir etkinlik kodu üretir\\.$")
    public void sistem_etkinliğe_özel_ve_eşşiz_bir_etkinlik_kodu_üretir() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }


}
