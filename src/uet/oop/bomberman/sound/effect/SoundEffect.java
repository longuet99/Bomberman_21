package uet.oop.bomberman.sound.effect;


import java.io.*;
import java.net.URL;
import java.util.Objects;
import javax.sound.sampled.*;

public enum SoundEffect {
    EXPLODE("boom.wav"),  // sfx bom no
    BGM("bgm.wav"),       // sfx nhac nen
    DEAD("dead.wav"),     // sfx chet
    KILL("kill.wav");     // sfx tieu diet dich

    // Mỗi hiệu ứng âm thanh có clip riêng, được tải bằng tệp âm thanh riêng.
    private Clip clip;

    // Hàm tạo để xây dựng từng phần tử của enum với tệp âm thanh của riêng nó.
    SoundEffect(String soundFileName) {
        try {
            // Sử dụng URL (thay vì Tệp) để đọc từ đĩa và JAR.
            URL url = this.getClass().getResource("/sounds/effects/" + soundFileName);

            // Thiết lập luồng đầu vào âm thanh được truyền từ tệp âm thanh.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(url));
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            // Nhận một nguồn tài nguyên môi.
            clip = (Clip) AudioSystem.getLine(info);
            // Mở đoạn âm thanh và tải mẫu từ luồng đầu vào âm thanh.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        clip.setLoopPoints(0, -1);
    }

    // Phát hoặc phát lại các hiệu ứng âm thanh từ đầu bằng cách tua lại.
    public void play() {
        System.out.println("SoundEffect " + clip.isActive());
        if (clip.isActive()) {
            clip.stop();
            clip.setFramePosition(0);
        }
        clip.start();
    }

    public void playKill() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
    public void playDead() {
        clip.start();
    }
    public void playExpLode() {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
    public void loopInf (){
        if (clip.isActive()){
            return;
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

    // Phương pháp tĩnh tùy chọn để tải trước tất cả các tệp âm thanh.
    public static void init() {
        values(); // gọi hàm tạo cho tất cả các phần tử
    }
}