package uet.oop.bomberman.entities.character.enemy.ai;

public class EasyAI extends AI {

    @Override
    public int calculateDirection() {
        /*
         TODO: cài đặt thuật toán tìm đường đi
         Kẻ địch di chuyển ngẫu nhiên 4 hướng 0 1 2 3
        */
        return this.random.nextInt(4);
    }

}
