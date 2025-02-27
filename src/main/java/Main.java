import com.gokalpcoban.Follower;
import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //1-Biyografi bilgisini göster.
        //2-Takipçi sayısını göster.
        //3-Profil resminin linkini ver.
        //4-Takipçi listesini getir.
        //5-Takip ettiği kişileri getir.
        //6-Çıkış
        //kullanıcı adı : johnarrive1212
        //şifre : javatest

        Scanner scanner = new Scanner(System.in);
        String username = null;
        String password = null;
        String islemler = "1-biyografi bilgisini göster\n" + "2-Takipçi sayısını göster\n" + "3-Profil resmi linkini al\n" + "4-Takipçi listesini getir\n" + "5-Takip ettiği kişilerin listesini getir\n" + "6-çıkış";

        System.out.println("INSTAGRAM PROJESİNE HOŞGELDİNİZ");
        System.out.println("kullanıcı adını giriniz : ");
        username = scanner.nextLine();

        System.out.print("şifrenizi giriniz : ");
        password = scanner.nextLine();
        if (username.equals("johnarrive1212") && password.equals("javatest")) {
            Instagram4j instagram = Instagram4j.builder().username(username).password(password).build();
            instagram.setup();
            try {
                instagram.login();
                InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(username));
                System.out.println(islemler);
                String secim = scanner.nextLine();

                if (secim.equals("6")){
                    System.out.println("Uygulama sonlandırılmıştır...");
                } else if (secim.equals("1")) {
                    System.out.println("Biyografi : " + userResult.getUser().biography);
                }else if (secim.equals("2")){
                    System.out.println("Takipçi sayısı : " + userResult.getUser().follower_count);
                    System.out.println("Takip ettiklerimin sayısı : " + userResult.getUser().following_count);
                } else if (secim.equals("3")) {
                    System.out.println("Profil resminin linki : " + userResult.getUser().profile_pic_url);
                } else if (secim.equals("4")) {
                    InstagramGetUserFollowersResult followerList = instagram.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));

                    String takipciİslemler ="1-Mail gönder\n" + "2-Dosyaya yazdır\n" + "3-Console yazdır\n" +"4-Hiçbir şey yapma";

                    String takipciSecim = scanner.nextLine();
                    if(takipciSecim.equals("1")){
                        //mail gönder
                    } else if (takipciSecim.equals("2")) {
                        //dosya yazdır
                        List<Follower> followers = new ArrayList<>();
                        for (InstagramUserSummary fw : followerList.getUsers()){
                            Follower follower = new Follower();
                            follower.setPk(fw.getPk());
                            follower.setUsername(fw.getUsername());
                            follower.setFullName(fw.getFull_name());
                            followers.add(follower);
                        }
                    } else if (takipciSecim.equals("3")) {
                        //console yazdır
                    } else if (takipciSecim.equals("4")) {
                        //hiçbir şey yapma
                    }else {
                        System.out.println("Lütfen 1 ile 4 arasında seçim yapınız.");
                    }
                }

            } catch (ClientProtocolException e) {
                System.out.println("Hata : " + e.getMessage());
            } catch (IOException e){
                System.out.println("Hata : " + e.getMessage());
            }
        } else {
            System.out.println("Kullanıcı adınız veya şifreniz yanlıştır.");
        }
    }

    public static void dosyayaYazdir(File file){

    }
}
