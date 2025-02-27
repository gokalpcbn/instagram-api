import com.gokalpcoban.Follower;
import com.gokalpcoban.Following;
import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
                    System.out.println(takipciİslemler);

                    String takipciSecim = scanner.nextLine();
                    if(takipciSecim.equals("1")){
                        //mail gönder
                        //javax mail api jar file
                        StringBuffer buffer = new StringBuffer();
                        int i = 1;
                        for (InstagramUserSummary follower : followerList.getUsers()){
                            buffer.append(i+ ")" +follower.getPk() + " " + follower.getUsername() + "" + follower.getFull_name()+"\n");
                            i++;
                        }

                        MailGonder("jhonarrive11@gmail.com",buffer.toString());

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
                        File file = new File("/Users/gokalpcoban/Desktop/javaiodersleri/follower.bin");
                        if (!file.exists()){
                            file.createNewFile();
                        }
                            writeFollowerToFile(file,followers);
                    } else if (takipciSecim.equals("3")) {
                        //console yazdır
                        int i=1;
                        for (InstagramUserSummary follower : followerList.getUsers()){
                            System.out.println(i + ")" + follower.getPk() + " " + follower.getUsername() + " " + follower.getFull_name());
                            i++;
                        }
                    } else if (takipciSecim.equals("4")) {
                        //hiçbir şey yapma
                        System.out.println("Yapılacak işlem yok...");
                    }else {
                        System.out.println("Lütfen 1 ile 4 arasında seçim yapınız.");
                    }
                }else if (secim.equals("5")){
                    InstagramGetUserFollowersResult followingList = instagram.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));
                    String takipciİslemler ="1-Mail gönder\n" + "2-Dosyaya yazdır\n" + "3-Console yazdır\n" +"4-Hiçbir şey yapma";
                    System.out.println(takipciİslemler);
                    String takipEttiklerimSecim = scanner.nextLine();
                    if (takipEttiklerimSecim.equals("1")){
                        //mail gönder
                        int i=1;
                        StringBuffer buffer = new StringBuffer();
                        for (InstagramUserSummary following : followingList.getUsers()){
                            buffer.append(i+")" + following.pk + " " + following.username + " " + following.full_name+"\n");
                            i++;
                        }
                        MailGonder("jhonarrive11@gmail.com", buffer.toString());
                    } else if (takipEttiklerimSecim.equals("2")) {
                        //dosya yazdır
                        List<Following> followings = new ArrayList<Following>();
                        for (InstagramUserSummary fw : followingList.getUsers()){
                            Following following = new Following(fw.getPk(),fw.getUsername(),fw.getFull_name());
                            followings.add(following);
                        }
                        File file = new File("/Users/gokalpcoban/Desktop/javaiodersleri/follower.bin");
                        if (!file.exists()){
                            file.createNewFile();
                        }
                        writeFollowingToFile(file,followings);


                    } else if (takipEttiklerimSecim.equals("3")) {
                        //console yazdır
                        int i =1;
                        for (InstagramUserSummary following : followingList.getUsers()){
                            System.out.println(i+")"+following.getPk() + " " + following.getUsername() + " " + following.getFull_name());
                            i++;
                        }
                    } else if (takipEttiklerimSecim.equals("4")) {
                        System.out.println("İşlem yapılmayacaktır.");
                    }else {
                        System.out.println("Lütfen 1 ile 4 arasında değer girişi yapınız.");
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

    public static void MailGonder(String to, String icerik)
    {
        String fromEmail = "blogsitemlog@gmail.com";
        String fromPassword="blogsitemlog99";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail,fromPassword);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("TAKIPCI LISTESI");
            message.setText(icerik);
            Transport.send(message);
            System.out.println("Mail başarılı bir şekilde gönderildi.");
        }catch (Exception e ){
            System.out.println("Mail gönderilirken hata oluştu" + e.getMessage());
        }
    }

    public static void writeFollowerToFile(File file, List<Follower> followers){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(followers);
            System.out.println("Takipçi listesi başarılı bir şekilde dosyaya yazdırıldı.");
        }catch (FileNotFoundException e){
            System.out.println("Hata : " + e.getMessage());
        }catch (IOException e){
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public static void writeFollowingToFile (File file, List<Following> following){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(following);
            System.out.println("Takipçi listesi başarılı bir şekilde dosyaya yazdırıldı..");
        }catch (FileNotFoundException e){
            System.out.println("Hata : " + e.getMessage());
        }catch (IOException e){
            System.out.println("Hata : " + e.getMessage());
        }
    }
}
